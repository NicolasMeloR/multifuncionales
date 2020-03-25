package com.davivienda.sara.tablas.ubicacion.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.entitys.Ubicacion;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;

/**
 * UbicacionServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class UbicacionServicio extends BaseEntityServicio<Ubicacion> implements AdministracionTablasInterface<Ubicacion> {

    public UbicacionServicio(EntityManager em) {
        super(em, Ubicacion.class);
    }
    
     @Override
    public Ubicacion actualizar(Ubicacion objetoModificado) throws EntityServicioExcepcion {
    
        Ubicacion objetoActual = super.buscar(objetoModificado.getCodigoUbicacion());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getCodigoUbicacion());
        } else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }        
        return objetoActual;
    }

    @Override
    public void borrar(Ubicacion entity) throws EntityServicioExcepcion {
         Ubicacion objetoActual = super.buscar(entity.getCodigoUbicacion());
        super.borrar(objetoActual);
  
    }

}
