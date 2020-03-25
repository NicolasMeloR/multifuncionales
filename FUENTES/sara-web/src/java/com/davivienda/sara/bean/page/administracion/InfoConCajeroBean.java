/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.administracion;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.dto.CajeroDTO;
import com.davivienda.sara.dto.listas.CajeroActivo;
import com.davivienda.sara.dto.listas.Denominacion;
import com.davivienda.sara.dto.listas.Red;
import com.davivienda.sara.dto.listas.TipoCajeroMulti;
import com.davivienda.sara.dto.listas.TipoProvision;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.Occa;
import com.davivienda.sara.entitys.Oficinamulti;
import com.davivienda.sara.entitys.TipoCajero;
import com.davivienda.sara.entitys.Transportadora;
import com.davivienda.sara.entitys.Ubicacion;
import com.davivienda.sara.procesos.cajero.session.CajeroProcesosSessionLocal;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.sara.tablas.occa.session.OccaSessionLocal;
import com.davivienda.sara.tablas.ofimulti.session.OfimultiSessionLocal;
import com.davivienda.sara.tablas.tipocajero.session.TipoCajeroSessionLocal;
import com.davivienda.sara.tablas.transportadora.session.TransportadoraSessionLocal;
import com.davivienda.sara.tablas.ubicacion.session.UbicacionSessionLocal;
import com.davivienda.utilidades.Constantes;
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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletException;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "infoConCajeroBean")
@ViewScoped
public class InfoConCajeroBean extends BaseBean implements Serializable {

    @EJB
    CajeroSessionLocal cajeroSession;
    @EJB
    CajeroProcesosSessionLocal cajeroProcesosSession;
    @EJB
    UbicacionSessionLocal ubicacionSessionLocal;
    @EJB
    TipoCajeroSessionLocal tipoCajeroSession;
    @EJB
    OccaSessionLocal occaSessionLocal;
    @EJB
    TransportadoraSessionLocal transportadoraSessionLocal;
    @EJB
    OfimultiSessionLocal ofimultiSessionLocal;

    public ComponenteAjaxObjectContextWeb objectContext;
    public CajeroDTO registro;
    public String registroSeleccionado;
    public boolean mostrarBtnGrabar;
    public boolean mostrarBtnCancelar;
    public int pagina;
    public int regPagina;
    public List<SelectItem> listaDenominacion = new ArrayList<SelectItem>();
    public List<SelectItem> listaCajeroActivo = new ArrayList<SelectItem>();
    public List<SelectItem> listaTipoCajeroMulti = new ArrayList<SelectItem>();
    public List<SelectItem> listaTipoProvision = new ArrayList<SelectItem>();
    public List<SelectItem> listaRed = new ArrayList<SelectItem>();

    public boolean showCodigoDispensador;

