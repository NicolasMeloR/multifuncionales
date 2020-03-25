package com.davivienda.sara.tablas.reintegros.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.reintegros.servicio.ReintegrosServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class ReintegrosSessionBean extends BaseAdministracionTablas<Reintegros> implements ReintegrosSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    ReintegrosServicio reintegrosServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.reintegrosServicio = new ReintegrosServicio(this.em);
        super.servicio = this.reintegrosServicio;
    }
    
    @Override
    public Collection<Reintegros> getReintegros(final Date fechaInicial, final Date fechaFinal, final Integer codigCajero, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.reintegrosServicio.getReintegros(fechaInicial, fechaFinal, codigCajero, fechaHisto);
    }
    
    @Override
    public Collection<Reintegros> getReintegros(final Date fecha, final Integer codigCajero, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.reintegrosServicio.getReintegros(fecha, codigCajero, fechaHisto);
    }
    
    @Override
    public Reintegros getReintegroXLlave(final Integer codigoCajero, final Date fechaProceso, final Integer numeroTransaccion, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.reintegrosServicio.getReintegroXLlave(codigoCajero, fechaProceso, numeroTransaccion, fechaHisto);
    }
    
    @Override
    public Reintegros getReintegroXCuentaValor(final Integer codigoCajero, final Date fechaProceso, final Integer numeroTransaccion, final String numeroCuenta, final Long valor, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.reintegrosServicio.getReintegroXCuentaValor(codigoCajero, fechaProceso, numeroTransaccion, numeroCuenta, valor, fechaHisto);
    }
    
    @Override
    public Collection<Object> getReintegrosXCajero(final Date fechaInicial, final Date fechaFinal, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.reintegrosServicio.getReintegrosXCajero(fechaInicial, fechaFinal, fechaHisto);
    }
    
    @Override
    public void actualizarEstadoReintegros(final Date fechaInicial, final Integer codigoCajero, final Date fechaHisto) throws EntityServicioExcepcion {
        this.reintegrosServicio.actualizarEstadoReintegros(fechaInicial, codigoCajero, fechaHisto);
    }
    
    public Reintegros findByPrimayKey(Integer codigoCajero, Date fechaSistema, Integer talon, Date fechaHisto) throws EntityServicioExcepcion {
        return reintegrosServicio.findByPrimayKey(codigoCajero,fechaSistema,talon,fechaHisto);
    }
}
