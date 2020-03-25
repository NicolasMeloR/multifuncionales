package com.davivienda.sara.config;

import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.log.LogFormatter;
import com.davivienda.utilidades.log.SaraAccesoLogFilter;
import com.davivienda.utilidades.log.SaraAppLogFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.PropertyResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.persistence.EntityManager;
import weblogic.logging.LoggingHelper;
import weblogic.logging.WLLevel;

/**
 * AppConfiguracion
 * Descripción : Contiene las propiedades de la aplicación
 * Fecha       : 29/01/2008 11:40:07 AM
 * @author     : jjvargas
 **/
public class SaraConfig {

    private static EntityManager em;
    public Logger loggerApp;
    public Logger loggerAcceso;
    private static String archivoPropiedades;
    private boolean inicioDesdeDB;
    private static SaraConfig instancia;
    /**
     * Configuración
     */
    public PropertyResourceBundle config = null;
    /**
     * Directorio raiz en el cual se instala el componente web
     */
    public String BABEL_DIRECTORIO_WAR = "";
    /**
     * Carpeta de los archivos de log
     */
    public String CONFIGURACION_LOG_DIRECTORIO_LOGS = "";
    /**
     * Tamaño en bites del archivo de logs
     */
    public int TAMANO_ARCHIVO_LOG;
    /**
     * Activa el log en consola
     */
    public int CONFIGURACION_LOG_MOSTRAR_LOG_CONSOLA;
    /**
     * Nivel del log
     * SEVERE (highest value)
     * WARNING
     * INFO
     * CONFIG
     * FINE
     * FINER
     * FINEST (lowest value)
     */
    public String CONFIGURACION_LOG_NIVEL_LOG;
    /**
     * Carpeta de archivos UpLoad, Diarios electronicos
     */
    public String DIRECTORIO_UPLOAD;
    /**
     *  Hora en que se inicia el proceso de diarios electrónicos
     */
    public int HORA_INICIO_TRANSMISION_DIARIOS_ELECTRONICOS;
    /**
     *  Hora en que se inicia el proceso de transamisión de los archivo de corte
     */
    public int HORA_INICIO_TRANSMISION_ARCHIVOS_CORTE;
    
      /**
     *  Usuario para invocar servlets desde quartz
     */
    
     /**
     * Carpeta de archivos CUADRE CIFRAS
     */
    public String DIRECTORIO_CUADRE;
    
    public String USUARIO_QUARTZ;
    
    
    public String URL_ACCESO_APP = "";
    /**
     * limite maximo valor de un reintegro
     */
    
    public String DOMINIOS_VALIDOS[];
    
  //  public int MAX_VALOR_REINTEGRO;
    
     /**
     * Tiempo en sesion usuario minutos
     */
    public int TIEMPO_SESION;

    public Date FECHA_HISTORICAS_CONSULTA;
    
    /**
     * Tiempo para volver a logear usuario minutos
     */
    public int TIEMPO_LOGIN;
    
    private SaraConfig() throws IllegalAccessException {
        
        loggerApp = Logger.getLogger(Constantes.LOGGER_APP);
        loggerAcceso = Logger.getLogger(Constantes.LOGGER_ACCESO);

        try {
            if (archivoPropiedades != null && !archivoPropiedades.trim().isEmpty()) {
                FileInputStream fis = new FileInputStream(archivoPropiedades);
                try {
                    config = new PropertyResourceBundle(fis);
                    fis.close();
                } catch (IOException ex) {
                    fis.close();
                    throw new IOException("Error al tratar de cargar archivo de propiedades: " + archivoPropiedades);
                }
                cargarParametrosProperties();
            }
            cargarParametrosDB();
            
            Logger serverLogger = LoggingHelper.getServerLogger();
            FileHandler fh = new FileHandler(CONFIGURACION_LOG_DIRECTORIO_LOGS.trim() + "sara.log", (int) TAMANO_ARCHIVO_LOG, 5, true);
            fh.setFormatter(new LogFormatter());
            fh.setEncoding("UTF-8");
            fh.setLevel(WLLevel.parse(CONFIGURACION_LOG_NIVEL_LOG));
            fh.setFilter(new SaraAppLogFilter());          
            serverLogger.addHandler(fh);
            
            FileHandler fhAcceso = new FileHandler(CONFIGURACION_LOG_DIRECTORIO_LOGS.trim() + "sara_acceso.log", (int) TAMANO_ARCHIVO_LOG, 5, true);
            fhAcceso.setFormatter(new LogFormatter());
            fhAcceso.setEncoding("UTF-8");
            fhAcceso.setLevel(WLLevel.parse(CONFIGURACION_LOG_NIVEL_LOG));
            fhAcceso.setFilter(new SaraAccesoLogFilter());          
            serverLogger.addHandler(fhAcceso);
            
            loggerApp.info("Se inicio satisfactoriamente la configuración de SARA " + Constantes.VERSION_APP + " desde " + ((!inicioDesdeDB) ? archivoPropiedades : " Base de datos"));
        } catch (Exception ex) {
            System.err.println("No se puede cargar la configuración : " + ex.getMessage());
            ex.printStackTrace();
            throw new IllegalAccessException("No se puede cargar la configuración : " + ex.getMessage());
        }
    }

