// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.prueba.servicio;

import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.Prueba2;
import com.davivienda.sara.base.BaseEntityServicio;

public class PruebaServicio extends BaseEntityServicio<Prueba2>
{
    public PruebaServicio(final EntityManager em) {
        super(em, Prueba2.class);
    }
}
