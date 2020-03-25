package com.davivienda.multifuncional.tablas.txmultifuncionalcheque.session;

import com.davivienda.multifuncional.tablas.txmultifuncionalcheque.servicio.TxMultifuncionalChequeServicio;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Txmultifuncionalcheque;
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
public class TxMultifuncionalChequeSessionBean extends BaseAdministracionTablas<Txmultifuncionalcheque> implements TxMultifuncionalChequeSessionLocal {

    @PersistenceContext
    private EntityManager em;
    private TxMultifuncionalChequeServicio transaccionServicio;

    @PostConstruct
    public void postConstructor() {
        transaccionServicio = new TxMultifuncionalChequeServicio(em);
        super.servicio = transaccionServicio;

    }

    public Collection<Txmultifuncionalcheque> getColeccionTxCheque(Integer codigoCajero, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {
        return transaccionServicio.getColeccionTxCheque(codigoCajero,fechaInicial, fechaFinal);
    }

    public Collection<Txmultifuncionalcheque> getColeccionTxCheque(Integer codigoCajero, Date fecha) throws EntityServicioExcepcion {
        return getColeccionTxCheque(codigoCajero, fecha);
    }

    public Txmultifuncionalcheque getTxCheque(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion) throws EntityServicioExcepcion {
        return transaccionServicio.getTxCheque(codigoCajero, fechaProceso, numeroTransaccion);
    }

    public Collection<Txmultifuncionalcheque> getColeccionTxCheque(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer numeroTransaccion, String cuenta) throws EntityServicioExcepcion {
        return transaccionServicio.getColeccionTxCheque(codigoCajero, fechaInicial, fechaFinal, numeroTransaccion, cuenta);
    }

   
}
