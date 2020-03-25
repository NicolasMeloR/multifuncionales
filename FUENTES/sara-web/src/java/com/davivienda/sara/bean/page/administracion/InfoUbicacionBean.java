/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.administracion;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.dto.RegionalDTO;
import com.davivienda.sara.dto.UbicacionDTO;
import com.davivienda.sara.dto.ZonaDTO;
import com.davivienda.sara.dto.listas.CajeroActivo;
import com.davivienda.sara.dto.listas.TipoOficina;
import com.davivienda.sara.dto.listas.TipoUbicacion;
import com.davivienda.sara.entitys.Regional;
import com.davivienda.sara.entitys.Ubicacion;
import com.davivienda.sara.entitys.Zona;
import com.davivienda.sara.tablas.regional.session.RegionalSessionLocal;
import com.davivienda.sara.tablas.ubicacion.session.UbicacionSessionLocal;
import com.davivienda.sara.tablas.zona.session.ZonaSessionLocal;
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
@ManagedBean(name = "infoUbicacionBean")
@ViewScoped
public class InfoUbicacionBean extends BaseBean implements Serializable {

    @EJB
    UbicacionSessionLocal ubicacionSession;
    @EJB
    ZonaSessionLocal zonaSession;
    @EJB
    RegionalSessionLocal regionalSessionLocal;

    public ComponenteAjaxObjectContextWeb objectContext;

    public UbicacionDTO registro;
    public String registroSeleccionado;
    private List<UbicacionDTO> listaRegistros;
    public boolean mostrarBtnGrabar;
    public boolean mostrarBtnCancelar;
    public int cantidadRegistros;
    public int pagina;
    public int regPagina;
    public List<SelectItem> listaUbicacionActivo = new ArrayList<SelectItem>();
    public List<SelectItem> listaTipoOficina = new ArrayList<SelectItem>();
    public List<SelectItem> listaTipoUbicacion = new ArrayList<SelectItem>();
    public List<ZonaDTO> listaZona = new ArrayList<ZonaDTO>();
    public List<RegionalDTO> listaRegional = new ArrayList<RegionalDTO>();
    public Collection<Zona> zonaItems;
    public Collection<Regional> regionalItems;
    public int primerZona;
    public int primerRegional;
    
