// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.edccargue.session;

import java.util.Date;
import java.util.Collection;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.edccargue.servicio.EdcCargueServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.EdcCargue;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class EdcCargueSessionBean extends BaseAdministracionTablas<EdcCargue> implements EdcCargueSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private EdcCargueServicio EdcCargueServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.EdcCargueServicio = new EdcCargueServicio(this.em);
        super.servicio = this.EdcCargueServicio;
    }
    
    @Override
    public Collection<EdcCargue> getCicloSnError(final Integer ciclo) throws Exception {
        return this.EdcCargueServicio.getCicloSnError(ciclo);
    }
    
    @Override
    public Collection<EdcCargue> getEDCCarguePorFecha(final Date fechaInicial, final Date fechaFinal) throws Exception {
        return this.EdcCargueServicio.getEDCCarguePorFecha(fechaInicial, fechaFinal);
    }
    
    @Override
    public int borrarCiclo(final Integer codigoCiclo) throws Exception {
        return this.EdcCargueServicio.borrarCiclo(codigoCiclo);
    }
}
