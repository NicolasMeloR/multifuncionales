package com.davivienda.multifuncional.tablas.notasdebitomulti.session;

import com.davivienda.multifuncional.tablas.notasdebito.servicio.NotasDebitoMultiServicio;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Notasdebitomultifuncional;

import com.davivienda.sara.tablas.notasdebito.servicio.NotasDebitoServicio;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * NotasDebitoMultiSessionBean
 * Descripción : 
 * Versión : 1.0 
 *
 * @author mdruiz
 * Davivienda 2011
 */
@Stateless
public class NotasDebitoMultiSessionBean extends BaseAdministracionTablas<Notasdebitomultifuncional> implements NotasDebitoMultiSessionLocal {

    @PersistenceContext
    private EntityManager em;
    NotasDebitoMultiServicio notasDebitoServicio;

    @PostConstruct
    public void postConstructor() {
        notasDebitoServicio = new NotasDebitoMultiServicio(em);
        super.servicio = notasDebitoServicio;

    }

    public Collection<Notasdebitomultifuncional> getNotasDebito(Date fechaInicial, Date fechaFinal, Integer codigCajero, Date fechaHisto) throws EntityServicioExcepcion {
      return notasDebitoServicio.getNotasDebito( fechaInicial,  fechaFinal,  codigCajero, fechaHisto);
    }

    public Notasdebitomultifuncional getNotasDebitoXLlave(Integer codigoCajero, Date fechaProceso, Date fechaHisto) throws EntityServicioExcepcion {
        return notasDebitoServicio.getNotasDebitoXLlave(codigoCajero,fechaProceso, fechaHisto);
    }

       public Notasdebitomultifuncional getNotasDebitoXCuentaValor(Integer codigoCajero, Date fechaProceso, String numeroCuenta,Long valor,Date fechaHisto) throws EntityServicioExcepcion{
        return notasDebitoServicio.getNotasDebitoXCuentaValor( codigoCajero,  fechaProceso,  numeroCuenta,  valor, fechaHisto);
    }



     
}
