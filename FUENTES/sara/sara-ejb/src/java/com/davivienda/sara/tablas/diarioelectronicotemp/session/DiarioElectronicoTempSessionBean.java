// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.diarioelectronicotemp.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.diarioelectronicotemp.servicio.DiarioElectronicoTempServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.DiarioelectronicoTemp;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class DiarioElectronicoTempSessionBean extends BaseAdministracionTablas<DiarioelectronicoTemp> implements DiarioElectronicoTempSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    DiarioElectronicoTempServicio diarioElectronicoServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.diarioElectronicoServicio = new DiarioElectronicoTempServicio(this.em);
        super.servicio = this.diarioElectronicoServicio;
    }
    
    @Override
    public Collection<DiarioelectronicoTemp> getDiarioElectronicoTemp(final Integer codigoCajero, final Date fechaInicial) throws EntityServicioExcepcion {
        return this.diarioElectronicoServicio.getDiarioElectronicoTemp(codigoCajero, fechaInicial);
    }
}
