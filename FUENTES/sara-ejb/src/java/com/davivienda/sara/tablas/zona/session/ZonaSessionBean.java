/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.sara.tablas.zona.session;



import com.davivienda.sara.entitys.Zona;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.tablas.zona.servicio.ZonaServicio;
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
public class ZonaSessionBean extends BaseAdministracionTablas<Zona> implements ZonaSessionLocal {
    

   
    @PersistenceContext
    private EntityManager em;
    private ZonaServicio zonaservicio;

    @PostConstruct
    public void postConstructor() {
        zonaservicio = new ZonaServicio(em);
        super.servicio = zonaservicio;
        
    }

  
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
}
