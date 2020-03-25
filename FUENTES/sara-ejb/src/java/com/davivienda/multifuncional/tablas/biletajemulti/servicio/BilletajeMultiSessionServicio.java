package com.davivienda.multifuncional.tablas.biletajemulti.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Billetajemulti;

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

public class BilletajeMultiSessionServicio extends BaseEntityServicio<Billetajemulti> implements AdministracionTablasInterface<Billetajemulti> {

    
    public BilletajeMultiSessionServicio(EntityManager em) {
        super(em, Billetajemulti.class);
    }


        
        public Collection<Billetajemulti> getBilletajeMultiRangoFecha(Calendar fechaInicial,Calendar fechaFinal) throws EntityServicioExcepcion {
        Collection<Billetajemulti> regs = null;
        try {
            Query query = null;
            query = em.createNamedQuery("Billetajemulti.Fecha");
            query.setParameter("fechaInicial",fechaInicial.getTime());            
            query.setParameter("fechaFinal", com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial).getTime());
            regs =  query.getResultList();
        } catch (IllegalStateException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.WARNING, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex); 
      
        }
            System.out.println("BilletajeMultiSessionServicio.getBilletajeMultiRangoFecha(): regs " + regs != null ? regs.size():0);
         return regs;
     }     

}
