// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.ubicacion.session;

import java.util.Collection;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.ubicacion.servicio.UbicacionServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Ubicacion;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class UbicacionSessionBean extends BaseAdministracionTablas<Ubicacion> implements UbicacionSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    
    @PostConstruct
    public void postConstructor() {
        super.servicio = new UbicacionServicio(this.em);
    }
    
    @Override
    public Collection<Ubicacion> getTodos(final Integer pagina, final Integer regsPorPagina) throws Exception {
        return this.servicio.consultarPorQuery(pagina, regsPorPagina, "Ubicacion.Todos");
    }
}
