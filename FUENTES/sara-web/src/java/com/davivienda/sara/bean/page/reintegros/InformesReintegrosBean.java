/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.reintegros;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.reintegros.general.ReintegrosGeneralObjectContext;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.helper.ReintegrosInformesServletHelper;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.notasdebito.session.NotasDebitoSessionLocal;
import com.davivienda.sara.tablas.reintegros.session.ReintegrosSessionLocal;
import com.davivienda.sara.util.ConvertidorJSon;
import com.davivienda.utilidades.Constantes;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "informesReintegrosBean")
@ViewScoped
public class InformesReintegrosBean extends BaseBean implements Serializable {

    @EJB
    ReintegrosSessionLocal reintegrosSessionLocal;
    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionLocal;
    @EJB
    NotasDebitoSessionLocal notasDebitoSessionLocal;

    public ReintegrosGeneralObjectContext reintegrosGeneralObjectContext;
    public ComponenteAjaxObjectContextWeb objectContext;
    private List<SelectItem> listaHora;
    private String fechaDesde;
    private String horaDesde;
    private String fechaHasta;
    private String horaHasta;
    private String tipoReporte;

    /**
     * Creates a new instance of CopiarArchivoBean
     */
    @PostConstruct
    public void informesReintegrosBean() {
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            reintegrosGeneralObjectContext = new ReintegrosGeneralObjectContext(getRequestFaces(), getResponseFaces());
            listaHora = new ArrayList<SelectItem>();
            if (objectContext != null) {
                dataInicial();
            }

        } catch (Exception ex) {
            abrirModal("SARA", "Error", ex);
        }
    }

    public void dataInicial() {
        this.fechaDesde = "";
        this.horaDesde = "00:00:00";
        this.fechaHasta = "";
        this.horaHasta = "23:59:59";
        this.tipoReporte = "";
        this.listaHora = cargarListaHora();
    }

    public void generarInforme() {
        System.err.println("InformesReintegrosBean-generarInforme");
        System.err.println("============== InformesReintegrosBean-generarInforme tipoReporte: " + this.tipoReporte);
        String respuesta = "";
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date fechaI = formatter.parse(this.fechaDesde);
            } catch (Exception ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }

            if (this.tipoReporte.isEmpty() || this.tipoReporte.equals("")) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_TIPO_REPORTE, null);
                return;
            }

            if(null == this.fechaHasta || this.fechaHasta.isEmpty()){
                fechaHasta = this.fechaDesde;
            }

            
            ReintegrosHelperInterface reintegrosHelper = null;
            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "crearInformeExcel" + " por  " + reintegrosGeneralObjectContext.getIdUsuarioEnSesion() + " desde " + reintegrosGeneralObjectContext.getdireccionIP());
            reintegrosGeneralObjectContext.setAtributoFechaInicial(this.fechaDesde);
            reintegrosGeneralObjectContext.setAtributoFechaHoraFinal(fechaHasta, horaHasta);
            reintegrosGeneralObjectContext.setAtributoTipoReporte(this.tipoReporte);

            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("InformesReintegrosBean-generarInforme: FechaInicial: " + this.fechaDesde+horaDesde + " FechaFinal: "+ this.fechaHasta+horaHasta);
            
            reintegrosHelper = new ReintegrosInformesServletHelper(reintegrosSessionLocal, reintegrosGeneralObjectContext, confModulosAplicacionLocal, notasDebitoSessionLocal);//, reintegrosHistoricoSessionLocal);
            if (reintegrosHelper != null) {
                respuesta = reintegrosHelper.obtenerDatos();

                if (respuesta.length() == 0) {
                    //abrirModal("SARA", "No se ha podido procesar la solicitud", null);
                } else {
                    abrirModal("SARA", ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "mensaje"), null);
                }
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
        System.err.println("InformesReintegrosBean esp: " + respuesta);
    }

    private List<SelectItem> cargarListaHora() {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        boolean iniciar = true;
        int mn = 0;
        int hr = 0;
        while (iniciar) {
            SelectItem item = null;
            if (hr < 24) {
                if (mn == 0) {
                    if (hr < 10) {
                        item = new SelectItem("0" + hr + ":00:00", "0" + hr + ":00:00");
                    } else {
                        item = new SelectItem(+hr + ":00:00", +hr + ":00:00");
                    }
                    mn += 15;
                } else if (mn < 60) {
                    if (hr < 10) {
                        item = new SelectItem("0" + hr + ":" + mn + ":00", "0" + hr + ":" + mn + ":00");
                    } else {
                        item = new SelectItem(+hr + ":" + mn + ":00", +hr + ":" + mn + ":00");
                    }
                    mn += 15;
                } else {
                    hr++;
                    mn = 0;
                    if (hr < 24) {
                        if (hr < 10) {
                            item = new SelectItem("0" + hr + ":00:00", "0" + hr + ":00:00");
                        } else {
                            item = new SelectItem(+hr + ":00:00", +hr + ":00:00");
                        }
                    }
                    mn += 15;

                }
                if (item != null) {
                    lista.add(item);
                }

            } else {
                iniciar = false;
            }
        }
        return lista;
    }

    public void cambioTipoReporte(ValueChangeEvent e) {
        String str = (String) e.getNewValue();
        if (null != str) {
            this.tipoReporte = str;
        }
    }

    public List<SelectItem> getListaHora() {
        return listaHora;
    }

    public void setListaHora(List<SelectItem> listaHora) {
        this.listaHora = listaHora;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getHoraDesde() {
        return horaDesde;
    }

    public void setHoraDesde(String horaDesde) {
        this.horaDesde = horaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getHoraHasta() {
        return horaHasta;
    }

    public void setHoraHasta(String horaHasta) {
        this.horaHasta = horaHasta;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

}
