/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.tablas.ofimulti.servicio;

import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Oficinamulti;
import javax.persistence.EntityManager;

/**
 *
 * @author P-CCHAPA
 */
public class OfimultiServicio extends BaseEntityServicio<Oficinamulti>  {

    public OfimultiServicio(EntityManager em) {
        super(em, Oficinamulti.class);
    }
    
    @Override
    public Oficinamulti actualizar(Oficinamulti objetoModificado) throws EntityServicioExcepcion {
    
        Oficinamulti objetoActual = super.buscar(objetoModificado.getCodigooficinamulti());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getCodigooficinamulti());
        } else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }        
        return objetoActual;
    }

    @Override
    public void borrar(Oficinamulti entity) throws EntityServicioExcepcion {
         Oficinamulti objetoActual = super.buscar(entity.getCodigooficinamulti());
        super.borrar(objetoActual);
  
    }
    

}