    /**
     * Retorna la instancia del objeto
     * @return
     * @throws IllegalAccessException 
     */
    public static SaraConfig obtenerInstancia() throws IllegalAccessException {
        instancia = (instancia == null) ? new SaraConfig() : instancia;
        return instancia;
    }

    /**
     * Crea una instancia a partir del archivo de propiedades
     * @param archivoPropiedades
     * @param em 
     * @return ELMWeb2Config
     * @throws IllegalAccessException 
     */
    public static SaraConfig obtenerInstancia(String archivoPropiedades, EntityManager em) throws IllegalAccessException {
        if (instancia == null) {
            SaraConfig.archivoPropiedades = archivoPropiedades;
            SaraConfig.em = em;
            instancia = new SaraConfig();
        }
        return instancia;
    }

    private void cargarParametrosProperties() {
        // Inicia clase con constantes
        CONFIGURACION_LOG_DIRECTORIO_LOGS = config.getString("CONFIGURACION_LOG.directorioLogs").trim();
        CONFIGURACION_LOG_NIVEL_LOG = config.getString("CONFIGURACION_LOG.nivelLog").trim();
        TAMANO_ARCHIVO_LOG = com.davivienda.utilidades.conversion.Cadena.aInteger(config.getString("CONFIGURACION_LOG.tamanoArchivo").trim(), "10000000");
        CONFIGURACION_LOG_MOSTRAR_LOG_CONSOLA = com.davivienda.utilidades.conversion.Cadena.aInteger(config.getString("CONFIGURACION_LOG.mostrarLogConsola").trim(), "0");
        
        //OJO CAMBIAR
         DIRECTORIO_CUADRE = config.getString("SARA.directorioCuadre").trim();
     //     DIRECTORIO_CUADRE = config.getString("SARA.directorioUpLoad").trim();
                                           
        DIRECTORIO_UPLOAD = config.getString("SARA.directorioUpLoad").trim();
        HORA_INICIO_TRANSMISION_DIARIOS_ELECTRONICOS = com.davivienda.utilidades.conversion.Cadena.aInteger(config.getString("SARA.horaInicioTransmisionDiariosElectronicos").trim(), "10800000");
        HORA_INICIO_TRANSMISION_ARCHIVOS_CORTE = com.davivienda.utilidades.conversion.Cadena.aInteger(config.getString("SARA.horaInicioTransmisionArchivosCorte").trim(), "10800000");
        USUARIO_QUARTZ= config.getString("SARA.directorioUpLoad").trim();
       // MAX_VALOR_REINTEGRO= com.davivienda.utilidades.conversion.Cadena.aInteger(config.getString("SARA.MAX_VALOR_REINTEGRO").trim(), "720000");

    }

    public void cargarParametrosDB() {
        try {
            CONFIGURACION_LOG_DIRECTORIO_LOGS =
                    em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "CONFIGURACION_LOG.directorioLogs")).getValor().trim();
            CONFIGURACION_LOG_NIVEL_LOG =
                    em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "CONFIGURACION_LOG.nivelLog")).getValor().trim();
            TAMANO_ARCHIVO_LOG =
                    com.davivienda.utilidades.conversion.Cadena.aInteger(em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "CONFIGURACION_LOG.tamanoArchivo")).getValor().trim(), "10000000");
            CONFIGURACION_LOG_MOSTRAR_LOG_CONSOLA =
                    com.davivienda.utilidades.conversion.Cadena.aInteger(em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "CONFIGURACION_LOG.mostrarLogConsola")).getValor().trim(), "0");
            DIRECTORIO_UPLOAD =
                    em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.directorioUpLoad")).getValor().trim();
            //OJO CAMBIAR
            DIRECTORIO_CUADRE =
                    em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.directorioCuadre")).getValor().trim();
