// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.txcontrolmulticheque.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.annotation.PostConstruct;
import com.davivienda.multifuncional.tablas.txcontrolmulticheque.servicio.TxControlMultiChequeServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Txcontrolmulticheque;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class TxControlMultiChequeSessionBean extends BaseAdministracionTablas<Txcontrolmulticheque> implements TxControlMultiChequeSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private TxControlMultiChequeServicio transaccionServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.transaccionServicio = new TxControlMultiChequeServicio(this.em);
        super.servicio = this.transaccionServicio;
    }
    
    @Override
    public Txcontrolmulticheque getRegistroControlCheque(final Long indControl) throws EntityServicioExcepcion {
        return this.transaccionServicio.getRegistroControlCheque(indControl);
    }
}
