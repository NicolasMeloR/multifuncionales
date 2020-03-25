// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.provisionhost.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.provisionhost.servicio.ProvisionHostServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.ProvisionHost;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class ProvisionHostBean extends BaseAdministracionTablas<ProvisionHost> implements ProvisionHostLocal
{
    @PersistenceContext
    private EntityManager em;
    ProvisionHostServicio provisionHostServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.provisionHostServicio = new ProvisionHostServicio(this.em);
        super.servicio = this.provisionHostServicio;
    }
    
    @Override
    public Collection<ProvisionHost> getProvisionHostRangoFecha(final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.provisionHostServicio.getProvisionHostRangoFecha(fechaInicial, fechaFinal);
    }
}
