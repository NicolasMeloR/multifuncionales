// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.transacciontemp.session;

import java.util.Map;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.transacciontemp.servicio.TransaccionTempServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.TransaccionTemp;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class TransaccionTempSessionBean extends BaseAdministracionTablas<TransaccionTemp> implements TransaccionTempSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private TransaccionTempServicio transaccionServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.transaccionServicio = new TransaccionTempServicio(this.em);
        super.servicio = this.transaccionServicio;
    }
    
    @Override
    public Collection<TransaccionTemp> getColeccionTransaccionTemp(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal) throws EntityServicioExcepcion {
        return this.transaccionServicio.getColeccionTransaccion(codigoCajero, fechaInicial, fechaFinal);
    }
    
    @Override
    public Collection<TransaccionTemp> getColeccionTransaccionTemp(final Integer codigoCajero, final Date fecha) throws EntityServicioExcepcion {
        return this.transaccionServicio.getColeccionTransaccion(codigoCajero, fecha);
    }
    
    @Override
    public TransaccionTemp getTransaccionTemp(final Integer codigoCajero, final Date fechaProceso, final Integer numeroTransaccion) throws EntityServicioExcepcion {
        return this.transaccionServicio.getTransaccion(codigoCajero, fechaProceso, numeroTransaccion);
    }
    
    @Override
    public Collection<TransaccionTemp> getColeccionTransaccionTemp(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal, final Integer numeroTransaccion, final Map criterios) throws EntityServicioExcepcion {
        return this.transaccionServicio.getColeccionTransaccion(codigoCajero, fechaInicial, fechaFinal, numeroTransaccion, criterios);
    }
    
    @Override
    public Date getFechaMinimaTx(final Integer codigoCajero) throws EntityServicioExcepcion {
        return this.transaccionServicio.getFechaMinimaTx(codigoCajero);
    }
    
    @Override
    public void cargarCicloTempXStoreP() throws EntityServicioExcepcion {
        this.transaccionServicio.cargarCicloTempXStoreP();
    }
    
    @Override
    public void cargarDiariosElectronicosXStoreP() throws EntityServicioExcepcion {
        this.transaccionServicio.cargarDiariosElectronicosXStoreP();
    }
    
    @Override
    public void mantenimientoDiariosStoreP() throws EntityServicioExcepcion {
        this.transaccionServicio.mantenimientoDiariosStoreP();
    }
    
    @Override
    public void cargarDiariosElectronicosXStoreP_Automatico() throws EntityServicioExcepcion {
        this.transaccionServicio.cargarDiariosElectronicosXStoreP_Automatico();
    }
    
    @Override
    public void calcReintegrosDAuto() throws EntityServicioExcepcion {
        this.transaccionServicio.calcReintegrosDAuto();
    }
    
    @Override
    public void mantenimientoDiariosBorraStoreP() throws EntityServicioExcepcion {
        this.transaccionServicio.mantenimientoDiariosBorraStoreP();
    }
    
    @Override
    public void descomprimirEDC() throws EntityServicioExcepcion {
        this.transaccionServicio.descomprimirEDC();
    }
    
    @Override
    public void descomprimirEDC_Automatico() throws EntityServicioExcepcion {
        this.transaccionServicio.descomprimirEDC_Automatico();
    }
}
