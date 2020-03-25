// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.jobs;

import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionAttribute;
import com.davivienda.sara.cuadrecifras.general.helper.CuadreCifrasHelperInterface;
import java.util.Date;
import org.quartz.CronExpression;
import com.davivienda.sara.cuadrecifras.general.helper.CuadreCifrasInformeServletHelper;
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
import com.davivienda.sara.tablas.reintegros.session.ReintegrosSessionLocal;
import com.davivienda.sara.tablas.provisionhost.session.ProvisionHostLocal;
import com.davivienda.sara.cuadrecifras.session.InformeDiferenciasSessionLocal;
import com.davivienda.sara.procesos.cuadrecifras.session.ProcesoCuadreCifrasSessionLocal;
import javax.ejb.EJB;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasSessionLocal;
import java.text.SimpleDateFormat;
import javax.ejb.TimerHandle;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.RemoteHome;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TimedObject;
import com.davivienda.sara.base.BaseServicio;

@Stateless(mappedName = "JobInformeDiferencias")
@Remote({ JobInformeDiferenciasRemote.class })
@RemoteHome(JobInformeDiferenciasHome.class)
public class JobInformeDiferencias extends BaseServicio implements JobInformeDiferenciasRemote, TimedObject
{
    @Resource
    SessionContext context;
    private TimerHandle timerHandler;
    SimpleDateFormat sdf;
    @EJB
    CuadreCifrasSessionLocal sessionCuadreCifras;
    @EJB
    ProcesoCuadreCifrasSessionLocal procesoCuadreCifrasSessionLocal;
    @EJB
    InformeDiferenciasSessionLocal diferenciasSessionLocal;
    @EJB
    ProvisionHostLocal provisionHostsession;
    @EJB
    ReintegrosSessionLocal reintegrosSession;
    private Logger loggerApp;
    
    public JobInformeDiferencias() {
        this.sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }
    
    @Override
    public TimerHandle createTimer(final SchedulerInfo schedulerInfo) throws EJBException {
        final TimerService timerService = this.context.getTimerService();
        if (null != timerService) {
            final Iterator iter = timerService.getTimers().iterator();
            while (iter.hasNext()) {
                try {
                    final Timer timer =(Timer) iter.next();
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
        System.out.println("JobInformeDiferencias: --> " + System.getProperty("weblogic.Name") + " Timer Created ... prox eje: " + timer2.getNextTimeout());
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
                        System.err.println("JobInformeDiferencias: --> Eliminando tarea programada: " + tarea.getTipoProceso() + " con fecha proxima ejecucion: " + this.sdf.format(tarea.getProximaEjecucion()));
                        timer.cancel();
                    }
                    catch (Exception exc) {
                        System.err.println("JobInformeDiferencias: --> Error eliminando tarea programada: " + exc.getMessage());
                    }
                }
            }
            else {
                System.err.println("JobInformeDiferencias: --> remove() context es nulo");
            }
        }
        catch (Exception exc2) {
            System.out.println("JobInformeDiferencias: --> No se pudo detener las tareas programadas: " + exc2.getMessage());
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
            (this.loggerApp = SaraConfig.obtenerInstancia().loggerApp).info("com.davivienda.sara.jobs.JobInformeDiferencias.ejbTimeout()");
            this.loggerApp.info("Inicio - " + this.getClass().getSimpleName());
            this.loggerApp.log(Level.INFO, "Empieza el proceso de JobInformeDiferencias con fecha: {0} y nombre nodo: {1}", new Object[] { Fecha.aCadena(Fecha.getCalendarHoy(), "yyyy/MM/dd HH:mm:ss"), System.getProperty("weblogic.Name") });
            final String respuesta = "";
            final SchedulerInfo scheduler = (SchedulerInfo)timer.getInfo();
            this.loggerApp.info("Inicia Proceso de filtro de registros susceptibles de reintegro");
            final CuadreCifrasHelperInterface cuadreCifrasHelper = new CuadreCifrasInformeServletHelper(this.sessionCuadreCifras, this.procesoCuadreCifrasSessionLocal, this.diferenciasSessionLocal, this.provisionHostsession, this.reintegrosSession);
            cuadreCifrasHelper.procesoInformeDescuadre(Fecha.getCalendarAyer(), -1);
            this.loggerApp.info("com.davivienda.sara.jobs.JobInformeDiferencias.ejbTimeout():" + respuesta);
            final TimerService timerService = this.context.getTimerService();
            timer = timerService.createTimer(new CronExpression(scheduler.getCronExpresion()).getNextValidTimeAfter(new Date()), (Serializable)scheduler);
            this.loggerApp.info(System.getProperty("weblogic.Name") + " EJB Timed Out ... prox eje: " + timer.getNextTimeout());
        }
        catch (Exception exc) {
            this.loggerApp.log(Level.SEVERE, "JobInformeDiferencias-Exception: ", exc);
        }
    }
}
