package com.davivienda.sara.administracion.session;

import com.davivienda.sara.base.BaseServicio;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import com.davivienda.sara.jobs.JobCargueDiariosHome;
import com.davivienda.sara.jobs.JobCargueDiariosRemote;
import com.davivienda.sara.jobs.JobDaliAutoHome;
import com.davivienda.sara.jobs.JobDaliAutoRemote;
import com.davivienda.sara.jobs.JobInformeDiferenciasHome;
import com.davivienda.sara.jobs.JobInformeDiferenciasRemote;
import com.davivienda.utilidades.SchedulerInfo;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.MemoryHandler;
import java.util.logging.SimpleFormatter;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.quartz.CronExpression;

@Stateless
public class SaraConfiguracionSessionBean implements SaraConfiguracionSession {
   @PersistenceContext
   private EntityManager em;
   private static SaraConfig configApp;
   private BaseServicio servicio;
   private static FileHandler fh;
   private static FileHandler fhAcceso;
   private static ConsoleHandler ch;
   private static MemoryHandler mh;
   private static MemoryHandler mhAcceso;

   public void iniciarConfiguracion(String archivoPropiedadesConfiguracion) {
      if (configApp == null) {
         try {
            configApp = SaraConfig.obtenerInstancia(archivoPropiedadesConfiguracion, this.em);
            this.servicio = new BaseServicio();
            this.crearLoggerAplicacion();
            this.imprimirConfiguracion();
         } catch (IllegalAccessException var3) {
            Logger.getLogger(SaraConfiguracionSessionBean.class.getName()).log(Level.SEVERE, var3.getMessage(), var3);
         } catch (Exception var4) {
            Logger.getLogger(SaraConfiguracionSessionBean.class.getName()).log(Level.SEVERE, var4.getMessage(), var4);
         }
      } else {
         configApp.cargarParametrosDB();
         this.imprimirConfiguracion();
      }

      this.cargarTareasProgramadas();
   }

   public SaraConfig getAppConfig() {
      return configApp;
   }

   private void crearLoggerAplicacion() {
      Level nivelLog = Level.ALL;

      try {
         configApp.loggerApp.setLevel(Level.ALL);
         configApp.loggerAcceso.setLevel(Level.ALL);
         if (ch == null && configApp.CONFIGURACION_LOG_MOSTRAR_LOG_CONSOLA > 0) {
            ch = new ConsoleHandler();
            ch.setFormatter(new SimpleFormatter());
            ch.setLevel(nivelLog);
            configApp.loggerApp.addHandler(ch);
         }

         if (fh == null) {
            fh = new FileHandler(configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS.trim() + "sara.log", configApp.TAMANO_ARCHIVO_LOG, 5, true);
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(nivelLog);
            configApp.loggerApp.addHandler(fh);
         }

         if (fhAcceso == null) {
            fhAcceso = new FileHandler(configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS.trim() + "sara_acceso.log", configApp.TAMANO_ARCHIVO_LOG, 5, true);
            fhAcceso.setFormatter(new SimpleFormatter());
            fhAcceso.setLevel(nivelLog);
            configApp.loggerAcceso.addHandler(fhAcceso);
         }
      } catch (IOException var13) {
         configApp.loggerApp.log(Level.SEVERE, var13.getMessage(), var13);
         System.err.println("No se puede cargar la configuración de logs : " + var13.getMessage());
         var13.printStackTrace();
      } catch (SecurityException var14) {
         configApp.loggerApp.log(Level.SEVERE, var14.getMessage(), var14);
         System.err.println("No se puede cargar la configuración de logs : " + var14.getMessage());
         var14.printStackTrace();
      } finally {
         if (fh != null) {
            if (mh == null) {
               mh = new MemoryHandler(fh, 2000, Level.ALL);
               mh.setLevel(nivelLog);
            }

            configApp.loggerApp.info("<<< SARA 12.2.6.9 >>>");
         }

         if (fhAcceso != null && mhAcceso == null) {
            mhAcceso = new MemoryHandler(fhAcceso, 2000, Level.ALL);
            mhAcceso.setLevel(nivelLog);
         }

         try {
            nivelLog = Level.parse(configApp.CONFIGURACION_LOG_NIVEL_LOG);
         } catch (Exception var12) {
            nivelLog = Level.ALL;
            configApp.loggerApp.log(Level.SEVERE, "No se puede asignar el  nivel del log , se asigno por defecto en ALL", var12);
         }

         this.cambiarPropiedadLevel("ALL");
         configApp.loggerApp.info("Se inició el Log en " + configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS + "sara.log con Nivel " + configApp.loggerApp.getLevel().toString());
         configApp.loggerApp.info("Se inició Parametro TIEMPO_SESION " + configApp.TIEMPO_SESION);
         configApp.loggerApp.info("Se inició Parametro TIEMPO_LOGIN " + configApp.TIEMPO_LOGIN);
         configApp.loggerApp.info("Se inició el Log de Acceso en " + configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS + "sara_acceso.log con Nivel " + configApp.loggerAcceso.getLevel().toString());
         configApp.loggerAcceso.info("Se inició el Log de Acceso en " + configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS + "sara_acceso.log con Nivel " + configApp.loggerAcceso.getLevel().toString());
      }

   }

