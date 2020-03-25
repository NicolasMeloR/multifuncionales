// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.provisionhostmultifuncional.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import com.davivienda.multifuncional.tablas.provisionhostmultifuncional.servicio.ProvisionHostMultifuncionalServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Provisionhostmulti;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class ProvisionHostMultifuncionalBean extends BaseAdministracionTablas<Provisionhostmulti> implements ProvisionHostMultifuncionalLocal
{
    @PersistenceContext
    private EntityManager em;
    ProvisionHostMultifuncionalServicio provisionHostMultiServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.provisionHostMultiServicio = new ProvisionHostMultifuncionalServicio(this.em);
        super.servicio = this.provisionHostMultiServicio;
    }
    
    @Override
    public Collection<Provisionhostmulti> getProvisionHostRangoFecha(final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.provisionHostMultiServicio.getProvisionHostRangoFecha(fechaInicial, fechaFinal);
    }
}
