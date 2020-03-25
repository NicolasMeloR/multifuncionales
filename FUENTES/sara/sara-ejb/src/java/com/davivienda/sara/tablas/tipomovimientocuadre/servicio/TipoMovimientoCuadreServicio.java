// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.tipomovimientocuadre.servicio;

import java.util.Collection;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import com.davivienda.sara.base.BaseEntityServicio;

public class TipoMovimientoCuadreServicio extends BaseEntityServicio<TipoMovimientoCuadre> implements AdministracionTablasInterface<TipoMovimientoCuadre>
{
    public TipoMovimientoCuadreServicio(final EntityManager em) {
        super(em, TipoMovimientoCuadre.class);
    }
    
    public Collection<TipoMovimientoCuadre> getTiposMovimientoCuadre() {
        return super.getTodos();
    }
}
