package com.davivienda.sara.tablas.transaccion.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;

import com.davivienda.utilidades.conversion.Fecha;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * TransaccionServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class TransaccionServicio extends BaseEntityServicio<Transaccion> implements AdministracionTablasInterface<Transaccion> {


    public TransaccionServicio(EntityManager em) {
        super(em, Transaccion.class);
    }

    /**
     * Retorna Retorna la colección de transacciones realizadas por el cajero <code>codigoCajero</code> enla fecha <code>fechaProceso</code>
     * @return Collection<Transaccion> 
     * @throws EntityServicioExcepcion 
     * @param codigoCajero
     * @param fechaInicial 
     * @param fechaFinal 
     */
    @SuppressWarnings("unchecked")
    public Collection<Transaccion> getColeccionTransaccion(Integer codigoCajero, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {
        return getColeccionTransaccion(codigoCajero, fechaInicial, fechaFinal, null, null);
    }

    /**
     * Retorna Retorna la colección de transacciones realizadas por el cajero <code>codigoCajero</code> enla fecha <code>fechaProceso</code>
     * @return Collection<Transaccion> 
     * @throws EntityServicioExcepcion 
     * @param codigoCajero
     * @param fecha 
     */
    @SuppressWarnings(value = "unchecked")
    public Collection<Transaccion> getColeccionTransaccion(Integer codigoCajero, Date fecha) throws EntityServicioExcepcion {
        return getColeccionTransaccion(codigoCajero, fecha, null, null, null);
    }

    /**
     * Retorna un objeto Transaccion que cumpla con los parámetros
     * @param codigoCajero
     * @param fechaProceso
     * @param numeroTransaccion
     * @return Transaccion
     * @throws EntityServicioExcepcion 
     */
    public Transaccion getTransaccion(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion) throws EntityServicioExcepcion {
        Transaccion reg = null;
        try {
            Date fInicial = (fechaProceso != null) ? fechaProceso : Fecha.getDateHoy();
            Date fFin = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fInicial);
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0000;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery("Transaccion.TransaccionBuscarReintegro");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
                query.setParameter("numeroTransaccion", nTran);
                reg = (Transaccion) query.getSingleResult();
            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return reg;
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
    @SuppressWarnings("unchecked")
     public Collection<Transaccion> getColeccionTransaccion(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer numeroTransaccion, Map criterios) throws EntityServicioExcepcion {
        Collection<Transaccion> regs = null;
        String strQuery = "select object(obj) from Transaccion obj " +
                  "        where  obj.transaccionPK.fechaTransaccion between :fechaInicial and :fechaFinal ";
        String orderQuery = "        order by obj.transaccionPK.fechaTransaccion";
        String referencia = "%" + (String) criterios.get("referencia");
        String productoOrigen = "%" + (String) criterios.get("productoOrigen");
        String tarjeta = "%" + (String) criterios.get("tarjeta");
        try {
           if (codigoCajero != 0) {
                strQuery += "          and obj.transaccionPK.codigoCajero =:codigoCajero";
            }
            if (numeroTransaccion != null) {
                strQuery += "          and obj.transaccionPK.numeroTransaccion = :numeroTransaccion";
            }
            if (!referencia.equals("%")) {
                strQuery += "          and obj.referencia like :referencia";
            }
            if (!productoOrigen.equals("%")) {
                strQuery += "          and obj.cuenta like :productoOrigen";
            }
            if (!tarjeta.equals("%")) {
                strQuery += "          and obj.tarjeta like :tarjeta";
            }
            strQuery += orderQuery;
            Date fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getDateHoy();
            Date fFin = (fechaFinal != null) ? com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaFinal) : com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fInicial);
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
               
                query = em.createQuery(strQuery);
                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
                if (codigoCajero != 0) {
                      query.setParameter("codigoCajero", cCajero);
                }
                if (numeroTransaccion != null) {
                    query.setParameter("numeroTransaccion", numeroTransaccion);
                }
                if (!referencia.equals("%")) {
                    query.setParameter("referencia", referencia);
                }
                if (!productoOrigen.equals("%")) {
                    query.setParameter("productoOrigen", productoOrigen);
                }
                if (!tarjeta.equals("%")) {
                    query.setParameter("tarjeta", tarjeta);
                }
                regs = query.getResultList();
            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return regs;
    }
    
    
        /**
     * copia los datos de la tabla transaccion temp en transaccion 
     * @param codigoCajero
     * @param fechaInicial 
     * @param fechaFinal 
     * @param numeroTransaccion 
     * @param criterios Map con el conjunto de paramtros extras para la consulta
     * @return Collection<Transaccion>
     * @throws EntityServicioExcepcion
     */
    @SuppressWarnings("unchecked")
     public void copiarTablasTransaccion() throws EntityServicioExcepcion {
     
        
        
      String strQuery = "Insert into Transaccion (select * from TransaccionTemp ) " ;
      Query query = null;
       try {
              query = em.createQuery(strQuery);
              query.executeUpdate();
            
        } catch (IllegalStateException ex) {
              configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
              throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
              configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
              throw new EntityServicioExcepcion(ex);
        }

    }
  
 
    
}
