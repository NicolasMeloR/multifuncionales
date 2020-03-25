// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.historicoajustes.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.historicoajustes.servicio.HistoricoAjustesServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.HistoricoAjustes;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class HistoricoAjustesSessionBean extends BaseAdministracionTablas<HistoricoAjustes> implements HistoricoAjustesLocal
{
    @PersistenceContext
    private EntityManager em;
    private HistoricoAjustesServicio historicoAjustesServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.historicoAjustesServicio = new HistoricoAjustesServicio(this.em);
        super.servicio = this.historicoAjustesServicio;
    }
    
    @Override
    public Collection<HistoricoAjustes> getColeccionHistoricoAjustes(final Integer codigoCajero, final Integer codigoOcca, final Date fechaInicial, final Date fechaFinal, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.historicoAjustesServicio.getColeccionHistoricoAjustes(codigoCajero, codigoOcca, fechaInicial, fechaFinal, fechaHisto);
    }
}
