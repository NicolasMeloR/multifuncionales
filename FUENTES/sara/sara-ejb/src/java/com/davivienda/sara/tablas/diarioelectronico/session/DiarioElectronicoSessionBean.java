// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.diarioelectronico.session;

import java.io.FileNotFoundException;
import java.util.Calendar;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.diarioelectronico.servicio.DiarioElectronicoServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.DiarioElectronico;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class DiarioElectronicoSessionBean extends BaseAdministracionTablas<DiarioElectronico> implements DiarioElectronicoSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    DiarioElectronicoServicio diarioElectronicoServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.diarioElectronicoServicio = new DiarioElectronicoServicio(this.em);
        super.servicio = this.diarioElectronicoServicio;
    }
    
    @Override
    public Collection<DiarioElectronico> getDiarioElectronico(final Integer codigoCajero, final Date fechaInicial) throws EntityServicioExcepcion {
        return this.diarioElectronicoServicio.getDiarioElectronico(codigoCajero, fechaInicial);
    }
    
    @Override
    public Collection<DiarioElectronico> getDiarioElectronicoDia(final Integer codigoCajero, final Calendar fecha, final String nombreArchivo) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        return this.diarioElectronicoServicio.getDiarioElectronicoDia(codigoCajero, fecha, nombreArchivo);
    }
}
