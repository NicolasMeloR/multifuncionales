package com.davivienda.sara.administracion.programadortareas.session;

import com.davivienda.sara.administracion.programadortareas.ProgramadorTareas;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.procesos.diarioelectronico.servicio.ProcesadorDiarioElectronicoServicio;

//import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.davivienda.sara.procesos.diarioelectronico.session.AdministradorProcesosSessionLocal;
import java.io.FileNotFoundException;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import com.davivienda.sara.procesos.edccargue.session.AdministradorProcesosEdcCargueSessionLocal;
import java.util.ArrayList;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;  



/**
 * ProgramadorTareasDiarioElectronicoSessionBean
 * Descripción : EJB utilizado para programar las tareas automáticas que se deben de ejecutar periodicamente
 *
 * @author jjvargas
 */
@Stateless
@Local(value = ProgramadorTareasDiarioElectronicoSession.class)
public class ProgramadorTareasDiarioElectronicoSessionBean extends ProgramadorTareas implements ProgramadorTareasDiarioElectronicoSession {

    @Resource
    private SessionContext ctx;
    @PersistenceContext
    private EntityManager em;
    
    
    ProcesadorDiarioElectronicoServicio servicio;
    
  @EJB
  private AdministradorProcesosSessionLocal diarioElectronicoCarga;
  
   @EJB
   private AdministradorProcesosEdcCargueSessionLocal  administradorProcesosEdcCargueSessionLocal; 
   
   @EJB
   private ConfModulosAplicacionLocal confModulosAplicacionSession;

    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
        
