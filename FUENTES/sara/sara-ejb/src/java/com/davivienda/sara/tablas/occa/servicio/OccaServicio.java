// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.occa.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.Occa;
import com.davivienda.sara.base.BaseEntityServicio;

public class OccaServicio extends BaseEntityServicio<Occa>
{
    public OccaServicio(final EntityManager em) {
        super(em, Occa.class);
    }
    
    @Override
    public Occa actualizar(final Occa objetoModificado) throws EntityServicioExcepcion {
        Occa objetoActual = super.buscar(objetoModificado.getCodigoOcca());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getCodigoOcca());
        }
        else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    @Override
    public void borrar(final Occa entity) throws EntityServicioExcepcion {
        final Occa objetoActual = super.buscar(entity.getCodigoOcca());
        super.borrar(objetoActual);
    }
}
