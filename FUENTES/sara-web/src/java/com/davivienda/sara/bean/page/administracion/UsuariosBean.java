/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.administracion;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.bean.InitBean;
import com.davivienda.sara.constantes.TareaBdConfAccesoAplicacion;
import com.davivienda.sara.dto.UsuarioAplicacionDTO;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import com.davivienda.sara.tablas.confaccesoaplicacion.session.ConfAccesoAplicacionSessionLocal;
import com.davivienda.sara.tareas.usuarioaplicacion.session.UsuarioAplicacionTareasSessionLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletException;
import com.davivienda.sara.tablas.usuarioaplicacion.session.UsuarioAplicacionSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SaraUtil;
import java.util.logging.Level;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "usuariosBean")
@ViewScoped
public class UsuariosBean extends BaseBean implements Serializable {

    @EJB
    UsuarioAplicacionSessionLocal usuarioAplicacionSession;
    @EJB
    ConfAccesoAplicacionSessionLocal confAccesoAplicacionSessionLocal;
    @EJB
    UsuarioAplicacionTareasSessionLocal usuarioAplicacionTareasSessionLocal;

    public ComponenteAjaxObjectContextWeb objectContext;
    public String usuario;
    public String nombre;
    public String restringirIp;
    public String token;
    public String claveEstatica;
    public boolean tipoNormal;
    public boolean bloquearTipoNormal;
    public boolean tipoAuditoria;
    public boolean tipoSistema;
    public boolean bloqueartipoSistema;
    public String usuarioSeleccionado;
    private List<SelectItem> listaUsuarios;
    public boolean mostrarBtnGrabar;
    public boolean mostrarBtnCancelar;
    public int cantidadUsuarios;
    public boolean primeraVez;
    public String opcionNuevoModificar;
    public boolean mostrarModalBorrar;

    private static final String userRegexp = "^[a-zA-Z0-9_.-]*$";
    private static final Pattern userPattern = Pattern.compile(userRegexp);