    /**
     * Creates a new instance of UsuariosBean
     */
    @PostConstruct
    public void InfoUbicacionBean() {
        String error = "";
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            listaRegistros = new ArrayList<UbicacionDTO>();
            if (objectContext != null) {
                Collection<Ubicacion> items = ubicacionSession.getTodos();
                listaRegistros = cargarListaRegistros(objectContext.getUbicacionCB(items));

                zonaItems = zonaSession.getTodos();
                listaZona = cargarListaZona(objectContext.getZonaCB(zonaItems));
                primerZona = listaZona.get(0).getIdZona();

                regionalItems = regionalSessionLocal.getTodos();
                listaRegional = cargarListaRegional(objectContext.getRegionalCB(regionalItems));
                primerRegional = listaRegional.get(0).getIdRegional();

                dataInicial();
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void dataInicial() {
        this.registro = new UbicacionDTO();
        this.pagina = 1;
        this.regPagina = 1;
        this.registroSeleccionado = "1";
        buscarRegistro();
        this.cantidadRegistros = listaRegistros.size();
        listaUbicacionActivo = new CajeroActivo().crearLista();
        listaTipoOficina = new TipoOficina().crearLista();
        listaTipoUbicacion = new TipoUbicacion().crearLista();
    }

    public void nuevoRegistro() {
        this.registro.limpiarUbicacionDTO(primerZona, primerRegional);
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
            System.err.println("InfoUbicacionBean-grabarRegistro:" + this.registro.toString());
            this.pagina = 1;
            Ubicacion registroEntity = cargarRegistro();
            ubicacionSession.actualizar(registroEntity);
            Collection<Ubicacion> items = ubicacionSession.getTodos();
            listaRegistros = cargarListaRegistros(objectContext.getUbicacionCB(items));
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

    private Ubicacion cargarRegistro() throws ServletException {
        Ubicacion tc = this.registro.entidad();
        Zona objZona = null;
        Regional objRegional = null;
        
        for (Regional item : regionalItems) {
            if (item.getIdRegional().equals(tc.getRegional().getIdRegional())) {
                objRegional = item;
                break;
            }
        }
        
        for (Zona item : zonaItems) {
            if (item.getIdZona().equals(tc.getZona().getIdZona())) {
                objZona = item;
                break;
            }
        }
        
        tc.setZona(objZona);
        tc.setRegional(objRegional);
        
        return tc;
    }

    public void buscarRegistro() {
        String error = "";
        UbicacionDTO reg = null;
        try {
            reg = obtenerRegistro("BUSCAR");
            Ubicacion registroEntity = new Ubicacion(reg.getCodigoUbicacion());
            registroEntity = ubicacionSession.buscar(registroEntity.getCodigoUbicacion());
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

    public void registroConsulta(Ubicacion registroEntity) {
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
            Collection<Ubicacion> items = ubicacionSession.getTodos(pagina, regPagina);
            this.registroSeleccionado = obtenerIdDeRegistro(objectContext.getUbicacionCB(items).get(0));
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
            Collection<Ubicacion> items = ubicacionSession.getTodos(pagina, regPagina);
            if (items.size() > 0) {
                this.registroSeleccionado = obtenerIdDeRegistro(objectContext.getUbicacionCB(items).get(0));
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

    public UbicacionDTO obtenerRegistro(String operUsuario) {
        UbicacionDTO mod = null;
        int rId = Integer.parseInt(this.registroSeleccionado);
        switch (operUsuario) {
            case "BUSCAR":
                for (UbicacionDTO item : listaRegistros) {
                    if (item.getIdUbicacion() == rId) {
                        mod = item;
                        this.registroSeleccionado = String.valueOf(item.getIdUbicacion());
                        this.pagina = item.getIdUbicacion();
                        break;
                    }
                }
                break;
            default:
                break;
        }
        return mod;
    }

    private List<UbicacionDTO> cargarListaRegistros(List<UbicacionDTO> ubicacionDTO) {
        List<UbicacionDTO> lista = new ArrayList<UbicacionDTO>();
        int cont = 1;
        for (UbicacionDTO dto : ubicacionDTO) {
            dto.setIdUbicacion(cont);
            lista.add(dto);
            cont++;
        }
        return lista;
    }

    private String obtenerIdDeRegistro(UbicacionDTO dTO) {
        String id = "1";
        for (UbicacionDTO item : listaRegistros) {
            if (item.getCodigoUbicacion().equals(dTO.getCodigoUbicacion())) {
                id = String.valueOf(item.getIdUbicacion());
                break;
            }
        }
        return id;
    }

    private List<ZonaDTO> cargarListaZona(List<ZonaDTO> dTOs) {
        List<ZonaDTO> lista = new ArrayList<ZonaDTO>();
        int cont = 1;
        for (ZonaDTO dto : dTOs) {
            dto.setIdZonac(cont);
            lista.add(dto);
            cont++;
        }
        return lista;
    }

    private List<RegionalDTO> cargarListaRegional(List<RegionalDTO> dTOs) {
        List<RegionalDTO> lista = new ArrayList<RegionalDTO>();
        int cont = 1;
        for (RegionalDTO dto : dTOs) {
            dto.setIdRegionalc(cont);
            lista.add(dto);
            cont++;
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

    public String getRegistroSeleccionado() {
        return registroSeleccionado;
    }

    public void setRegistroSeleccionado(String registroSeleccionado) {
        this.registroSeleccionado = registroSeleccionado;
    }

    public List<UbicacionDTO> getListaRegistros() {
        return listaRegistros;
    }

    public void setListaRegistros(List<UbicacionDTO> listaRegistros) {
        this.listaRegistros = listaRegistros;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public UbicacionDTO getRegistro() {
        return registro;
    }

    public void setRegistro(UbicacionDTO registro) {
        this.registro = registro;
    }

    public ComponenteAjaxObjectContextWeb getObjectContext() {
        return objectContext;
    }

    public void setObjectContext(ComponenteAjaxObjectContextWeb objectContext) {
        this.objectContext = objectContext;
    }

    public List<SelectItem> getListaUbicacionActivo() {
        return listaUbicacionActivo;
    }

    public void setListaUbicacionActivo(List<SelectItem> listaUbicacionActivo) {
        this.listaUbicacionActivo = listaUbicacionActivo;
    }

    public List<SelectItem> getListaTipoOficina() {
        return listaTipoOficina;
    }

    public void setListaTipoOficina(List<SelectItem> listaTipoOficina) {
        this.listaTipoOficina = listaTipoOficina;
    }

    public List<SelectItem> getListaTipoUbicacion() {
        return listaTipoUbicacion;
    }

    public void setListaTipoUbicacion(List<SelectItem> listaTipoUbicacion) {
        this.listaTipoUbicacion = listaTipoUbicacion;
    }

    public List<ZonaDTO> getListaZona() {
        return listaZona;
    }

    public void setListaZona(List<ZonaDTO> listaZona) {
        this.listaZona = listaZona;
    }

    public List<RegionalDTO> getListaRegional() {
        return listaRegional;
    }

    public void setListaRegional(List<RegionalDTO> listaRegional) {
        this.listaRegional = listaRegional;
    }
    
}
