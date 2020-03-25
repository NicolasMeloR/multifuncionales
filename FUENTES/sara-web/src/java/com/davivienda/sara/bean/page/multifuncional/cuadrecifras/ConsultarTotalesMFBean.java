/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.multifuncional.cuadrecifras;

import com.davivienda.multifuncional.constantes.TotalesMultifuncional;
import com.davivienda.multifuncional.ws.cuadrecifrasmulti.servicio.CuadreCifrasMultiWsSessionLocal;
import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.dto.ConsultarTotalesDTO;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.ws.gestor.cliente.ResumenAjustesMulti;
import java.io.Serializable;
import java.util.ArrayList;
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
@ManagedBean(name = "consultarTotalesMFBean")
@ViewScoped
public class ConsultarTotalesMFBean extends BaseBean implements Serializable {

    @EJB
    CuadreCifrasMultiWsSessionLocal session;

    public ComponenteAjaxObjectContextWeb objectContext;
    private String codigoCajero;
    private String codigoOficina;
    private String tipoSelecccionado;
    private List<SelectItem> tiposConsultas;
    private List<ConsultarTotalesDTO> consultaTotales;
    private boolean mostrarRespuesta;
    private boolean mostrarPanelGeneral;

    @PostConstruct
    public void ConsultarTotalesBean() {
        objectContext = cargarComponenteAjaxObjectContext();
        this.tiposConsultas = new ArrayList<SelectItem>();
        this.consultaTotales = new ArrayList<ConsultarTotalesDTO>();
        this.mostrarRespuesta = false;
        SelectItem item1 = new SelectItem(-1, "");
        this.tiposConsultas.add(item1);
        SelectItem item2 = new SelectItem(0, "TransaccionesXoficina");
        this.tiposConsultas.add(item2);
        SelectItem item3 = new SelectItem(1, "ProductoXoficina");
        this.tiposConsultas.add(item3);
        SelectItem item4 = new SelectItem(2, "TransaccionesXTerminal");
        this.tiposConsultas.add(item4);
        SelectItem item5 = new SelectItem(3, "ProductoXTerminal");
        this.tiposConsultas.add(item5);
        usuarioInicial();
    }

    public void usuarioInicial() {
        this.tipoSelecccionado = "";
        this.codigoCajero = "0";
        this.codigoOficina = "0";
        this.mostrarRespuesta = false;
        this.mostrarPanelGeneral = true;
    }

    public void consultar() {

        ResumenAjustesMulti[] items = null;

        Integer codigo_Cajero = 0;
        Integer codigo_Oficina = 0;

        TotalesMultifuncional totalesMultifuncional = TotalesMultifuncional.TransaccionesXoficina;

        try {
            if (tipoSelecccionado == null || tipoSelecccionado.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_TIPO_CONSULTA, null);
                return;
            }

            if (null == codigoCajero || codigoCajero.equals("0") || codigoCajero.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return;
            }

            if (null == codigoOficina || codigoOficina.equals("0") || codigoOficina.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_OFICINA, null);
                return;
            }

            codigo_Cajero = Integer.parseInt(this.codigoCajero);

            codigo_Oficina = Integer.parseInt(this.codigoOficina);

            totalesMultifuncional = TotalesMultifuncional.getConsultaAjuste(this.tipoSelecccionado);

            try {

                switch (totalesMultifuncional) {
                    case TransaccionesXoficina:
                        items = session.consultarInformeTotalesMultifuncional(codigo_Cajero, codigo_Oficina, 0, 1);
                        break;
                    case ProductoXoficina:
                        items = session.consultarInformeTotalesMultifuncional(codigo_Cajero, codigo_Oficina, 1, 1);
                        break;
                    case TransaccionesXTerminal:
                        items = session.consultarInformeTotalesMultifuncional(codigo_Cajero, codigo_Oficina, 0, 0);
                        break;
                    case ProductoXTerminal:
                        items = session.consultarInformeTotalesMultifuncional(codigo_Cajero, codigo_Oficina, 1, 0);
                        break;

                    default:

                        break;
                }

                if (null == items || items.length == 0) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
                }

            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
                }
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
            }

        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        }
        if (items == null || items.length == 0) {
            items = null;
        } else {
            this.convertResultadoTotales(items);
            this.mostrarRespuesta = true;
            this.mostrarPanelGeneral = false;
        }

    }

    private void convertResultadoTotales(ResumenAjustesMulti[] items) {
        Integer idRegistro = 0;
        try {
            for (ResumenAjustesMulti item : items) {
                ConsultarTotalesDTO consultar = new ConsultarTotalesDTO();
                consultar.setIdRegistro(++idRegistro);
                consultar.setTitulo(item.getTitulo());
                consultar.setCantidad(String.valueOf(item.getCantidad()));
                consultar.setValor(item.getValor());
                this.consultaTotales.add(consultar);
            }
        } catch (Exception ex) {
            //abrirModal("SARA", "Error al parsear Registros", ex);
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public String getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(String codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public String getCodigoOficina() {
        return codigoOficina;
    }

    public void setCodigoOficina(String codigoOficina) {
        this.codigoOficina = codigoOficina;
    }

    public String getTipoSelecccionado() {
        return tipoSelecccionado;
    }

    public void setTipoSelecccionado(String tipoSelecccionado) {
        this.tipoSelecccionado = tipoSelecccionado;
    }

    public List<SelectItem> getTiposConsultas() {
        return tiposConsultas;
    }

    public void setTiposConsultas(List<SelectItem> tiposConsultas) {
        this.tiposConsultas = tiposConsultas;
    }

    public List<ConsultarTotalesDTO> getConsultaTotales() {
        return consultaTotales;
    }

    public void setConsultaTotales(List<ConsultarTotalesDTO> consultaTotales) {
        this.consultaTotales = consultaTotales;
    }

    public boolean isMostrarRespuesta() {
        return mostrarRespuesta;
    }

    public void setMostrarRespuesta(boolean mostrarRespuesta) {
        this.mostrarRespuesta = mostrarRespuesta;
    }

    public boolean isMostrarPanelGeneral() {
        return mostrarPanelGeneral;
    }

    public void setMostrarPanelGeneral(boolean mostrarPanelGeneral) {
        this.mostrarPanelGeneral = mostrarPanelGeneral;
    }

}
