// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.zona.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.Zona;
import com.davivienda.sara.base.BaseEntityServicio;

public class ZonaServicio extends BaseEntityServicio<Zona>
{
    public ZonaServicio(final EntityManager em) {
        super(em, Zona.class);
    }
    
    @Override
    public Zona actualizar(final Zona objetoModificado) throws EntityServicioExcepcion {
        Zona objetoActual = super.buscar(objetoModificado.getIdZona());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getIdZona());
        }
        else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    @Override
    public void borrar(final Zona entity) throws EntityServicioExcepcion {
        final Zona objetoActual = super.buscar(entity.getIdZona());
        super.borrar(objetoActual);
    }
}
