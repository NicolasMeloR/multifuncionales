package com.davivienda.sara.tablas.transacciontemp.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.TransaccionTemp;
import com.davivienda.sara.tablas.transacciontemp.servicio.TransaccionTempServicio;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * TransaccionSessionBean - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Stateless
public class TransaccionTempSessionBean extends BaseAdministracionTablas<TransaccionTemp> implements TransaccionTempSessionLocal {

    @PersistenceContext
    private EntityManager em;
    private TransaccionTempServicio transaccionServicio;

    @PostConstruct
    public void postConstructor() {
        transaccionServicio = new TransaccionTempServicio(em);
        super.servicio = transaccionServicio;

    }

    public Collection<TransaccionTemp> getColeccionTransaccionTemp(Integer codigoCajero, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {
        return transaccionServicio.getColeccionTransaccion(codigoCajero, fechaInicial, fechaFinal);
    }

    public Collection<TransaccionTemp> getColeccionTransaccionTemp(Integer codigoCajero, Date fecha) throws EntityServicioExcepcion {
        return transaccionServicio.getColeccionTransaccion(codigoCajero, fecha);
    }

    public TransaccionTemp getTransaccionTemp(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion) throws EntityServicioExcepcion {
        return transaccionServicio.getTransaccion(codigoCajero, fechaProceso, numeroTransaccion);
    }

    /**
     * Retorna un objeto Transaccion que cumpla con los parámetros
     * @param codigoCajero
     * @param fechaInicial 
     * @param fechaFinal 
     * @param numeroTransaccion 
     * @param criterios Map con el conjunto de paramtros extras para la consulta
     * @return Collection<Transaccion>
     * @throws EntityServicioExcepcion
     */
    public Collection<TransaccionTemp> getColeccionTransaccionTemp(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer numeroTransaccion, Map criterios) throws EntityServicioExcepcion {
        return transaccionServicio.getColeccionTransaccion(codigoCajero, fechaInicial, fechaFinal, numeroTransaccion, criterios);
    }

    public Date getFechaMinimaTx( Integer codigoCajero) throws EntityServicioExcepcion 
    {
    return transaccionServicio.getFechaMinimaTx(codigoCajero);
    
    }
    
     public void cargarCicloTempXStoreP () throws EntityServicioExcepcion
     {
      transaccionServicio.cargarCicloTempXStoreP();
     }
     
    public void  cargarDiariosElectronicosXStoreP () throws EntityServicioExcepcion
    {
        transaccionServicio.cargarDiariosElectronicosXStoreP();
    }

    public void mantenimientoDiariosStoreP () throws EntityServicioExcepcion
    {
        transaccionServicio.mantenimientoDiariosStoreP();
    }
  
    public void cargarDiariosElectronicosXStoreP_Automatico  () throws EntityServicioExcepcion
    {
         transaccionServicio.cargarDiariosElectronicosXStoreP_Automatico();
    }      
    
    public void calcReintegrosDAuto() throws EntityServicioExcepcion 
    {        
         transaccionServicio.calcReintegrosDAuto();
    }
     
    public void mantenimientoDiariosBorraStoreP () throws EntityServicioExcepcion
    {
        transaccionServicio.mantenimientoDiariosBorraStoreP();
    }
    
    public void descomprimirEDC() throws EntityServicioExcepcion 
    {
        transaccionServicio.descomprimirEDC();
    } 
    
    public void descomprimirEDC_Automatico() throws EntityServicioExcepcion
    {
        transaccionServicio.descomprimirEDC_Automatico();
    }   
}
