// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.cuadrecifras.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.reintegros.servicio.InformeDiferenciasServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.InformeDiferencias;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class InformeDiferenciasSessionBean extends BaseAdministracionTablas<InformeDiferencias> implements InformeDiferenciasSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private InformeDiferenciasServicio servicioInformeDif;
    
    @PostConstruct
    public void postConstructor() {
        this.servicioInformeDif = new InformeDiferenciasServicio(this.em);
        super.servicio = this.servicioInformeDif;
    }
    
    @Override
    public Collection<InformeDiferencias> getInformeDiferenciaXFecha(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.servicioInformeDif.buscarDiferenciasXFecha(codigoCajero, fechaInicial, fechaFinal);
    }
}
