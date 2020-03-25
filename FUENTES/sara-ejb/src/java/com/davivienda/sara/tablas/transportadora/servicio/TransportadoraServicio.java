package com.davivienda.sara.tablas.transportadora.servicio;

import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Transportadora;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.entitys.Transportadora;
import javax.persistence.EntityManager;

/**
 * TransportadoraServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class TransportadoraServicio extends BaseEntityServicio<Transportadora>  {

    public TransportadoraServicio(EntityManager em) {
        super(em, Transportadora.class);
    }

    @Override
    public Transportadora actualizar(Transportadora objetoModificado) throws EntityServicioExcepcion {
    
        Transportadora objetoActual = super.buscar(objetoModificado.getIdTransportadora());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getIdTransportadora());
        } else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }        
        return objetoActual;
    }

    @Override
    public void borrar(Transportadora entity) throws EntityServicioExcepcion {
         Transportadora objetoActual = super.buscar(entity.getIdTransportadora());
        super.borrar(objetoActual);
  
    }
    
    

    
}
