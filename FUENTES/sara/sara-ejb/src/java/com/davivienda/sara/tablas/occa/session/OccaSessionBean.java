// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.occa.session;

import java.util.Collection;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.occa.servicio.OccaServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Occa;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class OccaSessionBean extends BaseAdministracionTablas<Occa> implements OccaSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private OccaServicio occaservicio;
    
    @PostConstruct
    public void postConstructor() {
        this.occaservicio = new OccaServicio(this.em);
        super.servicio = this.occaservicio;
    }
    
    @Override
    public Collection<Occa> getTodos(final Integer pagina, final Integer regsPorPagina) throws Exception {
        return this.servicio.consultarPorQuery(pagina, regsPorPagina, "Occa.Todos");
    }
}
