// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.ofimulti.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.Oficinamulti;
import com.davivienda.sara.base.BaseEntityServicio;

public class OfimultiServicio extends BaseEntityServicio<Oficinamulti>
{
    public OfimultiServicio(final EntityManager em) {
        super(em, Oficinamulti.class);
    }
    
    @Override
    public Oficinamulti actualizar(final Oficinamulti objetoModificado) throws EntityServicioExcepcion {
        Oficinamulti objetoActual = super.buscar(objetoModificado.getCodigooficinamulti());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getCodigooficinamulti());
        }
        else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    @Override
    public void borrar(final Oficinamulti entity) throws EntityServicioExcepcion {
        final Oficinamulti objetoActual = super.buscar(entity.getCodigooficinamulti());
        super.borrar(objetoActual);
    }
}
