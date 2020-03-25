/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.cuadreCajeros;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.constantes.ConsultaAjuste;
import com.davivienda.sara.dto.ConsultarTotalesDTO;
import com.davivienda.sara.procesos.ajustes.session.AjustesProcesosSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.ws.gestor.cliente.ResumenAjustes;
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
@ManagedBean(name = "consultarTotalesBean")
@ViewScoped
public class ConsultarTotalesBean extends BaseBean implements Serializable {

    @EJB
    AjustesProcesosSessionLocal sessionConsultaTotales;

    public ComponenteAjaxObjectContextWeb objectContext;
    private String codigoCajero;
    private String codigoOcca;
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
        this.mostrarPanelGeneral = true;
        this.mostrarRespuesta = false;
        SelectItem item1 = new SelectItem(-1, "");
        this.tiposConsultas.add(item1);
        SelectItem item2 = new SelectItem(0, "Informe TotalesATM");
        this.tiposConsultas.add(item2);
        SelectItem item3 = new SelectItem(1, "ResumenIDOTerminal");
        this.tiposConsultas.add(item3);
        SelectItem item4 = new SelectItem(2, "ResumenIDOOficina");
        this.tiposConsultas.add(item4);
        usuarioInicial();

    }

    public void usuarioInicial() {
        this.tipoSelecccionado = "";
        this.codigoCajero = "0";
        this.codigoOcca = "0";
        this.mostrarRespuesta = false;
        this.mostrarPanelGeneral = true;
        this.mostrarRespuesta = false;
    }

    public void consultar() {
        ResumenAjustes[] items = null;
        Integer codigo_Cajero = 0;
        Integer codigo_Occa = 0;

        if (null != this.consultaTotales) {
            this.consultaTotales.clear();
        }

        this.consultaTotales = new ArrayList<ConsultarTotalesDTO>();

        ConsultaAjuste consultaAjuste = ConsultaAjuste.Informe_TotalesATM;

        try {
            
            if(this.tipoSelecccionado.equals("")){
                    abrirModal("SARA", Constantes.MSJ_SELECCIONAR_TIPO_CONSULTA, null);
                    return;
            }
            
            codigo_Cajero = Integer.parseInt(this.codigoCajero);
            codigo_Occa = Integer.parseInt(this.codigoOcca);

            consultaAjuste = ConsultaAjuste.getConsultaAjuste(Integer.parseInt(this.tipoSelecccionado));

            try {

                switch (consultaAjuste) {
                    case Informe_TotalesATM:
                        items = sessionConsultaTotales.consultarInformeTotalesATM(codigo_Cajero, codigo_Occa);

                        break;
                    case ResumenIDO_Terminal:

                        items = sessionConsultaTotales.consultarResumenIDOTerminal(codigo_Occa);

                        break;
                    case ResumenIDO_Oficina:
                        items = sessionConsultaTotales.consultarResumenIDOOficina(codigo_Occa);

                        break;

                    default:

                        break;
                }

                if (null == items || items.length == 0) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
                    return;
                }

            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    //abrirModal("SARA", "Error Consultando Entitys", ex);
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return;
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    //abrirModal("SARA", "Error consultando Entitys", null);
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return;
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
                    return;
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
            this.mostrarRespuesta = false;
            this.mostrarPanelGeneral = true;
        } else {
            this.convertResultadoTotales(items);
            this.mostrarRespuesta = true;
            this.mostrarPanelGeneral = false;
        }
    }

    private void convertResultadoTotales(ResumenAjustes[] items) {
        Integer idRegistro = 0;
        try {
            for (ResumenAjustes item : items) {
                ConsultarTotalesDTO consultar = new ConsultarTotalesDTO();
                consultar.setIdRegistro(++idRegistro);
                consultar.setTitulo(item.getTitulo());
                consultar.setCantidad(String.valueOf(item.getCantidad()));
                consultar.setValor(com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValor()));
                this.consultaTotales.add(consultar);
            }
        } catch (Exception ex) {
            abrirModal("SARA", "Error al parsear Registros", ex);
        }
    }

    public String getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(String codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public String getCodigoOcca() {
        return codigoOcca;
    }

    public void setCodigoOcca(String codigoOcca) {
        this.codigoOcca = codigoOcca;
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
