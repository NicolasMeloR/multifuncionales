package com.davivienda.sara.tablas.occa.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.entitys.Occa;
import javax.persistence.EntityManager;

/**
 * OccaServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class OccaServicio extends BaseEntityServicio<Occa>  {

    public OccaServicio(EntityManager em) {
        super(em, Occa.class);
    }
    
    @Override
    public Occa actualizar(Occa objetoModificado) throws EntityServicioExcepcion {
    
        Occa objetoActual = super.buscar(objetoModificado.getCodigoOcca());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getCodigoOcca());
        } else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }        
        return objetoActual;
    }

    @Override
    public void borrar(Occa entity) throws EntityServicioExcepcion {
         Occa objetoActual = super.buscar(entity.getCodigoOcca());
        super.borrar(objetoActual);
  
    }
    

}
