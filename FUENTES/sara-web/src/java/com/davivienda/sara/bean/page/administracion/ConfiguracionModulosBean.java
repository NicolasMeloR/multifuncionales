/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.administracion;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.dto.ConfModulosAplicacionDTO;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
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
import javax.servlet.ServletException;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "configuracionModulosBean")
@ViewScoped
public class ConfiguracionModulosBean extends BaseBean implements Serializable {

    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionLocal;

    public ComponenteAjaxObjectContextWeb objectContext;

    public String modulo;
    public String atributo;
    public String descripcion;
    public String valor;

    public String registroSeleccionado;
    private List<ConfModulosAplicacionDTO> listaRegistros;
    public boolean mostrarBtnGrabar;
    public boolean mostrarBtnCancelar;
    public int cantidadRegistros;
    public int pagina;
    public int regPagina;

    /**
     * Creates a new instance of UsuariosBean
     */
    @PostConstruct
    public void ConfiguracionModulosBean() {
        String error = "";
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            listaRegistros = new ArrayList<ConfModulosAplicacionDTO>();

            if (objectContext != null) {
                dataInicial();
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);

        }
    }

    public void dataInicial() throws Exception {
        this.pagina = 1;
        this.regPagina = 1;
        Collection<ConfModulosAplicacion> items = confModulosAplicacionLocal.getTodos(pagina, regPagina);
        listaRegistros = cargarListaRegistros(objectContext.getConfModulosAplicacionCB(items));
        this.registroSeleccionado = "1";
        buscarRegistro();
        this.cantidadRegistros = listaRegistros.size();
    }

    public void nuevoRegistro() {
        this.modulo = "";
        this.atributo = "";
        this.descripcion = "";
        this.valor = "";
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
            System.err.println("ConfiguracionModulosBean-grabarUsuario modulo:" + this.modulo + " atributo:" + this.atributo + "  descripcion:" + this.descripcion + " valor:" + this.valor);
            this.pagina = 1;
            ConfModulosAplicacion registroEntity = cargarRegistro();
            confModulosAplicacionLocal.actualizar(registroEntity);
            Collection<ConfModulosAplicacion> items = confModulosAplicacionLocal.getTodos(pagina, regPagina);
            listaRegistros = cargarListaRegistros(objectContext.getConfModulosAplicacionCB(items));
            this.registroSeleccionado = "1";
            buscarRegistro();
            this.cantidadRegistros = listaRegistros.size();
            abrirModal("SARA", "Se ha guardado exitosamente el registro", null);
            this.mostrarBtnCancelar = false;
            this.mostrarBtnGrabar = false;
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

    private ConfModulosAplicacion cargarRegistro() throws ServletException {
        ConfModulosAplicacion mod = new ConfModulosAplicacion();
        ConfModulosAplicacionPK pk = new ConfModulosAplicacionPK();
        String str = "";
        str = this.modulo;
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("modulo vacio");
        }
        pk.setModulo(this.modulo);
        str = this.atributo;
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("atributo vacio");
        }
        pk.setAtributo(this.atributo);
        mod.setConfModulosAplicacionPK(pk);

        str = this.descripcion;
        if (str.length() == 0) {
            throw new ServletException("Descripción inválida");
        }
        mod.setDescripcion(this.descripcion);

        str = this.valor;
        if (str.length() == 0) {
            throw new ServletException("valor inválido");
        }

        mod.setValor(this.valor);
        return mod;
    }

    public void buscarRegistro() {
        String error = "";
        ConfModulosAplicacionDTO mod = null;
        try {
            mod = obtenerRegistro("BUSCAR");
            ConfModulosAplicacion registroEntity = new ConfModulosAplicacion(mod.getModulo(), mod.getAtributo());
            registroEntity = confModulosAplicacionLocal.buscar(registroEntity.getConfModulosAplicacionPK());
            registroConsulta(objectContext.getConfModulosAplicacionDTO(registroEntity));
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

    public void registroConsulta(ConfModulosAplicacionDTO registroEntity) {
        this.modulo = registroEntity.getModulo();
        this.atributo = registroEntity.getAtributo();
        this.descripcion = registroEntity.getDescripcion();
        this.valor = registroEntity.getValor();
        this.mostrarBtnGrabar = false;
        this.mostrarBtnCancelar = false;
    }

    public void anteriorRegistro() {
        String error = "";
        try {
            if (this.pagina > 1) {
                this.pagina -= 1;
            }
            Collection<ConfModulosAplicacion> items = confModulosAplicacionLocal.getTodos(pagina, regPagina);
            listaRegistros = cargarListaRegistros(objectContext.getConfModulosAplicacionCB(items));
            this.registroSeleccionado = "1";
            buscarRegistro();
            this.cantidadRegistros = listaRegistros.size();

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
            Collection<ConfModulosAplicacion> items = confModulosAplicacionLocal.getTodos(pagina, regPagina);
            if (items.size() > 0) {
                listaRegistros = cargarListaRegistros(objectContext.getConfModulosAplicacionCB(items));
                this.registroSeleccionado = "1";
                buscarRegistro();
                this.cantidadRegistros = listaRegistros.size();
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

    public ConfModulosAplicacionDTO obtenerRegistro(String operUsuario) {
        ConfModulosAplicacionDTO mod = null;
        int cMod = Integer.parseInt(this.registroSeleccionado);
        switch (operUsuario) {
            case "BUSCAR":
                for (ConfModulosAplicacionDTO item : listaRegistros) {
                    if (item.getIdModulo() == cMod) {
                        mod = item;
                        this.registroSeleccionado = String.valueOf(item.getIdModulo());
                        break;
                    }
                }
                break;
            default:
                break;
        }
        return mod;
    }

    private List<ConfModulosAplicacionDTO> cargarListaRegistros(List<ConfModulosAplicacionDTO> confModulosAplicacionDTO) {
        List<ConfModulosAplicacionDTO> lista = new ArrayList<ConfModulosAplicacionDTO>();
        int cont = 1;
        for (ConfModulosAplicacionDTO dto : confModulosAplicacionDTO) {
            dto.setIdModulo(cont);
            lista.add(dto);
        }
        return lista;
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

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getRegistroSeleccionado() {
        return registroSeleccionado;
    }

    public void setRegistroSeleccionado(String registroSeleccionado) {
        this.registroSeleccionado = registroSeleccionado;
    }

    public List<ConfModulosAplicacionDTO> getListaRegistros() {
        return listaRegistros;
    }

    public void setListaRegistros(List<ConfModulosAplicacionDTO> listaRegistros) {
        this.listaRegistros = listaRegistros;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

}
