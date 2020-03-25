package com.davivienda.sara.tablas.conceptomovimientocuadre.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.ConceptoMovimientoCuadre;
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

public class ConceptoMovimientoCuadreServicio extends BaseEntityServicio<ConceptoMovimientoCuadre> implements AdministracionTablasInterface<ConceptoMovimientoCuadre> {

    
    public ConceptoMovimientoCuadreServicio(EntityManager em) {
        super(em, ConceptoMovimientoCuadre.class);
    }
       /**
     * Retorna todos los entitys de TiposMovimientoCuadre
     * @return Collection de getTiposMovimientoCuadre
     */
    public Collection<ConceptoMovimientoCuadre> getConceptosMovimientoCuadre() {
        return super.getTodos();
    }
    

   
    

}
