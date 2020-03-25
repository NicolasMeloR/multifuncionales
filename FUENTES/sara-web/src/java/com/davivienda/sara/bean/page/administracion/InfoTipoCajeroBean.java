/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.administracion;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.dto.TipoCajeroDTO;
import com.davivienda.sara.dto.listas.AplicativoCajero;
import com.davivienda.sara.dto.listas.FormatoDiarioElectronico;
import com.davivienda.sara.dto.listas.MarcaCajero;
import com.davivienda.sara.dto.listas.SistemaOperativo;
import com.davivienda.sara.entitys.TipoCajero;
import com.davivienda.sara.tablas.tipocajero.session.TipoCajeroSessionLocal;
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
import javax.faces.model.SelectItem;
import javax.servlet.ServletException;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "infoTipoCajeroBean")
@ViewScoped
public class InfoTipoCajeroBean extends BaseBean implements Serializable {

    @EJB
    TipoCajeroSessionLocal tipoCajeroSession;

    public ComponenteAjaxObjectContextWeb objectContext;

    public TipoCajeroDTO registro;
    public String registroSeleccionado;
    private List<TipoCajeroDTO> listaRegistros;
    public boolean mostrarBtnGrabar;
    public boolean mostrarBtnCancelar;
    public int cantidadRegistros;
    public int pagina;
    public int regPagina;
    public List<SelectItem> listaMarcaCajero = new ArrayList<SelectItem>();
    public List<SelectItem> listaSistemaOperativo = new ArrayList<SelectItem>();
    public List<SelectItem> listaAplicativoCajero = new ArrayList<SelectItem>();
    public List<SelectItem> listaFormatoDiarioElectronico = new ArrayList<SelectItem>();

