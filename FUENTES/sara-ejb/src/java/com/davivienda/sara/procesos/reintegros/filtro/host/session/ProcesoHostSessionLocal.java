/*
 * ProcesoCuadreCifrasSessionLocal.java
 *
 * Created on 25/09/2007, 09:32:24 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.davivienda.sara.procesos.reintegros.filtro.host.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;

import java.util.Calendar;
import javax.ejb.Local;
import java.io.FileNotFoundException;

/**
 *
 * @author jjvargas
 */
@Local
public interface ProcesoHostSessionLocal {
    /**
     * Realiza el cargue de el archivo hostatm
     * en el parámetro fecha
     *
     * @param fecha 
     * @return 
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion 
     */
    public Integer CargarArchivo(Calendar fecha) throws  EntityServicioExcepcion,FileNotFoundException, IllegalArgumentException ;
    
}
