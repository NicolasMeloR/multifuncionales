/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.reintegros;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.procesos.reintegros.session.ProcesoReintegrosSessionLocal;
import com.davivienda.sara.reintegros.general.ReintegrosGeneralObjectContext;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.helper.ReintegrosCalcularReintegrosServletHelper;
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
import javax.faces.model.SelectItem;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "calcularReintegrosBean")
@ViewScoped
public class CalcularReintegrosBean extends BaseBean implements Serializable {

    @EJB
    private ProcesoReintegrosSessionLocal procesoReintegrosSessionLocal;

    private ReintegrosGeneralObjectContext reintegrosGeneralObjectContext;
    private ComponenteAjaxObjectContextWeb objectContext;
    private List<SelectItem> listaHora;
    private String fecha;
    private String hora;

    /**
     * Creates a new instance of CopiarArchivoBean
     */
    @PostConstruct
    public void CalcularReintegrosBean() {
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
        this.fecha = "";
        this.hora = "00:00:00";
        listaHora = cargarListaHora();
    }

    public void calcular() {
        reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("CalcularReintegrosBean-calcular");
        String respuesta = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date fechaI = formatter.parse(this.fecha);
            } catch (Exception ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }
            
            ReintegrosHelperInterface reintegrosHelper = null;
            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "calcularReintegros" + " por  " + reintegrosGeneralObjectContext.getIdUsuarioEnSesion() + " desde " + reintegrosGeneralObjectContext.getdireccionIP());
            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("CalcularReintegrosBean-calcular: Fecha: " + this.fecha + " Hora: "+ this.hora);
            reintegrosGeneralObjectContext.setAtributoFechaInicial(this.fecha);
            reintegrosHelper = new ReintegrosCalcularReintegrosServletHelper(procesoReintegrosSessionLocal, reintegrosGeneralObjectContext);
            if (reintegrosHelper != null) {
                respuesta = reintegrosHelper.obtenerDatos();

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
        System.err.println("CalcularReintegrosBean esp: " + respuesta);
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public List<SelectItem> getListaHora() {
        return listaHora;
    }

    public void setListaHora(List<SelectItem> listaHora) {
        this.listaHora = listaHora;
    }

}
