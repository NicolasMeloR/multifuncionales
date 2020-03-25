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
import com.davivienda.sara.dto.TransportadoraDTO;
import com.davivienda.sara.entitys.Transportadora;
import com.davivienda.sara.tablas.transportadora.session.TransportadoraSessionLocal;
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
import javax.servlet.ServletException;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "infoTransportadoraBean")
@ViewScoped
public class InfoTransportadoraBean extends BaseBean implements Serializable {

    @EJB
    TransportadoraSessionLocal transportadoraSessionLocal;

    public ComponenteAjaxObjectContextWeb objectContext;

    public TransportadoraDTO registro;
    public String registroSeleccionado;
    private List<TransportadoraDTO> listaRegistros;
    public boolean mostrarBtnGrabar;
    public boolean mostrarBtnCancelar;
    public int cantidadRegistros;
    public int pagina;
    public int regPagina;

    /**
     * Creates a new instance of UsuariosBean
     */
    @PostConstruct
    public void InfoTransportadoraBean() {
        String error = "";
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            listaRegistros = new ArrayList<TransportadoraDTO>();
            if (objectContext != null) {

                Collection<Transportadora> items = transportadoraSessionLocal.getTodos();
                listaRegistros = cargarListaRegistros(objectContext.getTransportadoraCB(items));
                dataInicial();
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void dataInicial() {
        this.registro = new TransportadoraDTO();
        this.pagina = 1;
        this.regPagina = 1;
        this.registroSeleccionado = "1";
        buscarRegistro();
        this.cantidadRegistros = listaRegistros.size();

    }

    public void nuevoRegistro() {
        this.registro.limpiarTranspotadoraDTO();
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
            System.err.println("InfoTransportadoraBean-grabarRegistro usuario:" + this.registro.toString());
            this.pagina = 1;
            Transportadora registroEntity = cargarRegistro();
            transportadoraSessionLocal.actualizar(registroEntity);
            Collection<Transportadora> items = transportadoraSessionLocal.getTodos();
            listaRegistros = cargarListaRegistros(objectContext.getTransportadoraCB(items));
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

    private Transportadora cargarRegistro() throws ServletException {
        return this.registro.entidad();
    }

    public void buscarRegistro() {
        String error = "";
        TransportadoraDTO reg = null;
        try {
            reg = obtenerRegistro("BUSCAR");
            Transportadora registroEntity = new Transportadora(reg.getIdTransportadora());
            registroEntity = transportadoraSessionLocal.buscar(registroEntity.getIdTransportadora());
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

    public void registroConsulta(Transportadora registroEntity) {
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
            Collection<Transportadora> items = transportadoraSessionLocal.getTodos(pagina, regPagina);
            this.registroSeleccionado = obtenerIdDeRegistro(objectContext.getTransportadoraCB(items).get(0));
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
            Collection<Transportadora> items = transportadoraSessionLocal.getTodos(pagina, regPagina);
            if (items.size() > 0) {
                this.registroSeleccionado = obtenerIdDeRegistro(objectContext.getTransportadoraCB(items).get(0));
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

    public TransportadoraDTO obtenerRegistro(String operUsuario) {
        TransportadoraDTO mod = null;
        int rId = Integer.parseInt(this.registroSeleccionado);
        switch (operUsuario) {
            case "BUSCAR":
                for (TransportadoraDTO item : listaRegistros) {
                    if (item.getIdTrans() == rId) {
                        mod = item;
                        this.registroSeleccionado = String.valueOf(item.getIdTrans());
                        this.pagina = item.getIdTrans();
                        break;
                    }
                }
                break;
            default:
                break;
        }
        return mod;
    }

    private List<TransportadoraDTO> cargarListaRegistros(List<TransportadoraDTO> dTOs) {
        List<TransportadoraDTO> lista = new ArrayList<TransportadoraDTO>();
        int cont = 1;
        for (TransportadoraDTO dto : dTOs) {
            dto.setIdTrans(cont);
            lista.add(dto);
            cont++;
        }
        return lista;
    }

    private String obtenerIdDeRegistro(TransportadoraDTO dTO) {
        String id = "1";
        for (TransportadoraDTO item : listaRegistros) {
            if (item.getIdTransportadora().equals(dTO.getIdTransportadora())) {
                id = String.valueOf(item.getIdTrans());
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

    public List<TransportadoraDTO> getListaRegistros() {
        return listaRegistros;
    }

    public void setListaRegistros(List<TransportadoraDTO> listaRegistros) {
        this.listaRegistros = listaRegistros;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public TransportadoraDTO getRegistro() {
        return registro;
    }

    public void setRegistro(TransportadoraDTO registro) {
        this.registro = registro;
    }

}