   private void imprimirConfiguracion() {
      configApp.loggerApp.config("<<<<<< Valores de configuracion >>>>>>");
      if (configApp.config != null) {
         Enumeration e = configApp.config.getKeys();

         while(e.hasMoreElements()) {
            String atributo = (String)e.nextElement();
            configApp.loggerApp.config(atributo + "=[" + configApp.config.getString(atributo) + "]");
         }
      }

      configApp.loggerApp.config("CONFIGURACION_LOG_DIRECTORIO_LOGS = " + configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS);
      configApp.loggerApp.config("TAMANO_ARCHIVO_LOG = " + configApp.TAMANO_ARCHIVO_LOG);
      configApp.loggerApp.config("CONFIGURACION_LOG_NIVEL_LOG = " + configApp.CONFIGURACION_LOG_NIVEL_LOG);
      configApp.loggerApp.config("CONFIGURACION_LOG_MOSTRAR_LOG_CONSOLA = " + (configApp.CONFIGURACION_LOG_MOSTRAR_LOG_CONSOLA > 0 ? "SI" : "N0"));
      configApp.loggerApp.config("DIRECTORIO_UPLOAD = " + configApp.DIRECTORIO_UPLOAD);
      configApp.loggerApp.config("DIRECTORIO_CUADRE = " + configApp.DIRECTORIO_CUADRE);
      configApp.loggerApp.config("HORA_INICIO_TRANSMISION_ARCHIVOS = " + configApp.HORA_INICIO_TRANSMISION_DIARIOS_ELECTRONICOS);
      configApp.loggerApp.config("URL_ACCESO_APP = " + configApp.URL_ACCESO_APP);
      configApp.loggerApp.config("DOMINIOS_VALIDOS = " + Arrays.toString(configApp.DOMINIOS_VALIDOS));
      configApp.loggerApp.config("FECHA_HISTORICAS_CONSULTA = " + configApp.FECHA_HISTORICAS_CONSULTA);
      configApp.loggerApp.config("TIEMPO_SESION = " + configApp.TIEMPO_SESION);
   }

   private void iniciarMBeanConfig() throws Exception {
      try {
         MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
         ObjectName idMBean = new ObjectName("com.davivienda.sara.config:type=SaraConfig");
         if (!mbs.isRegistered(idMBean)) {
            mbs.registerMBean(configApp, idMBean);
         }

         configApp.loggerApp.config("MBean Iniciado");
      } catch (JMException var3) {
         configApp.loggerApp.log(Level.WARNING, "No se inicio el MBean => " + var3.getMessage(), var3);
      }

   }

   @PreDestroy
   public void finalizarAplicacion() {
      try {
         this.destruirMBeanConfig();
         this.destruirLoggerAplicacion();
      } catch (Exception var2) {
         System.err.println(">>> Se termina la aplicación SARA 12.2.6.9 con ERROR " + var2.getMessage() + "  <<<");
         var2.printStackTrace();
      }

   }

