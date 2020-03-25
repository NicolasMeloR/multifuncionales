// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.ofimulti.session;

import java.util.Collection;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.ofimulti.servicio.OfimultiServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Oficinamulti;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class OfimultiSessionBean extends BaseAdministracionTablas<Oficinamulti> implements OfimultiSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private OfimultiServicio ofimultiservicio;
    
    @PostConstruct
    public void postConstructor() {
        this.ofimultiservicio = new OfimultiServicio(this.em);
        super.servicio = this.ofimultiservicio;
    }
    
    @Override
    public Collection<Oficinamulti> getTodos(final Integer pagina, final Integer regsPorPagina) throws Exception {
        return this.servicio.consultarPorQuery(pagina, regsPorPagina, "Oficinamulti.Todos");
    }
}
