package com.davivienda.sara.tablas.binentidad.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.entitys.BinEntidad;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * OccaServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class BinEntidadServicio extends BaseEntityServicio<BinEntidad>  {

    public BinEntidadServicio(EntityManager em) {
        super(em, BinEntidad.class);
    }
    
        /**
     * Retorna la entidad del bin
        * @param bin
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
  
    public BinEntidad getEntidadXBin(String bin) throws EntityServicioExcepcion {
       
      
        BinEntidad be = null;
        try {
            Query query = null;
           
                query = em.createNamedQuery("BinEntidad.RegistroUnico");
                query.setParameter("bin", bin);
                be = (BinEntidad) query.getSingleResult();
              
           
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return be;
    }


}
