/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.jobs;

import com.davivienda.sara.base.BaseServicio;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoProceso;
import com.davivienda.sara.procesos.diarioelectronico.session.AdministradorProcesosSessionLocal;
import com.davivienda.sara.procesos.edccargue.session.AdministradorProcesosEdcCargueSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.edccargue.session.EdcCargueSessionLocal;
import com.davivienda.sara.tablas.transacciontemp.session.TransaccionTempSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SchedulerInfo;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.JSon;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.Remote;
import javax.ejb.RemoteHome;
import javax.ejb.RemoveException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TimedObject;
import javax.ejb.Timer;
import javax.ejb.TimerHandle;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.quartz.CronExpression;

/**
 *
 * @author jediazs
 */
@Stateless(mappedName = "JobCargueDiarios")
@Remote({JobCargueDiariosRemote.class})
@RemoteHome(JobCargueDiariosHome.class)
public class JobCargueDiarios extends BaseServicio implements JobCargueDiariosRemote, TimedObject {

    @Resource
    SessionContext context;
    private TimerHandle timerHandler;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @EJB
    AdministradorProcesosSessionLocal administradorProcesos;
    @EJB
    AdministradorProcesosEdcCargueSessionLocal administradorProcesosEdcCargueSessionLocal;
    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionSession;
    @EJB
    EdcCargueSessionLocal edcCargueSessionLocal;
    @EJB
    TransaccionTempSessionLocal transaccionTempSessionLocal;

    private Logger loggerApp;

    @Override
    public TimerHandle createTimer(SchedulerInfo schedulerInfo) throws EJBException {
        TimerService timerService = this.context.getTimerService();

        if (null != timerService) {
            for (java.util.Iterator iter = timerService.getTimers().iterator(); iter.hasNext();) {
                try {
                    Timer timer = (Timer) iter.next();
                    SchedulerInfo tarea = (SchedulerInfo) timer.getInfo();
                    if (tarea.getTipoProceso().equals(schedulerInfo.getTipoProceso())) {
                        System.err.println("Tarea programada ya existe:  " + tarea.getTipoProceso() + " con fecha de ejecucion " + timer.getNextTimeout());
                        timer.cancel();
                        System.err.println("Tarea eliminada " + tarea.getTipoProceso());
                    }
                } catch (Exception exc) {
                    System.err.println("Error consultando tareas programadas: " + exc.getMessage());
                }
            }
        }

        Timer timer = timerService.createTimer(schedulerInfo.getProximaEjecucion(), schedulerInfo);
        this.timerHandler = timer.getHandle();
        System.out.println("JobCargueDiarios: --> " + System.getProperty("weblogic.Name") + " Timer Created ... prox eje: " + timer.getNextTimeout());
        return this.timerHandler;
    }

    @Override
    public EJBHome getEJBHome() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getPrimaryKey() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove() throws RemoteException, RemoveException {
        try {

            if (context != null) {
                for (java.util.Iterator iter = context.getTimerService().getTimers().iterator(); iter.hasNext();) {
                    try {
                        Timer timer = (Timer) iter.next();
                        SchedulerInfo tarea = (SchedulerInfo) timer.getInfo();
                        if (tarea.getTipoProceso().equals(Constantes.JOB_CARGUE_DIARIOS)) {
                            sdf.setLenient(false);
                            System.err.println("JobCargueDiarios: --> Eliminando tarea programada: " + tarea.getTipoProceso() + " con fecha proxima ejecucion: " + sdf.format(tarea.getProximaEjecucion()));
                            timer.cancel();
                        }
                    } catch (Exception exc) {
                        System.err.println("JobCargueDiarios: --> Error eliminando tarea programada: " + exc.getMessage());
                    }
                }
            } else {
                System.err.println("JobCargueDiarios: --> remove() context es nulo");
            }

        } catch (Exception exc) {
            System.out.println("JobCargueDiarios: --> No se pudo detener las tareas programadas: " + exc.getMessage());
            exc.printStackTrace();
        }
    }

