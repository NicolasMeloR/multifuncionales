package com.davivienda.sara.procesos.historicoajustes.session;

import com.davivienda.sara.procesos.historicoajustes.servicio.AdministradorProcesosHistoricoAjustesServicio;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ejb.Local;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.PersistenceContext;
import com.davivienda.utilidades.conversion.FormatoFecha;

import java.io.FileNotFoundException;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.ArrayList; 
import java.util.Date;
import javax.transaction.SystemException;
import java.math.BigDecimal;
/**
 * ProcesadorDiarioElectronicoServicio - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

@Stateless
@Local(value = AdministradorProcesosHistoricoAjustesSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.BEAN)
public class AdministradorProcesosHistoricoAjustesSessionBean implements AdministradorProcesosHistoricoAjustesSessionLocal {
    @PersistenceContext private EntityManager em;
    @Resource private UserTransaction utx;
     
   private CajeroServicio cajeroServicio;
    private AdministradorProcesosHistoricoAjustesServicio administradorProcesosHistoricoAjustesServicio ;
   
    
    
    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
        administradorProcesosHistoricoAjustesServicio = new AdministradorProcesosHistoricoAjustesServicio(em);
        cajeroServicio = new CajeroServicio(em);

    }
    
    
  /**
     * Carga un archivo de Diario Electronico en la tabla DIARIOELECTRONICO y TRANSACCION
     * @param codigoCajero
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
      public Integer  actualizarEdcCargue(ArrayList nombreArchivos,String nombreZip,Calendar fecha){//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
          Integer registros = -1;
           
         
        return registros;
      
  
    }
      /**
     * Carga el ciclo de cajeros diario
     * @param fecha
     * @return
     */  
//       public void guardarHistoricoAjustes(String usuario, Integer codigoCajero, Integer codigoOcca, String tipoAjuste,String cuenta, Date fecha, BigDecimal valor,String talon, String error, String descripcionError){
////        if (fecha == null || fecha.after(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy())) {
////            throw new IllegalArgumentException("Debe ingresar una fecha válida");
////        }
//          // ProcesoTransmisionTira procesoTransmisionTira= new ProcesoTransmisionTira();
//         
//           try
//            {
//             utx.begin();
//             
//               administradorProcesosHistoricoAjustesServicio.guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, tipoAjuste,cuenta, fecha, valor, talon, error, descripcionError);
//               
//             utx.commit();
//                }
//       catch (IllegalArgumentException ex) {
//          administradorProcesosHistoricoAjustesServicio.getConfigApp().loggerApp.info("Error al grabar los datos en HistoricoAjustes para codigoCajero " + codigoCajero + " " + ex.getMessage());
//     
//        } catch (Exception ex)
//        {
//         java.util.logging.Logger.getLogger("globalApp").info("Error cargando en HistoricoAjustes registro datos codigoCajero  :" + codigoCajero + " descripcion Error : " + ex.getMessage());           
//        }
//         finally{ 
//                
//            try {
//                
//                //Status.STATUS_COMMITTED tiene valor 6 
//                if( utx.getStatus()!= 6){
//                    utx.rollback();
//                      }
//               
//               
//            } catch (IllegalStateException ex1) {
//                   java.util.logging.Logger.getLogger("globalApp").info( ex1.getMessage());
//            } catch (SecurityException ex1) {
//                    java.util.logging.Logger.getLogger("globalApp").info( ex1.getMessage());
//            } catch (SystemException ex1) {
//                //Logger.getLogger(AdministradorProcesosSessionBean.class.getName()).log(Level.SEVERE, null, ex1);
//                   java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
//            }
//               administradorProcesosHistoricoAjustesServicio.getConfigApp().loggerApp.info("Fin del proceso de grabar los datos en Historico Ajustes   para el  codigoCajero" + codigoCajero );
//          }
//      
//             
//    }
//         
//            
//     
              
         
         public void guardarHistoricoAjustes(String usuario, Integer codigoCajero, Integer codigoOcca, String tipoAjuste, Date fecha, BigDecimal valor,String talon, String error, String descripcionError){
//        if (fecha == null || fecha.after(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy())) {
//            throw new IllegalArgumentException("Debe ingresar una fecha válida");
//        }
          // ProcesoTransmisionTira procesoTransmisionTira= new ProcesoTransmisionTira();
         
           try
            {
             utx.begin();
             
               administradorProcesosHistoricoAjustesServicio.guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, tipoAjuste, fecha, valor, talon, error, descripcionError);
               
             utx.commit();
                }
       catch (IllegalArgumentException ex) {
          administradorProcesosHistoricoAjustesServicio.getConfigApp().loggerApp.info("Error al grabar los datos en HistoricoAjustes para codigoCajero " + codigoCajero + " " + ex.getMessage());
     
        } catch (Exception ex)
        {
         java.util.logging.Logger.getLogger("globalApp").info("Error cargando en HistoricoAjustes registro datos codigoCajero  :" + codigoCajero + " descripcion Error : " + ex.getMessage());           
        }
         finally{ 
                
            try {
                
                //Status.STATUS_COMMITTED tiene valor 6 
                if( utx.getStatus()!= 6){
                    utx.rollback();
                      }
               
               
            } catch (IllegalStateException ex1) {
                   java.util.logging.Logger.getLogger("globalApp").info( ex1.getMessage());
            } catch (SecurityException ex1) {
                    java.util.logging.Logger.getLogger("globalApp").info( ex1.getMessage());
            } catch (SystemException ex1) {
                //Logger.getLogger(AdministradorProcesosSessionBean.class.getName()).log(Level.SEVERE, null, ex1);
                   java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            }
               administradorProcesosHistoricoAjustesServicio.getConfigApp().loggerApp.info("Fin del proceso de grabar los datos en Historico Ajustes   para el  codigoCajero" + codigoCajero );
          }
      
             
    }
         
            
     
   

       



}  
   

   