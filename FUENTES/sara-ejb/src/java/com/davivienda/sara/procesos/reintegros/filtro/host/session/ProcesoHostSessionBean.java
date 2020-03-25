/*
 * ProcesoCuadreCifrasSessionBean.java
 *
 * Created on 25/09/2007, 09:32:24 AM
 * 
 * Babel Ver   :1.0
 */

package com.davivienda.sara.procesos.reintegros.filtro.host.session;


import com.davivienda.sara.procesos.reintegros.servicio.ReintegrosProcesosServicio;
import com.davivienda.sara.procesos.reintegros.servicio.ReintegrosDiarioTEMPProcesosServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.procesos.reintegros.filtro.host.servicio.ProcesadorHostServicio;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.Notasdebito;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileNotFoundException;
import javax.annotation.Resource;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author jjvargas
 */
@Stateless
@Local(value = ProcesoHostSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ProcesoHostSessionBean implements ProcesoHostSessionLocal {
   @Resource private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    private ProcesadorHostServicio servicio;


    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
       
        servicio = new ProcesadorHostServicio(em);
   
    }
    
    
    /**
     * Realiza la generación de los registros de Dia sgte Real y Inico Día Real para la fecha pasada
     * en el parámetro fecha
     *
     * @param fecha
     * @returnx
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
//    public Integer CargarArchivo(Calendar fecha) throws  EntityServicioExcepcion,FileNotFoundException, IllegalArgumentException {                
//        return servicio.cargarArchivoHost( fecha);
//    }

    
//     public Integer CargarArchivo(Calendar fecha) throws  EntityServicioExcepcion,FileNotFoundException, IllegalArgumentException {                
//        String[] indOcca ={"A","B","C","D","E","F"};
//        Integer regHost=0;
//        
//        for(int i=0;i<6;i++)
//        {
//                regHost=regHost+CargarArchivoUnoAuno( fecha,indOcca[i]);
//        }
//       
//        return regHost;
//    }
    
    
         public Integer CargarArchivo(Calendar fecha) throws  EntityServicioExcepcion,FileNotFoundException, IllegalArgumentException {                
     
        Integer regHost=0;
        int regArchivo=0;
        int i =0;
        try {
            regArchivo = servicio.cuentaRegistros(fecha);
            for(i=0;i==0||i<=regArchivo;i=i+20000)
            {
                regHost=regHost+CargarArchivoxFilas( fecha,i);
            }
        } catch (IOException ex) {
            Logger.getLogger(ProcesoHostSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        return regHost;
    }
    
    
     public Integer CargarArchivoxFilas(Calendar fecha,int numRegistros) throws  EntityServicioExcepcion,FileNotFoundException, IllegalArgumentException {                
                Integer registros = -1;
           try {
            utx.begin();
            
           registros = servicio.cargarArchivoHost( fecha,numRegistros);
            utx.commit();
            } catch (FileNotFoundException ex) {
                     java.util.logging.Logger.getLogger("globalApp").info("No existe el archivo host   ATM con fecha "+ fecha.toString() + " " + ex.getMessage());
                  
            } catch (EntityServicioExcepcion ex) {
                  
                    java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos  archivo host   ATM con fecha "+ fecha.toString() + " " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                  
                         java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos archivo host   ATM con fecha "+ fecha.toString() + " " + ex.getMessage());
           }
              catch (Exception ex) {
                  
                     java.util.logging.Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo host   ATM con fecha "+ fecha.toString() + " " + ex.getMessage());
            }finally{ 
               
            try {
             
                //Status.STATUS_COMMITTED tiene valor 6 
                if( utx.getStatus()!= 6){
                      java.util.logging.Logger.getLogger("globalApp").info("No se grabaran los registros procesados se realiza rollback de cargue archivo host   ATM   con fecha "+ fecha.toString() + " ");
                    utx.rollback();
                      }
               
               
            } catch (IllegalStateException ex1) {
                   java.util.logging.Logger.getLogger("globalApp").info( ex1.getMessage());
            } catch (SecurityException ex1) {
                    java.util.logging.Logger.getLogger("globalApp").info( ex1.getMessage());
            } catch (SystemException ex1) {
               
                   java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            }
              
          }
         
        return registros;
       
    }
     
   }
       
   