   private void destruirLoggerAplicacion() {
      try {
         configApp.loggerApp.severe("Se da inicio al fin de la aplicación ... ");
         configApp.loggerApp.severe(">>> Se termina la aplicación SARA 12.2.6.9 <<<");
         Handler[] handlers = configApp.loggerAcceso.getHandlers();

         int i;
         Handler handler;
         for(i = 0; i < handlers.length; ++i) {
            handler = handlers[i];
            handler.flush();
            configApp.loggerAcceso.severe("Se finaliza el log de Acceso:" + handler.getClass().getSimpleName() + " con nivel " + handler.getLevel().toString());
            configApp.loggerApp.severe("Se finaliza el log de Acceso:" + handler.getClass().getSimpleName() + " con nivel " + handler.getLevel().toString());
            handler.close();
            configApp.loggerAcceso.removeHandler(handler);
         }

         handlers = configApp.loggerApp.getHandlers();

         for(i = 0; i < handlers.length; ++i) {
            handler = handlers[i];
            handler.flush();
            configApp.loggerApp.severe("Se finaliza el log :" + handler.getClass().getSimpleName() + " con nivel " + handler.getLevel().toString());
            handler.close();
            configApp.loggerApp.removeHandler(handler);
         }
      } catch (Exception var7) {
         configApp.loggerApp.log(Level.SEVERE, "Finalización con error de la aplicación ", var7);
         System.err.println(">>> Se termina la aplicación SARA 12.2.6.9 con ERROR " + var7.getMessage() + "  <<<");
      } finally {
         System.err.println(">>> Se termina la aplicación SARA 12.2.6.9 <<<");
      }

   }

   private void destruirMBeanConfig() {
      MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

      try {
         ObjectName idMBean = new ObjectName("com.davivienda.sara.config:type=SaraConfig");
         if (mbs.isRegistered(idMBean)) {
            mbs.unregisterMBean(idMBean);
         }
      } catch (Exception var4) {
         configApp.loggerApp.severe("No se elimino el MBean  " + var4.getMessage());
      }

   }

   public void cambiarPropiedadLevel(String nuevoNivel) {
      configApp.cambiarNivelLog(nuevoNivel);
   }

   public void cargarTareasProgramadas() {
      configApp.loggerApp.info("Iniciando la creación de tareas programadas:");

      try {
         String strCronExpresion = "";
         configApp.loggerApp.info("Iniciando la creación de tareas programadas:");
         CronExpression cronExpression = null;
         SecureRandom sr = new SecureRandom();
         int n = sr.nextInt(5);
         configApp.loggerApp.info("Procesando tiempo espera la creación de tareas programadas: " + n + " seg");
         Thread.sleep((long)(n * 1000));
         InitialContext ctx = new InitialContext();

         try {
            strCronExpresion = ((ConfModulosAplicacion)this.em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "QUARTZ.CARGUE_DIARIOS.EXP"))).getValor().trim();
            if (!CronExpression.isValidExpression(strCronExpresion)) {
               strCronExpresion = "0 30 23 * * ?";
            }
         } catch (Exception var19) {
            configApp.loggerApp.log(Level.SEVERE, "Error al cargar la hora parametrizada para  CargueDiarios", var19);
            strCronExpresion = "0 30 23 * * ?";
         }

         JobCargueDiariosHome homeCargueDiarios = (JobCargueDiariosHome)ctx.lookup("JobCargueDiariosJndi");
         configApp.loggerApp.log(Level.SEVERE, homeCargueDiarios!= null ? homeCargueDiarios.toString() : "homeCargueDiarios is null validar context.lookup");
         JobCargueDiariosRemote jobCargueDiariosRemote = homeCargueDiarios.create();
         configApp.loggerApp.log(Level.SEVERE, jobCargueDiariosRemote!= null ? jobCargueDiariosRemote.toString() : "jobCargueDiariosRemote is null");
         SchedulerInfo schedulerCargueDiarios = new SchedulerInfo();
         schedulerCargueDiarios.setTipoProceso("CargueDiarios");
         schedulerCargueDiarios.setCronExpresion(strCronExpresion);
         schedulerCargueDiarios.setDirectorioUpload(configApp.DIRECTORIO_UPLOAD);
         configApp.loggerApp.log(Level.SEVERE, schedulerCargueDiarios!= null ? schedulerCargueDiarios.toString() : "schedulerCargueDiarios is null");

