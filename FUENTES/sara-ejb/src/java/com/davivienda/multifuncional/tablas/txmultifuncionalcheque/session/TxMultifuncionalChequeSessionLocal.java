package com.davivienda.multifuncional.tablas.txmultifuncionalcheque.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Txmultifuncionalcheque;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import javax.ejb.Local;

/**
 * TxMultifuncionalChequeSessionLocal 
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-MDRUIZ
 * Davivienda 2011
 */
@Local
public interface TxMultifuncionalChequeSessionLocal extends AdministracionTablasInterface<Txmultifuncionalcheque> {

    /**
     * Retorna Retorna la colección de transacciones realizadas por el cajero <code>codigoCajero</code> enla fecha <code>fechaProceso</code>
     * @return Collection<TransTxmultifuncionalchequeaccion> 
     * @throws EntityServicioExcepcion 
     * @param codigoCajero
     * @param fechaInicial 
     * @param fechaFinal 
     */
    public Collection<Txmultifuncionalcheque> getColeccionTxCheque(Integer codigoCajero, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion;

    /**
     * Retorna Retorna la colección de transacciones realizadas por el cajero <code>codigoCajero</code> enla fecha <code>fechaProceso</code>
     * @return Collection<Txmultifuncionalcheque> 
     * @throws EntityServicioExcepcion 
     * @param codigoCajero
     * @param fecha 
     */
    
    public Collection<Txmultifuncionalcheque> getColeccionTxCheque(Integer codigoCajero, Date fecha) throws EntityServicioExcepcion;

    /**
     * Retorna un objeto Transaccion que cumpla con los parámetros
     * @param codigoCajero
     * @param fechaProceso
     * @param numeroTransaccion
     * @return Transaccion
     * @throws EntityServicioExcepcion 
     */
    public Txmultifuncionalcheque getTxCheque(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion) throws EntityServicioExcepcion;
    
    /**
     * Retorna un objeto Transaccion que cumpla con los parámetros
     * @param codigoCajero
     * @param fechaInicial 
     * @param fechaFinal 
     * @param numeroTransaccion 
     * @param criterios Map con el conjunto de paramtros extras para la consulta
     * @return Collection<Transaccion>
     * @throws EntityServicioExcepcion
     */
    public Collection<Txmultifuncionalcheque> getColeccionTxCheque(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer numeroTransaccion, String cuenta) throws EntityServicioExcepcion ;
    
    
    
    
}
