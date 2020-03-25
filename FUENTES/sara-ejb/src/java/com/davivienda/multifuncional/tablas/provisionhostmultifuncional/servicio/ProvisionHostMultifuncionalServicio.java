package com.davivienda.multifuncional.tablas.provisionhostmultifuncional.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.ProvisionHost;
import com.davivienda.sara.entitys.Provisionhostmulti;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * DiarioElectronicoServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author mdruiz
 * Davivienda 2011 
 */

public class ProvisionHostMultifuncionalServicio extends BaseEntityServicio<Provisionhostmulti> implements AdministracionTablasInterface<Provisionhostmulti> {

    
    public ProvisionHostMultifuncionalServicio(EntityManager em) {
        super(em, ProvisionHost.class);
    }

//        public void grabarProvisionHost(Provisionhostmulti provisionHostmulti) throws EntityServicioExcepcion {
//       
//       
//            try {
//                
//                if (provisionHostmulti.getCajero().getActivo()==1)
//                {
//                    
//                    provisionHost.setCajero(em.merge(provisionHost.getCajero()));
//
//                    em.persist(provisionHostmulti);
//   
//
//                  
//                }
//            } catch (IllegalStateException ex) {
//                java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
//                throw new EntityServicioExcepcion(ex);
//            } catch (IllegalArgumentException ex) {
//                java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
//                throw new EntityServicioExcepcion(ex);
//            } catch (TransactionRequiredException ex) {
//                java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "Se requiere un entorno de transacción ", ex);
//                throw new EntityServicioExcepcion(ex);
//            }
//        
//        
//    }
        
        public Collection<Provisionhostmulti> getProvisionHostRangoFecha(Calendar fechaInicial,Calendar fechaFinal) throws EntityServicioExcepcion {
        Collection<Provisionhostmulti> regs = null;
        try {
            Query query = null;
            query = em.createNamedQuery("Provisionhostmulti.RangoFecha");
            query.setParameter("fechaInicial", fechaInicial.getTime());            
            query.setParameter("fechaFinal", com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial).getTime());
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
