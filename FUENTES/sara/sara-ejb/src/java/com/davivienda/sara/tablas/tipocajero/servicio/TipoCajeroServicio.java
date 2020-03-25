// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.tipocajero.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.TipoCajero;
import com.davivienda.sara.base.BaseEntityServicio;

public class TipoCajeroServicio extends BaseEntityServicio<TipoCajero> implements AdministracionTablasInterface<TipoCajero>
{
    public TipoCajeroServicio(final EntityManager em) {
        super(em, TipoCajero.class);
    }
    
    @Override
    public TipoCajero actualizar(final TipoCajero objeto) throws EntityServicioExcepcion {
        TipoCajero objetoActual = super.buscar(objeto.getCodigoTipoCajero());
        final String accion = (objetoActual == null) ? "NUEVO" : "ACTUALIZAR";
        if (accion.equals("NUEVO")) {
            super.adicionar(objeto);
            objetoActual = super.buscar(objeto.getCodigoTipoCajero());
        }
        else {
            objetoActual = objetoActual.actualizarEntity(objeto);
            super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    @Override
    public void borrar(final TipoCajero entity) throws EntityServicioExcepcion {
        TipoCajero confAccesoAplicacion = super.buscar(entity.getCodigoTipoCajero());
        super.borrar(confAccesoAplicacion);
        confAccesoAplicacion = super.buscar(entity.getCodigoTipoCajero());
    }
}
