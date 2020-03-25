// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.billetajecajero.servicio;

import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.BilletajeCajero;
import com.davivienda.sara.base.BaseEntityServicio;

public class BilletajeCajeroServicio extends BaseEntityServicio<BilletajeCajero> implements AdministracionTablasInterface<BilletajeCajero>
{
    public BilletajeCajeroServicio(final EntityManager em) {
        super(em, BilletajeCajero.class);
    }
}
