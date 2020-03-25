package com.davivienda.multifuncional.tablas.totalesestacionmultifuncional.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Totalesestacionmultifuncional;

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

public class TotalesEstacionMultiSessionServicio extends BaseEntityServicio<Totalesestacionmultifuncional> implements AdministracionTablasInterface<Totalesestacionmultifuncional> {

    
    public TotalesEstacionMultiSessionServicio(EntityManager em) {
        super(em, Totalesestacionmultifuncional.class);
    }


        
        public Collection<Totalesestacionmultifuncional> getTotalesEstacionMultiRangoFecha(Calendar fechaInicial,Calendar fechaFinal) throws EntityServicioExcepcion {
        Collection<Totalesestacionmultifuncional> regs = null;
        try {
            Query query = null;
            query = em.createNamedQuery("Totalesestacionmultifuncional.Fecha");
            query.setParameter("fechaInicial", fechaInicial.getTime());            
            query.setParameter("fechaFinal", fechaFinal.getTime());
            regs =  query.getResultList();
        } catch (IllegalStateException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.WARNING, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex); 
      
        }
            System.out.println("TotalesEstacionMultiSessionServicio.getTotalesEstacionMultiRangoFecha(): regs " + regs != null? regs.size() : 0);
         return regs;
     }     

}
