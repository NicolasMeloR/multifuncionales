package com.davivienda.multifuncional.tablas.tipomovimientocuadremulti.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import com.davivienda.sara.entitys.Tipomovimientocuadremulti;
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

public class TipoMovimientoCuadreMultiServicio extends BaseEntityServicio<Tipomovimientocuadremulti> implements AdministracionTablasInterface<Tipomovimientocuadremulti> {

    
    public TipoMovimientoCuadreMultiServicio(EntityManager em) {
        super(em, Tipomovimientocuadremulti.class);
    }
    
        /**
     * Retorna todos los entitys de TiposMovimientoCuadre
     * @return Collection de getTiposMovimientoCuadre
     */
    public Collection<Tipomovimientocuadremulti> getTiposMovimientoCuadre() {
       //return super.buscarTodos();
        return super.getTodos();
    }
    
    

   
    

}
