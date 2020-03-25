// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.regional.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.Regional;
import com.davivienda.sara.base.BaseEntityServicio;

public class RegionalServicio extends BaseEntityServicio<Regional>
{
    public RegionalServicio(final EntityManager em) {
        super(em, Regional.class);
    }
    
    @Override
    public Regional actualizar(final Regional objetoModificado) throws EntityServicioExcepcion {
        Regional objetoActual = super.buscar(objetoModificado.getIdRegional());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getIdRegional());
        }
        else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    @Override
    public void borrar(final Regional entity) throws EntityServicioExcepcion {
        final Regional objetoActual = super.buscar(entity.getIdRegional());
        super.borrar(objetoActual);
    }
}
