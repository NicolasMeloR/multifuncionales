/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */

package com.davivienda.sara.tablas.regional.servicio;

import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Regional;
import javax.persistence.EntityManager;

/**
 * ZonaServicio
 * Descripción : 
 * Fecha       : 14/03/2008 11:38:06 AM
 * @author     : jjvargas
 **/
public class RegionalServicio  extends BaseEntityServicio<Regional> {

    public RegionalServicio(EntityManager em) {
        super(em, Regional.class);
    }
    

    
    @Override
    public Regional actualizar(Regional objetoModificado) throws EntityServicioExcepcion {
    
        Regional objetoActual = super.buscar(objetoModificado.getIdRegional());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getIdRegional());
        } else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }        
        return objetoActual;
    }

    @Override
    public void borrar(Regional entity) throws EntityServicioExcepcion {
         Regional objetoActual = super.buscar(entity.getIdRegional());
        super.borrar(objetoActual);
  
    }
    
    

}
