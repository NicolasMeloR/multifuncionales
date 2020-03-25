/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.contingencia;

import com.davivienda.multifuncional.reintegros.session.ReintegrosMultiSessionLocal;
import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.constantes.CargueArchivoContingencia;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.TipoCajeroMulti;
import com.davivienda.sara.contingencia.general.ContingenciaManualGeneralObjectContext;
import com.davivienda.sara.contingencia.general.ContingenciaManualHelperInterface;
import com.davivienda.sara.contingencia.general.helper.ContingenciaGeneralArchivosServletHelper;
import com.davivienda.sara.cuadrecifras.general.helper.CuadreCifrasHelperInterface;
import com.davivienda.sara.cuadrecifras.general.helper.CuadreCifrasInformeServletHelper;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasCargasSessionLocal;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasSessionLocal;
import com.davivienda.sara.cuadrecifras.session.InformeDiferenciasSessionLocal;
import com.davivienda.sara.procesos.cuadrecifras.session.ProcesoCuadreCifrasSessionLocal;
import com.davivienda.sara.procesos.edccargue.session.AdministradorProcesosEdcCargueSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.provisionhost.session.ProvisionHostLocal;
import com.davivienda.sara.tablas.regcargue.session.RegCargueArchivoSessionLocal;
import com.davivienda.sara.tablas.reintegros.session.ReintegrosSessionLocal;
import com.davivienda.sara.tablas.transacciontemp.session.TransaccionTempSessionLocal;
import com.davivienda.sara.tareas.contingencia.carguearchivos.session.ContingenciaCargueArchivosSessionLocal;
import com.davivienda.sara.tareas.regcargue.session.AdminTareasRegCargueArchivoSessionLocal;
import com.davivienda.sara.util.ConvertidorJSon;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.JSon;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
@ManagedBean(name = "contingenciaGeneralBean")
@ViewScoped
public class ContingenciaGeneralBean extends BaseBean implements Serializable {

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
    ReintegrosMultiSessionLocal reintegrosMultiSessionLocal;

    @EJB
    TransaccionTempSessionLocal transaccionTempSessionLocal;

    @EJB
    AdministradorProcesosEdcCargueSessionLocal administradorProcesosEdcCargueSessionLocal;

    @EJB
    CuadreCifrasSessionLocal sessionCuadreCifras;
    @EJB
    InformeDiferenciasSessionLocal diferenciasSessionLocal;
    @EJB
    ProvisionHostLocal provisionHostsession;
    @EJB
    ReintegrosSessionLocal reintegrosSession;

    public ContingenciaManualGeneralObjectContext contingenciaManualGeneralObjectContext;
    public ComponenteAjaxObjectContextWeb objectContext;
    private List<SelectItem> listaHora;
    private String fecha;
    private String hora;
    private String tipoTarea;
    private String tipoCajero;
    private Logger loggerApp;