    @Override
    public Handle getHandle() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIdentical(EJBObject obj) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ejbTimeout(Timer timer) {
        try {
            loggerApp = SaraConfig.obtenerInstancia().loggerApp;
            loggerApp.info("com.davivienda.sara.jobs.JobCargueDiariwebos.ejbTimeout()");
            loggerApp.info("Inicio - " + this.getClass().getSimpleName());
            loggerApp.log(Level.INFO, "Empieza el proceso de JobCargueDiarios para la fecha: {0} y nombre nodo: {1}",
                    new Object[]{com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(),
                                "yyyy/MM/dd HH:mm:ss"), System.getProperty("weblogic.Name")});

            String respuesta = "";
            int intArchivosDescomprimidos = -1;
            String strExepcion = "";
            ArrayList lstArchivos;
            String nombreArchivo = "";

            SchedulerInfo scheduler = (SchedulerInfo) timer.getInfo();
            try {

                Calendar fechaInicial = null;
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(com.davivienda.utilidades.conversion.Fecha.getDateHoy());

                try {

                    nombreArchivo = com.davivienda.utilidades.edc.Edc.getNombreArchivoCiclosComprimido(fechaInicial);
                    //COMENTAREADO para probar descompresion desde db
                    lstArchivos = com.davivienda.utilidades.archivo.ProcesosArchivo.unzipArray(scheduler.getDirectorioUpload(), nombreArchivo);
                    intArchivosDescomprimidos = administradorProcesosEdcCargueSessionLocal.registrarCicloEdcCargue(lstArchivos, nombreArchivo, fechaInicial, EstadoProceso.INICIADO.getEstado(), false);
                    //AQUI SE LLAMA AL STORE PROCEDURE CREAR SCRIPT DE INSTRUCCION DE PERMISOS AL USUARIO c_adminatm
                    //COMENTAREADO para probar descompresion desde db
                    //  session.descomprimirEDC_Automatico();
                    transaccionTempSessionLocal.cargarDiariosElectronicosXStoreP_Automatico();
                    
                    //Se elimina llamado a SP REINTEGROS AUTO 28 Abril 2017
                    //transaccionTempSessionLocal.calcReintegrosDAuto();

                    //PROBAR DESCOMPRESION DE ARCHIVOS PARCIALES
                    if (intArchivosDescomprimidos >= 0) {
                        respuesta = "Se Descomprimieron y Subieron a la Base de Datos : " + intArchivosDescomprimidos + " Archivos a la fecha: "
                                + //respuesta = "Se Descomprimieron : " + lstArchivos.size() + " Archivos a la fecha: " + 
                                Fecha.aCadena(Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
                        respuesta = JSon.getJSonRespuesta(CodigoError.SIN_ERROR.getCodigo(), respuesta);
                    }
                     

                } catch (EJBException ex) {
                    loggerApp.log(Level.SEVERE, "JobCargueDiarios-EJBException: ", ex);
                    if (ex.getMessage() == null) {
                        strExepcion = ex.getCause().getMessage();
                    } else {
                        strExepcion = ex.getMessage();
                    }
                    respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);
                } catch (Exception ex) {
                    loggerApp.log(Level.SEVERE, "JobCargueDiarios-Exception Interno: ", ex);
                    respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                }

            } catch (IllegalArgumentException ex) {
                loggerApp.log(Level.SEVERE, "JobCargueDiarios-IllegalArgumentException: ", ex);
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            }

            loggerApp.info("com.davivienda.sara.jobs.JobCargueDiarios.ejbTimeout():" + respuesta);

            TimerService timerService = this.context.getTimerService();
            timer = timerService.createTimer(new CronExpression(scheduler.getCronExpresion()).getNextValidTimeAfter(new Date()), scheduler);
            loggerApp.info(System.getProperty("weblogic.Name") + " EJB Timed Out ... prox eje: " + timer.getNextTimeout());

        } catch (Exception exc) {
            loggerApp.log(Level.SEVERE, "JobCargueDiarios-Exception: ", exc);
        }
    }

}
