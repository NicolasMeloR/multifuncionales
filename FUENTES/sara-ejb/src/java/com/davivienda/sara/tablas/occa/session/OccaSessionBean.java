package com.davivienda.sara.tablas.occa.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.entitys.Occa;
import com.davivienda.sara.tablas.occa.servicio.OccaServicio;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/**
 * OccaSessionBean - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Stateless
public class OccaSessionBean extends BaseAdministracionTablas<Occa> implements OccaSessionLocal {
    
    @PersistenceContext
    private EntityManager em;
    private OccaServicio occaservicio;


    @PostConstruct
    public void postConstructor() {
             
         occaservicio = new OccaServicio(em);
         super.servicio = occaservicio;
    }
     @SuppressWarnings("unchecked")
    public Collection<Occa> getTodos(Integer pagina, Integer regsPorPagina) throws Exception {
        return servicio.consultarPorQuery(pagina, regsPorPagina, "Occa.Todos");
    }
    
 
}
