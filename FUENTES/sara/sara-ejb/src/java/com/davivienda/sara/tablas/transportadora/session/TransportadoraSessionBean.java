// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.transportadora.session;

import java.util.Collection;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.transportadora.servicio.TransportadoraServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Transportadora;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class TransportadoraSessionBean extends BaseAdministracionTablas<Transportadora> implements TransportadoraSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private TransportadoraServicio transportadoraServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.transportadoraServicio = new TransportadoraServicio(this.em);
        super.servicio = this.transportadoraServicio;
    }
    
    @Override
    public Collection<Transportadora> getTodos(final Integer pagina, final Integer regsPorPagina) throws Exception {
        return this.servicio.consultarPorQuery(pagina, regsPorPagina, "Transportadora.Todos");
    }
}
