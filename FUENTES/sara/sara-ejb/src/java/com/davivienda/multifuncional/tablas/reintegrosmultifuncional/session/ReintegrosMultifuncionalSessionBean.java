// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.reintegrosmultifuncional.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.servicio.ReintegrosMultiEfectivoServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.ReintegrosMultifuncional;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class ReintegrosMultifuncionalSessionBean extends BaseAdministracionTablas<ReintegrosMultifuncional> implements ReintegrosMultifuncionalSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    ReintegrosMultiEfectivoServicio reintegrosServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.reintegrosServicio = new ReintegrosMultiEfectivoServicio(this.em);
        super.servicio = this.reintegrosServicio;
    }
    
    @Override
    public Collection<ReintegrosMultifuncional> getReintegrosMultifuncional(final Date fechaInicial, final Date fechaFinal, final Integer codigCajero) throws EntityServicioExcepcion {
        return this.reintegrosServicio.getReintegrosMultifuncional(fechaInicial, fechaFinal, codigCajero);
    }
}