    /**
     * Creates a new instance of CopiarArchivoBean
     */
    @PostConstruct
    public void contingenciaGeneralBean() {
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            contingenciaManualGeneralObjectContext = new ContingenciaManualGeneralObjectContext(getRequestFaces(), getResponseFaces());
            listaHora = new ArrayList<SelectItem>();
            if (objectContext != null) {
                listaHora = cargarListaHora();
                this.tipoTarea = "";
                this.tipoCajero = "";
                this.hora = "";
                this.fecha = "";
                loggerApp = objectContext.getConfigApp().loggerApp;
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void cargarArchivo() {
        loggerApp.info("contingenciaGeneralBean-cargarArchivo");
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date fechaInicial = formatter.parse(this.fecha);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }
            if (null == this.tipoTarea || this.tipoTarea.equals("")) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_TIPO_TAREA, null);
                return;
            }
            if (null == this.tipoCajero || this.tipoCajero.equals("")) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return;
            }

            loggerApp.log(Level.INFO, "contingenciaGeneralBean-fecha y hora: {0} {1} tipoTarea {2}", new Object[]{fecha, hora, this.tipoTarea});

            CargueArchivoContingencia tipoArchivo = CargueArchivoContingencia.TODOS;
            tipoArchivo = CargueArchivoContingencia.getCarguearchivo(com.davivienda.utilidades.conversion.Cadena.aInteger(this.tipoTarea));

            if (tipoArchivo.equals(CargueArchivoContingencia.INFORMEDIFERENCIAS)) {
                ejecutarProcesoInformeDiferencias();
            } else {
                ejecutarTareasContingencia();
            }

        } catch (Exception ex) {
            loggerApp.log(Level.SEVERE, null, ex);
            abrirModal("SARA", ex.getMessage(), null);
        }
    }

    public void ejecutarProcesoInformeDiferencias() {
        try {
            loggerApp.log(Level.INFO, "Empieza el proceso Manual de Informe Diferencias");

            Calendar fechaInicial = null;
            try {
                fechaInicial = com.davivienda.utilidades.conversion.Cadena.aCalendar(this.fecha);
            } catch (IllegalArgumentException ex) {
                loggerApp.log(Level.SEVERE, "Error:", ex);
                throw new IllegalArgumentException("Por favor seleccione una fecha");
            }
            CuadreCifrasHelperInterface cuadreCifrasHelper = new CuadreCifrasInformeServletHelper(sessionCuadreCifras, procesoCuadreCifrasSessionLocal, diferenciasSessionLocal, provisionHostsession, reintegrosSession);
            loggerApp.log(Level.INFO, "Fecha Manual Informe Diferencias {0}",
                    new Object[]{com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicial, "yyyy/MM/dd HH:mm:ss")});

            loggerApp.info("Inicia Proceso de filtro de registros susceptibles de reintegro");
            cuadreCifrasHelper.procesoInformeDescuadre(fechaInicial, -1);
        } catch (IllegalArgumentException | IllegalAccessException exc) {
            throw new IllegalArgumentException("Error al ejecutar proceso de informe diferencias manual");
        }
    }

    public void ejecutarTareasContingencia() {
        try {
            ContingenciaManualHelperInterface cuadreCifrasHelper = null;
            String directorioUpload = "";
            directorioUpload = contingenciaManualGeneralObjectContext.getConfigApp().DIRECTORIO_UPLOAD;

            String respuesta = null;

            if (contingenciaManualGeneralObjectContext.getConfigApp().USUARIO_QUARTZ.equals(contingenciaManualGeneralObjectContext.getAtributoString("usuario"))) {

                contingenciaManualGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "contingenciaGeneral" + " por  USUARIO_QUARTZ " + contingenciaManualGeneralObjectContext.getConfigApp().USUARIO_QUARTZ + " desde " + contingenciaManualGeneralObjectContext.getdireccionIP());

            } else {
                contingenciaManualGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "contingenciaGeneral" + " por  " + " desde " + contingenciaManualGeneralObjectContext.getdireccionIP());
            }

            contingenciaManualGeneralObjectContext.setAtributoFechaInicial(this.fecha);
            contingenciaManualGeneralObjectContext.setAtributoTipoCajeroString(this.tipoCajero);
            contingenciaManualGeneralObjectContext.setAtributoTipoTareaString(this.tipoTarea);
            cuadreCifrasHelper = new ContingenciaGeneralArchivosServletHelper(procesoCuadreCifrasSessionLocal, contingenciaManualGeneralObjectContext, cuadreCifrasCargas, confModulosAplicacionSession, adminTareasRegCargueArchivoSessionLocal, regCargueArchivoSessionLocal, contingenciaCargueArchivosSessionLocal, reintegrosMultiSessionLocal, directorioUpload, transaccionTempSessionLocal, administradorProcesosEdcCargueSessionLocal);
            if (cuadreCifrasHelper != null) {
                respuesta = cuadreCifrasHelper.obtenerDatos();
                if (null == respuesta || respuesta.length() == 0) {
                    abrirModal("SARA", "No se ha podido procesar la solicitud", null);
                } else {
                    abrirModal("SARA", ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "mensaje"), null);
                }
                loggerApp.info("contingenciaGeneralBean-cargarArchivo resp: " + respuesta);
            }
        } catch (Exception exc) {
            throw new IllegalArgumentException("Error al ejecutar proceso contingencia general de la tarea "+CargueArchivoContingencia.getCarguearchivo(Integer.parseInt(this.tipoTarea)).nombre + " y cajero " + TipoCajeroMulti.getTipoCajeroMulti(Integer.parseInt(this.tipoCajero)));
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

    public String getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(String tipoTarea) {
        this.tipoTarea = tipoTarea;
    }

    public String getTipoCajero() {
        return tipoCajero;
    }

    public void setTipoCajero(String tipoCajero) {
        this.tipoCajero = tipoCajero;
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

}
