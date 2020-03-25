package com.davivienda.sara.tablas.confaccesoaplicacion.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.tablas.confaccesoaplicacion.servicio.ConfAccesoAplicacionServicio;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * ConfAccesoAplicacionSessionBean - 19/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Stateless
public class ConfAccesoAplicacionSessionBean extends BaseAdministracionTablas<ConfAccesoAplicacion> implements ConfAccesoAplicacionSessionLocal {

    @PersistenceContext
    private EntityManager em;
    private ConfAccesoAplicacionServicio confAccesoAplicacionServicio;

    @PostConstruct
    public void postConstructor() {
        //super.servicio = new ConfAccesoAplicacionServicio(em);
           confAccesoAplicacionServicio = new ConfAccesoAplicacionServicio(em);
         super.servicio = confAccesoAplicacionServicio;
    }

    @SuppressWarnings("unchecked")
    public Collection<ConfAccesoAplicacion> getTodos(Integer pagina, Integer regsPorPagina) throws Exception {
        return servicio.consultarPorQuery(pagina, regsPorPagina, "ConfAccesoAplicacion.todos");
    }
    public int borrarPorUsuario(String usuario) throws Exception{
         
     return confAccesoAplicacionServicio.borrarPorUsuario(usuario);
    } 
    public int getNumElementosPorUsuario(String usuario) throws Exception{
     return confAccesoAplicacionServicio.getNumElementosPorUsuario(usuario);
    } 
    
    public Collection<ConfAccesoAplicacion> getElementosPorUsuario(String usuario) throws Exception{
      return confAccesoAplicacionServicio.getElementosPorUsuario(usuario);
    }
    
      public void AddBorrarRegAccesoUsuario() throws EntityServicioExcepcion 
    {
        confAccesoAplicacionServicio.AddBorrarRegAccesoUsuario();
    }



}
