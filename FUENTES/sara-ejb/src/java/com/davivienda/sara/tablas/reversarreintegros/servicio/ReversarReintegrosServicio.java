package com.davivienda.sara.tablas.reversarreintegros.servicio;

import com.davivienda.sara.base.BaseEntityServicio;

import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.ReintegrosHisto;
import com.davivienda.sara.entitys.ReintegrosReversados;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import javax.persistence.Query;

/**
 * OccaServicio - 21/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ReversarReintegrosServicio extends BaseEntityServicio<ReintegrosReversados> implements AdministracionTablasInterface<ReintegrosReversados> {

    public ReversarReintegrosServicio(EntityManager em) {
        super(em, ReintegrosReversados.class);

    }

    public void guardar(ReintegrosReversados reintegrosReversados) {
        em.persist(em);
    }

}
