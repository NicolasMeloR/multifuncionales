// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.transaccion.session;

import java.util.Map;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.transaccion.servicio.TransaccionServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class TransaccionSessionBean extends BaseAdministracionTablas<Transaccion> implements TransaccionSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private TransaccionServicio transaccionServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.transaccionServicio = new TransaccionServicio(this.em);
        super.servicio = this.transaccionServicio;
    }
    
    @Override
    public Collection<Transaccion> getColeccionTransaccion(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal) throws EntityServicioExcepcion {
        return this.transaccionServicio.getColeccionTransaccion(codigoCajero, fechaInicial, fechaFinal);
    }
    
    @Override
    public Collection<Transaccion> getColeccionTransaccion(final Integer codigoCajero, final Date fecha) throws EntityServicioExcepcion {
        return this.transaccionServicio.getColeccionTransaccion(codigoCajero, fecha);
    }
    
    @Override
    public Transaccion getTransaccion(final Integer codigoCajero, final Date fechaProceso, final Integer numeroTransaccion) throws EntityServicioExcepcion {
        return this.transaccionServicio.getTransaccion(codigoCajero, fechaProceso, numeroTransaccion);
    }
    
    @Override
    public Collection<Transaccion> getColeccionTransaccion(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal, final Integer numeroTransaccion, final Map criterios) throws EntityServicioExcepcion {
        return this.transaccionServicio.getColeccionTransaccion(codigoCajero, fechaInicial, fechaFinal, numeroTransaccion, criterios);
    }
}
