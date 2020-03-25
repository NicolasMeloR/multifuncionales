package com.davivienda.multifuncional.tablas.txcontrolmulticheque.session;

import com.davivienda.multifuncional.tablas.txcontrolmulticheque.servicio.TxControlMultiChequeServicio;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Txcontrolmulticheque;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * TxControlMultiChequeSessionBean
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-MDRUIZ 
 * Davivienda 2011
 */
@Stateless
public class TxControlMultiChequeSessionBean extends BaseAdministracionTablas<Txcontrolmulticheque> implements TxControlMultiChequeSessionLocal {

    @PersistenceContext
    private EntityManager em;
    private TxControlMultiChequeServicio transaccionServicio;

    @PostConstruct
    public void postConstructor() {
        transaccionServicio = new TxControlMultiChequeServicio(em);
        super.servicio = transaccionServicio;

    }

    public Txcontrolmulticheque getRegistroControlCheque(Long indControl) throws EntityServicioExcepcion {
        return transaccionServicio.getRegistroControlCheque(indControl);
    }

   
}
