package com.davivienda.sara.tablas.reintegros.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Reintegros;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

/**
 * DiarioElectronicoSessionLocal - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface ReintegrosSessionLocal extends AdministracionTablasInterface<Reintegros> {

    /**
     * Retorna los Reintegros de la fecha dada
        * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
    public Collection<Reintegros> getReintegros( Date fechaInicial ,Date fechaFinal,Integer codigCajero, Date fechaHisto) throws EntityServicioExcepcion ;
    public Collection<Reintegros> getReintegros(Date fecha, Integer codigCajero, Date fechaHisto) throws EntityServicioExcepcion;
    
           /** Retorna un objeto Reintegro que cumpla con los parámetros
     * @param codigoCajero
     * @param fechaProceso
     * @param numeroTransaccion
     * @return Reintegros
     * @throws EntityServicioExcepcion 
     */
    public Reintegros getReintegroXLlave(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, Date fechaHisto) throws EntityServicioExcepcion ;
        
    public Reintegros getReintegroXCuentaValor(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion,String numeroCuenta,Long valor, Date fechaHisto) throws EntityServicioExcepcion;
    
    public Collection<Object> getReintegrosXCajero(Date fechaInicial ,Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion ;
    
    public void actualizarEstadoReintegros (Date fechaInicial,Integer codigoCajero , Date fechaHisto) throws EntityServicioExcepcion ;

     public Reintegros findByPrimayKey(Integer codigoCajero, Date fechaSistema, Integer talon, Date fechaHisto) throws EntityServicioExcepcion;
}