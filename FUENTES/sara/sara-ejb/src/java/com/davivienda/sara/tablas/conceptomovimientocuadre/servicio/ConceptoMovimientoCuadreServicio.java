// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.conceptomovimientocuadre.servicio;

import java.util.Collection;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.ConceptoMovimientoCuadre;
import com.davivienda.sara.base.BaseEntityServicio;

public class ConceptoMovimientoCuadreServicio extends BaseEntityServicio<ConceptoMovimientoCuadre> implements AdministracionTablasInterface<ConceptoMovimientoCuadre>
{
    public ConceptoMovimientoCuadreServicio(final EntityManager em) {
        super(em, ConceptoMovimientoCuadre.class);
    }
    
    public Collection<ConceptoMovimientoCuadre> getConceptosMovimientoCuadre() {
        return super.getTodos();
    }
}
