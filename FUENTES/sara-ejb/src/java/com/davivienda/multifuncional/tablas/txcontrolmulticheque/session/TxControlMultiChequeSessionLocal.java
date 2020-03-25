package com.davivienda.multifuncional.tablas.txcontrolmulticheque.session;


import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Txcontrolmulticheque;
import javax.ejb.Local;

/**
 * TxControlMultiChequeSessionLocal 
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-MDRUIZ
 * Davivienda 2011 
 */
@Local
public interface TxControlMultiChequeSessionLocal extends AdministracionTablasInterface<Txcontrolmulticheque> {

    /**
     * Retorna el Registro de Control
     * @return  Txcontrolmulticheque
     * @throws EntityServicioExcepcion 
     * @param indControl
     */
    public Txcontrolmulticheque getRegistroControlCheque(Long indControl) throws EntityServicioExcepcion;
    
    
}
