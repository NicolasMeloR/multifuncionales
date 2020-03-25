/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.estadisticas;

import javax.ejb.Local;

/**
 * EstadisticasGeneralesSessionLocal - 1/09/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface EstadisticasGeneralesSessionLocal {
    
    

    public java.util.Collection<com.davivienda.sara.entitys.transaccion.CantidadTransaccionesBean> getTransaccionesRealizadasPorCajero(java.lang.Integer pagina, java.lang.Integer regsPorPagina, java.lang.Integer codigoCajeroInicial, java.lang.Integer codigoCajeroFinal, java.util.Date fechaInicial, java.util.Date fechaFinal) throws com.davivienda.sara.base.exception.EntityServicioExcepcion, java.lang.IllegalArgumentException;
    
}
