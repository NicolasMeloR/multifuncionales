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
import com.davivienda.sara.dto.OccaDTO;
import com.davivienda.sara.dto.OficinaMultiDTO;
import com.davivienda.sara.dto.TipoCajeroDTO;
import com.davivienda.sara.dto.TransportadoraDTO;
import com.davivienda.sara.dto.UbicacionDTO;
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
@ManagedBean(name = "infoCajeroBean")
@ViewScoped
public class InfoCajeroBean extends BaseBean implements Serializable {

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
    private List<CajeroDTO> listaRegistros;
    public boolean mostrarBtnGrabar;
    public boolean mostrarBtnCancelar;
    public int cantidadRegistros;
    public int pagina;
    public int regPagina;
    public List<SelectItem> listaDenominacion = new ArrayList<SelectItem>();
    public List<SelectItem> listaCajeroActivo = new ArrayList<SelectItem>();
    public List<SelectItem> listaTipoCajeroMulti = new ArrayList<SelectItem>();
    public List<SelectItem> listaTipoProvision = new ArrayList<SelectItem>();
    public List<SelectItem> listaRed = new ArrayList<SelectItem>();
    public List<UbicacionDTO> listaUbicacion = new ArrayList<UbicacionDTO>();
    public List<TipoCajeroDTO> listaTipoCajero = new ArrayList<TipoCajeroDTO>();
    public List<OccaDTO> listaOcca = new ArrayList<OccaDTO>();
    public List<TransportadoraDTO> listaTransportadora = new ArrayList<TransportadoraDTO>();
    public List<OficinaMultiDTO> listaOficinamulti = new ArrayList<OficinaMultiDTO>();
    public Collection<Cajero> items;
    public Collection<Ubicacion> ubicacionItems;
    public Collection<TipoCajero> tipoCajeroItems;
    public Collection<Occa> occaItems;
    public Collection<Transportadora> transportadoraItems;
    public Collection<Oficinamulti> oficinamultiItems;
    public int primerUbicacion;
    public int primerTipoCajero;
    public int primerOcca;
    public int primertransportadora;
    public int primerOficina;
    public boolean showCodigoDispensador;

