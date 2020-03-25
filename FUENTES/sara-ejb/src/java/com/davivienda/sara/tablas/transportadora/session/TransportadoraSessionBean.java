package com.davivienda.sara.tablas.transportadora.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.entitys.Transportadora;
import com.davivienda.sara.tablas.transportadora.servicio.TransportadoraServicio;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/**
 * TransportadoraSessionBean - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Stateless
public class TransportadoraSessionBean  extends BaseAdministracionTablas<Transportadora>  implements TransportadoraSessionLocal {
    
    @PersistenceContext
    private EntityManager em;
    private TransportadoraServicio transportadoraServicio;

    
    

    @PostConstruct
    public void postConstructor() {
        transportadoraServicio = new TransportadoraServicio(em);
        super.servicio = transportadoraServicio;
       
    }
     @SuppressWarnings("unchecked")
    public Collection<Transportadora> getTodos(Integer pagina, Integer regsPorPagina) throws Exception {
        return servicio.consultarPorQuery(pagina, regsPorPagina, "Transportadora.Todos");
    }
    
 
}
