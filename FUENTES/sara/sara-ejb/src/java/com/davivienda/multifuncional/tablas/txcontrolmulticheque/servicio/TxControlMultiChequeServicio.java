// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.txcontrolmulticheque.servicio;

import javax.persistence.Query;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Txcontrolmulticheque;
import com.davivienda.sara.base.BaseEntityServicio;

public class TxControlMultiChequeServicio extends BaseEntityServicio<Txcontrolmulticheque> implements AdministracionTablasInterface<Txcontrolmulticheque>
{
    public TxControlMultiChequeServicio(final EntityManager em) {
        super(em, Txcontrolmulticheque.class);
    }
    
    public Txcontrolmulticheque getRegistroControlCheque(final Long indControl) throws EntityServicioExcepcion {
        Txcontrolmulticheque reg = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("Txcontrolmulticheque.registroUnico");
            query.setParameter("idregistro", (Object)indControl);
            reg = (Txcontrolmulticheque)query.getSingleResult();
        }
        catch (IllegalStateException ex) {
            TxControlMultiChequeServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TxControlMultiChequeServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return reg;
    }
}
