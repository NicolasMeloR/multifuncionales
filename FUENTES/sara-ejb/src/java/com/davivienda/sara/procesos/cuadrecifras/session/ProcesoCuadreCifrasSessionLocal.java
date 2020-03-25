/*
 * ProcesoCuadreCifrasSessionLocal.java
 *
 * Created on 25/09/2007, 09:32:24 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.davivienda.sara.procesos.cuadrecifras.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Calendar;
import javax.ejb.Local;

/**
 *
 * @author jjvargas
 */
@Local
public interface ProcesoCuadreCifrasSessionLocal {
    /**
     * Realiza la generación de los registros de Dia sgte Real y Inico Día Real para la fecha pasada
     * en el parámetro fecha
     *
     * @param fecha 
     * @return 
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion 
     */
    public Integer procesarCuadreCifras(Calendar fecha) throws EntityServicioExcepcion ;
    
}
