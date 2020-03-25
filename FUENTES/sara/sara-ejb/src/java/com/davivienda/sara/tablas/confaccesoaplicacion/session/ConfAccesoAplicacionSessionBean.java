// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.confaccesoaplicacion.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.confaccesoaplicacion.servicio.ConfAccesoAplicacionServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class ConfAccesoAplicacionSessionBean extends BaseAdministracionTablas<ConfAccesoAplicacion> implements ConfAccesoAplicacionSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private ConfAccesoAplicacionServicio confAccesoAplicacionServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.confAccesoAplicacionServicio = new ConfAccesoAplicacionServicio(this.em);
        super.servicio = this.confAccesoAplicacionServicio;
    }
    
    @Override
    public Collection<ConfAccesoAplicacion> getTodos(final Integer pagina, final Integer regsPorPagina) throws Exception {
        return this.servicio.consultarPorQuery(pagina, regsPorPagina, "ConfAccesoAplicacion.todos");
    }
    
    @Override
    public int borrarPorUsuario(final String usuario) throws Exception {
        return this.confAccesoAplicacionServicio.borrarPorUsuario(usuario);
    }
    
    @Override
    public int getNumElementosPorUsuario(final String usuario) throws Exception {
        return this.confAccesoAplicacionServicio.getNumElementosPorUsuario(usuario);
    }
    
    @Override
    public Collection<ConfAccesoAplicacion> getElementosPorUsuario(final String usuario) throws Exception {
        return this.confAccesoAplicacionServicio.getElementosPorUsuario(usuario);
    }
    
    @Override
    public void AddBorrarRegAccesoUsuario() throws EntityServicioExcepcion {
        this.confAccesoAplicacionServicio.AddBorrarRegAccesoUsuario();
    }
}
