// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.txmultifuncionalcheque.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.multifuncional.tablas.txmultifuncionalcheque.servicio.TxMultifuncionalChequeServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Txmultifuncionalcheque;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class TxMultifuncionalChequeSessionBean extends BaseAdministracionTablas<Txmultifuncionalcheque> implements TxMultifuncionalChequeSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private TxMultifuncionalChequeServicio transaccionServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.transaccionServicio = new TxMultifuncionalChequeServicio(this.em);
        super.servicio = this.transaccionServicio;
    }
    
    @Override
    public Collection<Txmultifuncionalcheque> getColeccionTxCheque(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal) throws EntityServicioExcepcion {
        return this.transaccionServicio.getColeccionTxCheque(codigoCajero, fechaInicial, fechaFinal);
    }
    
    @Override
    public Collection<Txmultifuncionalcheque> getColeccionTxCheque(final Integer codigoCajero, final Date fecha) throws EntityServicioExcepcion {
        return this.getColeccionTxCheque(codigoCajero, fecha);
    }
    
    @Override
    public Txmultifuncionalcheque getTxCheque(final Integer codigoCajero, final Date fechaProceso, final Integer numeroTransaccion) throws EntityServicioExcepcion {
        return this.transaccionServicio.getTxCheque(codigoCajero, fechaProceso, numeroTransaccion);
    }
    
    @Override
    public Collection<Txmultifuncionalcheque> getColeccionTxCheque(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal, final Integer numeroTransaccion, final String cuenta) throws EntityServicioExcepcion {
        return this.transaccionServicio.getColeccionTxCheque(codigoCajero, fechaInicial, fechaFinal, numeroTransaccion, cuenta);
    }
}
