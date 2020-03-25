// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.transportadora.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.Transportadora;
import com.davivienda.sara.base.BaseEntityServicio;

public class TransportadoraServicio extends BaseEntityServicio<Transportadora>
{
    public TransportadoraServicio(final EntityManager em) {
        super(em, Transportadora.class);
    }
    
    @Override
    public Transportadora actualizar(final Transportadora objetoModificado) throws EntityServicioExcepcion {
        Transportadora objetoActual = super.buscar(objetoModificado.getIdTransportadora());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getIdTransportadora());
        }
        else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    @Override
    public void borrar(final Transportadora entity) throws EntityServicioExcepcion {
        final Transportadora objetoActual = super.buscar(entity.getIdTransportadora());
        super.borrar(objetoActual);
    }
}
