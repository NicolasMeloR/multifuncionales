package com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

/**
 * DiarioElectronicoSessionLocal - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author p-cchapa
 * Davivienda 2011 
 */
@Local
public interface ReintegrosMultiEfectivoSessionLocal extends AdministracionTablasInterface<Reintegrosmultiefectivo> {

    /**
     * Retorna los Reintegros de la fecha dada
        * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
    public Collection<Reintegrosmultiefectivo> getReintegros( Date fechaInicial ,Date fechaFinal,Integer codigCajero, Date fechaHisto) throws EntityServicioExcepcion ;
    
           /** Retorna un objeto Reintegro que cumpla con los parámetros
     * @param codigoCajero
     * @param fechaProceso
     * @param numeroTransaccion
     * @return Reintegros
     * @throws EntityServicioExcepcion 
     */
    public Reintegrosmultiefectivo getReintegroXLlave(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, Date fechaHisto) throws EntityServicioExcepcion ;
    
    
    public Reintegrosmultiefectivo getReintegroXCuentaValor(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion,String numeroCuenta,Long valor, Date fechaHisto) throws EntityServicioExcepcion;
     
}