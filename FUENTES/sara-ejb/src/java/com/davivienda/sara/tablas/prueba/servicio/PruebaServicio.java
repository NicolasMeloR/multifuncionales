/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */

package com.davivienda.sara.tablas.prueba.servicio;

import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Prueba2;
import javax.persistence.EntityManager;

/**
 * ZonaServicio
 * Descripción : 
 * Fecha       : 14/03/2008 11:38:06 AM
 * @author     : jjvargas
 **/
public class PruebaServicio  extends BaseEntityServicio<Prueba2> {

    public PruebaServicio(EntityManager em) {
        super(em, Prueba2.class);
    }
    

    
   
    
    

}