    /**
     * Creates a new instance of UsuariosBean
     */
    @PostConstruct
    public void InfoConCajeroBean() {
        String error = "";
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            if (objectContext != null) {
                dataInicial();
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void dataInicial() throws Exception {
        this.registro = new CajeroDTO();
        this.pagina = 1;
        this.regPagina = 1;
        Collection<Cajero> items = cajeroSession.getTodos(this.pagina, this.regPagina);
        Cajero caItem = items.iterator().next();
        showCodigoDispensador = false;
        this.registroSeleccionado = String.valueOf(caItem.getCodigoCajero());
        registroConsulta(caItem);
        listaDenominacion = new Denominacion().crearLista();
        listaCajeroActivo = new CajeroActivo().crearLista();
        listaTipoCajeroMulti = new TipoCajeroMulti().crearLista();
        listaTipoProvision = new TipoProvision().crearLista();
        listaRed = new Red().crearLista();

    }

    public void nuevoRegistro() {
        this.registro.limpiarCajeroDTO(0, 0, 0, 0, 0);
        this.mostrarBtnCancelar = true;
        this.mostrarBtnGrabar = true;
    }

    public void modificarRegistro() {
        this.mostrarBtnCancelar = true;
        this.mostrarBtnGrabar = true;
    }

    public void grabarRegistro() {
        String error = "";
        try {
            System.err.println("InfoCajeroBean-grabarRegistro:" + this.registro.toString());
            this.pagina = 1;
            Cajero registroEntity = cargarRegistro();
            if (registroEntity != null) {

                cajeroProcesosSession.actualizarCajero(registroEntity);
                Collection<Cajero> items = cajeroSession.getTodos(this.pagina, this.regPagina);
                Cajero caItem = items.iterator().next();
                this.registroSeleccionado = "1";
                buscarRegistro();
                this.mostrarBtnCancelar = false;
                this.mostrarBtnGrabar = false;
                abrirModal("SARA", "Se ha guardado exitosamente el registro ", null);
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

    private Cajero cargarRegistro() {
        Cajero tc = null;
        try {
            tc = this.registro.entidad();
            Occa objOcca = null;
            TipoCajero objTipoCajero = null;
            Ubicacion objUbicacion = null;
            Transportadora objTransportadora = null;
            Oficinamulti objOficinamulti = null;

            objOcca = occaSessionLocal.buscar(tc.getOcca().getCodigoOcca());
            if (objOcca != null) {
                tc.setOcca(objOcca);
            } else {
                tc = null;
                abrirModal("SARA", "No se puede realizar la solicitud código de Occa no Valido", null);
                return tc;
            }

            objTipoCajero = tipoCajeroSession.buscar(tc.getTipoCajero().getCodigoTipoCajero());
            if (objTipoCajero != null) {
                tc.setTipoCajero(objTipoCajero);
            } else {
                tc = null;
                abrirModal("SARA", "No se puede realizar la solicitud código del Tipo de Cajero no Valido", null);
                return tc;
            }

            objUbicacion = ubicacionSessionLocal.buscar(tc.getUbicacion().getCodigoUbicacion());
            if (objUbicacion != null) {
                tc.setUbicacion(objUbicacion);
            } else {
                tc = null;
                abrirModal("SARA", "No se puede realizar la solicitud código de la Ubicacion no Valido", null);
                return tc;
            }

            objTransportadora = transportadoraSessionLocal.buscar(tc.getTransportadora().getIdTransportadora());
            if (objTransportadora != null) {
                tc.setTransportadora(objTransportadora);
            } else {
                tc = null;
                abrirModal("SARA", "No se puede realizar la solicitud código de la Transportadora no Valido", null);
                return tc;
            }
            objOficinamulti = ofimultiSessionLocal.buscar(tc.getOficinaMulti().getCodigooficinamulti());
            if (objOficinamulti != null) {
                tc.setOficinaMulti(objOficinamulti);
            } else {
                tc = null;
                abrirModal("SARA", "No se puede realizar la solicitud código de la Oficina Multi no Valido", null);
                return tc;
            }

        } catch (ServletException | EntityServicioExcepcion ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
        return tc;
    }

    public void buscarRegistro() {
        String error = "";
        try {
            Cajero registroEntity = new Cajero(Integer.parseInt(this.registroSeleccionado));
            registroEntity = cajeroSession.buscar(registroEntity.getCodigoCajero());
            if (registroEntity != null) {
                registroConsulta(registroEntity);
            } else {
                abrirModal("SARA", "No se encuentra el Cajero ", null);
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

    public void registroConsulta(Cajero registroEntity) {
        this.registro = this.registro.entidadDTO(registroEntity);
        if (this.registro.getTipoCajeroFuncion().equals("1")) {
            this.showCodigoDispensador = true;
        } else {
            this.showCodigoDispensador = false;
        }

        this.mostrarBtnGrabar = false;
        this.mostrarBtnCancelar = false;
    }

    private List<CajeroDTO> cargarListaRegistros(List<CajeroDTO> cajeroDTO) {
        List<CajeroDTO> lista = new ArrayList<CajeroDTO>();
        int cont = 1;
        for (CajeroDTO dto : cajeroDTO) {
            dto.setIdCajero(cont);
            lista.add(dto);
            cont++;
        }
        return lista;
    }

    public void activarCodigoDispensador(ValueChangeEvent e) {
        String str = (String) e.getNewValue();
        this.showCodigoDispensador = false;
        if (str.equals("1")) {
            this.showCodigoDispensador = true;
        }
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

    public String getRegistroSeleccionado() {
        return registroSeleccionado;
    }

    public void setRegistroSeleccionado(String registroSeleccionado) {
        this.registroSeleccionado = registroSeleccionado;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public CajeroDTO getRegistro() {
        return registro;
    }

    public void setRegistro(CajeroDTO registro) {
        this.registro = registro;
    }

    public ComponenteAjaxObjectContextWeb getObjectContext() {
        return objectContext;
    }

    public void setObjectContext(ComponenteAjaxObjectContextWeb objectContext) {
        this.objectContext = objectContext;
    }

    public List<SelectItem> getListaDenominacion() {
        return listaDenominacion;
    }

    public void setListaDenominacion(List<SelectItem> listaDenominacion) {
        this.listaDenominacion = listaDenominacion;
    }

    public List<SelectItem> getListaCajeroActivo() {
        return listaCajeroActivo;
    }

    public void setListaCajeroActivo(List<SelectItem> listaCajeroActivo) {
        this.listaCajeroActivo = listaCajeroActivo;
    }

    public List<SelectItem> getListaTipoCajeroMulti() {
        return listaTipoCajeroMulti;
    }

    public void setListaTipoCajeroMulti(List<SelectItem> listaTipoCajeroMulti) {
        this.listaTipoCajeroMulti = listaTipoCajeroMulti;
    }

    public List<SelectItem> getListaTipoProvision() {
        return listaTipoProvision;
    }

    public void setListaTipoProvision(List<SelectItem> listaTipoProvision) {
        this.listaTipoProvision = listaTipoProvision;
    }

    public List<SelectItem> getListaRed() {
        return listaRed;
    }

    public void setListaRed(List<SelectItem> listaRed) {
        this.listaRed = listaRed;
    }

    public boolean isShowCodigoDispensador() {
        return showCodigoDispensador;
    }

    public void setShowCodigoDispensador(boolean showCodigoDispensador) {
        this.showCodigoDispensador = showCodigoDispensador;
    }

}
