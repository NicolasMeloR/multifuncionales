/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.administracion;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.dto.OccaDTO;
import com.davivienda.sara.entitys.Occa;
import com.davivienda.sara.entitys.Zona;
import com.davivienda.sara.tablas.occa.session.OccaSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.JSon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author jmcastel
 */
@ManagedBean(name = "occaBean")
@ViewScoped
public class OccaBean extends BaseBean implements Serializable {

    @EJB
    OccaSessionLocal occaSession;

    private String codigo;
    private String nombre;
    private String ArchivoMovimiento;
    private String UbicacionArchivoMovimiento;
    private String codigoTerminalCaja;
    private boolean primeraVez;
    private String descripcion;
    private int cantidadZona;
    private boolean mostrarBtnGrabar;
    private String usuarioSeleccionado;
    private boolean mostrarBtnCancelar;
    private List<SelectItem> listaOcca;
    private List<OccaDTO> occaComplete;
    public ComponenteAjaxObjectContextWeb objectContext;

    @PostConstruct
    public void OccaBean() {

        String error = "";
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            setListaOcca(new ArrayList<SelectItem>());
            if (objectContext != null) {
                Collection<Occa> items = occaSession.getTodos();
                setListaOcca(cargarListaOcca(objectContext.getOccaCB(items)));
                setOccaComplete(objectContext.getOccaCB(items));
                this.setUsuarioSeleccionado("");
                this.setPrimeraVez(true);
                usuarioInicial();
                this.setCantidadZona(getListaOcca().size());
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

    }

    public void usuarioInicial() {
        System.err.println("ZonaBean-usuarioInicial: Inicio");
        if (!this.occaComplete.isEmpty()) {
            this.codigo = String.valueOf(this.occaComplete.get(0).getCodigoOcca());
            this.nombre = this.occaComplete.get(0).getNombre();//.split("-")[1];
            this.ArchivoMovimiento = this.occaComplete.get(0).getNombreArchivoMovimiento();
            this.UbicacionArchivoMovimiento = this.occaComplete.get(0).getUbicacionArchivoMovimiento();
            this.codigoTerminalCaja = String.valueOf(this.occaComplete.get(0).getCodTerminalCaja());
        }
        this.mostrarBtnGrabar = false;
        this.mostrarBtnCancelar = false;
        System.err.println("ZonaBean-usuarioInicial: Final");
    }

    public void nuevoUsuario() {
        this.codigo = "";
        this.nombre = "";
        this.descripcion = "";
        this.setMostrarBtnCancelar(true);
        this.setMostrarBtnGrabar(true);
    }

    public void modificarUsuario() {
        this.setMostrarBtnCancelar(true);
        this.setMostrarBtnGrabar(true);
    }

    public void grabarUsuario() {
        try {
            Occa registroEntity = cargarOccaAplicacion();
            if (registroEntity == null) {
                abrirModal("SARA", "Los campos Codigo, Nombre, Nombre Archivo Movimiento, Ubicación Archivo Movimiento y Código Terminal Caja son obligatorios", null);
            } else {
                occaSession.actualizar(registroEntity);
                Collection<Occa> items = occaSession.getTodos();
                setListaOcca(cargarListaOcca(objectContext.getOccaCB(items)));
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

    private Zona cargarUsuarioAplicacion() {
        Zona zona = new Zona();

        zona.setIdZona(Integer.parseInt(this.codigo));
        zona.setZona(this.nombre);
        zona.setDescripcionZona(this.descripcion);

        return zona;
    }

    public void buscarRegistro() {
        String error = "";
        String user = "";
        try {
            if (!this.usuarioSeleccionado.equals("")) {
                user = obtenerUsuario("BUSCAR");
                Occa registroEntity = new Occa(Integer.parseInt(user.split("-")[0].trim()));
                registroEntity = occaSession.buscar(registroEntity.getCodigoOcca());
                usuarioConvert(objectContext.getOccaDTO(registroEntity));
                this.usuarioSeleccionado = String.valueOf(buscarCodUsuario(user));
            }
            if (this.primeraVez) {
                this.usuarioSeleccionado = "0";
                this.primeraVez = false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Ha llegado al último registro", null);
        } catch (EJBException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (EntityServicioExcepcion ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void usuarioConvert(OccaDTO registroEntity) {
        this.codigo = String.valueOf(registroEntity.getCodigoOcca());
        this.nombre = registroEntity.getNombre();
        this.ArchivoMovimiento = registroEntity.getNombreArchivoMovimiento();
        this.UbicacionArchivoMovimiento = registroEntity.getUbicacionArchivoMovimiento();
        this.codigoTerminalCaja = String.valueOf(registroEntity.getCodTerminalCaja());
        this.setMostrarBtnGrabar(false);
        this.setMostrarBtnCancelar(false);
    }

    public void borrarUsuario() {
        try {
            Occa registroEntity = cargarOccaAplicacion();
            if (registroEntity == null) {
                abrirModal("SARA", "Los campos Codigo, Nombre, Nombre Archivo Movimiento, Ubicación Archivo Movimiento y Código Terminal Caja son obligatorios", null);
            } else {
                occaSession.borrar(registroEntity);
                Collection<Occa> items = occaSession.getTodos();
                setListaOcca(cargarListaOcca(objectContext.getOccaCB(items)));
                usuarioInicial();
                abrirModal("SARA", "Se ha borrado exitosamente el registro", null);
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Ha llegado al último registro", null);
        } catch (EJBException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (EntityServicioExcepcion ex) {
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
                Occa registroEntity = new Occa(Integer.parseInt(user.split("-")[0].trim()));
                registroEntity = occaSession.buscar(registroEntity.getCodigoOcca());
                usuarioConvert(objectContext.getOccaDTO(registroEntity));
                this.usuarioSeleccionado = String.valueOf(buscarCodUsuario(user));
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Ha llegado al último registro", null);
        } catch (EJBException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (EntityServicioExcepcion ex) {
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
                Occa registroEntity = new Occa(Integer.parseInt(user.split("-")[0].trim()));
                registroEntity = occaSession.buscar(registroEntity.getCodigoOcca());
                usuarioConvert(objectContext.getOccaDTO(registroEntity));
                this.usuarioSeleccionado = String.valueOf(buscarCodUsuario(user));
            } else {
                usuarioInicial();
                abrirModal("SARA", "Ha llegado al último registro", null);
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Ha llegado al último registro", null);
        } catch (EJBException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (EntityServicioExcepcion ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

    }

    public int buscarCodUsuario(String nombreUsuario) {
        int codUsuario = 0;
        for (SelectItem occa : this.listaOcca) {
            if (nombreUsuario.equalsIgnoreCase(occa.getLabel())) {
                codUsuario = Integer.parseInt(String.valueOf(occa.getValue()));
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
                for (SelectItem lista : getListaOcca()) {
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
                    for (SelectItem lista : getListaOcca()) {
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
                    for (SelectItem lista : getListaOcca()) {
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

    private List<SelectItem> cargarListaOcca(List<OccaDTO> occaCB) {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem(0, "");
        lista.add(item);
        int cont = 1;
        for (OccaDTO dto : occaCB) {
            item = new SelectItem(cont, dto.getCodigoOcca() + " - " + dto.getNombre());
            lista.add(item);
            cont++;
        }
        return lista;
    }

    private Occa cargarOccaAplicacion() {

        Occa tc = new Occa();
        Integer intTmp = null;
        String strTmp = "";

        intTmp = Integer.parseInt(this.codigo);
        if (intTmp == null) {
            return null;
        }

        tc.setCodigoOcca(intTmp);
        strTmp = this.nombre;
        if (strTmp.length() == 0) {
            return null;
        }
        tc.setNombre(strTmp);
        strTmp = this.ArchivoMovimiento;
        if (strTmp.length() == 0) {
            return null;
        }
        tc.setNombreArchivoMovimiento(strTmp);
        strTmp = this.UbicacionArchivoMovimiento;
        if (strTmp.length() == 0) {
            return null;
        }
        tc.setUbicacionArchivoMovimiento(strTmp);
        intTmp = Integer.parseInt(this.codigoTerminalCaja);
        if (intTmp == null) {
            return null;
        }
        tc.setCodigoTerminal(intTmp);

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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    public List<SelectItem> getListaOcca() {
        return listaOcca;
    }

    /**
     * @param listaOcca the listaOcca to set
     */
    public void setListaOcca(List<SelectItem> listaOcca) {
        this.listaOcca = listaOcca;
    }

    /**
     * @return the ArchivoMovimiento
     */
    public String getArchivoMovimiento() {
        return ArchivoMovimiento;
    }

    /**
     * @param ArchivoMovimiento the ArchivoMovimiento to set
     */
    public void setArchivoMovimiento(String ArchivoMovimiento) {
        this.ArchivoMovimiento = ArchivoMovimiento;
    }

    /**
     * @return the UbicacionArchivoMovimiento
     */
    public String getUbicacionArchivoMovimiento() {
        return UbicacionArchivoMovimiento;
    }

    /**
     * @param UbicacionArchivoMovimiento the UbicacionArchivoMovimiento to set
     */
    public void setUbicacionArchivoMovimiento(String UbicacionArchivoMovimiento) {
        this.UbicacionArchivoMovimiento = UbicacionArchivoMovimiento;
    }

    /**
     * @return the codigoTerminalCaja
     */
    public String getCodigoTerminalCaja() {
        return codigoTerminalCaja;
    }

    /**
     * @param codigoTerminalCaja the codigoTerminalCaja to set
     */
    public void setCodigoTerminalCaja(String codigoTerminalCaja) {
        this.codigoTerminalCaja = codigoTerminalCaja;
    }

    /**
     * @return the occaComplete
     */
    public List<OccaDTO> getOccaComplete() {
        return occaComplete;
    }

    /**
     * @param occaComplete the occaComplete to set
     */
    public void setOccaComplete(List<OccaDTO> occaComplete) {
        this.occaComplete = occaComplete;
    }

}
