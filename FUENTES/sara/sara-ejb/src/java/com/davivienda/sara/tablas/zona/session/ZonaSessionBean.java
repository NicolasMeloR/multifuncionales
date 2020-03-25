// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.zona.session;

import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.zona.servicio.ZonaServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Zona;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class ZonaSessionBean extends BaseAdministracionTablas<Zona> implements ZonaSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private ZonaServicio zonaservicio;
    
    @PostConstruct
    public void postConstructor() {
        this.zonaservicio = new ZonaServicio(this.em);
        super.servicio = this.zonaservicio;
    }
}
