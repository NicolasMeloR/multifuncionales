package com.davivienda.sara.tablas.reversarreintegros.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.ReintegrosReversados;
import com.davivienda.sara.tablas.reintegros.servicio.ReintegrosServicio;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class ReversarReintegrosSessionBean extends BaseAdministracionTablas<ReintegrosReversados> implements ReversarReintegrosSessionLocal {

    @PersistenceContext
    private EntityManager em;
    ReintegrosServicio reintegrosServicio;

    @PostConstruct
    public void postConstructor() {
        reintegrosServicio = new ReintegrosServicio(em);
        super.servicio = reintegrosServicio;
    }

    @Override
    public Reintegros findByPrimayKey(Integer codigoCajero, Date fechaSistema, Integer talon, Date fechaHisto) throws EntityServicioExcepcion {
        return reintegrosServicio.findByPrimayKey(codigoCajero, fechaSistema, talon, fechaHisto);
    }
}
