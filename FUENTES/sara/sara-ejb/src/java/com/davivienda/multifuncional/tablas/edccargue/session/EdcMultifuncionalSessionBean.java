// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.edccargue.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.multifuncional.tablas.edccargue.servicio.EdcMultifuncionalServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Edcarguemultifuncional;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class EdcMultifuncionalSessionBean extends BaseAdministracionTablas<Edcarguemultifuncional> implements EDCCargueMultifuncionalLocal
{
    @PersistenceContext
    private EntityManager em;
    private EdcMultifuncionalServicio EdcCargueServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.EdcCargueServicio = new EdcMultifuncionalServicio(this.em);
        super.servicio = this.EdcCargueServicio;
    }
    
    @Override
    public Collection<Edcarguemultifuncional> getEDCCargueXFecha(final Date fechaInicial, final Date fechaFinal) throws EntityServicioExcepcion {
        return this.EdcCargueServicio.getEDCCargueXFecha(fechaInicial, fechaFinal);
    }
}
