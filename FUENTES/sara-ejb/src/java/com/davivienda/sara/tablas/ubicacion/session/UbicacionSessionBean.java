package com.davivienda.sara.tablas.ubicacion.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.entitys.Ubicacion;
import com.davivienda.sara.tablas.ubicacion.servicio.UbicacionServicio;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
/**
 * UbicacionSessionBean - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Stateless
public class UbicacionSessionBean extends BaseAdministracionTablas<Ubicacion> implements UbicacionSessionLocal {
    
    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void postConstructor() {
        super.servicio = new UbicacionServicio(em);
    }
     @SuppressWarnings("unchecked")
    public Collection<Ubicacion> getTodos(Integer pagina, Integer regsPorPagina) throws Exception {
        return servicio.consultarPorQuery(pagina, regsPorPagina, "Ubicacion.Todos");
    }
    
    
}
