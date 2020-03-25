/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */

package com.davivienda.sara.tablas.zona.servicio;

import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Zona;
import javax.persistence.EntityManager;

/**
 * ZonaServicio
 * Descripción : 
 * Fecha       : 14/03/2008 11:38:06 AM
 * @author     : jjvargas
 **/
public class ZonaServicio  extends BaseEntityServicio<Zona> {

    public ZonaServicio(EntityManager em) {
        super(em, Zona.class);
    }
    

    
    @Override
    public Zona actualizar(Zona objetoModificado) throws EntityServicioExcepcion {
    
        Zona objetoActual = super.buscar(objetoModificado.getIdZona());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getIdZona());
        } else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }        
        return objetoActual;
    }

    @Override
    public void borrar(Zona entity) throws EntityServicioExcepcion {
         Zona objetoActual = super.buscar(entity.getIdZona());
        super.borrar(objetoActual);
  
    }
    
    

}
