package com.davivienda.sara.tablas.tipocajero.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.entitys.TipoCajero;
import com.davivienda.sara.tablas.tipocajero.servicio.TipoCajeroServicio;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * TipoCajeroSessionBean - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Stateless
public class TipoCajeroSessionBean extends BaseAdministracionTablas<TipoCajero> implements TipoCajeroSessionLocal {

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void postConstructor() {
        super.servicio = new TipoCajeroServicio(em);
    }
    
    @SuppressWarnings("unchecked")
    public Collection<TipoCajero> getTodos(Integer pagina, Integer regsPorPagina) throws Exception {
        return servicio.consultarPorQuery(pagina, regsPorPagina, "TipoCajero.Todos");
    }
    

}
