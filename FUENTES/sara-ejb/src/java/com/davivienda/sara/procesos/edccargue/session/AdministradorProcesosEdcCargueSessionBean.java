package com.davivienda.sara.procesos.edccargue.session;

import com.davivienda.sara.procesos.edccargue.servicio.AdministradorProcesosEdcCargueServicio;
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
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import java.util.ArrayList; 
import java.util.Date;
import javax.transaction.SystemException;

/**
 * ProcesadorDiarioElectronicoServicio - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

@Stateless
@Local(value = AdministradorProcesosEdcCargueSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.BEAN)
public class AdministradorProcesosEdcCargueSessionBean implements AdministradorProcesosEdcCargueSessionLocal {
    @PersistenceContext private EntityManager em;
    @Resource private UserTransaction utx;
     
   private CajeroServicio cajeroServicio;
    private AdministradorProcesosEdcCargueServicio administradorProcesosEdcCargueServicio ;
   

   private ConfModulosAplicacionLocal confModulosAplicacionSession;
    
    
    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
        administradorProcesosEdcCargueServicio = new AdministradorProcesosEdcCargueServicio(em);
        cajeroServicio = new CajeroServicio(em);

    }
    
    

      public Integer  actualizarEdcCargue(ArrayList nombreArchivos,String nombreZip,Calendar fecha){//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
          Integer registros = -1;
           
         
        return registros;
      
  
    }
  
      /**
     * Carga el ciclo de cajeros diario A la tabla historico EdcCargue
     * @param nombreArchivos
     * @param nombreZip
     * @param fecha
     * @return
     */  
       public Integer registrarCicloEdcCargue(ArrayList nombreArchivos,String nombreZip,Calendar fecha,Integer estado,boolean actualiza) {
//        if (fecha == null || fecha.after(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy())) {
//            throw new IllegalArgumentException("Debe ingresar una fecha válida");
//        }
          // ProcesoTransmisionTira procesoTransmisionTira= new ProcesoTransmisionTira();
         
           String cadenaArray[] = new String[2];
           Integer tamano=0;
           Date fechaCarga = new Date();
           String nombreArchivo="";
           Integer codigoCajero;
           Integer ciclo=0;
           String strYear="";
       // Collection<Cajero> cajeros = cajeroServicio.getTodos();
      
        Integer regsProceso = 0;
     
        
           java.util.logging.Logger.getLogger("globalApp").info("Se inicia el proceso del ciclo " + com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD));
           
         

        
          
                try {
                 
                  
                    for (int i=1; i<nombreArchivos.size();i++) 
                    {
                        fechaCarga=fecha.getTime();
                        strYear=  com.davivienda.utilidades.conversion.Numero.aMoneda(fechaCarga.getYear()).substring(2);
                     // GregorianCalendar calendar = new GregorianCalendar();
                  

                        
                     // fechaCarga=fecha.getTime();
                    //  fechaCarga=com.davivienda.utilidades.conversion.Fecha.getDateHoy();
                        cadenaArray=((String) nombreArchivos.get(i)).split(";");
                        nombreArchivo=cadenaArray[0];
                        tamano=com.davivienda.utilidades.conversion.Cadena.aInteger(cadenaArray[1]);
                        codigoCajero= com.davivienda.utilidades.conversion.Cadena.aInteger(nombreArchivo.substring(0, 4));
                        if(nombreArchivo.substring(4).equals(strYear));
                            ciclo=com.davivienda.utilidades.conversion.Cadena.aInteger(nombreZip.substring(3,7)+strYear);
                         regsProceso= regsProceso+registrarArchivoEdcCargue(codigoCajero,nombreArchivo,tamano,ciclo, estado, actualiza);
                    }
                   
                }
                catch (IllegalArgumentException ex) {
                  java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos en EDCCARGUE para el archivo " + nombreArchivo + " " + ex.getMessage());
                }
                catch (Exception ex){
                   java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos en EDCCARGUE  para el archivo :" + nombreArchivo + " descripcion Error : " + ex.getMessage());            
                }
        java.util.logging.Logger.getLogger("globalApp").info("Fin del proceso de grabar los datos en EDCCARGUE   para el  " + fechaCarga.toString() + ". registros procesados : " + regsProceso);
        return regsProceso;
    }

     
         public Integer registrarArchivoEdcCargue(Integer codigoCajero,String nombreArchivo,Integer tamano,Integer ciclo,Integer estado,boolean actualiza)
         {
              Date fechaCarga = new Date();
              Integer regGuardados=0;

             
               try {
                 
                     utx.begin();
                     fechaCarga=com.davivienda.utilidades.conversion.Fecha.getDateHoy();
                     regGuardados=administradorProcesosEdcCargueServicio.guardarProcesoTransmisionTiras(nombreArchivo,tamano,ciclo,fechaCarga,codigoCajero,estado,actualiza);         
                     utx.commit();
                }
                catch (IllegalArgumentException ex) {
                  java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos en EDCCARGUE para el archivo " + nombreArchivo + " " + ex.getMessage());
                }
                catch (Exception ex){
                   java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos en EDCCARGUE  para el archivo :" + nombreArchivo + " descripcion Error : " + ex.getMessage());            
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
              
          }
            return regGuardados;

         
         }
         
          public void guardarProcesoTransmisionTiras(Integer codigoCajero,String nombreArchivo,Integer tamano,Integer ciclo,Integer estado,boolean actualiza)
         {
              Date fechaCarga = new Date();

               try {

                     utx.begin();
                     fechaCarga=com.davivienda.utilidades.conversion.Fecha.getDateHoy();
                     administradorProcesosEdcCargueServicio.guardarProcesoTransmisionTiras(nombreArchivo,tamano,ciclo,fechaCarga,codigoCajero,estado,actualiza);   
                     utx.commit();
                }
                catch (IllegalArgumentException ex) {
                  java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos en EDCCARGUE para el archivo " + nombreArchivo + " " + ex.getMessage());
                }
                catch (Exception ex){
                   java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos en EDCCARGUE  para el archivo :" + nombreArchivo + " descripcion Error : " + ex.getMessage());
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

          }

         }
       


}  
   

   