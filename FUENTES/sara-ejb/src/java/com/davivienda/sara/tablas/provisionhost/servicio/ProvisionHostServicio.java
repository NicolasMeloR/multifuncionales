package com.davivienda.sara.tablas.provisionhost.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.ProvisionHost;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

/**
 * DiarioElectronicoServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class ProvisionHostServicio extends BaseEntityServicio<ProvisionHost> implements AdministracionTablasInterface<ProvisionHost> {

    
    public ProvisionHostServicio(EntityManager em) {
        super(em, ProvisionHost.class);
    }

        public void grabarProvisionHost(ProvisionHost provisionHost) throws EntityServicioExcepcion {
       
       
            try {
                
                if (provisionHost.getCajero().getActivo()==1)
                {
                    
                    provisionHost.setCajero(em.merge(provisionHost.getCajero()));

                    em.persist(provisionHost);
   

                  
                }
            } catch (IllegalStateException ex) {
                java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
                throw new EntityServicioExcepcion(ex);
            } catch (IllegalArgumentException ex) {
                java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
                throw new EntityServicioExcepcion(ex);
            } catch (TransactionRequiredException ex) {
                java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "Se requiere un entorno de transacción ", ex);
                throw new EntityServicioExcepcion(ex);
            }
        
        
    }
        
        public Collection<ProvisionHost> getProvisionHostRangoFecha(Calendar fechaInicial,Calendar fechaFinal) throws EntityServicioExcepcion {
        Collection<ProvisionHost> regs = null;
        try {
            Query query = null;
            query = em.createNamedQuery("ProvisionHost.RangoFecha");
            query.setParameter("fechaInicial", fechaInicial.getTime());            
            query.setParameter("fechaFinal", fechaFinal.getTime());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            regs =  query.getResultList();
        } catch (IllegalStateException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.WARNING, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex); 
      
        }
         return regs;
     }     

}
