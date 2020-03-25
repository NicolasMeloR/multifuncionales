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
import com.davivienda.sara.dto.OficinaMultiDTO;
import com.davivienda.sara.entitys.Oficinamulti;
import com.davivienda.sara.entitys.Zona;
import com.davivienda.sara.tablas.ofimulti.session.OfimultiSessionLocal;
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

/**
 *
 * @author jmcastel
 */
@ManagedBean(name = "oficinaMulti")
@ViewScoped
public class OficinaMultifuncional extends BaseBean implements Serializable {

    @EJB
    OfimultiSessionLocal ofimultiSession;

    private String codigo;
    private String nombre;
    private String codigoCentroEfectivo;
    private boolean primeraVez;
    private int cantidadZona;
    private boolean mostrarBtnGrabar;
    private String usuarioSeleccionado;
    private boolean mostrarBtnCancelar;
    private List<SelectItem> listaOficina;
    private List<OficinaMultiDTO> oficinasMulfs;
    public ComponenteAjaxObjectContextWeb objectContext;

    @PostConstruct
    public void OficinaMultifuncional() {

        String error = "";
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            setListaOficina(new ArrayList<SelectItem>());
            if (objectContext != null) {
                Collection<Oficinamulti> items = ofimultiSession.getTodos();
                List<OficinaMultiDTO> respuesta = objectContext.getOfimultiCB(items);
                setListaOficina(cargarListaOficinaMulti(respuesta));
                setOficinasMulfs(respuesta);
                this.setUsuarioSeleccionado("");
                this.setPrimeraVez(true);
                usuarioInicial();
                this.setCantidadZona(getListaOficina().size());
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

    }

    public void usuarioInicial() {
        System.err.println("ZonaBean-usuarioInicial: Inicio");
        if (!this.oficinasMulfs.isEmpty()) {
            this.codigo = String.valueOf(this.oficinasMulfs.get(0).getCodigooficinamulti());
            this.nombre = this.oficinasMulfs.get(0).getNombre();
            this.codigoCentroEfectivo = String.valueOf(this.oficinasMulfs.get(0).getCodigocentroefectivo());
        }
        this.mostrarBtnGrabar = false;
        this.mostrarBtnCancelar = false;
        System.err.println("ZonaBean-usuarioInicial: Final");
    }

    public void nuevoUsuario() {
        this.codigo = "";
        this.nombre = "";
        this.codigoCentroEfectivo = "";
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
            Oficinamulti registroEntity = cargarOficinaAplicacion();
            if (registroEntity == null) {
                abrirModal("SARA", "Los campos Código, Nombre , Código Centro Efectivo son campos Obligatorios ", null);
            } else {
                ofimultiSession.actualizar(registroEntity);
                Collection<Oficinamulti> items = ofimultiSession.getTodos();
                List<OficinaMultiDTO> respuesta = objectContext.getOfimultiCB(items);
                setListaOficina(cargarListaOficinaMulti(respuesta));
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
    }

    public void buscarRegistro() {
        String error = "";
        String user = "";
        try {
            if (!this.usuarioSeleccionado.equals("")) {
                user = obtenerUsuario("BUSCAR");
                Oficinamulti registroEntity = new Oficinamulti(Integer.parseInt(user.split("-")[0].trim()));
                registroEntity = ofimultiSession.buscar(registroEntity.getCodigooficinamulti());
                usuarioConvert(objectContext.getOfincinaMultiDTO(registroEntity));
                this.usuarioSeleccionado = String.valueOf(buscarCodUsuario(user));
            }
            if (this.primeraVez) {
                this.usuarioSeleccionado = "0";
                this.primeraVez = false;
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

    public void usuarioConvert(OficinaMultiDTO registroEntity) {
        this.codigo = String.valueOf(registroEntity.getCodigooficinamulti());
        this.nombre = registroEntity.getNombre();
        this.codigoCentroEfectivo = String.valueOf(registroEntity.getCodigocentroefectivo());
        this.setMostrarBtnGrabar(false);
        this.setMostrarBtnCancelar(false);
    }

    public void borrarUsuario() {
        String resp = "";
        try {
            Oficinamulti registroEntity = cargarOficinaAplicacion();
            if (registroEntity == null) {
                abrirModal("SARA", "Los campos Código, Nombre , Código Centro Efectivo ", null);
            } else {
                ofimultiSession.borrar(registroEntity);
                Collection<Oficinamulti> items = ofimultiSession.getTodos();
                List<OficinaMultiDTO> respuesta = objectContext.getOfimultiCB(items);
                setListaOficina(cargarListaOficinaMulti(respuesta));
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
                Oficinamulti registroEntity = new Oficinamulti(Integer.parseInt(user.split("-")[0].trim()));
                registroEntity = ofimultiSession.buscar(registroEntity.getCodigooficinamulti());
                usuarioConvert(objectContext.getOfincinaMultiDTO(registroEntity));
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
                Oficinamulti registroEntity = new Oficinamulti(Integer.parseInt(user.split("-")[0].trim()));
                registroEntity = ofimultiSession.buscar(registroEntity.getCodigooficinamulti());
                usuarioConvert(objectContext.getOfincinaMultiDTO(registroEntity));
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

    public int buscarCodUsuario(String nombreUsuario) {

        int codUsuario = 0;
        for (SelectItem regional : this.listaOficina) {
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
                for (SelectItem lista : getListaOficina()) {
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
                    for (SelectItem lista : getListaOficina()) {
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
                    for (SelectItem lista : getListaOficina()) {
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

    private List<SelectItem> cargarListaOficinaMulti(List<OficinaMultiDTO> ofiMultiCB) {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem(0, "");
        lista.add(item);
        int cont = 1;
        for (OficinaMultiDTO dto : ofiMultiCB) {
            item = new SelectItem(cont, dto.getCodigooficinamulti() + " - " + dto.getNombre());
            lista.add(item);
            cont++;
        }
        return lista;
    }

    private Oficinamulti cargarOficinaAplicacion() {

        Oficinamulti tc = new Oficinamulti();
        Integer intTmp = null;
        String strTmp = "";

        if (this.codigo != null) {
            intTmp = Integer.parseInt(this.codigo);
        } else {
            return null;
        }

        tc.setCodigooficinamulti(intTmp);

        strTmp = this.nombre;
        if (strTmp.length() == 0) {
            return null;
        }
        tc.setNombre(strTmp);

        intTmp = Integer.parseInt(this.codigoCentroEfectivo);
        if (strTmp.length() == 0) {
            return null;
        }
        tc.setCodigocentroefectivo(intTmp);

        return tc;
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

    /**
     * @return the listaOcca
     */
    public List<SelectItem> getListaOficina() {
        return listaOficina;
    }

    /**
     * @param listaOcca the listaOcca to set
     */
    public void setListaOficina(List<SelectItem> listaOficina) {
        this.listaOficina = listaOficina;
    }

    /**
     * @return the codigoCentroEfectivo
     */
    public String getCodigoCentroEfectivo() {
        return codigoCentroEfectivo;
    }

    /**
     * @param codigoCentroEfectivo the codigoCentroEfectivo to set
     */
    public void setCodigoCentroEfectivo(String codigoCentroEfectivo) {
        this.codigoCentroEfectivo = codigoCentroEfectivo;
    }

    /**
     * @return the oficinasMulfs
     */
    public List<OficinaMultiDTO> getOficinasMulfs() {
        return oficinasMulfs;
    }

    /**
     * @param oficinasMulfs the oficinasMulfs to set
     */
    public void setOficinasMulfs(List<OficinaMultiDTO> oficinasMulfs) {
        this.oficinasMulfs = oficinasMulfs;
    }

}
