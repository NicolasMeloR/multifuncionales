// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.confmodulosaplicacion.session;

import java.util.Collection;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.confmodulosaplicacion.servicio.ConfModulosAplicacionServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class ConfModulosAplicacionBean extends BaseAdministracionTablas<ConfModulosAplicacion> implements ConfModulosAplicacionLocal
{
    @PersistenceContext
    private EntityManager em;
    private ConfModulosAplicacionServicio confModulosAplicacionServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.confModulosAplicacionServicio = new ConfModulosAplicacionServicio(this.em);
        super.servicio = this.confModulosAplicacionServicio;
    }
    
    @Override
    public Collection<ConfModulosAplicacion> getTodos(final Integer pagina, final Integer regsPorPagina) throws Exception {
        return this.servicio.consultarPorQuery(pagina, regsPorPagina, "ConfModulosAplicacion.All");
    }
}
