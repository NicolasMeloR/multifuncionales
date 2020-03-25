// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.confmodulosaplicacion.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.base.BaseEntityServicio;

public class ConfModulosAplicacionServicio extends BaseEntityServicio<ConfModulosAplicacion>
{
    public ConfModulosAplicacionServicio(final EntityManager em) {
        super(em, ConfModulosAplicacion.class);
    }
    
    @Override
    public ConfModulosAplicacion actualizar(final ConfModulosAplicacion objetoModificado) throws EntityServicioExcepcion {
        ConfModulosAplicacion objetoActual = super.buscar(objetoModificado.getConfModulosAplicacionPK());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getConfModulosAplicacionPK());
        }
        else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    @Override
    public void borrar(final ConfModulosAplicacion entity) throws EntityServicioExcepcion {
        final ConfModulosAplicacion objetoActual = super.buscar(entity.getConfModulosAplicacionPK());
        super.borrar(objetoActual);
    }
}
