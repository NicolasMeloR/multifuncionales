package com.davivienda.multifuncional.tablas.txmultifuncionalcheque.servicio;


import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;

import com.davivienda.sara.entitys.Txmultifuncionalcheque;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * TxMultifuncionalChequeServicio 
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-MDRUIZ
 * Davivienda 2011 
 */
public class TxMultifuncionalChequeServicio extends BaseEntityServicio<Txmultifuncionalcheque> implements AdministracionTablasInterface<Txmultifuncionalcheque> {


    public TxMultifuncionalChequeServicio(EntityManager em) {
        super(em, Txmultifuncionalcheque.class);
    }

   
    @SuppressWarnings("unchecked")
    public Collection<Txmultifuncionalcheque> getColeccionTxCheque(Integer codigoCajero, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {
        return getColeccionTxCheque(codigoCajero, fechaInicial, fechaFinal, null, null);
    }


    @SuppressWarnings(value = "unchecked")
    public Collection<Txmultifuncionalcheque> getColeccionTxCheque(Integer codigoCajero, Date fecha) throws EntityServicioExcepcion {
        return getColeccionTxCheque(codigoCajero, fecha, null, null, null);
    }

    
    public Txmultifuncionalcheque getTxCheque(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion) throws EntityServicioExcepcion {
        Txmultifuncionalcheque reg = null;
        try {
            Date fInicial = (fechaProceso != null) ? fechaProceso : Fecha.getDateHoy();
            Date fFin = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fInicial);
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0000;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery("Txmultifuncionalcheque.TransaccionBuscarReintegro");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
                query.setParameter("numeroTransaccion", nTran);
                reg = (Txmultifuncionalcheque) query.getSingleResult();
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
    
    
    @SuppressWarnings("unchecked")
     public Collection<Txmultifuncionalcheque> getColeccionTxCheque(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer numeroTransaccion, String cuenta) throws EntityServicioExcepcion {
        Collection<Txmultifuncionalcheque> regs = null;
        String strQuery = "select object(obj) from Txmultifuncionalcheque obj " +
              
                "        where  obj.txmultifuncionalchequePK.fechacajero between :fechaInicial and :fechaFinal ";
        String orderQuery = "        order by obj.txmultifuncionalchequePK.fechacajero";
       
        try {
           if (codigoCajero != 0) {
                strQuery += "          and obj.txmultifuncionalchequePK.codigocajero =:codigoCajero";
            }
            if (numeroTransaccion != null) {
                strQuery += "          and obj.txmultifuncionalchequePK.codigotransaccion = :numeroTransaccion";
            }
            if ((cuenta != null) && (cuenta.equals("")==false)){
                strQuery += "          and obj.numerocuentaconsignar = :cuenta";
            }
            strQuery += orderQuery;
            Date fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getDateHoy();
            Date fFin = (fechaFinal != null) ? com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaFinal) : com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fInicial);
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                //createNamedQuery("Transaccion.CajeroRangoFecha");
                query = em.createQuery(strQuery);
           
                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
                 if (codigoCajero != 0) {
                      query.setParameter("codigoCajero", cCajero);
                }
                if (numeroTransaccion != null) {
                    query.setParameter("numeroTransaccion", numeroTransaccion);
                }
                if (cuenta != null && (cuenta.equals("")==false)){
                    query.setParameter("cuenta",cuenta);
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
//    public Collection<Transaccion> getColeccionTransaccion(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer numeroTransaccion, Map criterios) throws EntityServicioExcepcion {
//        Collection<Transaccion> regs = null;
//
//        String strQuery = "select object(obj) from Transaccion obj " +
//                "        where obj.transaccionPK.codigoCajero =:codigoCajero and " +
//                "              obj.transaccionPK.fechaTransaccion between :fechaInicial and :fechaFinal ";
//
//        String orderQuery = "        order by obj.transaccionPK.fechaTransaccion";
//
//        String referencia = "%" + (String) criterios.get("referencia");
//        String productoOrigen = "%" + (String) criterios.get("productoOrigen");
//        String tarjeta = "%" + (String) criterios.get("tarjeta");
//        try {
//            if (numeroTransaccion != null) {
//                strQuery += "          and obj.transaccionPK.numeroTransaccion = :numeroTransaccion";
//            }
//            if (!referencia.equals("%")) {
//                strQuery += "          and obj.referencia like :referencia";
//            }
//            if (!productoOrigen.equals("%")) {
//                strQuery += "          and obj.cuenta like :productoOrigen";
//            }
//            if (!tarjeta.equals("%")) {
//                strQuery += "          and obj.tarjeta like :tarjeta";
//            }
//            strQuery += orderQuery;
//
//            Date fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getDateHoy();
//            Date fFin = (fechaFinal != null) ? com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaFinal) : com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fInicial);
//            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
//            Query query = null;
//            if (!cCajero.equals(9999)) {
//                //createNamedQuery("Transaccion.CajeroRangoFecha");
//                query = em.createQuery(strQuery);
//                query.setParameter("codigoCajero", cCajero);
//                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
//                if (numeroTransaccion != null) {
//                    query.setParameter("numeroTransaccion", numeroTransaccion);
//                }
//                if (!referencia.equals("%")) {
//                    query.setParameter("referencia", referencia);
//                }
//                if (!productoOrigen.equals("%")) {
//                    query.setParameter("productoOrigen", productoOrigen);
//                }
//                if (!tarjeta.equals("%")) {
//                    query.setParameter("tarjeta", tarjeta);
//                }
//                regs = query.getResultList();
//            }
//        } catch (IllegalStateException ex) {
//            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
//            throw new EntityServicioExcepcion(ex);
//        } catch (IllegalArgumentException ex) {
//            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
//            throw new EntityServicioExcepcion(ex);
//        }
//        return regs;
//
//    }
//    
 
    
}
