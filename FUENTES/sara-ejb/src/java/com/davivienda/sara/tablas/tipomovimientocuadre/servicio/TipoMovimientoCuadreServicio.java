package com.davivienda.sara.tablas.tipomovimientocuadre.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * DiarioElectronicoServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class TipoMovimientoCuadreServicio extends BaseEntityServicio<TipoMovimientoCuadre> implements AdministracionTablasInterface<TipoMovimientoCuadre> {

    
    public TipoMovimientoCuadreServicio(EntityManager em) {
        super(em, TipoMovimientoCuadre.class);
    }
    
        /**
     * Retorna todos los entitys de TiposMovimientoCuadre
     * @return Collection de getTiposMovimientoCuadre
     */
    public Collection<TipoMovimientoCuadre> getTiposMovimientoCuadre() {
       //return super.buscarTodos();
        return super.getTodos();
    }
    
    

   
    

}