         try {
            cronExpression = new CronExpression(strCronExpresion);
            configApp.loggerApp.info("Cargando datos para CargueDiarios con expresion: " + strCronExpresion);
            if (cronExpression != null) {
               configApp.loggerApp.log(Level.SEVERE, "schedulerCargueDiarios.setProximaEjecucion");
               schedulerCargueDiarios.setProximaEjecucion(cronExpression.getNextValidTimeAfter(new Date()));
               configApp.loggerApp.log(Level.SEVERE, "jobCargueDiariosRemote.createTimer(schedulerCargueDiarios) :" + schedulerCargueDiarios.toString());
               jobCargueDiariosRemote.createTimer(schedulerCargueDiarios);
            }
         } catch (Exception var21) {
            configApp.loggerApp.log(Level.SEVERE, "Finalización con error  la creación de tarea programada para CargueDiarios", var21);
            if (cronExpression != null) {
               schedulerCargueDiarios.setProximaEjecucion(cronExpression.getNextValidTimeAfter(new Date()));
               jobCargueDiariosRemote.createTimer(schedulerCargueDiarios);
            }
         }

         try {
            strCronExpresion = ((ConfModulosAplicacion)this.em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "QUARTZ.DALI_AUTO.EXP"))).getValor().trim();
            if (!CronExpression.isValidExpression(strCronExpresion)) {
               strCronExpresion = "0 30 23 * * ?";
            }
         } catch (Exception var18) {
            configApp.loggerApp.log(Level.SEVERE, "Error al cargar la hora parametrizada para  DaliAuto", var18);
            strCronExpresion = "0 30 23 * * ?";
         }

         JobDaliAutoHome homeDaliAuto = (JobDaliAutoHome)ctx.lookup("JobDaliAutoJndi");
         JobDaliAutoRemote jobDaliAutoRemote = homeDaliAuto.create();
         SchedulerInfo schedulerDaliAuto = new SchedulerInfo();
         schedulerDaliAuto.setTipoProceso("DaliAuto");
         schedulerDaliAuto.setCronExpresion(strCronExpresion);
         schedulerDaliAuto.setDirectorioUpload("");

         try {
            cronExpression = new CronExpression(strCronExpresion);
            configApp.loggerApp.info("Cargando datos para DaliAuto con expresion: " + strCronExpresion);
            schedulerDaliAuto.setProximaEjecucion(cronExpression.getNextValidTimeAfter(new Date()));
            jobDaliAutoRemote.createTimer(schedulerDaliAuto);
         } catch (Exception var17) {
            configApp.loggerApp.log(Level.SEVERE, "Finalización con error  la creación de tarea programada para DaliAuto", var17);
            schedulerDaliAuto.setProximaEjecucion(cronExpression.getNextValidTimeAfter(new Date()));
            jobDaliAutoRemote.createTimer(schedulerDaliAuto);
         }

         try {
            strCronExpresion = ((ConfModulosAplicacion)this.em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "QUARTZ.INFORME_DIFERENCIAS.EXP"))).getValor().trim();
            if (!CronExpression.isValidExpression(strCronExpresion)) {
               strCronExpresion = "0 30 23 * * ?";
            }
         } catch (Exception var16) {
            configApp.loggerApp.log(Level.SEVERE, "Error al cargar la hora parametrizada para  InformeDiferencias", var16);
            strCronExpresion = "0 30 23 * * ?";
         }

         JobInformeDiferenciasHome homeInformeDiferencias = (JobInformeDiferenciasHome)ctx.lookup("JobInformeDiferenciasJndi");
         JobInformeDiferenciasRemote jobInformeDifeRemote = homeInformeDiferencias.create();
         SchedulerInfo schedulerInformeDife = new SchedulerInfo();
         schedulerInformeDife.setTipoProceso("InformeDiferencias");
         schedulerInformeDife.setCronExpresion(strCronExpresion);
         schedulerInformeDife.setDirectorioUpload(configApp.DIRECTORIO_UPLOAD);

         try {
            cronExpression = new CronExpression(strCronExpresion);
            configApp.loggerApp.info("Cargando datos para InformeDiferencias con expresion: " + strCronExpresion);
            if (cronExpression != null) {
               schedulerInformeDife.setProximaEjecucion(cronExpression.getNextValidTimeAfter(new Date()));
               jobInformeDifeRemote.createTimer(schedulerInformeDife);
            }
         } catch (Exception var20) {
            configApp.loggerApp.log(Level.SEVERE, "Finalización con error  la creación de tarea programada para InformeDiferencias", var20);
            if (cronExpression != null) {
               schedulerCargueDiarios.setProximaEjecucion(cronExpression.getNextValidTimeAfter(new Date()));
               jobCargueDiariosRemote.createTimer(schedulerCargueDiarios);
            }
         }
      } catch (Exception var22) {
         configApp.loggerApp.log(Level.SEVERE, "", var22);
      }

   }
}