//             DIRECTORIO_CUADRE =
//                    em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.directorioUpLoad")).getValor().trim();
            HORA_INICIO_TRANSMISION_DIARIOS_ELECTRONICOS =
                    com.davivienda.utilidades.conversion.Cadena.aInteger(em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.horaInicioTransmisionDiariosElectronicos")).getValor().trim(), "10800000");
            HORA_INICIO_TRANSMISION_ARCHIVOS_CORTE =
                    com.davivienda.utilidades.conversion.Cadena.aInteger(em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.horaInicioTransmisionArchivosCorte")).getValor().trim(), "10800000");
            USUARIO_QUARTZ=
                     em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.USUARIO_QUARTZ")).getValor().trim();
//             MAX_VALOR_REINTEGRO=
//                     com.davivienda.utilidades.conversion.Cadena.aInteger(em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.MAX_VALOR_REINTEGRO")).getValor().trim());

            URL_ACCESO_APP=
                     em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", Constantes.PARAM_URL_ACCESO_APP)).getValor().trim();
            
            TIEMPO_SESION= 
                     com.davivienda.utilidades.conversion.Cadena.aInteger(em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", Constantes.TIEMPO_SESION_USUARIO)).getValor().trim());
            
            DOMINIOS_VALIDOS = (em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", Constantes.DOMINIOS_VALIDOS)).getValor().split(Constantes.CARACTER_COMA));
           
            FECHA_HISTORICAS_CONSULTA = com.davivienda.utilidades.conversion.Cadena.aDate(em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", Constantes.FECHA_HISTORICAS_CONSULTA)).getValor(), FormatoFecha.DEFECTO_SEPARADOR_GUION);
            
            TIEMPO_LOGIN= 
                     com.davivienda.utilidades.conversion.Cadena.aInteger(em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", Constantes.TIEMPO_LOGIN_USUARIO)).getValor().trim());
            
            inicioDesdeDB = true;
        } catch (Exception ex) {
            System.err.println("No se puede cargar la configuración desde la base de datos se tomaran los volores por defecto " + ex.getMessage());
            ex.printStackTrace();
            cargarParametrosProperties();
            inicioDesdeDB = false;
        }
    }

    /**
     * Get Versión de la aplicación
     * @return 
     */
    public String getVERSION_APP() {
        return Constantes.VERSION_APP;
    }

    /**
     * Get Directorio de archivos de logs
     * @return 
     */
    public String getCONFIGURACION_LOG_DIRECTORIO_LOGS() {
        return CONFIGURACION_LOG_DIRECTORIO_LOGS;
    }

    /**
     * Get Nivel del log
     * @return 
     */
    public String getCONFIGURACION_LOG_NIVEL_LOG() {
        return CONFIGURACION_LOG_NIVEL_LOG;
    }

    /**
     * Get Mostrar log en consola
     * @return 
     */
    public int getCONFIGURACION_LOG_MOSTRAR_LOG_CONSOLA() {
        return CONFIGURACION_LOG_MOSTRAR_LOG_CONSOLA;
    }

    /**
     * Activa o desactivar log en consola
     * @param mostarLogEnConsola [0] Desactiva [1] Activa
     */
    public void activarLogAConsola(int mostarLogEnConsola) {
        ConsoleHandler ch = new ConsoleHandler();
        if (mostarLogEnConsola == 0) {
            Handler[] handlers = loggerApp.getHandlers();
            for (int i = 0; i < handlers.length; i++) {
                Handler handler = handlers[i];
                if (handler.getClass().getSimpleName().equals("ConsoleHandler")) {
                    loggerApp.info("Se eliminará el Handler de log a consola");
                    handler.close();
                    loggerApp.removeHandler(handler);
                    loggerApp.info("Se eliminó el Handler de log a consola");
                }
            }
        } else {
            if (CONFIGURACION_LOG_MOSTRAR_LOG_CONSOLA == 0 && mostarLogEnConsola > 0) {
                ch.setFormatter(new SimpleFormatter());
                ch.setLevel(loggerApp.getLevel());
                loggerApp.addHandler(ch);
                loggerApp.info("Se adiciono el Handler de log a consola" + " con nivel " + ch.getLevel().toString());
            } else {
                loggerApp.info("Ya esta activo el Handler de log a consola" + " con nivel " + ch.getLevel().toString());
            }
        }
        CONFIGURACION_LOG_MOSTRAR_LOG_CONSOLA = mostarLogEnConsola;
    }

    /**
     * Get Tamaño del Archivo de logs
     * @return 
     */
    public double getTAMANO_ARCHIVO_LOG() {
        return TAMANO_ARCHIVO_LOG;
    }

    /**
     * Get Archivo de propiedades por defecto
     * @return 
     */
    public String getArchivoPropiedades() {
        return archivoPropiedades;
    }

    /**
     * Cambia el niverl del log
     * @param nuevoNivel Nuevo nivel del log
     */
    public void cambiarNivelLog(String nuevoNivel) {
        Level nivelLog = Level.parse(nuevoNivel);
        loggerApp.setLevel(nivelLog);
        Handler[] handlers = loggerApp.getHandlers();
        for (int i = 0; i < handlers.length; i++) {
            Handler handler = handlers[i];
            handler.setLevel(nivelLog);
            loggerApp.info("Se cambio el handler del log " + handler.getClass().getSimpleName() + " con nivel " + handler.getLevel().toString());
            CONFIGURACION_LOG_NIVEL_LOG = nuevoNivel;
        }

    }

    public int getTIEMPO_SESION() {
        return TIEMPO_SESION;
    }

    public void setTIEMPO_SESION(int TIEMPO_SESION) {
        this.TIEMPO_SESION = TIEMPO_SESION;
    }

    public Date getFECHA_HISTORICAS_CONSULTA() {
        return FECHA_HISTORICAS_CONSULTA;
    }

    public void setFECHA_HISTORICAS_CONSULTA(Date FECHA_HISTORICAS_CONSULTA) {
        this.FECHA_HISTORICAS_CONSULTA = FECHA_HISTORICAS_CONSULTA;
    }

    public int getTIEMPO_LOGIN() {
        return TIEMPO_LOGIN;
    }

    public void setTIEMPO_LOGIN(int TIEMPO_LOGIN) {
        this.TIEMPO_LOGIN = TIEMPO_LOGIN;
    }
    
    
    
    
}
