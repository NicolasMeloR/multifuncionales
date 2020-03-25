// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.prueba.session;

import java.util.Collection;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.prueba.servicio.PruebaServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Prueba2;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class PruebaSessionBean extends BaseAdministracionTablas<Prueba2> implements PruebaSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private PruebaServicio pruebaServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.pruebaServicio = new PruebaServicio(this.em);
        super.servicio = this.pruebaServicio;
    }
    
    @Override
    public Collection<Prueba2> getTodos() {
        return this.servicio.getTodos();
    }
}
