// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.jobs;

import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionAttribute;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.quartz.CronExpression;
import com.davivienda.utilidades.conversion.JSon;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.sara.constantes.EstadoProceso;
import com.davivienda.utilidades.archivo.ProcesosArchivo;
import com.davivienda.utilidades.edc.Edc;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.logging.Level;
import com.davivienda.sara.config.SaraConfig;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.RemoveException;
import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.EJBException;
import java.util.Iterator;
import javax.ejb.TimerService;
import java.io.Serializable;
import javax.ejb.Timer;
import com.davivienda.utilidades.SchedulerInfo;
import java.util.logging.Logger;
import com.davivienda.sara.tablas.transacciontemp.session.TransaccionTempSessionLocal;
import com.davivienda.sara.tablas.edccargue.session.EdcCargueSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.procesos.edccargue.session.AdministradorProcesosEdcCargueSessionLocal;
import javax.ejb.EJB;
import com.davivienda.sara.procesos.diarioelectronico.session.AdministradorProcesosSessionLocal;
import java.text.SimpleDateFormat;
import javax.ejb.TimerHandle;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.RemoteHome;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TimedObject;
import com.davivienda.sara.base.BaseServicio;

@Stateless(mappedName = "JobCargueDiarios")
@Remote({ JobCargueDiariosRemote.class })
@RemoteHome(JobCargueDiariosHome.class)
public class JobCargueDiarios extends BaseServicio implements JobCargueDiariosRemote, TimedObject
{
    @Resource
    SessionContext context;
    private TimerHandle timerHandler;
    SimpleDateFormat sdf;
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
    
    public JobCargueDiarios() {
        this.sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }
    
    @Override
    public TimerHandle createTimer(final SchedulerInfo schedulerInfo) throws EJBException {
        final TimerService timerService = this.context.getTimerService();
        if (null != timerService) {
            final Iterator iter = timerService.getTimers().iterator();
            while (iter.hasNext()) {
                try {
                    final Timer timer = (Timer) iter.next();
                    final SchedulerInfo tarea = (SchedulerInfo)timer.getInfo();
                    if (!tarea.getTipoProceso().equals(schedulerInfo.getTipoProceso())) {
                        continue;
                    }
                    System.err.println("Tarea programada ya existe:  " + tarea.getTipoProceso() + " con fecha de ejecucion " + timer.getNextTimeout());
                    timer.cancel();
                    System.err.println("Tarea eliminada " + tarea.getTipoProceso());
                }
                catch (Exception exc) {
                    System.err.println("Error consultando tareas programadas: " + exc.getMessage());
                }
            }
        }
        final Timer timer2 = timerService.createTimer(schedulerInfo.getProximaEjecucion(), (Serializable)schedulerInfo);
        this.timerHandler = timer2.getHandle();
        System.out.println("JobCargueDiarios: --> " + System.getProperty("weblogic.Name") + " Timer Created ... prox eje: " + timer2.getNextTimeout());
        return this.timerHandler;
    }
    
    public EJBHome getEJBHome() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Object getPrimaryKey() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void remove() throws RemoteException, RemoveException {
        try {
            if (this.context != null) {
                final Iterator iter = this.context.getTimerService().getTimers().iterator();
                while (iter.hasNext()) {
                    try {
                        final Timer timer = (Timer) iter.next();
                        final SchedulerInfo tarea = (SchedulerInfo)timer.getInfo();
                        if (!tarea.getTipoProceso().equals("CargueDiarios")) {
                            continue;
                        }
                        this.sdf.setLenient(false);
                        System.err.println("JobCargueDiarios: --> Eliminando tarea programada: " + tarea.getTipoProceso() + " con fecha proxima ejecucion: " + this.sdf.format(tarea.getProximaEjecucion()));
                        timer.cancel();
                    }
                    catch (Exception exc) {
                        System.err.println("JobCargueDiarios: --> Error eliminando tarea programada: " + exc.getMessage());
                    }
                }
            }
            else {
                System.err.println("JobCargueDiarios: --> remove() context es nulo");
            }
        }
        catch (Exception exc2) {
            System.out.println("JobCargueDiarios: --> No se pudo detener las tareas programadas: " + exc2.getMessage());
            exc2.printStackTrace();
        }
    }
    
