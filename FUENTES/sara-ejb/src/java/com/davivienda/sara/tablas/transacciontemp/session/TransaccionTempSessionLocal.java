package com.davivienda.sara.tablas.transacciontemp.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.TransaccionTemp;
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
public interface TransaccionTempSessionLocal extends AdministracionTablasInterface<TransaccionTemp> {

    /**
     * Retorna Retorna la colección de transacciones realizadas por el cajero <code>codigoCajero</code> enla fecha <code>fechaProceso</code>
     * @return Collection<Transaccion> 
     * @throws EntityServicioExcepcion 
     * @param codigoCajero
     * @param fechaInicial 
     * @param fechaFinal 
     */
    public Collection<TransaccionTemp> getColeccionTransaccionTemp(Integer codigoCajero, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion;

    /**
     * Retorna Retorna la colección de transacciones realizadas por el cajero <code>codigoCajero</code> enla fecha <code>fechaProceso</code>
     * @return Collection<Transaccion> 
     * @throws EntityServicioExcepcion 
     * @param codigoCajero
     * @param fecha 
     */
    public Collection<TransaccionTemp> getColeccionTransaccionTemp(Integer codigoCajero, Date fecha) throws EntityServicioExcepcion;

    /**
     * Retorna un objeto Transaccion que cumpla con los parámetros
     * @param codigoCajero
     * @param fechaProceso
     * @param numeroTransaccion
     * @return Transaccion
     * @throws EntityServicioExcepcion 
     */
    public TransaccionTemp getTransaccionTemp(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion) throws EntityServicioExcepcion;
    
    
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
    public Collection<TransaccionTemp> getColeccionTransaccionTemp(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer numeroTransaccion, Map criterios) throws EntityServicioExcepcion ;
    

    public Date getFechaMinimaTx( Integer codigoCajero) throws EntityServicioExcepcion ;
    
    public void cargarCicloTempXStoreP () throws EntityServicioExcepcion;
    
    public void mantenimientoDiariosStoreP () throws EntityServicioExcepcion;
    
    public void mantenimientoDiariosBorraStoreP () throws EntityServicioExcepcion;
    
    public void cargarDiariosElectronicosXStoreP  () throws EntityServicioExcepcion;
    
    public void cargarDiariosElectronicosXStoreP_Automatico  () throws EntityServicioExcepcion;
    
    public void calcReintegrosDAuto() throws EntityServicioExcepcion ;
    
    public void descomprimirEDC() throws EntityServicioExcepcion ;
    
    public void descomprimirEDC_Automatico() throws EntityServicioExcepcion;


}
