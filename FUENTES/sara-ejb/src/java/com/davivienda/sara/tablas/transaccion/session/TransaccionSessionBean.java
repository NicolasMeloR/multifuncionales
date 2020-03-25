package com.davivienda.sara.tablas.transaccion.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import com.davivienda.sara.tablas.transaccion.servicio.TransaccionServicio;
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
public class TransaccionSessionBean extends BaseAdministracionTablas<Transaccion> implements TransaccionSessionLocal {

    @PersistenceContext
    private EntityManager em;
    private TransaccionServicio transaccionServicio;

    @PostConstruct
    public void postConstructor() {
        transaccionServicio = new TransaccionServicio(em);
        super.servicio = transaccionServicio;

    }

    public Collection<Transaccion> getColeccionTransaccion(Integer codigoCajero, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {
        return transaccionServicio.getColeccionTransaccion(codigoCajero, fechaInicial, fechaFinal);
    }

    public Collection<Transaccion> getColeccionTransaccion(Integer codigoCajero, Date fecha) throws EntityServicioExcepcion {
        return transaccionServicio.getColeccionTransaccion(codigoCajero, fecha);
    }

    public Transaccion getTransaccion(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion) throws EntityServicioExcepcion {
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
    public Collection<Transaccion> getColeccionTransaccion(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer numeroTransaccion, Map criterios) throws EntityServicioExcepcion {
        return transaccionServicio.getColeccionTransaccion(codigoCajero, fechaInicial, fechaFinal, numeroTransaccion, criterios);
    }

   
}
