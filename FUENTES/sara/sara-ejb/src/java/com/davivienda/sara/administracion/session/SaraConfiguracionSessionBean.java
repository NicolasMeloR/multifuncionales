package com.davivienda.sara.administracion.session;

import com.davivienda.sara.jobs.JobInformeDiferenciasRemote;
import com.davivienda.sara.jobs.JobDaliAutoRemote;
import com.davivienda.sara.jobs.JobCargueDiariosRemote;
import com.davivienda.sara.jobs.JobInformeDiferenciasHome;
import com.davivienda.sara.jobs.JobDaliAutoHome;
import java.util.Date;
import com.davivienda.utilidades.SchedulerInfo;
import com.davivienda.sara.jobs.JobCargueDiariosHome;
import org.quartz.CronExpression;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import javax.naming.InitialContext;
import java.security.SecureRandom;
import javax.annotation.PreDestroy;
import javax.management.MBeanServer;
import javax.management.JMException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Enumeration;
import java.util.Arrays;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Formatter;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.MemoryHandler;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import com.davivienda.sara.base.BaseServicio;
import com.davivienda.sara.config.SaraConfig;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;

@Stateless
public class SaraConfiguracionSessionBean implements SaraConfiguracionSession
{
    @PersistenceContext
    private EntityManager em;
    private static SaraConfig configApp;
    private BaseServicio servicio;
    private static FileHandler fh;
    private static FileHandler fhAcceso;
    private static ConsoleHandler ch;
    private static MemoryHandler mh;
    private static MemoryHandler mhAcceso;
    
