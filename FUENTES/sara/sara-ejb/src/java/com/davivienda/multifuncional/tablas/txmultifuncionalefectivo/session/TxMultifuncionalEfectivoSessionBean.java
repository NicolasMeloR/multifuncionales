// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.txmultifuncionalefectivo.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.multifuncional.tablas.txmultifuncionalefectivo.servicio.TxMultifuncionalEfectivoServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class TxMultifuncionalEfectivoSessionBean extends BaseAdministracionTablas<Txmultifuncionalefectivo> implements TxMultifuncionalEfectivoSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private TxMultifuncionalEfectivoServicio transaccionServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.transaccionServicio = new TxMultifuncionalEfectivoServicio(this.em);
        super.servicio = this.transaccionServicio;
    }
    
    @Override
    public Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.transaccionServicio.getColeccionTxEfectivo(codigoCajero, fechaInicial, fechaFinal, fechaHisto);
    }
    
    @Override
    public Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(final Integer codigoCajero, final Date fecha, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.transaccionServicio.getColeccionTxEfectivo(codigoCajero, fecha, fechaHisto);
    }
    
    @Override
    public Txmultifuncionalefectivo getTxEfectivo(final Integer codigoCajero, final Date fechaProceso, final Integer numeroTransaccion, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.transaccionServicio.getTxEfectivo(codigoCajero, fechaProceso, numeroTransaccion, fechaHisto);
    }
    
    @Override
    public Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal, final Integer numeroTransaccion, final String cuenta, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.transaccionServicio.getColeccionTxEfectivo(codigoCajero, fechaInicial, fechaFinal, numeroTransaccion, cuenta, fechaHisto);
    }
    
    @Override
    public void guardarArchivoMultifuncionalEfectivo(final Integer codigoCajero) throws EntityServicioExcepcion {
        this.transaccionServicio.guardarArchivoMultifuncionalEfectivo(codigoCajero);
    }
    
    @Override
    public void cargarDiariosMultiEfectivo() throws EntityServicioExcepcion {
        this.transaccionServicio.cargarDiariosMultiEfectivo();
    }
    
    @Override
    public void cargarDiariosMultiCheque() throws EntityServicioExcepcion {
        this.transaccionServicio.cargarDiariosMultiCheque();
    }
    
    @Override
    public void cargarLogEventos() throws EntityServicioExcepcion {
        this.transaccionServicio.cargarLogEventos();
    }
}