    /**
     * Creates a new instance of UsuariosBean
     */
    @PostConstruct
    public void UsuariosBean() {
        String error = "";
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            listaUsuarios = new ArrayList<SelectItem>();
            if (objectContext != null) {
                Collection<UsuarioAplicacion> items = usuarioAplicacionSession.getTodos();
                listaUsuarios = cargarListaUsuarios(objectContext.getUsuarioAplicacionCB(items));
                dataInicial();
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void dataInicial() {
        this.usuarioSeleccionado = "1";
        this.primeraVez = true;
        buscarRegistro();
        this.cantidadUsuarios = listaUsuarios.size();
        this.opcionNuevoModificar = "";
        this.mostrarModalBorrar = false;
    }

    public void nuevoUsuario() {
        this.usuario = "";
        this.nombre = "";
        this.restringirIp = "*";
        this.token = "*";
        this.claveEstatica = "*";
        this.tipoNormal = true;
        this.tipoAuditoria = false;
        this.tipoSistema = false;
        this.bloquearTipoNormal = false;
        this.bloqueartipoSistema = false;
        this.mostrarBtnCancelar = true;
        this.mostrarBtnGrabar = true;
        this.opcionNuevoModificar = "NUEVO";
    }

    public void modificarUsuario() {
        this.bloquearTipoNormal = false;
        this.bloqueartipoSistema = false;
        this.mostrarBtnCancelar = true;
        this.mostrarBtnGrabar = true;
        this.opcionNuevoModificar = "MODIFICAR";
    }

    public void abrirModalBorrar() {
        this.mostrarModalBorrar = true;
    }

    public void cerraModalBorrar() {
        this.mostrarModalBorrar = false;
    }

    public void grabarUsuario() {
        try {
            System.err.println("UsuariosBean-grabarUsuario usuario:" + this.usuario + " nombre:" + this.nombre + "  restringirIp:" + this.restringirIp + " token:" + this.token + " claveEstatica:" + this.claveEstatica + " tipoAuditoria:" + this.tipoAuditoria + " tipoNormal:" + this.tipoNormal + " tipoSistema:" + this.tipoSistema);
            Date fecha = new Date();
            fecha = com.davivienda.utilidades.conversion.Fecha.getDateHoy();
            Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
            InitBean initBean = (InitBean) viewMap.get("initBean");

            UsuarioAplicacion registroEntity = cargarUsuarioAplicacion();

            if (this.opcionNuevoModificar.equals("NUEVO")) {
                usuarioAplicacionTareasSessionLocal.guardarRegTareasAdminUsuario(registroEntity.getUsuario(),
                        (long) 0,
                        (short) TareaBdConfAccesoAplicacion.INSERTAREGUSUARIO.getTareaBD().intValue(),
                        initBean.getObjectContext().getUsuario(),
                        registroEntity.getNombre(),
                        fecha);
                usuarioAplicacionTareasSessionLocal.addBorrarRegAccesoUsuarioDesdeApp();
                //confAccesoAplicacionSessionLocal.AddBorrarRegAccesoUsuario();
                abrirModal("SARA", "Se ha guardado exitosamente el registro", null);
            } else if (this.opcionNuevoModificar.equals("MODIFICAR")) {
                usuarioAplicacionSession.actualizar(registroEntity);
                usuarioAplicacionSession.actualizarPersistencia();
                abrirModal("SARA", "Se ha actualizado exitosamente el registro", null);
            }
            Collection<UsuarioAplicacion> items = usuarioAplicacionSession.getTodos();
            listaUsuarios.clear();
            listaUsuarios = cargarListaUsuarios(objectContext.getUsuarioAplicacionCB(items));
            this.opcionNuevoModificar = "";
            this.mostrarBtnCancelar = false;
            this.mostrarBtnGrabar = false;
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Ha llegado al último registro", null);
        } catch (EJBException | EntityServicioExcepcion ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

    }

    private UsuarioAplicacion cargarUsuarioAplicacion() throws ServletException {
        UsuarioAplicacion user = new UsuarioAplicacion();

        String str = "";

        str = this.usuario;
        if (null ==str || str.length() == 0) {
            throw new ServletException("No se puede obtener el identificador del usuario");
        }
        user.setUsuario(str);

        str = this.nombre;
        if (null ==str || str.length() == 0) {
            throw new ServletException("No se puede obtener el nombre del usuario");
        }
        user.setNombre(str);

        str = this.restringirIp;
        if (null ==str || str.length() == 0) {
            throw new ServletException("No se puede obtener la dirección IP restringida del usuario");
        }
        user.setDireccionIp(str);

        str = this.token;
        if (null ==str || str.length() == 0) {
            throw new ServletException("No se puede obtener el identificador del token asociado al usuario");
        }
        user.setToken(str);

        str = this.claveEstatica;
        if (str.length() == 0) {
            throw new ServletException("No se puede obtener la clave estatica asociada al usuario");
        }

        if (this.tipoAuditoria) {
            user.setAuditoria(new Short("1"));
        } else {
            user.setAuditoria(new Short("0"));
        }

        if (this.tipoNormal) {
            user.setNormal(new Short("1"));
        } else {
            user.setNormal(new Short("0"));
        }

        if (this.tipoSistema) {
            user.setSistema(new Short("1"));
        } else {
            user.setSistema(new Short("0"));
        }

        return user;
    }

    public void buscarRegistro() {

        this.opcionNuevoModificar = "";
        String user = "";
        try {
            if (!this.usuarioSeleccionado.equals("")) {
                user = obtenerUsuario("BUSCAR");
                UsuarioAplicacion registroEntity = new UsuarioAplicacion(user);
                registroEntity = usuarioAplicacionSession.buscar(registroEntity.getUsuario());
                usuarioConsulta(objectContext.getUsuarioAplicacionDTO(registroEntity));
            }
            if (this.primeraVez) {
                this.primeraVez = false;
            }
        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Ha llegado al último registro ", null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

    }

    public void usuarioConsulta(UsuarioAplicacionDTO registroEntity) {
        this.usuario = registroEntity.getUsuario();
        this.nombre = registroEntity.getNombre();
        this.restringirIp = registroEntity.getDireccionIP();
        this.token = registroEntity.getToken();
        this.claveEstatica = registroEntity.getClaveEstatica();
        this.tipoNormal = registroEntity.getNormal().equals("1");
        this.tipoAuditoria = registroEntity.getAuditoria().equals("1");
        this.tipoSistema = registroEntity.getSistema().equals("1");

        this.mostrarBtnGrabar = false;
        this.mostrarBtnCancelar = false;

        if (registroEntity.getAuditoria().equals("1")) {
            this.bloquearTipoNormal = true;
            this.bloqueartipoSistema = true;
        }
    }

    public void borrarUsuario() {
        String error = "";

        Date fecha = new Date();
        fecha = com.davivienda.utilidades.conversion.Fecha.getDateHoy();
        try {
            Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
            InitBean initBean = (InitBean) viewMap.get("initBean");
            UsuarioAplicacion registroEntity = cargarUsuarioAplicacion();
            usuarioAplicacionTareasSessionLocal.guardarRegTareasAdminUsuario(registroEntity.getUsuario(),
                    (long) 0,
                    (short) TareaBdConfAccesoAplicacion.BORRATODOSUSUARIO.getTareaBD().intValue(),
                    initBean.getObjectContext().getUsuario(),//AQUI va el usuario en session
                    "",
                    fecha);
            usuarioAplicacionTareasSessionLocal.addBorrarRegAccesoUsuarioDesdeApp();
            //confAccesoAplicacionSessionLocal.AddBorrarRegAccesoUsuario();
            Collection<UsuarioAplicacion> items = usuarioAplicacionSession.getTodos();
            listaUsuarios.clear();
            listaUsuarios = cargarListaUsuarios(objectContext.getUsuarioAplicacionCB(items));
            this.mostrarModalBorrar = false;
            dataInicial();
            abrirModal("SARA", "Se ha borrado exitosamente el usuario ", null);
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Ha llegado al último registro", null);
        } catch (EJBException | EntityServicioExcepcion ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
        this.mostrarBtnGrabar = false;
        this.mostrarBtnCancelar = false;
        System.err.println("UsuariosBean-borrarUsuario usuario:" + this.usuario + " nombre:" + this.nombre + "  restringirIp:" + this.restringirIp + " token:" + this.token + " claveEstatica:" + this.claveEstatica + " tipoAuditoria:" + this.tipoAuditoria + " tipoNormal:" + this.tipoNormal + " tipoSistema:" + this.tipoSistema);
    }

    public void anteriorUsuario() {

        String user = "";
        try {
            user = obtenerUsuario("ANTERIOR");
            if (user.length() > 0) {
                UsuarioAplicacion registroEntity = new UsuarioAplicacion(user);
                registroEntity = usuarioAplicacionSession.buscar(registroEntity.getUsuario());
                usuarioConsulta(objectContext.getUsuarioAplicacionDTO(registroEntity));

            }
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Ha llegado al último registro", null);
        } catch (EJBException | EntityServicioExcepcion ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

    }

    public void siguienteUsuario() {
        String user = "";
        try {
            user = obtenerUsuario("SIGUIENTE");
            if (user.length() > 0) {
                UsuarioAplicacion registroEntity = new UsuarioAplicacion(user);
                registroEntity = usuarioAplicacionSession.buscar(registroEntity.getUsuario());
                usuarioConsulta(objectContext.getUsuarioAplicacionDTO(registroEntity));
            } else {
                abrirModal("SARA", "Ha llegado al último registro ", null);
            }

        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Ha llegado al último registro ", null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

    }

    public String obtenerUsuario(String operUsuario) {
        String idUsuario = "1";
        String user = "";

        switch (operUsuario) {
            case "BUSCAR":
                idUsuario = this.usuarioSeleccionado;
                for (SelectItem lista : listaUsuarios) {
                    if (String.valueOf(lista.getValue()).equals(idUsuario)) {
                        user = lista.getLabel();
                        this.usuarioSeleccionado = idUsuario;
                        break;
                    }
                }
                break;
            case "ANTERIOR": {
                int cUser = Integer.parseInt(this.usuarioSeleccionado);

                cUser = (cUser - 1);
                if (cUser >= 1) {
                    idUsuario = String.valueOf(cUser);
                    for (SelectItem lista : listaUsuarios) {
                        if (String.valueOf(lista.getValue()).equals(idUsuario)) {
                            user = lista.getLabel();
                            this.usuarioSeleccionado = idUsuario;
                            break;
                        }
                    }
                }
                break;
            }
            case "SIGUIENTE": {
                int cUser = Integer.parseInt(this.usuarioSeleccionado);
                cUser = (cUser + 1);
                if (cUser <= this.cantidadUsuarios) {
                    idUsuario = String.valueOf(cUser);
                    for (SelectItem lista : listaUsuarios) {
                        if (String.valueOf(lista.getValue()).equals(idUsuario)) {
                            user = lista.getLabel();
                            this.usuarioSeleccionado = idUsuario;
                            break;
                        }
                    }
                }
                break;
            }
            default:
                break;
        }
        return user;
    }

    private List<SelectItem> cargarListaUsuarios(List<UsuarioAplicacionDTO> usuarioAplicacionCB) {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem(0, "");
        lista.add(item);
        int cont = 1;
        for (UsuarioAplicacionDTO dto : usuarioAplicacionCB) {
            item = new SelectItem(cont, dto.getUsuario());
            lista.add(item);
            cont++;
        }
        return lista;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        if (usuario != null) {
            usuario = SaraUtil.stripXSS(usuario, Constantes.REGEX_ACEPTAR_DEFAULT);
            Matcher m = userPattern.matcher(usuario);
            if (usuario.trim().length() > 18 || !m.find()) {
                abrirModal("SARA", "Usuario inválido", null);
                return;
            }
            this.usuario = usuario;
        }else{
            abrirModal("SARA", "Usuario inválido", null);
        }
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRestringirIp() {
        return restringirIp;
    }

    public void setRestringirIp(String restringirIp) {
        this.restringirIp = restringirIp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClaveEstatica() {
        return claveEstatica;
    }

    public void setClaveEstatica(String claveEstatica) {
        this.claveEstatica = claveEstatica;
    }

    public boolean isTipoNormal() {
        return tipoNormal;
    }

    public void setTipoNormal(boolean tipoNormal) {
        this.tipoNormal = tipoNormal;
    }

    public boolean isTipoAuditoria() {
        return tipoAuditoria;
    }

    public void setTipoAuditoria(boolean tipoAuditoria) {
        this.tipoAuditoria = tipoAuditoria;
    }

    public boolean isTipoSistema() {
        return tipoSistema;
    }

    public void setTipoSistema(boolean tipoSistema) {
        this.tipoSistema = tipoSistema;
    }

    public boolean isMostrarBtnGrabar() {
        return mostrarBtnGrabar;
    }

    public void setMostrarBtnGrabar(boolean mostrarBtnGrabar) {
        this.mostrarBtnGrabar = mostrarBtnGrabar;
    }

    public boolean isMostrarBtnCancelar() {
        return mostrarBtnCancelar;
    }

    public void setMostrarBtnCancelar(boolean mostrarBtnCancelar) {
        this.mostrarBtnCancelar = mostrarBtnCancelar;
    }

    public boolean isBloquearTipoNormal() {
        return bloquearTipoNormal;
    }

    public void setBloquearTipoNormal(boolean bloquearTipoNormal) {
        this.bloquearTipoNormal = bloquearTipoNormal;
    }

    public boolean isBloqueartipoSistema() {
        return bloqueartipoSistema;
    }

    public void setBloqueartipoSistema(boolean bloqueartipoSistema) {
        this.bloqueartipoSistema = bloqueartipoSistema;
    }

    public String getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(String usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public List<SelectItem> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<SelectItem> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public boolean isMostrarModalBorrar() {
        return mostrarModalBorrar;
    }

    public void setMostrarModalBorrar(boolean mostrarModalBorrar) {
        this.mostrarModalBorrar = mostrarModalBorrar;
    }

}
