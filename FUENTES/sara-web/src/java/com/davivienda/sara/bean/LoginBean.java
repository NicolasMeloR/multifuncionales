/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean;

import com.davivienda.sara.administracion.session.SaraConfiguracionSession;
import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.dto.UsuarioAplicacionDTO;
import com.davivienda.sara.entitys.ControlUsuarioAplicacion;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import com.davivienda.sara.procesos.autenticacion.webservice.session.AutenticacionLdapWSProcesosSessionLocal;
import com.davivienda.sara.seguridad.session.SeguridadSessionLocal;
import com.davivienda.sara.tablas.controlusuarioaplicacion.session.ControlUsuarioAplicacionSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SaraUtil;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.rmi.CORBA.Util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.davivienda.utilidades.Hex;
import com.davivienda.utilidades.Base64;
/** ehlopez 27-02-2020*/

/**
 *
 * @author ocaldero
 */

@ManagedBean(name = "loginBean")
@ViewScoped
public class LoginBean extends BaseBean implements Serializable {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_ACCESO);

    private String usuario;
    private transient String clave;
    private String tituloModal;
    private String descripcionModal;
    private boolean mostrarModalMsg;
    
/** ehlopez 27-02-2020*/
    private String llavePrivada;
    private String llavePublica;
    
    private String usuarioOculto;
    private String claveOculto;    
