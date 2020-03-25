// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.controlusuarioaplicacion.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.controlusuarioaplicacion.servicio.ControlUsuarioAplicacionServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.ControlUsuarioAplicacion;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class ControlUsuarioAplicacionSessionBean extends BaseAdministracionTablas<ControlUsuarioAplicacion> implements ControlUsuarioAplicacionSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    ControlUsuarioAplicacionServicio usuarioAplicacionservicio;
    
    @PostConstruct
    public void postConstructor() {
        this.usuarioAplicacionservicio = new ControlUsuarioAplicacionServicio(this.em);
        super.servicio = this.usuarioAplicacionservicio;
    }
    
    @Override
    public Collection<ControlUsuarioAplicacion> getTodos(final Integer pagina, final Integer regsPorPagina) throws Exception {
        return this.servicio.consultarPorQuery(pagina, regsPorPagina, "ControlUsuarioAplicacion.todos");
    }
    
    @Override
    public ControlUsuarioAplicacion getControlUsuarioAplicacion(final String usuario) throws EntityServicioExcepcion {
        return this.usuarioAplicacionservicio.getControlUsuarioAplicacion(usuario);
    }
    
    @Override
    public void eliminarDatosControlUsuario() {
        this.usuarioAplicacionservicio.eliminarDatosControlUsuario();
    }
    
    @Override
    public void eliminarDatosControlXUsuario(final String usuario) {
        this.usuarioAplicacionservicio.eliminarDatosControlXUsuario(usuario);
    }
}
