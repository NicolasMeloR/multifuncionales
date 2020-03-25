// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.servicioaplicacion.servicio;

import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.seguridad.ServicioAplicacion;
import com.davivienda.sara.base.BaseEntityServicio;

public class ServicioAplicacionServicio extends BaseEntityServicio<ServicioAplicacion>
{
    public ServicioAplicacionServicio(final EntityManager em) {
        super(em, ServicioAplicacion.class);
    }
}
