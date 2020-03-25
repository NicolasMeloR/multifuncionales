package com.davivienda.multifuncional.tablas.txcontrolmulticheque.servicio;


import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;

import com.davivienda.sara.entitys.Txcontrolmulticheque;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * TxControlMultiCheque 
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-MDRUIZ
 * Davivienda 2011 
 */
public class TxControlMultiChequeServicio extends BaseEntityServicio<Txcontrolmulticheque> implements AdministracionTablasInterface<Txcontrolmulticheque> {


    public TxControlMultiChequeServicio(EntityManager em) {
        super(em, Txcontrolmulticheque.class);
    }

   
    public Txcontrolmulticheque getRegistroControlCheque(Long indControl) throws EntityServicioExcepcion {
        Txcontrolmulticheque reg = null;
        try {
            
            Query query = null;
                                            
                query = em.createNamedQuery("Txcontrolmulticheque.registroUnico");
                query.setParameter("idregistro", indControl);
                
                reg = (Txcontrolmulticheque) query.getSingleResult();
            
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return reg;
    }
    

}
    