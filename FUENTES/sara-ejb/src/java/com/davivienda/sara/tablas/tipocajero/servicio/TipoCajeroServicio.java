package com.davivienda.sara.tablas.tipocajero.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.TipoCajero;
import javax.persistence.EntityManager;

/**
 * TipoCajeroServicio - 21/08/2008
 * Descripci�n : Servicio para la administraci�n de datos en la tabla TipoCajero
 * Versi�n : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class TipoCajeroServicio extends BaseEntityServicio<TipoCajero> implements AdministracionTablasInterface<TipoCajero> {

    
    public TipoCajeroServicio(EntityManager em) {
        super(em, TipoCajero.class);
    }

    @Override
    public TipoCajero actualizar(TipoCajero objeto) throws EntityServicioExcepcion {
        TipoCajero objetoActual = super.buscar(objeto.getCodigoTipoCajero());
        String accion = (objetoActual == null) ? "NUEVO" : "ACTUALIZAR";
        if (accion.equals("NUEVO")) {
            // Es nuevo y debo asociar las relaciones
            super.adicionar(objeto);
            objetoActual = super.buscar(objeto.getCodigoTipoCajero());
        } else {
            // Se actualizan solo datos
            objetoActual = objetoActual.actualizarEntity(objeto);
            super.actualizar(objetoActual);
        }
        return objetoActual;
    }

    @Override
    public void borrar(TipoCajero entity) throws EntityServicioExcepcion {
        TipoCajero confAccesoAplicacion = super.buscar(entity.getCodigoTipoCajero());
        super.borrar(confAccesoAplicacion);
        confAccesoAplicacion = super.buscar(entity.getCodigoTipoCajero());
    }

}
