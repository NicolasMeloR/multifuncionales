// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.usuarioaplicacion.session;

import java.util.Collection;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.usuarioaplicacion.servicio.UsuarioAplicacionServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class UsuarioAplicacionSessionBean extends BaseAdministracionTablas<UsuarioAplicacion> implements UsuarioAplicacionSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private UsuarioAplicacionServicio usuarioAplicacionservicio;
    
    @PostConstruct
    public void postConstructor() {
        this.usuarioAplicacionservicio = new UsuarioAplicacionServicio(this.em);
        super.servicio = this.usuarioAplicacionservicio;
    }
    
    @Override
    public Collection<UsuarioAplicacion> getTodos(final Integer pagina, final Integer regsPorPagina) throws Exception {
        return this.servicio.consultarPorQuery(pagina, regsPorPagina, "UsuarioAplicacion.todos");
    }
}
