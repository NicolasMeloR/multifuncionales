package com.davivienda.multifuncional.tablas.logcajeromulti.session;

import com.davivienda.multifuncional.tablas.logcajeromulti.servicio.LogCajeroMultiServicio;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Logcajeromulti;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * TxMultifuncionalChequeSessionBean
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-MDRUIZ
 * Davivienda 2011 
 */
@Stateless
public class LogCajeroMultiSessionBean extends BaseAdministracionTablas<Logcajeromulti> implements LogCajeroMultiSessionLocal {

    @PersistenceContext
    private EntityManager em;
    private LogCajeroMultiServicio logCajeroMultiServicio;

    @PostConstruct
    public void postConstructor() {
        logCajeroMultiServicio = new LogCajeroMultiServicio(em);
        super.servicio = logCajeroMultiServicio;

    }

   

    public Collection<Logcajeromulti> getColeccionLogCajeroMulti(Integer codigoCajero, Date fecha) throws EntityServicioExcepcion {
      return logCajeroMultiServicio.getColeccionLogCajeroMulti(codigoCajero, fecha);
    }

  

   
}
