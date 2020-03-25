package com.davivienda.multifuncional.tablas.logcajeromulti.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Logcajeromulti;
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
public interface LogCajeroMultiSessionLocal extends AdministracionTablasInterface<Logcajeromulti> {

    /**
     * Retorna un objeto Logcajeromulti que cumpla con los parámetros
     * @param codigoCajero
     * @param fecha
     * @return Logcajeromulti
     * @throws EntityServicioExcepcion 
     */
    
    public Collection<Logcajeromulti> getColeccionLogCajeroMulti(Integer codigoCajero, Date fecha) throws EntityServicioExcepcion;

   
   
    
    
    
}
