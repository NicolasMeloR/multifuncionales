package com.davivienda.multifuncional.tablas.txmultifuncionalefectivo.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

/**
 * TransaccionSessionLocal - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface TxMultifuncionalEfectivoSessionLocal extends AdministracionTablasInterface<Txmultifuncionalefectivo> {

    /**
     * Retorna Retorna la colección de transacciones realizadas por el cajero <code>codigoCajero</code> enla fecha <code>fechaProceso</code>
     * @return Collection<Transaccion> 
     * @throws EntityServicioExcepcion 
     * @param codigoCajero
     * @param fechaInicial 
     * @param fechaFinal 
     */
    public Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion;

    /**
     * Retorna Retorna la colección de transacciones realizadas por el cajero <code>codigoCajero</code> enla fecha <code>fechaProceso</code>
     * @return Collection<Transaccion> 
     * @throws EntityServicioExcepcion 
     * @param codigoCajero
     * @param fecha 
     */
    public Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(Integer codigoCajero, Date fecha, Date fechaHisto) throws EntityServicioExcepcion;

    /**
     * Retorna un objeto Transaccion que cumpla con los parámetros
     * @param codigoCajero
     * @param fechaProceso
     * @param numeroTransaccion
     * @return Transaccion
     * @throws EntityServicioExcepcion 
     */
    public Txmultifuncionalefectivo getTxEfectivo(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, Date fechaHisto) throws EntityServicioExcepcion;
    
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
    public Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer numeroTransaccion, String cuenta, Date fechaHisto) throws EntityServicioExcepcion ;
    
    
    public void guardarArchivoMultifuncionalEfectivo(Integer codigoCajero) throws EntityServicioExcepcion;
    
    public void cargarDiariosMultiEfectivo() throws EntityServicioExcepcion;
    
    public void cargarDiariosMultiCheque() throws EntityServicioExcepcion;
    
    public void cargarLogEventos() throws EntityServicioExcepcion ;
     
    
    
    
}
