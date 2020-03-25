// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.tipocajero.session;

import java.util.Collection;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.tipocajero.servicio.TipoCajeroServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.TipoCajero;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class TipoCajeroSessionBean extends BaseAdministracionTablas<TipoCajero> implements TipoCajeroSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    
    @PostConstruct
    public void postConstructor() {
        super.servicio = new TipoCajeroServicio(this.em);
    }
    
    @Override
    public Collection<TipoCajero> getTodos(final Integer pagina, final Integer regsPorPagina) throws Exception {
        return this.servicio.consultarPorQuery(pagina, regsPorPagina, "TipoCajero.Todos");
    }
}
