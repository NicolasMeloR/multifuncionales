/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.sara.tablas.confmodulosaplicacion.session;



import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.tablas.confmodulosaplicacion.servicio.ConfModulosAplicacionServicio;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jjvargas
 */

@Stateless
public class ConfModulosAplicacionBean extends BaseAdministracionTablas<ConfModulosAplicacion> implements ConfModulosAplicacionLocal {
    

   
    @PersistenceContext
    private EntityManager em;
    private ConfModulosAplicacionServicio confModulosAplicacionServicio;

    @PostConstruct
    public void postConstructor() {
        confModulosAplicacionServicio = new ConfModulosAplicacionServicio(em);
        super.servicio = confModulosAplicacionServicio;
        
    }

    @SuppressWarnings("unchecked")
    public Collection<ConfModulosAplicacion> getTodos(Integer pagina, Integer regsPorPagina) throws Exception {
        return servicio.consultarPorQuery(pagina, regsPorPagina, "ConfModulosAplicacion.All");
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
}
