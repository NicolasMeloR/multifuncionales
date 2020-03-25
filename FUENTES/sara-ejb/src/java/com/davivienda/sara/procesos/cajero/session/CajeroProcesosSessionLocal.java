/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operaci�n Cajeros Autom�ticos
 * Versi�n  1.0
 */
package com.davivienda.sara.procesos.cajero.session;


import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.ejb.Local;

import com.davivienda.sara.entitys.Cajero;

/**
 *
 * @author jjvargas
 */
@Local
public interface CajeroProcesosSessionLocal {

    

    public Cajero actualizarCajero(Cajero objeto)  throws EntityServicioExcepcion ;

 
    
    }
