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
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.dto.RegionalDTO;
import com.davivienda.sara.entitys.Regional;
import com.davivienda.sara.tablas.regional.session.RegionalSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.JSon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletException;
import javax.xml.rpc.encoding.Serializer;

/**
 *
 * @author jmcastel
 */
@ManagedBean(name = "regionalBean")
@ViewScoped
public class regionalBean extends BaseBean implements Serializable {

    @EJB
    RegionalSessionLocal regionalSession;

    private boolean mostrarBtnGrabar;
    private boolean mostrarBtnCancelar;
    private String usuarioSeleccionado;
    private boolean primeraVez;
    private int cantidadZona;
    private String codigo;
    private String nombre;

    private List<SelectItem> listaRegionales;

    public ComponenteAjaxObjectContextWeb objectContext;

    @PostConstruct
    public void RegionalBean() {

        String error = "";
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            this.setListaRegionales(new ArrayList<SelectItem>());
            if (objectContext != null) {
                Collection<Regional> items = regionalSession.getTodos();
                this.setListaRegionales(cargarListaRegional(objectContext.getRegionalCB(items)));
                this.setUsuarioSeleccionado("");
                this.setPrimeraVez(true);
                usuarioInicial();
                this.setCantidadZona(this.getListaRegionales().size());
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void usuarioInicial() {
        System.err.println("Regional-usuarioInicial: Inicio");
        if (!this.listaRegionales.isEmpty()) {
            this.codigo = String.valueOf(this.listaRegionales.get(1).getLabel().split("-")[0]);
            String nombre = this.listaRegionales.get(1).getLabel().split("-")[1];
            this.nombre = nombre.trim();
        }
        this.mostrarBtnGrabar = false;
        this.mostrarBtnCancelar = false;
        System.err.println("Regional-usuarioInicial: Final");
    }

    public void nuevoUsuario() {
        this.setCodigo("");
        this.setNombre("");
        this.mostrarBtnCancelar = true;
        this.mostrarBtnGrabar = true;
    }

    public void modificarUsuario() {
        this.mostrarBtnCancelar = true;
        this.mostrarBtnGrabar = true;
    }

    public void grabarUsuario() {
        String resp = "";
        try {
            Regional registroEntity = cargarRegionalAplicacion();
            if (registroEntity == null) {
                abrirModal("SARA", "Los campos Código y Nombre son obligatorios", null);
            } else {
                regionalSession.actualizar(registroEntity);
                Collection<Regional> items = regionalSession.getTodos();
                this.setListaRegionales(cargarListaRegional(objectContext.getRegionalCB(items)));
                abrirModal("SARA", "Se ha guardado exitosamente el registro", null);
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
        this.mostrarBtnGrabar = false;
        this.mostrarBtnCancelar = false;
    }

    public void buscarRegistro() {
        String error = "";
        String user = "";
        try {
            if (!this.usuarioSeleccionado.equals("")) {
                user = obtenerUsuario("BUSCAR");
                Regional registroEntity = new Regional(Integer.parseInt(user.split("-")[0].trim()));
                registroEntity = regionalSession.buscar(registroEntity.getIdRegional());
                usuarioConvert(objectContext.getRegionalDTO(registroEntity));
                this.usuarioSeleccionado = String.valueOf(buscarCodUsuario(user));
            }
            if (this.isPrimeraVez()) {
                this.setUsuarioSeleccionado("0");
                this.setPrimeraVez(false);
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

    public void usuarioConvert(RegionalDTO registroEntity) {
        this.setCodigo(String.valueOf(registroEntity.getIdRegional()));
        this.setNombre(registroEntity.getRegional());
        this.setMostrarBtnGrabar(false);
        this.setMostrarBtnCancelar(false);
    }

    public void borrarUsuario() {
        String resp = "";
        try {
            Regional registroEntity = cargarRegionalAplicacion();
            if (registroEntity == null) {
                abrirModal("SARA", "Los campos Código y Nombre son obligatorios", null);
            } else {
                regionalSession.borrar(registroEntity);
                Collection<Regional> items = regionalSession.getTodos();
                this.setListaRegionales(cargarListaRegional(objectContext.getRegionalCB(items)));
                usuarioInicial();
                abrirModal("SARA", "Se ha borrado exitosamente el registro", null);
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

    public void anteriorUsuario() {

        String error = "";
        String user = "";
        try {
            user = obtenerUsuario("ANTERIOR");
            if (!user.isEmpty()) {
                Regional registroEntity = new Regional(Integer.parseInt(user.split("-")[0].trim()));
                registroEntity = regionalSession.buscar(registroEntity.getIdRegional());
                usuarioConvert(objectContext.getRegionalDTO(registroEntity));
                this.usuarioSeleccionado = String.valueOf(buscarCodUsuario(user));
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

        String error = "";
        String user = "";
        try {
            user = obtenerUsuario("SIGUIENTE");
            if (!user.isEmpty()) {
                Regional registroEntity = new Regional(Integer.parseInt(user.split("-")[0].trim()));
                registroEntity = regionalSession.buscar(registroEntity.getIdRegional());
                usuarioConvert(objectContext.getRegionalDTO(registroEntity));
                this.usuarioSeleccionado = String.valueOf(buscarCodUsuario(user));
            } else {
                usuarioInicial();
                abrirModal("SARA", "Ha llegado al último registro", null);
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

    public int buscarCodUsuario(String nombreUsuario) {

        int codUsuario = 0;
        for (SelectItem regional : this.listaRegionales) {
            if (nombreUsuario.equalsIgnoreCase(regional.getLabel())) {
                codUsuario = Integer.parseInt(String.valueOf(regional.getValue()));
            }
        }
        return codUsuario;
    }

    public String obtenerUsuario(String operUsuario) {
        String idUsuario = "1";
        String user = "";

        switch (operUsuario) {
            case "BUSCAR":
                idUsuario = this.getUsuarioSeleccionado();
                for (SelectItem lista : getListaRegionales()) {
                    if (String.valueOf(lista.getValue()).equals(idUsuario)) {
                        user = lista.getLabel();
                        this.setUsuarioSeleccionado(idUsuario);
                        break;
                    }
                }
                break;
            case "ANTERIOR": {
                int cUser = Integer.parseInt(this.getUsuarioSeleccionado());

                cUser = (cUser - 1);
                if (cUser >= 1) {
                    idUsuario = String.valueOf(cUser);
                    for (SelectItem lista : getListaRegionales()) {
                        if (String.valueOf(lista.getValue()).equals(idUsuario)) {
                            user = lista.getLabel();
                            this.setUsuarioSeleccionado(idUsuario);
                            break;
                        }
                    }
                }
                break;
            }
            case "SIGUIENTE": {
                int cUser = Integer.parseInt(this.getUsuarioSeleccionado());
                cUser = (cUser + 1);
                if (cUser <= this.getCantidadZona()) {
                    idUsuario = String.valueOf(cUser);
                    for (SelectItem lista : getListaRegionales()) {
                        if (String.valueOf(lista.getValue()).equals(idUsuario)) {
                            user = lista.getLabel();
                            this.setUsuarioSeleccionado(idUsuario);
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

    private List<SelectItem> cargarListaRegional(List<RegionalDTO> regionalCB) {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem(0, "");
        lista.add(item);
        int cont = 1;
        for (RegionalDTO dto : regionalCB) {
            item = new SelectItem(cont, dto.getIdRegional() + " - " + dto.getRegional());
            lista.add(item);
            cont++;
        }
        return lista;
    }

    private Regional cargarRegionalAplicacion() {

        Regional tc = new Regional();
        Integer intTmp = null;
        String strTmp = "";

        intTmp = Integer.parseInt(this.codigo);
        if (intTmp == null) {
            return null;
        }

        tc.setIdRegional(intTmp);
        strTmp = this.nombre;
        if (strTmp.length() == 0) {
            return null;
        }
        tc.setRegional(strTmp);

        return tc;
    }

    /**
     * @return the usuarioSeleccionado
     */
    public String getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    /**
     * @param usuarioSeleccionado the usuarioSeleccionado to set
     */
    public void setUsuarioSeleccionado(String usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    /**
     * @return the primeraVez
     */
    public boolean isPrimeraVez() {
        return primeraVez;
    }

    /**
     * @param primeraVez the primeraVez to set
     */
    public void setPrimeraVez(boolean primeraVez) {
        this.primeraVez = primeraVez;
    }

    /**
     * @return the cantidadZona
     */
    public int getCantidadZona() {
        return cantidadZona;
    }

    /**
     * @param cantidadZona the cantidadZona to set
     */
    public void setCantidadZona(int cantidadZona) {
        this.cantidadZona = cantidadZona;
    }

    /**
     * @return the listaRegionales
     */
    public List<SelectItem> getListaRegionales() {
        return listaRegionales;
    }

    /**
     * @param listaRegionales the listaRegionales to set
     */
    public void setListaRegionales(List<SelectItem> listaRegionales) {
        this.listaRegionales = listaRegionales;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the mostrarBtnGrabar
     */
    public boolean isMostrarBtnGrabar() {
        return mostrarBtnGrabar;
    }

    /**
     * @param mostrarBtnGrabar the mostrarBtnGrabar to set
     */
    public void setMostrarBtnGrabar(boolean mostrarBtnGrabar) {
        this.mostrarBtnGrabar = mostrarBtnGrabar;
    }

    /**
     * @return the mostrarBtnCancelar
     */
    public boolean isMostrarBtnCancelar() {
        return mostrarBtnCancelar;
    }

    /**
     * @param mostrarBtnCancelar the mostrarBtnCancelar to set
     */
    public void setMostrarBtnCancelar(boolean mostrarBtnCancelar) {
        this.mostrarBtnCancelar = mostrarBtnCancelar;
    }
}
