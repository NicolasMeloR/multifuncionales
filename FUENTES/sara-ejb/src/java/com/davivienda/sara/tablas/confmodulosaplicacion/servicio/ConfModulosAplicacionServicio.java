/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */

package com.davivienda.sara.tablas.confmodulosaplicacion.servicio;

import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * ZonaServicio
 * Descripción : 
 * Fecha       : 14/03/2008 11:38:06 AM
 * @author     : jjvargas
 **/
public class ConfModulosAplicacionServicio  extends BaseEntityServicio<ConfModulosAplicacion> {

    public ConfModulosAplicacionServicio(EntityManager em) {
        super(em, ConfModulosAplicacion.class);
    }
    

    
    @Override
    public ConfModulosAplicacion actualizar(ConfModulosAplicacion objetoModificado) throws EntityServicioExcepcion {
    
        ConfModulosAplicacion objetoActual = super.buscar(objetoModificado.getConfModulosAplicacionPK());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getConfModulosAplicacionPK());
        } else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }        
        return objetoActual;
    }

    @Override
    public void borrar(ConfModulosAplicacion entity) throws EntityServicioExcepcion {
         ConfModulosAplicacion objetoActual = super.buscar(entity.getConfModulosAplicacionPK());
        super.borrar(objetoActual);
  
    }
    
    public ConfModulosAplicacion findByAtributo(String atributo){
        Query query = em.createNamedQuery("ConfModulosAplicacion.findByAtributo");
        query.setParameter("atributo",atributo);
        ConfModulosAplicacion confModulosAplicacion =(ConfModulosAplicacion) query.getSingleResult();
        return confModulosAplicacion;
    }
}