/** ehlopez 27-02-2020*/
    
    @EJB
    SeguridadSessionLocal seguridadSession;

    @EJB
    ControlUsuarioAplicacionSessionLocal controlUsuarioSession;

    @EJB
    AutenticacionLdapWSProcesosSessionLocal autenticacionLdapWSProcesosSessionLocal;

    @EJB
    SaraConfiguracionSession saraConfiguracionSession;

    @PostConstruct
    public void LoginBean() {
        generarLlaves();

        if (Boolean.parseBoolean(String.valueOf(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constantes.CSRFTOKEN_ATTR)))) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
            abrirModal("SARA", "No se pudo procesar la solicitud, intente de nuevo", null);
        }
    }

    public String autenticar() {        
        System.out.println("Pruebas iniciales del sistema");
        logger.info("Valor usuario oculto: " + usuarioOculto );
        /** ehlopez 27-02-2020*/
        if (!usuarioOculto.trim().equals("")){ 
            usuario = desencriptar(usuarioOculto);
            logger.info("Valor usuario desencriptado: " + usuario );
        }
        if (!claveOculto.trim().equals("")){ 
            clave = desencriptar(claveOculto);
            logger.info("Valor password desencriptado: " + clave );
        }
        
        /** ehlopez 27-02-2020*/
        usuario = SaraUtil.stripXSS(usuario, Constantes.REGEX_ACEPTAR_DEFAULT);
        logger.log(Level.FINEST, "Autenticando usuario " + usuario);
        UsuarioAplicacion usuarioAplicacion = null;
        try {
            if (usuario == null || usuario.trim().length() == 0
                    || clave == null || clave.trim().length() == 0) {
                throw new Exception("Los campos usuario y clave son requeridos");
            }
            ComponenteAjaxObjectContextWeb objectContext = new ComponenteAjaxObjectContextWeb(getRequestFaces(), getResponseFaces());
            usuarioAplicacion = seguridadSession.validarAccesoAplicacion(usuario, objectContext.getdireccionIP());
            if (usuarioAplicacion != null) {

                //Validando multisesion del usuario a logear
                ControlUsuarioAplicacion usuarioSesion = controlUsuarioSession.getControlUsuarioAplicacion(usuario);
                int tiempoLogin = saraConfiguracionSession.getAppConfig().TIEMPO_LOGIN;

                if (usuarioSesion != null) {
                    logger.log(Level.INFO, "Autenticando:Se encontr\u00f3 registro en tabla control_usuario_aplicacion: {0}", usuario);
                    Date fechaIngreso = usuarioSesion.getFechaIngreso();

                    if (0 == tiempoLogin) {
                        tiempoLogin = Constantes.TIEMPO_LOGIN_DEFAULT;
                    }

                    fechaIngreso.setMinutes(fechaIngreso.getMinutes() + tiempoLogin);

                    if (new Date().compareTo(fechaIngreso) > 0) {
                        logger.log(Level.INFO, "Autenticando: Se encontr\u00f3 registro en tabla control_usuario_aplicacion pero el tiempo de sesion expir\u00f3 para: {0} ", usuario);
                        return autenticarLdap(usuarioAplicacion, objectContext, usuarioSesion);
                    } else {
                        logger.log(Level.INFO, "Autenticando: Se encontr\u00f3 registro en tabla control_usuario_aplicacion, no permitir ingreso su tiempo de sesion aún no expira para: {0} ", usuario);
                        throw new Exception("Ya existe un sesión abierta para el usuario " + usuario);
                    }
                } else {
                    logger.log(Level.INFO, "Autenticando usuario primera vez, no se encontr\u00f3 registro en tabla control_usuario_aplicacion: {0}", usuario);
                    return autenticarLdap(usuarioAplicacion, objectContext, null);
                }
            } else {
                throw new Exception("Usuario no esta registrado en base de datos SARA");
            }
        } catch (Exception e) {
            this.abrirModal("SARA", " Error al autenticar usuario: " + e.getMessage(), null);
            logger.log(Level.SEVERE, "Error al autenticar usuario: " + e.getMessage(), e);
        }
        return null;
    }

    public String autenticarLdap(UsuarioAplicacion usuarioAplicacion, ComponenteAjaxObjectContextWeb objectContext, ControlUsuarioAplicacion usuarioSesion) throws Exception {
        String respuesta = autenticacionLdapWSProcesosSessionLocal.autenticarLdap(usuario, clave);
//        String respuesta = "B;GG-Servicios Automatas";

        if ("B".equals(respuesta.substring(0, 1))) {
            Collection<ConfAccesoAplicacion> confAccesoAplicacion = seguridadSession.getConfAccesoAplicacion(usuarioAplicacion.getUsuario());
            objectContext.setPrivilegiosAccesoEnSesion(confAccesoAplicacion);
            usuarioAplicacion.setConfAccesoAplicacionCollection(confAccesoAplicacion);
            UsuarioAplicacionDTO usuarioAplicacionDTO = objectContext.getUsuarioAplicacionDTO(usuarioAplicacion);
            this.getRequestFaces().getSession().setAttribute(Constantes.SESSION_SECURITY_DTO, usuarioAplicacionDTO);

            String ip = objectContext.getIpFromRequest(getRequestFaces());

            if (null != usuarioSesion) {
                logger.log(Level.INFO, "Actualizando registro en tabla control_usuario_aplicacion");
                usuarioSesion.setFechaIngreso(new Date());
                usuarioSesion.setDireccionIp(ip);
                controlUsuarioSession.actualizar(usuarioSesion);
            } else {
                logger.log(Level.INFO, "Creando registro en tabla control_usuario_aplicacion");
                usuarioSesion = new ControlUsuarioAplicacion();
                usuarioSesion.setFechaIngreso(new Date());
                usuarioSesion.setUsuario(usuario);
                usuarioSesion.setDireccionIp(ip);
                controlUsuarioSession.adicionar(usuarioSesion);
            }

            controlUsuarioSession.actualizarPersistencia();

            try {
                String randomId = generateRandomId();
                this.getRequestFaces().getSession().setAttribute(Constantes.CSRFTOKEN_NAME, randomId);
                logger.log(Level.INFO, "LoginBean --> autenticarLdap Sesión creada " + randomId);
            } catch (NoSuchAlgorithmException ex) {
                System.err.println(ex);
            }

            logger.log(Level.INFO, "Inicio sesi\u00f3n {0} desde {1} con privilegio {2}", new Object[]{usuarioAplicacion.getUsuario(), objectContext.getdireccionIP(), (usuarioAplicacion.getNormal() == 1) ? "NORMAL" : (usuarioAplicacion.getAuditoria() == 1) ? "AUDITORIA" : (usuarioAplicacion.getSistema() == 1) ? "SISTEMA" : "NO DEFINIDO"});
            return "/pages/sara.xhtml?faces-redirect=true";
        } else {
            throw new Exception("Ha ocurrido un error durante la Autenticación LDAP");
        }
    }

    @Override
    public void abrirModal(String tituloModal, String descripcionModal, Exception ex) {
        this.setTituloModal(tituloModal);
        String mensaje = descripcionModal;
        if (ex != null) {
            mensaje += "\nError : " + ex.getClass().getName();
            mensaje += "\n" + ex.getMessage();
            mensaje += "\n" + ex.getLocalizedMessage();
        }
        this.setDescripcionModal(mensaje);
        this.setMostrarModalMsg(true);
    }

    public void cerrarModal() {
        this.descripcionModal = "";
        this.mostrarModalMsg = false;
    }

    private String generateRandomId() throws NoSuchAlgorithmException {
        SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

        //generate a random number
        String randomNum = new Integer(prng.nextInt()).toString();

        //get its digest
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] result = sha.digest(randomNum.getBytes());

        return (hexEncode(result));
    }

    static private String hexEncode(byte[] aInput) {
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int idx = 0; idx < aInput.length; ++idx) {
            byte b = aInput[idx];
            result.append(digits[(b & 0xf0) >> 4]);
            result.append(digits[b & 0x0f]);
        }
        return result.toString();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTituloModal() {
        return tituloModal;
    }

    public void setTituloModal(String tituloModal) {
        this.tituloModal = tituloModal;
    }

    public String getDescripcionModal() {
        return descripcionModal;
    }

    public void setDescripcionModal(String descripcionModal) {
        this.descripcionModal = descripcionModal;
    }

    public boolean isMostrarModalMsg() {
        return mostrarModalMsg;
    }

    public void setMostrarModalMsg(boolean mostrarModalMsg) {
        this.mostrarModalMsg = mostrarModalMsg;
    }

    public String getVersion() {
        return Constantes.VERSION_APP;
    }
    
    /** ehlopez 27-02-2020*/
    public String getLlavePrivada() {
        return llavePrivada;
    }

    public void setLlavePrivada(String llavePrivada) {
        this.llavePrivada = llavePrivada;
    }

    public String getLlavePublica() {
        return llavePublica;
    }

    public void setLlavePublica(String llavePublica) {
        this.llavePublica = llavePublica;
    }

    public String getUsuarioOculto() {
        return usuarioOculto;
    }

    public void setUsuarioOculto(String usuarioOculto) {
        this.usuarioOculto = usuarioOculto;
    }

    public String getClaveOculto() {
        return claveOculto;
    }

    public void setClaveOculto(String claveOculto) {
        this.claveOculto = claveOculto;
    }

    /**
     *Método que desencripta los datos de la vista
     * @param contrasenaEncriptada
     * @throws Exception
     */
    public String desencriptar(String encryptString)  {
        String bytesString = null;
        String decodeBase64String = null;
        try {
            SecretKey key;
            key = new SecretKeySpec(Base64.decode(this.llavePublica), "AES");
            AlgorithmParameterSpec iv = new IvParameterSpec(Base64.decode(this.llavePrivada));
            byte[] decodeBase64 = Base64.decode(encryptString);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            Cipher cipher = Cipher.getInstance(ConstantesApp.TIPO_ENCRIPCION);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            decodeBase64String = new String(cipher.doFinal(decodeBase64), StandardCharsets.UTF_8);
            byte[] bytes = Hex.fromHexString(decodeBase64String);
            bytesString = new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println(new Date() + " ERROR AL DESENCRIPTAR LOS DATOS DEL FORMULARIO :: " + e.getMessage());
            e.printStackTrace();
        }
        return bytesString;
    } 
    
    
    /**
     * Método que genera las llaves para descencriptar los datos de la vista
     */
    private void generarLlaves() {
        this.llavePrivada = null;
        this.llavePublica = null;
        StringBuilder idTransaccion = new StringBuilder("");
        StringBuilder idTransaccion2 = new StringBuilder("");
        try {
            Calendar fechaHoy = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSSS");
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("SSSssmmHHddMM");
            idTransaccion.append("PU");
            idTransaccion.append(dateFormat.format(fechaHoy.getTime()));
            idTransaccion.append("K");
            idTransaccion2.append("PR");
            idTransaccion2.append(dateFormat1.format(fechaHoy.getTime()));
            idTransaccion2.append("K");
            this.llavePublica = Base64.encode(idTransaccion.toString().getBytes());
            this.llavePrivada = Base64.encode(idTransaccion.toString().getBytes());
        } catch (Exception e) {
        	System.out.println(new Date() + " ERROR AL GENERAR LLAVE DE CIFRADO :: " + e.getMessage());
        }
    }
    
    /** ehlopez 27-02-2020*/
}
