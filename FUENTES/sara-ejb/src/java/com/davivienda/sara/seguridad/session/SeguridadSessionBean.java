package com.davivienda.sara.seguridad.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.entitys.seguridad.ServicioAplicacion;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import com.davivienda.sara.tablas.servicioaplicacion.servicio.ServicioAplicacionServicio;
import com.davivienda.sara.tablas.usuarioaplicacion.servicio.UsuarioAplicacionServicio;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SaraUtil;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * SeguridadSessionBean - 16/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Stateless
public class SeguridadSessionBean implements SeguridadSessionLocal {
    
    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_ACCESO);
    
    @PersistenceContext
    private EntityManager em;
    private UsuarioAplicacionServicio servicio;
    private ServicioAplicacionServicio servicioAplicacionServicio;
    private SaraConfig configApp;

    @PostConstruct
    public void postConstructor() {
        servicio = new UsuarioAplicacionServicio(em);
        servicioAplicacionServicio = new ServicioAplicacionServicio(em);
        configApp = servicio.getConfigApp();        
    }

    public UsuarioAplicacion validarAccesoAplicacion(String idUsuario, String direccionIP) {
        return validarAccesoAplicacion(idUsuario, direccionIP, null, null);
    }

    public UsuarioAplicacion validarAccesoAplicacion(String idUsuario, String direccionIP, String token, String claveEstatica) {
        //cambiado Alvaro
        idUsuario=SaraUtil.stripXSS(idUsuario.toLowerCase(),Constantes.REGEX_ACEPTAR_DEFAULT);
        UsuarioAplicacion usuario = null;
        Boolean usuarioOk = false;
        Boolean tokenOk = false;
        Boolean direccionIPOk = false;
        Boolean claveEstaticaOk = false;
        
        logger.info("Validando usuario '" + idUsuario + "' en BD");
        
        // Se valida si existe el usuario
        try {
            usuario = servicio.buscar(idUsuario);            
            if (usuario != null) {
                em.refresh(usuario);
                usuarioOk = true;
            }
        } catch (EntityServicioExcepcion ex) {
            configApp.loggerApp.log(Level.SEVERE, null, ex);
            usuario = null;
            usuarioOk = false;
        }
        // Se valida el token del usuario 
        if (usuarioOk && usuario.getToken() != null) {
            String tokenRegistrado = usuario.getToken();
            if (!tokenRegistrado.equals("*")) {
                // Proceso de validación aca....
                tokenOk = true; // Por ahora no se valida el token

                configApp.loggerApp.info("Se solicita la validación de token y no existe");
            } else {
                tokenOk = true;
            }
        }


        // Se valida la dirección asignada para todas se debe utilizar "*" o "*.*.*.*"
        if (usuarioOk && usuario.getDireccionIp() != null && usuario.getDireccionIp().length() > 0) {
            String dirIpRegistrada = usuario.getDireccionIp(); // "127.*.0.2;128.*.*.*";

            if (!dirIpRegistrada.equals("*") || !dirIpRegistrada.equals("*.*.*.*")) {
                if (direccionIP != null && direccionIP.length() > 6) {
                    dirIpRegistrada = dirIpRegistrada.replaceAll("[*]", "([0-9])*");
                    String[] dirsIp = dirIpRegistrada.split(";");
                    for (String string : dirsIp) {
                        Pattern p = Pattern.compile(string);
                        Matcher m = p.matcher(direccionIP);
                        while (m.find()) {
                            direccionIPOk = true;
                        }
                    }
                }
            } else {
                direccionIPOk = true;
            }
        }

        // Se valida clave estática
        if (usuarioOk && usuario.getClaveEstatica() != null && usuario.getClaveEstatica().length() > 0) {
            String claveEstaticaRegistrada = usuario.getClaveEstatica();
            if (!claveEstaticaRegistrada.equals("*")) {
                if (claveEstatica != null && usuario.getClaveEstatica().equals(claveEstatica)) {
                    claveEstaticaOk = true;
                }
            } else {
                claveEstaticaOk = true;
            }
        }
        if (!usuarioOk || !tokenOk || !direccionIPOk || !claveEstaticaOk) {
            configApp.loggerAcceso.info("El usuario " + idUsuario + " desde la IP " + direccionIP + " NO se encuentra registrado en BD.");
            usuario = null;
        } else {
            configApp.loggerAcceso.info("El usuario " + idUsuario + " desde la IP " + direccionIP + " se encuentra registrado en BD.");
        }
        return usuario;
    }

    public String getPrivilegiosUsuarioJSon(String idUsuario) {
//        return servicio.getPrivilegiosUsuarioJSon(idUsuario);
        return "";
    }
    
    /**
     * Retorna la configuración de acceso del usuario
     * @param idUsuario
     * @return
     */
    public Collection<ConfAccesoAplicacion> getConfAccesoAplicacion(String idUsuario) {
        return servicio.getConfAccesoAplicacion(idUsuario);
    }
    
    public Collection<ServicioAplicacion> geServiciosAplicacion() {
        return servicioAplicacionServicio.getTodos();
    } 
}