    /**
     * Creates a new instance of UsuariosBean
     */
    @PostConstruct
    public void InfoCajeroBean() {
        String error = "";
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            listaRegistros = new ArrayList<CajeroDTO>();
            if (objectContext != null) {
                this.registro = new CajeroDTO();
                this.pagina = 1;
                this.regPagina = 1;
                items = cajeroSession.getTodos();
                listaRegistros = cargarListaRegistros(objectContext.getCajeroSinFiltroCB(items));

                ubicacionItems = ubicacionSessionLocal.getTodos();
                listaUbicacion = cargarListaUbicacion(objectContext.getUbicacionCB(ubicacionItems));
                primerUbicacion = listaUbicacion.get(0).getCodigoUbicacion();

                tipoCajeroItems = tipoCajeroSession.getTodos();
                listaTipoCajero = cargarListaTipoCajero(objectContext.getTipoCajeroCB(tipoCajeroItems));
                primerTipoCajero = listaTipoCajero.get(0).getCodigoTipoCajero();

                occaItems = occaSessionLocal.getTodos();
                listaOcca = cargarListaOcca(objectContext.getOccaCB(occaItems));
                primerOcca = listaOcca.get(0).getCodigoOcca();

                transportadoraItems = transportadoraSessionLocal.getTodos();
                listaTransportadora = cargarListaTransportadora(objectContext.getTransportadoraCB(transportadoraItems));
                primertransportadora = listaTransportadora.get(0).getIdTransportadora();

                oficinamultiItems = ofimultiSessionLocal.getTodos();
                listaOficinamulti = cargarListaOficinaMulti(objectContext.getOfimultiCB(oficinamultiItems));
                primerOficina = listaOficinamulti.get(0).getCodigooficinamulti();

                dataInicial();
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void dataInicial() {
        this.registroSeleccionado = "1";
        showCodigoDispensador = false;
        buscarRegistro();
        this.cantidadRegistros = listaRegistros.size();
        listaDenominacion = new Denominacion().crearLista();
        listaCajeroActivo = new CajeroActivo().crearLista();
        listaTipoCajeroMulti = new TipoCajeroMulti().crearLista();
        listaTipoProvision = new TipoProvision().crearLista();
        listaRed = new Red().crearLista();

    }

    public void nuevoRegistro() {
        this.registro.limpiarCajeroDTO(primerOcca, primerOficina, primerTipoCajero, primerUbicacion, primertransportadora);
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
            cajeroProcesosSession.actualizarCajero(registroEntity);
            Collection<Cajero> items = cajeroSession.getTodos();
            listaRegistros = cargarListaRegistros(objectContext.getCajeroSinFiltroCB(items));
            this.registroSeleccionado = "1";
            buscarRegistro();
            this.cantidadRegistros = listaRegistros.size();
            this.mostrarBtnCancelar = false;
            this.mostrarBtnGrabar = false;
            abrirModal("SARA", "Se ha guardado exitosamente el registro", null);
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

    private Cajero cargarRegistro() throws ServletException, EntityServicioExcepcion {
        Cajero tc = this.registro.entidad();
        Occa objOcca = null;
        TipoCajero objTipoCajero = null;
        Ubicacion objUbicacion = null;
        Transportadora objTransportadora = null;
        Oficinamulti objOficinamulti = null;

        for (Occa item : occaItems) {
            if (item.getCodigoOcca().equals(tc.getOcca().getCodigoOcca())) {
                objOcca = item;
                break;
            }
        }

        for (TipoCajero item : tipoCajeroItems) {
            if (item.getCodigoTipoCajero().equals(tc.getTipoCajero().getCodigoTipoCajero())) {
                objTipoCajero = item;
                break;
            }
        }

        for (Ubicacion item : ubicacionItems) {
            if (item.getCodigoUbicacion().equals(tc.getUbicacion().getCodigoUbicacion())) {
                objUbicacion = item;
                break;
            }
        }

        for (Transportadora item : transportadoraItems) {
            if (item.getIdTransportadora().equals(tc.getTransportadora().getIdTransportadora())) {
                objTransportadora = item;
                break;
            }
        }

        for (Oficinamulti item : oficinamultiItems) {
            if (item.getCodigooficinamulti().equals(tc.getOficinaMulti().getCodigooficinamulti())) {
                objOficinamulti = item;
                break;
            }
        }

        tc.setOcca(objOcca);
        tc.setTipoCajero(objTipoCajero);
        tc.setUbicacion(objUbicacion);
        tc.setTransportadora(objTransportadora);
        tc.setOficinaMulti(objOficinamulti);

        return tc;
    }

    public void buscarRegistro() {
        String error = "";
        CajeroDTO reg = null;
        try {
            reg = obtenerRegistro("BUSCAR");
            Cajero registroEntity = new Cajero(reg.getCodigoCajero());
            registroEntity = cajeroSession.buscar(registroEntity.getCodigoCajero());
            registroConsulta(registroEntity);
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

    public void anteriorRegistro() {
        String error = "";
        try {
            if (this.pagina > 1) {
                this.pagina -= 1;
            }
            Collection<Cajero> items = cajeroSession.getTodos(pagina, regPagina);
            this.registroSeleccionado = obtenerIdDeRegistro(objectContext.getCajeroSinFiltroCB(items).get(0));
            buscarRegistro();
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

    public void siguienteRegistro() {
        String error = "";
        try {
            this.pagina += 1;
            Collection<Cajero> items = cajeroSession.getTodos(pagina, regPagina);
            if (items.size() > 0) {
                this.registroSeleccionado = obtenerIdDeRegistro(objectContext.getCajeroSinFiltroCB(items).get(0));
                buscarRegistro();
            } else {
                abrirModal("SARA", "Ha llegado al último registro", null);
                this.pagina -= 1;
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

    public CajeroDTO obtenerRegistro(String operUsuario) {
        CajeroDTO mod = null;
        int rId = Integer.parseInt(this.registroSeleccionado);
        switch (operUsuario) {
            case "BUSCAR":
                for (CajeroDTO item : listaRegistros) {
                    if (item.getIdCajero() == rId) {
                        mod = item;
                        this.registroSeleccionado = String.valueOf(item.getIdCajero());
                        this.pagina = item.getIdCajero();
                        break;
                    }
                }
                break;
            default:
                break;
        }
        return mod;
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

    private String obtenerIdDeRegistro(CajeroDTO dTO) {
        String id = "1";
        for (CajeroDTO item : listaRegistros) {
            if (item.getCodigoCajero().equals(dTO.getCodigoCajero())) {
                id = String.valueOf(item.getIdCajero());
                this.registro = item;
                break;
            }
        }
        return id;
    }

    private List<UbicacionDTO> cargarListaUbicacion(List<UbicacionDTO> ubicacionDTO) {
        List<UbicacionDTO> lista = new ArrayList<UbicacionDTO>();
        int cont = 1;
        for (UbicacionDTO dto : ubicacionDTO) {
            dto.setIdUbicacion(cont);
            lista.add(dto);
            cont++;
        }
        return lista;
    }

    private List<TipoCajeroDTO> cargarListaTipoCajero(List<TipoCajeroDTO> cajeroDTO) {
        List<TipoCajeroDTO> lista = new ArrayList<TipoCajeroDTO>();
        int cont = 1;
        for (TipoCajeroDTO dto : cajeroDTO) {
            dto.setIdCajero(cont);
            lista.add(dto);
            cont++;
        }
        return lista;
    }

    private List<OccaDTO> cargarListaOcca(List<OccaDTO> occaDTO) {
        List<OccaDTO> lista = new ArrayList<OccaDTO>();
        int cont = 1;
        for (OccaDTO dto : occaDTO) {
            dto.setIdOcca(cont);
            lista.add(dto);
            cont++;
        }
        return lista;
    }

    private List<TransportadoraDTO> cargarListaTransportadora(List<TransportadoraDTO> dTOs) {
        List<TransportadoraDTO> lista = new ArrayList<TransportadoraDTO>();
        int cont = 1;
        for (TransportadoraDTO dto : dTOs) {
            dto.setIdTrans(cont);
            lista.add(dto);
            cont++;
        }
        return lista;
    }

    private List<OficinaMultiDTO> cargarListaOficinaMulti(List<OficinaMultiDTO> dTOs) {
        List<OficinaMultiDTO> lista = new ArrayList<OficinaMultiDTO>();
        int cont = 1;
        for (OficinaMultiDTO dto : dTOs) {
            dto.setIdOficina(cont);
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

    public List<CajeroDTO> getListaRegistros() {
        return listaRegistros;
    }

    public void setListaRegistros(List<CajeroDTO> listaRegistros) {
        this.listaRegistros = listaRegistros;
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

    public List<UbicacionDTO> getListaUbicacion() {
        return listaUbicacion;
    }

    public void setListaUbicacion(List<UbicacionDTO> listaUbicacion) {
        this.listaUbicacion = listaUbicacion;
    }

    public List<TipoCajeroDTO> getListaTipoCajero() {
        return listaTipoCajero;
    }

    public void setListaTipoCajero(List<TipoCajeroDTO> listaTipoCajero) {
        this.listaTipoCajero = listaTipoCajero;
    }

    public List<OccaDTO> getListaOcca() {
        return listaOcca;
    }

    public void setListaOcca(List<OccaDTO> listaOcca) {
        this.listaOcca = listaOcca;
    }

    public List<TransportadoraDTO> getListaTransportadora() {
        return listaTransportadora;
    }

    public void setListaTransportadora(List<TransportadoraDTO> listaTransportadora) {
        this.listaTransportadora = listaTransportadora;
    }

    public OfimultiSessionLocal getOfimultiSessionLocal() {
        return ofimultiSessionLocal;
    }

    public void setOfimultiSessionLocal(OfimultiSessionLocal ofimultiSessionLocal) {
        this.ofimultiSessionLocal = ofimultiSessionLocal;
    }

    public List<OficinaMultiDTO> getListaOficinamulti() {
        return listaOficinamulti;
    }

    public void setListaOficinamulti(List<OficinaMultiDTO> listaOficinamulti) {
        this.listaOficinamulti = listaOficinamulti;
    }

    public boolean isShowCodigoDispensador() {
        return showCodigoDispensador;
    }

    public void setShowCodigoDispensador(boolean showCodigoDispensador) {
        this.showCodigoDispensador = showCodigoDispensador;
    }

}
