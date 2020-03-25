// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.logcajeromulti.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.multifuncional.tablas.logcajeromulti.servicio.LogCajeroMultiServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Logcajeromulti;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class LogCajeroMultiSessionBean extends BaseAdministracionTablas<Logcajeromulti> implements LogCajeroMultiSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private LogCajeroMultiServicio logCajeroMultiServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.logCajeroMultiServicio = new LogCajeroMultiServicio(this.em);
        super.servicio = this.logCajeroMultiServicio;
    }
    
    @Override
    public Collection<Logcajeromulti> getColeccionLogCajeroMulti(final Integer codigoCajero, final Date fecha) throws EntityServicioExcepcion {
        return this.logCajeroMultiServicio.getColeccionLogCajeroMulti(codigoCajero, fecha);
    }
}
