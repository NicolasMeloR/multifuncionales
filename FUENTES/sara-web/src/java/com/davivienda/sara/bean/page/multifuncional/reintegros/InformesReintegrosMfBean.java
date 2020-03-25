/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.multifuncional.reintegros;

import com.davivienda.multifuncional.reintegros.general.ReintegrosMultifuncionalGeneralObjectContext;
import com.davivienda.multifuncional.reintegros.general.ReintegrosMultifuncionalHelperInterface;
import com.davivienda.multifuncional.reintegros.helper.ReintegrosMultiInformesServletHelper;
import com.davivienda.multifuncional.tablas.notasdebitomulti.session.NotasDebitoMultiSessionLocal;
import com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.session.ReintegrosMultiEfectivoSessionLocal;
import com.davivienda.multifuncional.tablas.reintegrosmultifuncional.session.ReintegrosMultifuncionalSessionLocal;
import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.notasdebito.session.NotasDebitoSessionLocal;
import com.davivienda.sara.tablas.reintegros.session.ReintegrosSessionLocal;
import com.davivienda.sara.util.ConvertidorJSon;
import com.davivienda.utilidades.Constantes;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "informesReintegrosMfBean")
@ViewScoped
public class InformesReintegrosMfBean extends BaseBean implements Serializable {

    @EJB
    ReintegrosSessionLocal reintegrosSessionLocal;
    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionLocal;
    @EJB
    NotasDebitoSessionLocal notasDebitoSessionLocal;
    @EJB
    ReintegrosMultiEfectivoSessionLocal reintegrosMultiEfectivoSessionLocal;
    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionSession;
    @EJB
    NotasDebitoMultiSessionLocal notasDebitoMultiSessionLocal;

    @EJB
    ReintegrosMultifuncionalSessionLocal reintegrosMultifuncionalSessionLocal;

    public ReintegrosMultifuncionalGeneralObjectContext reintegroMultifuncionalGeneralObjectContext;
    public ComponenteAjaxObjectContextWeb objectContext;
    private List<SelectItem> listaHora;
    private String fechaDesde;
    private String horaDesde;
    private String fechaHasta;
    private String horaHasta;
    private String tipoReporte;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates a new instance of CopiarArchivoBean
     */
    @PostConstruct
    public void InformesReintegrosMfBean() {
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            reintegroMultifuncionalGeneralObjectContext = new ReintegrosMultifuncionalGeneralObjectContext(getRequestFaces(), getResponseFaces());
            listaHora = new ArrayList<SelectItem>();
            if (objectContext != null) {
                dataInicial();
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
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
        System.err.println("InformesReintegrosMfBean-generarInforme");
        String respuesta = "";
        try {
            try {
                Date fechaInicial = formatter.parse(this.fechaDesde);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }
            if (tipoReporte == null || tipoReporte.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_TIPO_REPORTE, null);
                return;
            }

            if(null == this.fechaHasta || this.fechaHasta.isEmpty()){
                this.fechaHasta = this.fechaDesde;
            }

            
            ReintegrosMultifuncionalHelperInterface reintegroMultifuncionalHelper = null;
            reintegroMultifuncionalGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "crearInformeExcel" + " por  " + reintegroMultifuncionalGeneralObjectContext.getIdUsuarioEnSesion() + " desde " + reintegroMultifuncionalGeneralObjectContext.getdireccionIP());
            reintegroMultifuncionalGeneralObjectContext.setAtributoFechaInicial(this.fechaDesde);
            reintegroMultifuncionalGeneralObjectContext.setAtributoFechaHoraFinal(fechaHasta, horaHasta);
            reintegroMultifuncionalGeneralObjectContext.setAtributoTipoReporte(this.tipoReporte);

            objectContext.getConfigApp().loggerApp.info("InformesReintegrosMfBean-generarInforme - fechaInicial " + fechaDesde +" "+ horaDesde + " fechaFinal " + fechaHasta +" "+ horaHasta);
            
            reintegroMultifuncionalHelper = new ReintegrosMultiInformesServletHelper(reintegrosMultiEfectivoSessionLocal, reintegroMultifuncionalGeneralObjectContext, confModulosAplicacionSession, notasDebitoMultiSessionLocal, reintegrosMultifuncionalSessionLocal);
            if (reintegroMultifuncionalHelper != null) {
                respuesta = reintegroMultifuncionalHelper.obtenerDatos();

                if (respuesta.length() == 0) {
                    abrirModal("SARA", "No se ha podido procesar la solicitud", null);
                } else {
                    abrirModal("SARA", ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "mensaje"), null);
                }
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
        System.err.println("InformesReintegrosMfBean esp: " + respuesta);
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