    public Handle getHandle() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean isIdentical(final EJBObject obj) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ejbTimeout(Timer timer) {
        try {
            (this.loggerApp = SaraConfig.obtenerInstancia().loggerApp).info("com.davivienda.sara.jobs.JobCargueDiariwebos.ejbTimeout()");
            this.loggerApp.info("Inicio - " + this.getClass().getSimpleName());
            this.loggerApp.log(Level.INFO, "Empieza el proceso de JobCargueDiarios para la fecha: {0} y nombre nodo: {1}", new Object[] { Fecha.aCadena(Fecha.getCalendarHoy(), "yyyy/MM/dd HH:mm:ss"), System.getProperty("weblogic.Name") });
            String respuesta = "";
            int intArchivosDescomprimidos = -1;
            String strExepcion = "";
            String nombreArchivo = "";
            final SchedulerInfo scheduler = (SchedulerInfo)timer.getInfo();
            try {
                Calendar fechaInicial = null;
                fechaInicial = Fecha.getCalendar(Fecha.getDateHoy());
                try {
                    nombreArchivo = Edc.getNombreArchivoCiclosComprimido(fechaInicial);
                    final ArrayList lstArchivos = ProcesosArchivo.unzipArray(scheduler.getDirectorioUpload(), nombreArchivo);
                    intArchivosDescomprimidos = this.administradorProcesosEdcCargueSessionLocal.registrarCicloEdcCargue(lstArchivos, nombreArchivo, fechaInicial, EstadoProceso.INICIADO.getEstado(), false);
                    this.transaccionTempSessionLocal.cargarDiariosElectronicosXStoreP_Automatico();
                    if (intArchivosDescomprimidos >= 0) {
                        respuesta = "Se Descomprimieron y Subieron a la Base de Datos : " + intArchivosDescomprimidos + " Archivos a la fecha: " + Fecha.aCadena(Fecha.getCalendarHoy(), FormatoFecha.FECHA_HORA);
                        respuesta = JSon.getJSonRespuesta(CodigoError.SIN_ERROR.getCodigo(), respuesta);
                    }
                }
                catch (EJBException ex) {
                    this.loggerApp.log(Level.SEVERE, "JobCargueDiarios-EJBException: ", (Throwable)ex);
                    if (ex.getMessage() == null) {
                        strExepcion = ex.getCause().getMessage();
                    }
                    else {
                        strExepcion = ex.getMessage();
                    }
                    respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);
                }
                catch (Exception ex2) {
                    this.loggerApp.log(Level.SEVERE, "JobCargueDiarios-Exception Interno: ", ex2);
                    respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex2.getMessage());
                }
            }
            catch (IllegalArgumentException ex3) {
                this.loggerApp.log(Level.SEVERE, "JobCargueDiarios-IllegalArgumentException: ", ex3);
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex3.getMessage());
            }
            this.loggerApp.info("com.davivienda.sara.jobs.JobCargueDiarios.ejbTimeout():" + respuesta);
            final TimerService timerService = this.context.getTimerService();
            timer = timerService.createTimer(new CronExpression(scheduler.getCronExpresion()).getNextValidTimeAfter(new Date()), (Serializable)scheduler);
            this.loggerApp.info(System.getProperty("weblogic.Name") + " EJB Timed Out ... prox eje: " + timer.getNextTimeout());
        }
        catch (Exception exc) {
            this.loggerApp.log(Level.SEVERE, "JobCargueDiarios-Exception: ", exc);
        }
    }
}
