package com.davivienda.sara.procesos.cajero.session;


import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.Cajero;

import javax.ejb.Local;
import javax.annotation.Resource;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.PersistenceContext;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
//import javax.net.ssl.SSLEngineResult.Status;

import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
/**
 * ProcesadorDiarioElectronicoServicio - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

@Stateless
@Local(value = CajeroProcesosSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CajeroProcesosSessionBean implements CajeroProcesosSessionLocal {
    @PersistenceContext private EntityManager em;
    @Resource private UserTransaction utx;
     
   private CajeroServicio cajeroServicio;
   
    
 
    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
      
        cajeroServicio = new CajeroServicio(em);
      
    }
    
    
    
    public Cajero actualizarCajero(Cajero objeto)  throws EntityServicioExcepcion 
    {
         Cajero objetoActual = null;
         String srtError="";
         
            
           try {
            utx.begin();
            
            objetoActual = cajeroServicio.actualizar(objeto);
            utx.commit();
          
            } catch (EntityServicioExcepcion ex) {
                
                    srtError=ex.getMessage() ;
                   
                    java.util.logging.Logger.getLogger("globalApp").info("Error al actualizar  los datos  de el cajero del tipo "+ ex.getMessage() );
            } catch (IllegalArgumentException ex) {
                   
                         java.util.logging.Logger.getLogger("globalApp").info("Error al actualizar  los datos  de el cajero del tipo "+ ex.getMessage() );
           }
              catch (Exception ex) {
                   
                     java.util.logging.Logger.getLogger("globalApp").info("Error al actualizar  los datos  de el cajero del tipo "+ ex.getMessage() );
            }finally{ 
               
            try {
              
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
            
            if(!srtError.equals(""))
            {
                
             throw new EntityServicioExcepcion(srtError);             
                
            }
         
        return objetoActual;
      
    }
    
  



}  
   

   