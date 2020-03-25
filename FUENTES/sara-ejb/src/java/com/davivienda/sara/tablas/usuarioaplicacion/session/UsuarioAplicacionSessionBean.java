package com.davivienda.sara.tablas.usuarioaplicacion.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import com.davivienda.sara.tablas.usuarioaplicacion.servicio.UsuarioAplicacionServicio;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * UsuarioAplicacionSessionBean - 19/08/2008
 Descripción : 
 Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Stateless
public class UsuarioAplicacionSessionBean extends BaseAdministracionTablas<UsuarioAplicacion> implements UsuarioAplicacionSessionLocal {
    

   
    @PersistenceContext
    private EntityManager em;
    private UsuarioAplicacionServicio usuarioAplicacionservicio;

    @PostConstruct
    public void postConstructor() {
        usuarioAplicacionservicio = new UsuarioAplicacionServicio(em);
         super.servicio = usuarioAplicacionservicio;
        
    }

    @SuppressWarnings("unchecked")
    public Collection<UsuarioAplicacion> getTodos(Integer pagina, Integer regsPorPagina) throws Exception {
     
        return servicio.consultarPorQuery(pagina, regsPorPagina, "UsuarioAplicacion.todos");
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
}