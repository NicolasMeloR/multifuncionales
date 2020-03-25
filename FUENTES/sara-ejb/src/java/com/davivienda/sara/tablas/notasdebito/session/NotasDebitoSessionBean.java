package com.davivienda.sara.tablas.notasdebito.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.tablas.notasdebito.servicio.NotasDebitoServicio;

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
public class NotasDebitoSessionBean extends BaseAdministracionTablas<Notasdebito> implements NotasDebitoSessionLocal {

    @PersistenceContext
    private EntityManager em;
    NotasDebitoServicio notasDebitoServicio;

    @PostConstruct
    public void postConstructor() {
        notasDebitoServicio = new NotasDebitoServicio(em);
        super.servicio = notasDebitoServicio;

    }

    /**
     * Retorna los Reintegros de la fecha dada
     *
     * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
    @SuppressWarnings("unchecked")
    public Collection<Notasdebito> getNotasDebito(Date fechaInicial, Date fechaFinal, Integer codigCajero, Date fechaHisto) throws EntityServicioExcepcion {
        return notasDebitoServicio.getNotasDebito(fechaInicial, fechaFinal, codigCajero, fechaHisto);
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
    public Notasdebito getNotasDebitoXLlave(Integer codigoCajero, Date fechaProceso, Date fechaHisto) throws EntityServicioExcepcion {
        return notasDebitoServicio.getNotasDebitoXLlave(codigoCajero, fechaProceso, fechaHisto);
    }

    public Notasdebito getNotasDebitoXCuentaValor(Integer codigoCajero, Date fechaProceso, String numeroCuenta, Long valor, Date fechaHisto) throws EntityServicioExcepcion {
        return notasDebitoServicio.getNotasDebitoXCuentaValor(codigoCajero, fechaProceso, numeroCuenta, valor, fechaHisto);
    }

    public String findByPrimayKey(Integer codigoCajero, Date fechaSistema, String numeroCuenta) throws EntityServicioExcepcion {
        return notasDebitoServicio.findByPrimayKey(codigoCajero, fechaSistema, numeroCuenta);
    }

}
