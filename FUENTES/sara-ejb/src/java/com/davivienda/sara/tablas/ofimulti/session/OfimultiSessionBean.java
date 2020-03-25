package com.davivienda.sara.tablas.ofimulti.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.entitys.Oficinamulti;
import com.davivienda.sara.tablas.ofimulti.servicio.OfimultiServicio;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * OficinamultiSessionBean - 01/06/2011
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-CCHAPA
 */

@Stateless
public class OfimultiSessionBean extends BaseAdministracionTablas<Oficinamulti> implements OfimultiSessionLocal {
    
    @PersistenceContext
    private EntityManager em;
    private OfimultiServicio ofimultiservicio;


    @PostConstruct
    public void postConstructor() {
             
         ofimultiservicio = new OfimultiServicio(em);
         super.servicio = ofimultiservicio;
    }
    
    @SuppressWarnings("unchecked")
      public Collection<Oficinamulti> getTodos(Integer pagina, Integer regsPorPagina) throws Exception {
        return servicio.consultarPorQuery(pagina, regsPorPagina, "Oficinamulti.Todos");
    }
    
 
}
