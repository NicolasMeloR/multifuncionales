// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.tipomovimientocuadremulti.servicio;

import java.util.Collection;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Tipomovimientocuadremulti;
import com.davivienda.sara.base.BaseEntityServicio;

public class TipoMovimientoCuadreMultiServicio extends BaseEntityServicio<Tipomovimientocuadremulti> implements AdministracionTablasInterface<Tipomovimientocuadremulti>
{
    public TipoMovimientoCuadreMultiServicio(final EntityManager em) {
        super(em, Tipomovimientocuadremulti.class);
    }
    
    public Collection<Tipomovimientocuadremulti> getTiposMovimientoCuadre() {
        return super.getTodos();
    }
}
