// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.servicio.ReintegrosMultiEfectivoServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class ReintegrosMultiEfectivoSessionBean extends BaseAdministracionTablas<Reintegrosmultiefectivo> implements ReintegrosMultiEfectivoSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    ReintegrosMultiEfectivoServicio reintegrosServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.reintegrosServicio = new ReintegrosMultiEfectivoServicio(this.em);
        super.servicio = this.reintegrosServicio;
    }
    
    @Override
    public Collection<Reintegrosmultiefectivo> getReintegros(final Date fechaInicial, final Date fechaFinal, final Integer codigCajero, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.reintegrosServicio.getReintegros(fechaInicial, fechaFinal, codigCajero, fechaHisto);
    }
    
    @Override
    public Reintegrosmultiefectivo getReintegroXLlave(final Integer codigoCajero, final Date fechaProceso, final Integer numeroTransaccion, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.reintegrosServicio.getReintegroXLlave(codigoCajero, fechaProceso, numeroTransaccion, fechaHisto);
    }
    
    @Override
    public Reintegrosmultiefectivo getReintegroXCuentaValor(final Integer codigoCajero, final Date fechaProceso, final Integer numeroTransaccion, final String numeroCuenta, final Long valor, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.reintegrosServicio.getReintegroXCuentaValor(codigoCajero, fechaProceso, numeroTransaccion, numeroCuenta, valor, fechaHisto);
    }
}
