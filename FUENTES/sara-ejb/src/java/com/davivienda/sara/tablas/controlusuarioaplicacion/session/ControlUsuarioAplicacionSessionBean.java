package com.davivienda.sara.tablas.controlusuarioaplicacion.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.ControlUsuarioAplicacion;
import com.davivienda.sara.tablas.controlusuarioaplicacion.servicio.ControlUsuarioAplicacionServicio;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * ControlUsuarioAplicacionSessionLocal - 06/09/2017 Descripción : Versión : 1.0
 *
 * @author jediazs@co.ibm.com IBM 2017
 */
@Stateless
public class ControlUsuarioAplicacionSessionBean extends BaseAdministracionTablas<ControlUsuarioAplicacion> implements ControlUsuarioAplicacionSessionLocal {

    @PersistenceContext
    private EntityManager em;
    ControlUsuarioAplicacionServicio usuarioAplicacionservicio;

    @PostConstruct
    public void postConstructor() {
        usuarioAplicacionservicio = new ControlUsuarioAplicacionServicio(em);
        super.servicio = usuarioAplicacionservicio;

    }

    @SuppressWarnings("unchecked")
    public Collection<ControlUsuarioAplicacion> getTodos(Integer pagina, Integer regsPorPagina) throws Exception {
        return servicio.consultarPorQuery(pagina, regsPorPagina, "ControlUsuarioAplicacion.todos");
    }

    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
    public ControlUsuarioAplicacion getControlUsuarioAplicacion(String usuario) throws EntityServicioExcepcion {
        return usuarioAplicacionservicio.getControlUsuarioAplicacion(usuario);
    }

    public void eliminarDatosControlUsuario() {
        usuarioAplicacionservicio.eliminarDatosControlUsuario();
    }

    public void eliminarDatosControlXUsuario(String usuario) {
        usuarioAplicacionservicio.eliminarDatosControlXUsuario(usuario);
    }

}
