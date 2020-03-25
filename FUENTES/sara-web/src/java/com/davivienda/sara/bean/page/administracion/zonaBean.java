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
import com.davivienda.sara.dto.ZonaDTO;
import com.davivienda.sara.entitys.Zona;
import com.davivienda.sara.tablas.zona.session.ZonaSessionLocal;
import com.davivienda.utilidades.conversion.JSon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import com.davivienda.sara.tablas.usuarioaplicacion.session.UsuarioAplicacionSessionLocal;
import com.davivienda.utilidades.Constantes;
import java.util.logging.Level;

/**
 *
 * @author jmcastel
 */
@ManagedBean(name = "zonaBean")
@ViewScoped
public class zonaBean extends BaseBean implements Serializable {

    @EJB
    ZonaSessionLocal zonaSession;

    @EJB
    UsuarioAplicacionSessionLocal usuarioAplicacionSession;

    private String codigo;
    private String nombre;
    private boolean primeraVez;
    private String descripcion;
    private int cantidadZona;
    private boolean mostrarBtnGrabar;
    private String usuarioSeleccionado;
    private boolean mostrarBtnCancelar;
    private List<SelectItem> listaZona;
    public ComponenteAjaxObjectContextWeb objectContext;

    @PostConstruct
    public void ZonaBean() {
        String error = "";
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            setListaZona(new ArrayList<SelectItem>());
            if (objectContext != null) {
                Collection<Zona> items = zonaSession.getTodos();
                this.listaZona = cargarListaZona(objectContext.getZonaCB(items));
                this.setUsuarioSeleccionado("");
                this.setPrimeraVez(true);
                usuarioInicial();
                this.cantidadZona = this.getListaZona().size();
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void usuarioInicial() {
        System.err.println("ZonaBean-usuarioInicial: Inicio");
        if (!this.listaZona.isEmpty()) {
            this.codigo = String.valueOf(this.listaZona.get(1).getValue());
            String nombre = this.listaZona.get(1).getLabel().split("-")[1];
            this.nombre = nombre.trim();
            this.descripcion = nombre.trim();
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
        this.mostrarBtnCancelar = true;
        this.mostrarBtnGrabar = true;
    }

    public void grabarUsuario() {
        try {
            Zona registroEntity = cargarZonaAplicacion();
            if (registroEntity == null) {
                abrirModal("SARA", "Los campos Código,Nombre y Descripción son obligatorios", null);
            } else {
                zonaSession.actualizar(registroEntity);
                Collection<Zona> items = zonaSession.getTodos();
                setListaZona(cargarListaZona(objectContext.getZonaCB(items)));
                this.setCantidadZona(getListaZona().size());
                this.mostrarBtnCancelar = false;
                this.mostrarBtnGrabar = false;
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
                Zona registroEntity = new Zona(Integer.parseInt(user.split("-")[0].trim()));
                registroEntity = zonaSession.buscar(registroEntity.getIdZona());
                usuarioConvert(objectContext.getZonaDTO(registroEntity));
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

    public void usuarioConvert(ZonaDTO registroEntity) {
        this.codigo = String.valueOf(registroEntity.getIdZona());
        this.nombre = registroEntity.getZona();
        this.descripcion = registroEntity.getDescripcionZona();
        this.setMostrarBtnGrabar(false);
        this.setMostrarBtnCancelar(false);
    }

    public void borrarUsuario() {
        String resp = "";
        try {
            Zona registroEntity = cargarZonaAplicacion();
            if (registroEntity == null) {
                abrirModal("SARA", "Los campos Código,Nombre y Descripción son obligatorios", null);
                return;
            } else {
                zonaSession.borrar(registroEntity);
                Collection<Zona> items = zonaSession.getTodos();
                setListaZona(cargarListaZona(objectContext.getZonaCB(items)));
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

        this.mostrarBtnCancelar = false;
        this.mostrarBtnGrabar = false;
    }

    public void anteriorUsuario() {

        String error = "";
        String user = "";
        try {
            user = obtenerUsuario("ANTERIOR");
            if (!user.isEmpty()) {
                Zona registroEntity = new Zona(Integer.parseInt(user.split("-")[0].trim()));
                registroEntity = zonaSession.buscar(registroEntity.getIdZona());
                usuarioConvert(objectContext.getZonaDTO(registroEntity));
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
                Zona registroEntity = new Zona(Integer.parseInt(user.split("-")[0].trim()));
                registroEntity = zonaSession.buscar(registroEntity.getIdZona());
                usuarioConvert(objectContext.getZonaDTO(registroEntity));
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
        for (SelectItem regional : this.listaZona) {
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
                for (SelectItem lista : getListaZona()) {
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
                    for (SelectItem lista : getListaZona()) {
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
                    for (SelectItem lista : getListaZona()) {
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

    private List<SelectItem> cargarListaZona(List<ZonaDTO> zonaCB) {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem(0, "");
        lista.add(item);
        int cont = 1;
        for (ZonaDTO dto : zonaCB) {
            item = new SelectItem(cont, dto.getIdZona() + " - " + dto.getZona());
            lista.add(item);
            cont++;
        }
        return lista;
    }

    public Zona cargarZonaAplicacion() {
        Zona tc = new Zona();
        Integer intTmp = null;
        String strTmp = "";

        intTmp = Integer.parseInt(this.codigo);
        if (intTmp == null) {
            return null;
        }

        tc.setIdZona(intTmp);
        strTmp = this.descripcion;
        if (strTmp.length() == 0) {
            return null;
        }
        tc.setDescripcionZona(strTmp);
        strTmp = this.nombre;
        if (strTmp.length() == 0) {
            return null;
        }
        tc.setZona(strTmp);

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
     * @return the listaZona
     */
    public List<SelectItem> getListaZona() {
        return listaZona;
    }

    /**
     * @param listaZona the listaZona to set
     */
    public void setListaZona(List<SelectItem> listaZona) {
        this.listaZona = listaZona;
    }

}