    /**
     * Creates a new instance of UsuariosBean
     */
    @PostConstruct
    public void InfoTipoCajeroBean() {
        String error = "";
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            listaRegistros = new ArrayList<TipoCajeroDTO>();
            if (objectContext != null) {
                Collection<TipoCajero> items = tipoCajeroSession.getTodos();
                listaRegistros = cargarListaRegistros(objectContext.getTipoCajeroCB(items));

                dataInicial();
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void dataInicial() {
        this.registro = new TipoCajeroDTO();
        this.pagina = 1;
        this.regPagina = 1;
        this.registroSeleccionado = "1";
        buscarRegistro();
        this.cantidadRegistros = listaRegistros.size();
        listaMarcaCajero = new MarcaCajero().crearLista();
        listaSistemaOperativo = new SistemaOperativo().crearLista();
        listaAplicativoCajero = new AplicativoCajero().crearLista();
        listaFormatoDiarioElectronico = new FormatoDiarioElectronico().crearLista();
    }

    public void nuevoRegistro() {
        this.registro.limpiarTipoCajeroDTO();
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
            System.err.println("InfoTipoCajeroBean-grabarRegistro usuario:" + this.registro.toString());
            this.pagina = 1;
            TipoCajero registroEntity = cargarRegistro();
            tipoCajeroSession.actualizar(registroEntity);
            Collection<TipoCajero> items = tipoCajeroSession.getTodos();
            listaRegistros = cargarListaRegistros(objectContext.getTipoCajeroCB(items));
            this.registroSeleccionado = "1";
            buscarRegistro();
            this.cantidadRegistros = listaRegistros.size();
            this.mostrarBtnCancelar = false;
            this.mostrarBtnGrabar = false;
            abrirModal("SARA", "Se ha guardado exitosamente el registro", null);
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

    private TipoCajero cargarRegistro() throws ServletException {
        return this.registro.entidad();
    }

    public void buscarRegistro() {
        String error = "";
        TipoCajeroDTO reg = null;
        try {
            reg = obtenerRegistro("BUSCAR");
            TipoCajero registroEntity = new TipoCajero(reg.getCodigoTipoCajero());
            registroEntity = tipoCajeroSession.buscar(registroEntity.getCodigoTipoCajero());
            registroConsulta(registroEntity);
        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Ha llegado al último registro", null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

    }

    public void registroConsulta(TipoCajero registroEntity) {
        this.registro = this.registro.entidadDTO(registroEntity);
        this.mostrarBtnGrabar = false;
        this.mostrarBtnCancelar = false;
    }

    public void anteriorRegistro() {
        String error = "";
        try {
            if (this.pagina > 1) {
                this.pagina -= 1;
            }
            Collection<TipoCajero> items = tipoCajeroSession.getTodos(pagina, regPagina);
            this.registroSeleccionado = obtenerIdDeRegistro(objectContext.getTipoCajeroCB(items).get(0));
            buscarRegistro();
        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Ha llegado al último registro", null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

    }

    public void siguienteRegistro() {
        String error = "";
        try {
            this.pagina += 1;
            Collection<TipoCajero> items = tipoCajeroSession.getTodos(pagina, regPagina);
            if (items.size() > 0) {
                this.registroSeleccionado = obtenerIdDeRegistro(objectContext.getTipoCajeroCB(items).get(0));
                buscarRegistro();
            } else {
                abrirModal("SARA", "Ha llegado al último registro", null);
                this.pagina -= 1;
            }
        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Ha llegado al último registro", null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public TipoCajeroDTO obtenerRegistro(String operUsuario) {
        TipoCajeroDTO mod = null;
        int rId = Integer.parseInt(this.registroSeleccionado);
        switch (operUsuario) {
            case "BUSCAR":
                for (TipoCajeroDTO item : listaRegistros) {
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

    private List<TipoCajeroDTO> cargarListaRegistros(List<TipoCajeroDTO> cajeroDTO) {
        List<TipoCajeroDTO> lista = new ArrayList<TipoCajeroDTO>();
        int cont = 1;
        for (TipoCajeroDTO dto : cajeroDTO) {
            dto.setIdCajero(cont);
            lista.add(dto);
            cont++;
        }
        return lista;
    }

    private String obtenerIdDeRegistro(TipoCajeroDTO dTO) {
        String id = "1";
        for (TipoCajeroDTO item : listaRegistros) {
            if (item.getCodigoTipoCajero().equals(dTO.getCodigoTipoCajero())) {
                id = String.valueOf(item.getIdCajero());
                break;
            }
        }
        return id;
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

    public List<TipoCajeroDTO> getListaRegistros() {
        return listaRegistros;
    }

    public void setListaRegistros(List<TipoCajeroDTO> listaRegistros) {
        this.listaRegistros = listaRegistros;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public TipoCajeroDTO getRegistro() {
        return registro;
    }

    public void setRegistro(TipoCajeroDTO registro) {
        this.registro = registro;
    }

    public List<SelectItem> getListaMarcaCajero() {
        return listaMarcaCajero;
    }

    public void setListaMarcaCajero(List<SelectItem> listaMarcaCajero) {
        this.listaMarcaCajero = listaMarcaCajero;
    }

    public List<SelectItem> getListaSistemaOperativo() {
        return listaSistemaOperativo;
    }

    public void setListaSistemaOperativo(List<SelectItem> listaSistemaOperativo) {
        this.listaSistemaOperativo = listaSistemaOperativo;
    }

    public List<SelectItem> getListaAplicativoCajero() {
        return listaAplicativoCajero;
    }

    public void setListaAplicativoCajero(List<SelectItem> listaAplicativoCajero) {
        this.listaAplicativoCajero = listaAplicativoCajero;
    }

    public List<SelectItem> getListaFormatoDiarioElectronico() {
        return listaFormatoDiarioElectronico;
    }

    public void setListaFormatoDiarioElectronico(List<SelectItem> listaFormatoDiarioElectronico) {
        this.listaFormatoDiarioElectronico = listaFormatoDiarioElectronico;
    }

}
