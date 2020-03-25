/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.contingencia;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.contingencia.general.ContingenciaManualGeneralObjectContext;
import com.davivienda.sara.contingencia.general.ContingenciaManualHelperInterface;
import com.davivienda.sara.contingencia.general.helper.ContingenciaGeneralDiariosServletHelper;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasCargasSessionLocal;
import com.davivienda.sara.procesos.cuadrecifras.session.ProcesoCuadreCifrasSessionLocal;
import com.davivienda.sara.procesos.diarioelectronico.session.AdministradorProcesosSessionLocal;
import com.davivienda.sara.procesos.edccargue.session.AdministradorProcesosEdcCargueSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.regcargue.session.RegCargueArchivoSessionLocal;
import com.davivienda.sara.tablas.transacciontemp.session.TransaccionTempSessionLocal;
import com.davivienda.sara.tareas.contingencia.carguearchivos.session.ContingenciaCargueArchivosSessionLocal;
import com.davivienda.sara.tareas.regcargue.session.AdminTareasRegCargueArchivoSessionLocal;
import com.davivienda.sara.util.ConvertidorJSon;
import com.davivienda.utilidades.Constantes;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "contingenciaDiariosEBean")
@ViewScoped
public class ContingenciaDiariosEBean  extends BaseBean implements Serializable {

    @EJB
    ProcesoCuadreCifrasSessionLocal procesoCuadreCifrasSessionLocal;

    @EJB
    CuadreCifrasCargasSessionLocal cuadreCifrasCargas;

    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionSession;

    @EJB
    AdminTareasRegCargueArchivoSessionLocal adminTareasRegCargueArchivoSessionLocal;

    @EJB
    RegCargueArchivoSessionLocal regCargueArchivoSessionLocal;

    @EJB
    ContingenciaCargueArchivosSessionLocal contingenciaCargueArchivosSessionLocal;

    @EJB
    TransaccionTempSessionLocal transaccionTempSessionLocal;

    @EJB
    AdministradorProcesosEdcCargueSessionLocal administradorProcesosEdcCargueSessionLocal;
    
    @EJB 
    AdministradorProcesosSessionLocal administradorProcesosSessionLocal;

    public ContingenciaManualGeneralObjectContext contingenciaManualGeneralObjectContext;
    public ComponenteAjaxObjectContextWeb objectContext;
    private List<SelectItem> listaHora;
    private String fecha;
    private String hora;
    private String cajero;
    private String tipoDiario;
    private Logger loggerApp;

    /**
     * Creates a new instance of CopiarArchivoBean
     */
    @PostConstruct
    public void ContingenciaDiariosEBean() {
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            contingenciaManualGeneralObjectContext = new ContingenciaManualGeneralObjectContext(getRequestFaces(), getResponseFaces());
            listaHora = new ArrayList<SelectItem>();
            if (objectContext != null) {
                listaHora = cargarListaHora();
                this.fecha = "";
                this.hora = "";
                this.cajero = "0";
                this.tipoDiario = "";
                loggerApp = contingenciaManualGeneralObjectContext.getConfigApp().loggerApp;
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void cargarDiario() {
       loggerApp.info("ContingenciaDiariosEBean-cargarDiario");
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                 Date fechaInicial = formatter.parse(this.fecha);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }
             if(null == this.cajero || this.cajero.equals("") || this.cajero.equals("0")){
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return;
            }
             
            if(null == this.tipoDiario || this.tipoDiario.equals("")){
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_TIPO_DIARIO, null);
                return;
            }
            
            loggerApp.info("ContingenciaDiariosEBean-fecha y hora: " +fecha+hora);
            ContingenciaManualHelperInterface cuadreCifrasHelper = null;
            String directorioUpload = "";
            directorioUpload = contingenciaManualGeneralObjectContext.getConfigApp().DIRECTORIO_UPLOAD;

            loggerApp.info("ContingenciaDiariosEBean directorioUpload: " + directorioUpload);
            String respuesta = null;

            if (contingenciaManualGeneralObjectContext.getConfigApp().USUARIO_QUARTZ.equals(contingenciaManualGeneralObjectContext.getAtributoString("usuario"))) {

                contingenciaManualGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "contingenciaGeneral" + " por  USUARIO_QUARTZ " + contingenciaManualGeneralObjectContext.getConfigApp().USUARIO_QUARTZ + " desde " + contingenciaManualGeneralObjectContext.getdireccionIP());

            } else {
                contingenciaManualGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "contingenciaGeneral" + " por  " + " desde " + contingenciaManualGeneralObjectContext.getdireccionIP());
            }

            contingenciaManualGeneralObjectContext.setAtributoFechaInicial(this.fecha);
            contingenciaManualGeneralObjectContext.setAtributoTipoDiarioString(this.tipoDiario);
            contingenciaManualGeneralObjectContext.setAtributoCodigo_Cajero(this.cajero);
            
            cuadreCifrasHelper = new ContingenciaGeneralDiariosServletHelper(administradorProcesosSessionLocal, contingenciaManualGeneralObjectContext, administradorProcesosEdcCargueSessionLocal, confModulosAplicacionSession, directorioUpload, adminTareasRegCargueArchivoSessionLocal, regCargueArchivoSessionLocal, contingenciaCargueArchivosSessionLocal);

            if (cuadreCifrasHelper != null) {
                respuesta = cuadreCifrasHelper.obtenerDatos();
                if (respuesta.length() == 0) {
                    abrirModal("SARA", "No se ha podido procesar la solicitud", null);
                }else{
                    abrirModal("SARA", ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "mensaje"), null);
                }
                loggerApp.info("ContingenciaDiariosEBean-cargarDiario resp: " +respuesta);
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
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

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public String getTipoDiario() {
        return tipoDiario;
    }

    public void setTipoDiario(String tipoDiario) {
        this.tipoDiario = tipoDiario;
    }

    

}
