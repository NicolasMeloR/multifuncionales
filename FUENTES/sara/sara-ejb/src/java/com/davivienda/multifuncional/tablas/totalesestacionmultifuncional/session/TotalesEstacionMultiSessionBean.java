// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.totalesestacionmultifuncional.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import com.davivienda.multifuncional.tablas.totalesestacionmultifuncional.servicio.TotalesEstacionMultiSessionServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Totalesestacionmultifuncional;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class TotalesEstacionMultiSessionBean extends BaseAdministracionTablas<Totalesestacionmultifuncional> implements TotalesEstacionMultiSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    TotalesEstacionMultiSessionServicio totalesEstacionMultiSessionServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.totalesEstacionMultiSessionServicio = new TotalesEstacionMultiSessionServicio(this.em);
        super.servicio = this.totalesEstacionMultiSessionServicio;
    }
    
    @Override
    public Collection<Totalesestacionmultifuncional> getTotalesEstacionMultiRangoFecha(final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.totalesEstacionMultiSessionServicio.getTotalesEstacionMultiRangoFecha(fechaInicial, fechaFinal);
    }
}
