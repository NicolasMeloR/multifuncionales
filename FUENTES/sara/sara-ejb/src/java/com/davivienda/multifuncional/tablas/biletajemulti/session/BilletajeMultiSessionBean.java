// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.biletajemulti.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import com.davivienda.multifuncional.tablas.biletajemulti.servicio.BilletajeMultiSessionServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Billetajemulti;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class BilletajeMultiSessionBean extends BaseAdministracionTablas<Billetajemulti> implements BilletajeMultiSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    BilletajeMultiSessionServicio billetajeMultiSessionServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.billetajeMultiSessionServicio = new BilletajeMultiSessionServicio(this.em);
        super.servicio = this.billetajeMultiSessionServicio;
    }
    
    @Override
    public Collection<Billetajemulti> getBilletajeMultiRangoFecha(final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.billetajeMultiSessionServicio.getBilletajeMultiRangoFecha(fechaInicial, fechaFinal);
    }
}
