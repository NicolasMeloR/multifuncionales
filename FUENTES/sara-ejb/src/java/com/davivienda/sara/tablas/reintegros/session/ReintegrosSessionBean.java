package com.davivienda.sara.tablas.reintegros.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.tablas.reintegros.servicio.ReintegrosServicio;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * DiarioElectronicoSessionBean - 22/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
@Stateless
public class ReintegrosSessionBean extends BaseAdministracionTablas<Reintegros> implements ReintegrosSessionLocal {

    @PersistenceContext
    private EntityManager em;
    ReintegrosServicio reintegrosServicio;

    @PostConstruct
    public void postConstructor() {
        reintegrosServicio = new ReintegrosServicio(em);
        super.servicio = reintegrosServicio;

    }

    /**
     * Retorna los Reintegros de la fecha dada
     *
     * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
    @SuppressWarnings("unchecked")
    public Collection<Reintegros> getReintegros(Date fechaInicial, Date fechaFinal, Integer codigCajero, Date fechaHisto) throws EntityServicioExcepcion {
        return reintegrosServicio.getReintegros(fechaInicial, fechaFinal, codigCajero, fechaHisto);
    }

    public Collection<Reintegros> getReintegros(Date fecha, Integer codigCajero, Date fechaHisto) throws EntityServicioExcepcion {
        return reintegrosServicio.getReintegros(fecha, codigCajero, fechaHisto);
    }

    /**
     * Retorna un objeto Reintegro que cumpla con los parámetros
     *
     * @param codigoCajero
     * @param fechaProceso
     * @param numeroTransaccion
     * @return Reintegros
     * @throws EntityServicioExcepcion
     */
    public Reintegros getReintegroXLlave(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, Date fechaHisto) throws EntityServicioExcepcion {
        return reintegrosServicio.getReintegroXLlave(codigoCajero, fechaProceso, numeroTransaccion, fechaHisto);
    }

    public Reintegros getReintegroXCuentaValor(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, String numeroCuenta, Long valor, Date fechaHisto) throws EntityServicioExcepcion {
        return reintegrosServicio.getReintegroXCuentaValor(codigoCajero, fechaProceso, numeroTransaccion, numeroCuenta, valor, fechaHisto);
    }

    public Collection<Object> getReintegrosXCajero(Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion {
        return reintegrosServicio.getReintegrosXCajero(fechaInicial, fechaFinal, fechaHisto);
    }

    public void actualizarEstadoReintegros(Date fechaInicial, Integer codigoCajero, Date fechaHisto) throws EntityServicioExcepcion {
        reintegrosServicio.actualizarEstadoReintegros(fechaInicial, codigoCajero, fechaHisto);
    }
    
    public Reintegros findByPrimayKey(Integer codigoCajero, Date fechaSistema, Integer talon, Date fechaHisto) throws EntityServicioExcepcion {
        return reintegrosServicio.findByPrimayKey(codigoCajero,fechaSistema,talon,fechaHisto);
    }
}
