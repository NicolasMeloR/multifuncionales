/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
 package com.davivienda.sara.administracion.programadortareas.session;


import com.davivienda.sara.cuadrecifras.session.CuadreCifrasCargasSessionLocal;
import com.davivienda.sara.administracion.programadortareas.ProgramadorTareas;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.EJB;
import java.util.Calendar;
import java.io.FileNotFoundException;
import com.davivienda.sara.procesos.cuadrecifras.session.ProcesoCuadreCifrasSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;  
/**
 *
 * @author jjvargas
 */
@Stateless
@Local(value = ProgramadorTareasCargaDatosCorteCajerosSession.class)
public class ProgramadorTareasCargaDatosCorteCajerosSessionBean extends ProgramadorTareas implements ProgramadorTareasCargaDatosCorteCajerosSession {

    @Resource
    private SessionContext ctx;
    @PersistenceContext
    private EntityManager em;
    
   
    @EJB
    private CuadreCifrasCargasSessionLocal cuadreCifrasCargas;
    
    @EJB
    private ProcesoCuadreCifrasSessionLocal procesoCuadreCifrasSessionLocal;
    
    @EJB
    private ConfModulosAplicacionLocal confModulosAplicacionSession;
    /**
     * Método PostConstruct
     */
  
    /**
     * Inicia el timer para el proceso de diarios electrónicos
     * @param appConfiguracion 
     */
 
    public void configuraProgramador(SaraConfig appConfiguracion) {
        super.appConfiguracion = appConfiguracion;

        // Obtengo el tiempo que falta para la hora de inicio normal de la tarea
        long horaInicio = getHoraInicioProgramador(appConfiguracion.HORA_INICIO_TRANSMISION_ARCHIVOS_CORTE);

        // Obtengo el servicio timer del contenedor y creo un nuevo timer
        TimerService timerService = ctx.getTimerService();

        //Timer timerProcesoDiarioElectronico = timerService.createTimer(0, 15000, null); // para pruebas se ejecuta inmediatamente y repite cada 15 seg.        
        Timer timerProcesoDatosCorte = timerService.createTimer(horaInicio, DIA_EN_MILISEGUNDOS, null);
        java.util.logging.Logger.getLogger("globalApp").info("Se ha iniciado el Timer para la carga de archivos de datos de corte cajeros, la primera " +
                "ejecución será el " + com.davivienda.utilidades.conversion.Fecha.aCadena(timerProcesoDatosCorte.getNextTimeout(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA) + " , " + horaInicio + " milisegundos");
         
    }

        
    /**
     * 
     * @param timer
     */
    @Timeout
    public void ejecutaTimer(Timer timer) {
      
        Calendar fechaProceso;
        
       if(ConsultarEstadoCargue().equals("0")){
        try {
        //  fechaProceso=com.davivienda.utilidades.conversion.Fecha.getCalendar(-1);
          CambiarEstadoCargue("1");
          fechaProceso=com.davivienda.utilidades.conversion.Fecha.getCalendarHoy();
          java.util.logging.Logger.getLogger("globalApp").info("Se ejecuta el Timer Carga Archivos Corte Cajeros " + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA));
          java.util.logging.Logger.getLogger("globalApp").info("Tarea 1 Cargar Archivo Corte " + com.davivienda.utilidades.conversion.Fecha.aCadena(fechaProceso, com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA));
            
            //OJO SOLO PARA PRUEBAS
           // cuadreCifrasCargas.cargarArchivoCorte( com.davivienda.utilidades.conversion.Fecha.getCalendarHoy());
         cuadreCifrasCargas.cargarArchivoCorte( fechaProceso,true);
          java.util.logging.Logger.getLogger("globalApp").info("Tarea 2 Cargar Archivo Totales Egresos " + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA));
           // cuadreCifrasCargas.cargarArchivoTotalesEgresos( com.davivienda.utilidades.conversion.Fecha.getCalendarHoy());
            //OJO SOLO PARA PRUEBAS
         cuadreCifrasCargas.cargarArchivoTotalesEgresos( fechaProceso,true);
          java.util.logging.Logger.getLogger("globalApp").info("Tarea 3 Cargar Archivo provisiones " + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA));
           // cuadreCifrasCargas.cargarArchivoProvisiones( com.davivienda.utilidades.conversion.Fecha.getCalendarHoy());
            //OJO SOLO PARA PRUEBAS
     
         cuadreCifrasCargas.cargarArchivoProvisiones( fechaProceso,true);
             
          java.util.logging.Logger.getLogger("globalApp").info("Tarea cuadrea Automatico " + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA));
       //ESTA FECHA DEBE SER DE UN DIA ANTES A LA FECHA DE CARGUE A LOS ARCHIVOS
             fechaProceso=com.davivienda.utilidades.conversion.Fecha.getCalendar(-1);
       
          procesoCuadreCifrasSessionLocal.procesarCuadreCifras(fechaProceso);
     //     java.util.logging.Logger.getLogger("globalApp").info("Fin ejecución del Timer Carga Archivos Corte Cajeros " + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA));
//       } catch (FileNotFoundException ex) {
//           // appConfiguracion.loggerApp.severe("No se puede descomprimir el archivo " + nombreArchivo);
//          java.util.logging.Logger.getLogger("globalApp").info("No se puede encontrar el archivo ,descripcion exception : " + ex.getMessage());
//            //appConfiguracion.loggerApp.info("No se puede descompr   imir el archivo exception del tipo " + ex.getMessage());
       
        } catch (SecurityException ex) {
           // appConfiguracion.loggerApp.severe("No se puede descomprimir el archivo " + nombreArchivo);
           java.util.logging.Logger.getLogger("globalApp").info("No se puede leer el archivo por permisos ,descripcion exception : " + ex.getMessage());
            //appConfiguracion.loggerApp.info("No se puede descomprimir el archivo exception del tipo " + ex.getMessage());
          
        } catch (Exception ex) {
           java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "No se pudo ejecutar el proceso de carga archivos o  el cuadre automatico:" + ex.getMessage(), ex);             
        } finally {
           java.util.logging.Logger.getLogger("globalApp").info("Próxima ejecución del Timer Carga Archivos Corte Cajeros " + com.davivienda.utilidades.conversion.Fecha.aCadena(timer.getNextTimeout(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA));
          CambiarEstadoCargue("0");
        }
        
        }
         else
        {
         java.util.logging.Logger.getLogger("globalApp").info("No Se ejecuta el Timer Carga Archivos cuadre cajeros " + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA)+ "debido a que el indicador de caraga de diarios electronicos esta en proceso");
        
        }
      }
    
    
    private String ConsultarEstadoCargue()
    {
        String strEstadoCargue = "0";
       
        try {
        strEstadoCargue = (em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGACUADRE")).getValor().trim());
        
         } catch (Exception ex) {
                java.util.logging.Logger.getLogger("globalApp").info("Error obteniendo estado cargue : " + ex.getMessage());
                 strEstadoCargue = "0";
         }
        return strEstadoCargue;
    
    }



  private void CambiarEstadoCargue(String strEstado)
    {
       
           
        
       
        try {

         
         ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGACUADRE");
         registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
         ConfModulosAplicacion registroEntity =new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGACUADRE");
         registroEntity.setValor(strEstado);
         registroEntity.setDescripcion(registroEntityConsulta.getDescripcion());

         confModulosAplicacionSession.actualizar(registroEntity);
        
         } catch (Exception ex) {
                java.util.logging.Logger.getLogger("globalApp").info("Error cambiando estado cargue : " + ex.getMessage());
              
         }
      
    
    }
}
