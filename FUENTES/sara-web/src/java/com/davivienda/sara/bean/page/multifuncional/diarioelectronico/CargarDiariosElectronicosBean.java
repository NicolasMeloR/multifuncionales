/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.multifuncional.diarioelectronico;

import com.davivienda.multifuncional.tablas.txmultifuncionalefectivo.session.TxMultifuncionalEfectivoSessionLocal;
import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.bean.InitBean;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.TipoDiario;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.JSon;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author calvarez
 */
@ManagedBean(name = "cargarDiariosElectronicosBean")
@ViewScoped
public class CargarDiariosElectronicosBean extends BaseBean implements Serializable {

    private Integer tipointDiario_;

    @EJB
    TxMultifuncionalEfectivoSessionLocal session;
    public ComponenteAjaxObjectContextWeb objectContext;

    @PostConstruct
    public void CargarDiariosElectronicosBean() {
        objectContext = cargarComponenteAjaxObjectContext();
    }

    public void cargarDiariosElectronicos() {

        String respuesta = "";
        String strExepcion = "";
        try {
            
            if(null ==  tipointDiario_){
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_TIPO_DIARIO, null);
                return;
            }
            
            try {
                if (tipointDiario_ > 0) {

                    TipoDiario tipoDiario = TipoDiario.Efectivo;
                    if (tipointDiario_ == 3) {
                        tipointDiario_ = 0;
                    }
                    tipoDiario = TipoDiario.getTipoDiario(tipointDiario_);

                    switch (tipoDiario) {
                        case Efectivo:
                            session.cargarDiariosMultiEfectivo();
                            respuesta = "Se actualizaron con éxito en la bd los registros de diario efectivo";
                            abrirModal("SARA", respuesta, null);
                            break;
                        case Cheque:
                            session.cargarDiariosMultiCheque();
                            respuesta = "Se actualizaron con éxito en la bd los registros de diario cheque";
                            abrirModal("SARA", respuesta, null);
                            break;
                        case Log:
                            session.cargarLogEventos();
                            respuesta = "Se actualizaron con éxito en la bd los registros de log de eventos";
                            abrirModal("SARA", respuesta, null);
                            break;
                    }
                }

            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //objectContext.setError(strExepcion, CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
                //abrirModal("SARA", CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo() + ": ", ex);
                abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                objectContext.setError(ex.getMessage(), CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
                //abrirModal("SARA", CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo() + ": ", ex);
                abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
            }

        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            objectContext.setError(ex.getMessage(), CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
            //abrirModal("SARA", CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo() + ": ", ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        }
    }

    public String diasTranscurridos(int dias) {

        String fechaFinal = "";
        Calendar fecha = Calendar.getInstance();
        fecha.set(2000, 00, 01);
        fecha.add(Calendar.DAY_OF_YEAR, dias);
        Timestamp time = new Timestamp(fecha.getTimeInMillis());
        SimpleDateFormat Formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaString = Formato.format(time);
        System.out.println(fechaString);
        return fechaFinal;
    }

    public Integer getTipointDiario_() {
        return tipointDiario_;
    }

    public void setTipointDiario_(Integer tipointDiario_) {
        this.tipointDiario_ = tipointDiario_;
    }
}
