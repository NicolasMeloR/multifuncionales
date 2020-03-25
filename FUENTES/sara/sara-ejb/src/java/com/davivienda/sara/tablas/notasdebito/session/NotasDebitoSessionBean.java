// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.notasdebito.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.notasdebito.servicio.NotasDebitoServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class NotasDebitoSessionBean extends BaseAdministracionTablas<Notasdebito> implements NotasDebitoSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    NotasDebitoServicio notasDebitoServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.notasDebitoServicio = new NotasDebitoServicio(this.em);
        super.servicio = this.notasDebitoServicio;
    }
    
    @Override
    public Collection<Notasdebito> getNotasDebito(final Date fechaInicial, final Date fechaFinal, final Integer codigCajero, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.notasDebitoServicio.getNotasDebito(fechaInicial, fechaFinal, codigCajero, fechaHisto);
    }
    
    @Override
    public Notasdebito getNotasDebitoXLlave(final Integer codigoCajero, final Date fechaProceso, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.notasDebitoServicio.getNotasDebitoXLlave(codigoCajero, fechaProceso, fechaHisto);
    }
    
    @Override
    public Notasdebito getNotasDebitoXCuentaValor(final Integer codigoCajero, final Date fechaProceso, final String numeroCuenta, final Long valor, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.notasDebitoServicio.getNotasDebitoXCuentaValor(codigoCajero, fechaProceso, numeroCuenta, valor, fechaHisto);
    }
    
    public String findByPrimayKey(Integer codigoCajero, Date fechaSistema, String numeroCuenta) throws EntityServicioExcepcion {
        return notasDebitoServicio.findByPrimayKey(codigoCajero, fechaSistema, numeroCuenta);
    }
}
