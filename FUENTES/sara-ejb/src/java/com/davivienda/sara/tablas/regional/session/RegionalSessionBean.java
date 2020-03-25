/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.sara.tablas.regional.session;



import com.davivienda.sara.entitys.Regional;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.tablas.regional.servicio.RegionalServicio;
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
public class RegionalSessionBean extends BaseAdministracionTablas<Regional> implements RegionalSessionLocal {
    

   
    @PersistenceContext
    private EntityManager em;
    private RegionalServicio regionalservicio;

    @PostConstruct
    public void postConstructor() {
        regionalservicio = new RegionalServicio(em);
        super.servicio = regionalservicio;
        
    }

    @SuppressWarnings("unchecked")
    public Collection<Regional> getTodos(Integer pagina, Integer regsPorPagina) throws Exception {
        return servicio.consultarPorQuery(pagina, regsPorPagina, "Regional.Todos");
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
}
