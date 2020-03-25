// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.ubicacion.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Ubicacion;
import com.davivienda.sara.base.BaseEntityServicio;

public class UbicacionServicio extends BaseEntityServicio<Ubicacion> implements AdministracionTablasInterface<Ubicacion>
{
    public UbicacionServicio(final EntityManager em) {
        super(em, Ubicacion.class);
    }
    
    @Override
    public Ubicacion actualizar(final Ubicacion objetoModificado) throws EntityServicioExcepcion {
        Ubicacion objetoActual = super.buscar(objetoModificado.getCodigoUbicacion());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getCodigoUbicacion());
        }
        else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    @Override
    public void borrar(final Ubicacion entity) throws EntityServicioExcepcion {
        final Ubicacion objetoActual = super.buscar(entity.getCodigoUbicacion());
        super.borrar(objetoActual);
    }
}
