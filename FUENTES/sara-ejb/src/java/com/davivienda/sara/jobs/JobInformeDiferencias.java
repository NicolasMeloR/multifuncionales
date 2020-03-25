/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.jobs;

import com.davivienda.sara.base.BaseServicio;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.cuadrecifras.general.bean.InformeAyerBean;
import com.davivienda.sara.cuadrecifras.general.helper.CuadreCifrasHelperInterface;
import com.davivienda.sara.cuadrecifras.general.helper.CuadreCifrasInformeServletHelper;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasSessionLocal;
import com.davivienda.sara.cuadrecifras.session.InformeDiferenciasSessionLocal;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.procesos.cuadrecifras.session.ProcesoCuadreCifrasSessionLocal;
import com.davivienda.sara.tablas.provisionhost.session.ProvisionHostLocal;
import com.davivienda.sara.tablas.reintegros.session.ReintegrosSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SchedulerInfo;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
@Stateless(mappedName = "JobInformeDiferencias")
@Remote({JobInformeDiferenciasRemote.class})
@RemoteHome(JobInformeDiferenciasHome.class)
public class JobInformeDiferencias extends BaseServicio implements JobInformeDiferenciasRemote, TimedObject {

    @Resource
    SessionContext context;
    private TimerHandle timerHandler;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

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
        System.out.println("JobInformeDiferencias: --> " + System.getProperty("weblogic.Name") + " Timer Created ... prox eje: " + timer.getNextTimeout());
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
                            System.err.println("JobInformeDiferencias: --> Eliminando tarea programada: " + tarea.getTipoProceso() + " con fecha proxima ejecucion: " + sdf.format(tarea.getProximaEjecucion()));
                            timer.cancel();
                        }
                    } catch (Exception exc) {
                        System.err.println("JobInformeDiferencias: --> Error eliminando tarea programada: " + exc.getMessage());
                    }
                }
            } else {
                System.err.println("JobInformeDiferencias: --> remove() context es nulo");
            }

        } catch (Exception exc) {
            System.out.println("JobInformeDiferencias: --> No se pudo detener las tareas programadas: " + exc.getMessage());
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
            loggerApp.info("com.davivienda.sara.jobs.JobInformeDiferencias.ejbTimeout()");
            loggerApp.info("Inicio - " + this.getClass().getSimpleName());
            loggerApp.log(Level.INFO, "Empieza el proceso de JobInformeDiferencias con fecha: {0} y nombre nodo: {1}",
                    new Object[]{com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(),
                                "yyyy/MM/dd HH:mm:ss"), System.getProperty("weblogic.Name")});

            String respuesta = "";

            SchedulerInfo scheduler = (SchedulerInfo) timer.getInfo();

            loggerApp.info("Inicia Proceso de filtro de registros susceptibles de reintegro");

            CuadreCifrasHelperInterface cuadreCifrasHelper = new CuadreCifrasInformeServletHelper(sessionCuadreCifras, procesoCuadreCifrasSessionLocal, diferenciasSessionLocal, provisionHostsession, reintegrosSession);
            /*Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_MONTH, 31);
            c.set(Calendar.MONTH, 4);
            loggerApp.info("Fecha quemada: " + c.getTime().toString());*/
            cuadreCifrasHelper.procesoInformeDescuadre(com.davivienda.utilidades.conversion.Fecha.getCalendarAyer(), -1);
          
            loggerApp.info("com.davivienda.sara.jobs.JobInformeDiferencias.ejbTimeout():" + respuesta);

            TimerService timerService = this.context.getTimerService();
            timer = timerService.createTimer(new CronExpression(scheduler.getCronExpresion()).getNextValidTimeAfter(new Date()), scheduler);
            loggerApp.info(System.getProperty("weblogic.Name") + " EJB Timed Out ... prox eje: " + timer.getNextTimeout());

        } catch (Exception exc) {
            loggerApp.log(Level.SEVERE, "JobInformeDiferencias-Exception: ", exc);
        }
    }
}