          servicio = new ProcesadorDiarioElectronicoServicio(em);
        
        
    }

    /**
     * Inicia el timer para el proceso de diarios electrónicos
     * @param appConfiguracion 
     */
    public void configuraProgramador(SaraConfig appConfiguracion) {
        super.appConfiguracion = appConfiguracion;

        // Obtengo el tiempo que falta para la hora de inicio normal de la tarea
        long horaInicio = getHoraInicioProgramador(appConfiguracion.HORA_INICIO_TRANSMISION_DIARIOS_ELECTRONICOS);

        // Obtengo el servicio timer del contenedor y creo un nuevo timer
        TimerService timerService = ctx.getTimerService();

        // Timer timerProcesoDiarioElectronico = timerService.createTimer(0, 15000, null); // para pruebas se ejecuta inmediatamente y repite cada 15 seg.        
        //Timer timerProcesoDiarioElectronico = timerService.createTimer(horaInicio, HORA_EN_MILESEGUNDOS, null); // para pruebas 
        Timer timerProcesoDiarioElectronico = timerService.createTimer(horaInicio, DIA_EN_MILISEGUNDOS, null);
        appConfiguracion.loggerApp.info("Se ha iniciado el Timer para DiarioElectronico, la primera " +
        "ejecución será el " + com.davivienda.utilidades.conversion.Fecha.aCadena(timerProcesoDiarioElectronico.getNextTimeout(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA) + " , " + horaInicio + " milisegundos");
    }
    

    /**
     * 
     * @param timer
     */
    @Timeout
    public void ejecutaTimerDiarioElectronico(Timer timer) {
        String nombreArchivo = "";
        String directorioArchivo = "";
        ArrayList lstArchivos ;
        if(ConsultarEstadoCargue().equals("0")){
            try {
                CambiarEstadoCargue("1");
                java.util.logging.Logger.getLogger("globalApp").info("Se ejecuta el Timer DiarioElectronico " + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA));
                nombreArchivo = com.davivienda.utilidades.edc.Edc.getNombreArchivoCiclosComprimido(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy());
                java.util.logging.Logger.getLogger("globalApp").info("el nombre de el archivo a descomprimir es :" + nombreArchivo);
                directorioArchivo=em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.directorioUpLoad")).getValor().trim();
                java.util.logging.Logger.getLogger("globalApp").info("el directorio de el archivo a descomprimir es :" + directorioArchivo);
               // com.davivienda.utilidades.archivo.ProcesosArchivo.unzip(appConfiguracion.DIRECTORIO_UPLOAD, nombreArchivo);
                lstArchivos=com.davivienda.utilidades.archivo.ProcesosArchivo.unzipArray(directorioArchivo, nombreArchivo);
              //  administradorProcesosEdcCargueSessionLocal.registrarCicloEdcCargue(lstArchivos,nombreArchivo,com.davivienda.utilidades.conversion.Fecha.getCalendarHoy());
                diarioElectronicoCarga.cargarCiclo(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy());
                //appConfiguracion.loggerApp.info("Fin ejecución del Timer DiarioElectronico " + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA));
                java.util.logging.Logger.getLogger("globalApp").info("Se ejecuta el Timer DiarioElectronico " + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA));
               
            } catch (FileNotFoundException ex) {
               // appConfiguracion.loggerApp.severe("No se puede descomprimir el archivo " + nombreArchivo);
              java.util.logging.Logger.getLogger("globalApp").info("No se puede encontrar el archivo ,descripcion exception : " + ex.getMessage());
                //appConfiguracion.loggerApp.info("No se puede descomprimir el archivo exception del tipo " + ex.getMessage());

            } catch (SecurityException ex) {
               // appConfiguracion.loggerApp.severe("No se puede descomprimir el archivo " + nombreArchivo);
              java.util.logging.Logger.getLogger("globalApp").info("No se puede descomprimir el archivo por permisos ,descripcion exception : " + ex.getMessage());
                //appConfiguracion.loggerApp.info("No se puede descomprimir el archivo exception del tipo " + ex.getMessage());

            } catch (Exception ex) {
               // appConfiguracion.loggerApp.severe("No se puede descomprimir el archivo " + nombreArchivo);

                java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "No se puede descomprimir el archivo exception del tipo :" + ex.getMessage(), ex);
                //appConfiguracion.loggerApp.info("No se puede descomprimir el archivo exception del tipo " + ex.getMessage());
            } finally {
                 CambiarEstadoCargue("0");
                 try {
                     java.util.logging.Logger.getLogger("globalApp").info("Próxima ejecución del Timer DiarioElectronico " + com.davivienda.utilidades.conversion.Fecha.aCadena(timer.getNextTimeout(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA));
               //appConfiguracion.loggerApp.info("Próxima ejecución del Timer DiarioElectronico " + com.davivienda.utilidades.conversion.Fecha.aCadena(timer.getNextTimeout(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA));
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger("globalApp").info("No se puede obtener la  ejecución del Timer DiarioElectronico ,descripcion exception : " + ex.getMessage());
                }
                }
        }
        else
        {
         java.util.logging.Logger.getLogger("globalApp").info("No Se ejecuta el Timer DiarioElectronico " + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA)+ "debido a que el indicador de caraga de diarios electronicos esta en proceso");
        
        }
        
        }
    
    //metodo consulta estado de  el proceso de cargue de tiras  0- no se esta ejecutando 1- se esta ejecutando
    
    private String ConsultarEstadoCargue()
    {
        String strEstadoCargue = "0";
       
        try {
        strEstadoCargue = (em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGADIARIO")).getValor().trim());
        
         } catch (Exception ex) {
                java.util.logging.Logger.getLogger("globalApp").info("Error obteniendo estado cargue : " + ex.getMessage());
                 strEstadoCargue = "0";
         }
        return strEstadoCargue;
    
    }
    

    
  private void CambiarEstadoCargue(String strEstado)
    {
       
           
        
       
        try {
//         ConfModulosAplicacion registroEntity =em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGADIARIO"));
//                  // new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
//         registroEntity.setValor(strEstado);
//         em.persist(registroEntity);
//         em.flush();
         
           ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
            registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            
         //ConfModulosAplicacion registroEntityConsulta=confModulosAplicacionSession.buscar(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGADIARIO"));
         ConfModulosAplicacion registroEntity =new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
                  // new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
         registroEntity.setValor(strEstado);
         registroEntity.setDescripcion(registroEntityConsulta.getDescripcion());
//       utx.begin();
         confModulosAplicacionSession.actualizar(registroEntity);
        
         } catch (Exception ex) {
                java.util.logging.Logger.getLogger("globalApp").info("Error cambiando estado cargue : " + ex.getMessage());
              
         }
      
    
    }
 
 }
    


