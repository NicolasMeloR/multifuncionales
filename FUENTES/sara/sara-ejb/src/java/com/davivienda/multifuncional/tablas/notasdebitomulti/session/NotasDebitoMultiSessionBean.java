// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.notasdebitomulti.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.multifuncional.tablas.notasdebito.servicio.NotasDebitoMultiServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class NotasDebitoMultiSessionBean extends BaseAdministracionTablas<Notasdebitomultifuncional> implements NotasDebitoMultiSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    NotasDebitoMultiServicio notasDebitoServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.notasDebitoServicio = new NotasDebitoMultiServicio(this.em);
        super.servicio = this.notasDebitoServicio;
    }
    
    @Override
    public Collection<Notasdebitomultifuncional> getNotasDebito(final Date fechaInicial, final Date fechaFinal, final Integer codigCajero, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.notasDebitoServicio.getNotasDebito(fechaInicial, fechaFinal, codigCajero, fechaHisto);
    }
    
    @Override
    public Notasdebitomultifuncional getNotasDebitoXLlave(final Integer codigoCajero, final Date fechaProceso, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.notasDebitoServicio.getNotasDebitoXLlave(codigoCajero, fechaProceso, fechaHisto);
    }
    
    @Override
    public Notasdebitomultifuncional getNotasDebitoXCuentaValor(final Integer codigoCajero, final Date fechaProceso, final String numeroCuenta, final Long valor, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.notasDebitoServicio.getNotasDebitoXCuentaValor(codigoCajero, fechaProceso, numeroCuenta, valor, fechaHisto);
    }
}
