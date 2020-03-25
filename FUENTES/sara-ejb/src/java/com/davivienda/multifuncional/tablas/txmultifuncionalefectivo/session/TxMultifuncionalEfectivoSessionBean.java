package com.davivienda.multifuncional.tablas.txmultifuncionalefectivo.session;


import com.davivienda.multifuncional.tablas.txmultifuncionalefectivo.servicio.TxMultifuncionalEfectivoServicio;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * TxMultifuncionalEfectivoSessionBean 
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-MDRUIZ
 * Davivienda 2011 
 */
@Stateless

public class TxMultifuncionalEfectivoSessionBean extends BaseAdministracionTablas<Txmultifuncionalefectivo> implements TxMultifuncionalEfectivoSessionLocal {

    @PersistenceContext
    private EntityManager em;
    private TxMultifuncionalEfectivoServicio transaccionServicio;

    @PostConstruct
    public void postConstructor() {
        transaccionServicio = new TxMultifuncionalEfectivoServicio(em);
        super.servicio = transaccionServicio;

    }


    public Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion {
        return transaccionServicio.getColeccionTxEfectivo(codigoCajero, fechaInicial, fechaFinal, fechaHisto);
    }

    public Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(Integer codigoCajero, Date fecha, Date fechaHisto) throws EntityServicioExcepcion {
        return transaccionServicio.getColeccionTxEfectivo(codigoCajero, fecha,fechaHisto);
    }

    public Txmultifuncionalefectivo getTxEfectivo(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, Date fechaHisto) throws EntityServicioExcepcion {
        return transaccionServicio.getTxEfectivo(codigoCajero, fechaProceso, numeroTransaccion,fechaHisto);
    }

    public Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer numeroTransaccion, String cuenta, Date fechaHisto) throws EntityServicioExcepcion {
        return transaccionServicio.getColeccionTxEfectivo(codigoCajero,fechaInicial, fechaFinal, numeroTransaccion, cuenta,fechaHisto);
    }
    
    public void guardarArchivoMultifuncionalEfectivo(Integer codigoCajero) throws EntityServicioExcepcion {
         transaccionServicio.guardarArchivoMultifuncionalEfectivo(codigoCajero);
    }
    
     public void cargarDiariosMultiEfectivo() throws EntityServicioExcepcion{
         transaccionServicio.cargarDiariosMultiEfectivo();
    }
     
     public void cargarDiariosMultiCheque() throws EntityServicioExcepcion{
         transaccionServicio.cargarDiariosMultiCheque();
    }
     public void cargarLogEventos() throws EntityServicioExcepcion 
     {
      transaccionServicio.cargarLogEventos();         
     }
     

   
}
