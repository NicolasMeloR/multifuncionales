// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.regional.session;

import java.util.Collection;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.regional.servicio.RegionalServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Regional;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class RegionalSessionBean extends BaseAdministracionTablas<Regional> implements RegionalSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private RegionalServicio regionalservicio;
    
    @PostConstruct
    public void postConstructor() {
        this.regionalservicio = new RegionalServicio(this.em);
        super.servicio = this.regionalservicio;
    }
    
    @Override
    public Collection<Regional> getTodos(final Integer pagina, final Integer regsPorPagina) throws Exception {
        return this.servicio.consultarPorQuery(pagina, regsPorPagina, "Regional.Todos");
    }
}
