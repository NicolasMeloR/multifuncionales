package com.davivienda.sara.tablas.transaccion.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
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
public interface TransaccionSessionLocal extends AdministracionTablasInterface<Transaccion> {

    /**
     * Retorna Retorna la colección de transacciones realizadas por el cajero <code>codigoCajero</code> enla fecha <code>fechaProceso</code>
     * @return Collection<Transaccion> 
     * @throws EntityServicioExcepcion 
     * @param codigoCajero
     * @param fechaInicial 
     * @param fechaFinal 
     */
    public Collection<Transaccion> getColeccionTransaccion(Integer codigoCajero, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion;

    /**
     * Retorna Retorna la colección de transacciones realizadas por el cajero <code>codigoCajero</code> enla fecha <code>fechaProceso</code>
     * @return Collection<Transaccion> 
     * @throws EntityServicioExcepcion 
     * @param codigoCajero
     * @param fecha 
     */
    public Collection<Transaccion> getColeccionTransaccion(Integer codigoCajero, Date fecha) throws EntityServicioExcepcion;

    /**
     * Retorna un objeto Transaccion que cumpla con los parámetros
     * @param codigoCajero
     * @param fechaProceso
     * @param numeroTransaccion
     * @return Transaccion
     * @throws EntityServicioExcepcion 
     */
    public Transaccion getTransaccion(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion) throws EntityServicioExcepcion;
    
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
    public Collection<Transaccion> getColeccionTransaccion(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer numeroTransaccion, Map criterios) throws EntityServicioExcepcion ;
    
    
    
    
}
