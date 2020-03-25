// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.totalesestacion.servicio;

import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.TotalesEstacion;
import com.davivienda.sara.base.BaseEntityServicio;

public class TotalesEstacionServicio extends BaseEntityServicio<TotalesEstacion> implements AdministracionTablasInterface<TotalesEstacion>
{
    public TotalesEstacionServicio(final EntityManager em) {
        super(em, TotalesEstacion.class);
    }
}