    @Override
    public void iniciarConfiguracion(final String archivoPropiedadesConfiguracion) {
        if (SaraConfiguracionSessionBean.configApp == null) {
            try {
                SaraConfiguracionSessionBean.configApp = SaraConfig.obtenerInstancia(archivoPropiedadesConfiguracion, this.em);
                this.servicio = new BaseServicio();
                this.crearLoggerAplicacion();
                this.imprimirConfiguracion();
            }
            catch (IllegalAccessException ex) {
                Logger.getLogger(SaraConfiguracionSessionBean.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            catch (Exception ex2) {
                Logger.getLogger(SaraConfiguracionSessionBean.class.getName()).log(Level.SEVERE, ex2.getMessage(), ex2);
            }
        }
        else {
            SaraConfiguracionSessionBean.configApp.cargarParametrosDB();
            this.imprimirConfiguracion();
        }
        this.cargarTareasProgramadas();
    }
    
    @Override
    public SaraConfig getAppConfig() {
        return SaraConfiguracionSessionBean.configApp;
    }
    
    private void crearLoggerAplicacion() {
        Level nivelLog = Level.ALL;
        try {
            SaraConfiguracionSessionBean.configApp.loggerApp.setLevel(Level.ALL);
            SaraConfiguracionSessionBean.configApp.loggerAcceso.setLevel(Level.ALL);
            if (SaraConfiguracionSessionBean.ch == null && SaraConfiguracionSessionBean.configApp.CONFIGURACION_LOG_MOSTRAR_LOG_CONSOLA > 0) {
                (SaraConfiguracionSessionBean.ch = new ConsoleHandler()).setFormatter(new SimpleFormatter());
                SaraConfiguracionSessionBean.ch.setLevel(nivelLog);
                SaraConfiguracionSessionBean.configApp.loggerApp.addHandler(SaraConfiguracionSessionBean.ch);
            }
            if (SaraConfiguracionSessionBean.fh == null) {
                (SaraConfiguracionSessionBean.fh = new FileHandler(SaraConfiguracionSessionBean.configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS.trim() + "sara.log", SaraConfiguracionSessionBean.configApp.TAMANO_ARCHIVO_LOG, 5, true)).setFormatter(new SimpleFormatter());
                SaraConfiguracionSessionBean.fh.setLevel(nivelLog);
                SaraConfiguracionSessionBean.configApp.loggerApp.addHandler(SaraConfiguracionSessionBean.fh);
            }
            if (SaraConfiguracionSessionBean.fhAcceso == null) {
                (SaraConfiguracionSessionBean.fhAcceso = new FileHandler(SaraConfiguracionSessionBean.configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS.trim() + "sara_acceso.log", SaraConfiguracionSessionBean.configApp.TAMANO_ARCHIVO_LOG, 5, true)).setFormatter(new SimpleFormatter());
                SaraConfiguracionSessionBean.fhAcceso.setLevel(nivelLog);
                SaraConfiguracionSessionBean.configApp.loggerAcceso.addHandler(SaraConfiguracionSessionBean.fhAcceso);
            }
        }
        catch (IOException ex) {
            SaraConfiguracionSessionBean.configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            System.err.println("No se puede cargar la configuraci\u00f3n de logs : " + ex.getMessage());
            ex.printStackTrace();
        }
        catch (SecurityException ex2) {
            SaraConfiguracionSessionBean.configApp.loggerApp.log(Level.SEVERE, ex2.getMessage(), ex2);
            System.err.println("No se puede cargar la configuraci\u00f3n de logs : " + ex2.getMessage());
            ex2.printStackTrace();
        }
        finally {
            if (SaraConfiguracionSessionBean.fh != null) {
                if (SaraConfiguracionSessionBean.mh == null) {
                    (SaraConfiguracionSessionBean.mh = new MemoryHandler(SaraConfiguracionSessionBean.fh, 2000, Level.ALL)).setLevel(nivelLog);
                }
                SaraConfiguracionSessionBean.configApp.loggerApp.info("<<< SARA 12.2.7.7 >>>");
            }
            if (SaraConfiguracionSessionBean.fhAcceso != null && SaraConfiguracionSessionBean.mhAcceso == null) {
                (SaraConfiguracionSessionBean.mhAcceso = new MemoryHandler(SaraConfiguracionSessionBean.fhAcceso, 2000, Level.ALL)).setLevel(nivelLog);
            }
            try {
                nivelLog = Level.parse(SaraConfiguracionSessionBean.configApp.CONFIGURACION_LOG_NIVEL_LOG);
            }
            catch (Exception ex3) {
                nivelLog = Level.ALL;
                SaraConfiguracionSessionBean.configApp.loggerApp.log(Level.SEVERE, "No se puede asignar el  nivel del log , se asigno por defecto en ALL", ex3);
            }
            this.cambiarPropiedadLevel("ALL");
            SaraConfiguracionSessionBean.configApp.loggerApp.info("Se inici\u00f3 el Log en " + SaraConfiguracionSessionBean.configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS + "sara.log con Nivel " + SaraConfiguracionSessionBean.configApp.loggerApp.getLevel().toString());
            SaraConfiguracionSessionBean.configApp.loggerApp.info("Se inici\u00f3 Parametro TIEMPO_SESION " + SaraConfiguracionSessionBean.configApp.TIEMPO_SESION);
            SaraConfiguracionSessionBean.configApp.loggerApp.info("Se inici\u00f3 Parametro TIEMPO_LOGIN " + SaraConfiguracionSessionBean.configApp.TIEMPO_LOGIN);
            SaraConfiguracionSessionBean.configApp.loggerApp.info("Se inici\u00f3 el Log de Acceso en " + SaraConfiguracionSessionBean.configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS + "sara_acceso.log con Nivel " + SaraConfiguracionSessionBean.configApp.loggerAcceso.getLevel().toString());
            SaraConfiguracionSessionBean.configApp.loggerAcceso.info("Se inici\u00f3 el Log de Acceso en " + SaraConfiguracionSessionBean.configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS + "sara_acceso.log con Nivel " + SaraConfiguracionSessionBean.configApp.loggerAcceso.getLevel().toString());
        }
    }
    
    private void imprimirConfiguracion() {
        SaraConfiguracionSessionBean.configApp.loggerApp.config("<<<<<< Valores de configuracion >>>>>>");
        if (SaraConfiguracionSessionBean.configApp.config != null) {
            final Enumeration e = SaraConfiguracionSessionBean.configApp.config.getKeys();
            while (e.hasMoreElements()) {
                final String atributo = (String)  e.nextElement();
                SaraConfiguracionSessionBean.configApp.loggerApp.config(atributo + "=[" + SaraConfiguracionSessionBean.configApp.config.getString(atributo) + "]");
            }
        }
        SaraConfiguracionSessionBean.configApp.loggerApp.config("CONFIGURACION_LOG_DIRECTORIO_LOGS = " + SaraConfiguracionSessionBean.configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS);
        SaraConfiguracionSessionBean.configApp.loggerApp.config("TAMANO_ARCHIVO_LOG = " + SaraConfiguracionSessionBean.configApp.TAMANO_ARCHIVO_LOG);
        SaraConfiguracionSessionBean.configApp.loggerApp.config("CONFIGURACION_LOG_NIVEL_LOG = " + SaraConfiguracionSessionBean.configApp.CONFIGURACION_LOG_NIVEL_LOG);
        SaraConfiguracionSessionBean.configApp.loggerApp.config("CONFIGURACION_LOG_MOSTRAR_LOG_CONSOLA = " + ((SaraConfiguracionSessionBean.configApp.CONFIGURACION_LOG_MOSTRAR_LOG_CONSOLA > 0) ? "SI" : "N0"));
        SaraConfiguracionSessionBean.configApp.loggerApp.config("DIRECTORIO_UPLOAD = " + SaraConfiguracionSessionBean.configApp.DIRECTORIO_UPLOAD);
        SaraConfiguracionSessionBean.configApp.loggerApp.config("DIRECTORIO_CUADRE = " + SaraConfiguracionSessionBean.configApp.DIRECTORIO_CUADRE);
        SaraConfiguracionSessionBean.configApp.loggerApp.config("HORA_INICIO_TRANSMISION_ARCHIVOS = " + SaraConfiguracionSessionBean.configApp.HORA_INICIO_TRANSMISION_DIARIOS_ELECTRONICOS);
        SaraConfiguracionSessionBean.configApp.loggerApp.config("URL_ACCESO_APP = " + SaraConfiguracionSessionBean.configApp.URL_ACCESO_APP);
        SaraConfiguracionSessionBean.configApp.loggerApp.config("DOMINIOS_VALIDOS = " + Arrays.toString(SaraConfiguracionSessionBean.configApp.DOMINIOS_VALIDOS));
        SaraConfiguracionSessionBean.configApp.loggerApp.config("FECHA_HISTORICAS_CONSULTA = " + SaraConfiguracionSessionBean.configApp.FECHA_HISTORICAS_CONSULTA);
        SaraConfiguracionSessionBean.configApp.loggerApp.config("TIEMPO_SESION = " + SaraConfiguracionSessionBean.configApp.TIEMPO_SESION);
    }
    
    private void iniciarMBeanConfig() throws Exception {
        try {
            final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            final ObjectName idMBean = new ObjectName("com.davivienda.sara.config:type=SaraConfig");
            if (!mbs.isRegistered(idMBean)) {
                mbs.registerMBean(SaraConfiguracionSessionBean.configApp, idMBean);
            }
            SaraConfiguracionSessionBean.configApp.loggerApp.config("MBean Iniciado");
        }
        catch (JMException ex) {
            SaraConfiguracionSessionBean.configApp.loggerApp.log(Level.WARNING, "No se inicio el MBean => " + ex.getMessage(), ex);
        }
    }
    
    @PreDestroy
    @Override
    public void finalizarAplicacion() {
        try {
            this.destruirMBeanConfig();
            this.destruirLoggerAplicacion();
        }
        catch (Exception ex) {
            System.err.println(">>> Se termina la aplicaci\u00f3n SARA 12.2.7.7 con ERROR " + ex.getMessage() + "  <<<");
            ex.printStackTrace();
        }
    }
    
    private void destruirLoggerAplicacion() {
        try {
            SaraConfiguracionSessionBean.configApp.loggerApp.severe("Se da inicio al fin de la aplicaci\u00f3n ... ");
            SaraConfiguracionSessionBean.configApp.loggerApp.severe(">>> Se termina la aplicaci\u00f3n SARA 12.2.7.7 <<<");
            Handler[] handlers = SaraConfiguracionSessionBean.configApp.loggerAcceso.getHandlers();
            for (int i = 0; i < handlers.length; ++i) {
                final Handler handler = handlers[i];
                handler.flush();
                SaraConfiguracionSessionBean.configApp.loggerAcceso.severe("Se finaliza el log de Acceso:" + handler.getClass().getSimpleName() + " con nivel " + handler.getLevel().toString());
                SaraConfiguracionSessionBean.configApp.loggerApp.severe("Se finaliza el log de Acceso:" + handler.getClass().getSimpleName() + " con nivel " + handler.getLevel().toString());
                handler.close();
                SaraConfiguracionSessionBean.configApp.loggerAcceso.removeHandler(handler);
            }
            handlers = SaraConfiguracionSessionBean.configApp.loggerApp.getHandlers();
            for (int i = 0; i < handlers.length; ++i) {
                final Handler handler = handlers[i];
                handler.flush();
                SaraConfiguracionSessionBean.configApp.loggerApp.severe("Se finaliza el log :" + handler.getClass().getSimpleName() + " con nivel " + handler.getLevel().toString());
                handler.close();
                SaraConfiguracionSessionBean.configApp.loggerApp.removeHandler(handler);
            }
        }
        catch (Exception ex) {
            SaraConfiguracionSessionBean.configApp.loggerApp.log(Level.SEVERE, "Finalizaci\u00f3n con error de la aplicaci\u00f3n ", ex);
            System.err.println(">>> Se termina la aplicaci\u00f3n SARA 12.2.7.7 con ERROR " + ex.getMessage() + "  <<<");
        }
        finally {
            System.err.println(">>> Se termina la aplicaci\u00f3n SARA 12.2.7.7 <<<");
        }
    }
    
    private void destruirMBeanConfig() {
        final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            final ObjectName idMBean = new ObjectName("com.davivienda.sara.config:type=SaraConfig");
            if (mbs.isRegistered(idMBean)) {
                mbs.unregisterMBean(idMBean);
            }
        }
        catch (Exception ex) {
            SaraConfiguracionSessionBean.configApp.loggerApp.severe("No se elimino el MBean  " + ex.getMessage());
        }
    }
    
    @Override
    public void cambiarPropiedadLevel(final String nuevoNivel) {
        SaraConfiguracionSessionBean.configApp.cambiarNivelLog(nuevoNivel);
    }
    
    public void cargarTareasProgramadas() {
        SaraConfiguracionSessionBean.configApp.loggerApp.info("Iniciando la creaci\u00f3n de tareas programadas:");
        try {
            String strCronExpresion = "";
            SaraConfiguracionSessionBean.configApp.loggerApp.info("Iniciando la creaci\u00f3n de tareas programadas:");
            CronExpression cronExpression = null;
            final SecureRandom sr = new SecureRandom();
            final int n = sr.nextInt(5);
            SaraConfiguracionSessionBean.configApp.loggerApp.info("Procesando tiempo espera la creaci\u00f3n de tareas programadas: " + n + " seg");
            Thread.sleep(n * 1000);
            final InitialContext ctx = new InitialContext();
            try {
                strCronExpresion = ((ConfModulosAplicacion)this.em.find((Class)ConfModulosAplicacion.class, (Object)new ConfModulosAplicacionPK("SARA", "QUARTZ.CARGUE_DIARIOS.EXP"))).getValor().trim();
                if (!CronExpression.isValidExpression(strCronExpresion)) {
                    strCronExpresion = "0 30 23 * * ?";
                }
            }
            catch (Exception ex) {
                SaraConfiguracionSessionBean.configApp.loggerApp.log(Level.SEVERE, "Error al cargar la hora parametrizada para  CargueDiarios", ex);
                strCronExpresion = "0 30 23 * * ?";
            }
            final JobCargueDiariosHome homeCargueDiarios = (JobCargueDiariosHome)ctx.lookup("JobCargueDiariosJndi");
            final JobCargueDiariosRemote jobCargueDiariosRemote = homeCargueDiarios.create();
            final SchedulerInfo schedulerCargueDiarios = new SchedulerInfo();
            schedulerCargueDiarios.setTipoProceso("CargueDiarios");
            schedulerCargueDiarios.setCronExpresion(strCronExpresion);
            schedulerCargueDiarios.setDirectorioUpload(SaraConfiguracionSessionBean.configApp.DIRECTORIO_UPLOAD);
            try {
                cronExpression = new CronExpression(strCronExpresion);
                SaraConfiguracionSessionBean.configApp.loggerApp.info("Cargando datos para CargueDiarios con expresion: " + strCronExpresion);
                if (cronExpression != null) {
                    schedulerCargueDiarios.setProximaEjecucion(cronExpression.getNextValidTimeAfter(new Date()));
                    jobCargueDiariosRemote.createTimer(schedulerCargueDiarios);
                }
            }
            catch (Exception ex2) {
                SaraConfiguracionSessionBean.configApp.loggerApp.log(Level.SEVERE, "Finalizaci\u00f3n con error  la creaci\u00f3n de tarea programada para CargueDiarios", ex2);
                if (cronExpression != null) {
                    schedulerCargueDiarios.setProximaEjecucion(cronExpression.getNextValidTimeAfter(new Date()));
                    jobCargueDiariosRemote.createTimer(schedulerCargueDiarios);
                }
            }
            try {
                strCronExpresion = ((ConfModulosAplicacion)this.em.find((Class)ConfModulosAplicacion.class, (Object)new ConfModulosAplicacionPK("SARA", "QUARTZ.DALI_AUTO.EXP"))).getValor().trim();
                if (!CronExpression.isValidExpression(strCronExpresion)) {
                    strCronExpresion = "0 30 23 * * ?";
                }
            }
            catch (Exception ex2) {
                SaraConfiguracionSessionBean.configApp.loggerApp.log(Level.SEVERE, "Error al cargar la hora parametrizada para  DaliAuto", ex2);
                strCronExpresion = "0 30 23 * * ?";
            }
            final JobDaliAutoHome homeDaliAuto = (JobDaliAutoHome)ctx.lookup("JobDaliAutoJndi");
            final JobDaliAutoRemote jobDaliAutoRemote = homeDaliAuto.create();
            final SchedulerInfo schedulerDaliAuto = new SchedulerInfo();
            schedulerDaliAuto.setTipoProceso("DaliAuto");
            schedulerDaliAuto.setCronExpresion(strCronExpresion);
            schedulerDaliAuto.setDirectorioUpload("");
            try {
                cronExpression = new CronExpression(strCronExpresion);
                SaraConfiguracionSessionBean.configApp.loggerApp.info("Cargando datos para DaliAuto con expresion: " + strCronExpresion);
                schedulerDaliAuto.setProximaEjecucion(cronExpression.getNextValidTimeAfter(new Date()));
                jobDaliAutoRemote.createTimer(schedulerDaliAuto);
            }
            catch (Exception ex3) {
                SaraConfiguracionSessionBean.configApp.loggerApp.log(Level.SEVERE, "Finalizaci\u00f3n con error  la creaci\u00f3n de tarea programada para DaliAuto", ex3);
                schedulerDaliAuto.setProximaEjecucion(cronExpression.getNextValidTimeAfter(new Date()));
                jobDaliAutoRemote.createTimer(schedulerDaliAuto);
            }
            try {
                strCronExpresion = ((ConfModulosAplicacion)this.em.find((Class)ConfModulosAplicacion.class, (Object)new ConfModulosAplicacionPK("SARA", "QUARTZ.INFORME_DIFERENCIAS.EXP"))).getValor().trim();
                if (!CronExpression.isValidExpression(strCronExpresion)) {
                    strCronExpresion = "0 30 23 * * ?";
                }
            }
            catch (Exception ex3) {
                SaraConfiguracionSessionBean.configApp.loggerApp.log(Level.SEVERE, "Error al cargar la hora parametrizada para  InformeDiferencias", ex3);
                strCronExpresion = "0 30 23 * * ?";
            }
            final JobInformeDiferenciasHome homeInformeDiferencias = (JobInformeDiferenciasHome)ctx.lookup("JobInformeDiferenciasJndi");
            final JobInformeDiferenciasRemote jobInformeDifeRemote = homeInformeDiferencias.create();
            final SchedulerInfo schedulerInformeDife = new SchedulerInfo();
            schedulerInformeDife.setTipoProceso("InformeDiferencias");
            schedulerInformeDife.setCronExpresion(strCronExpresion);
            schedulerInformeDife.setDirectorioUpload(SaraConfiguracionSessionBean.configApp.DIRECTORIO_UPLOAD);
            try {
                cronExpression = new CronExpression(strCronExpresion);
                SaraConfiguracionSessionBean.configApp.loggerApp.info("Cargando datos para InformeDiferencias con expresion: " + strCronExpresion);
                if (cronExpression != null) {
                    schedulerInformeDife.setProximaEjecucion(cronExpression.getNextValidTimeAfter(new Date()));
                    jobInformeDifeRemote.createTimer(schedulerInformeDife);
                }
            }
            catch (Exception ex4) {
                SaraConfiguracionSessionBean.configApp.loggerApp.log(Level.SEVERE, "Finalizaci\u00f3n con error  la creaci\u00f3n de tarea programada para InformeDiferencias", ex4);
                if (cronExpression != null) {
                    schedulerCargueDiarios.setProximaEjecucion(cronExpression.getNextValidTimeAfter(new Date()));
                    jobCargueDiariosRemote.createTimer(schedulerCargueDiarios);
                }
            }
        }
        catch (Exception ex5) {
            SaraConfiguracionSessionBean.configApp.loggerApp.log(Level.SEVERE, "", ex5);
        }
    }
}
