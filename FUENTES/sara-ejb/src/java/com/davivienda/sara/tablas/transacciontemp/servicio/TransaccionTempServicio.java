package com.davivienda.sara.tablas.transacciontemp.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.TransaccionTemp;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;

import com.davivienda.utilidades.conversion.Fecha;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * TransaccionServicio - 21/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class TransaccionTempServicio extends BaseEntityServicio<TransaccionTemp> implements AdministracionTablasInterface<TransaccionTemp> {

    public TransaccionTempServicio(EntityManager em) {
        super(em, TransaccionTemp.class);
    }

    /**
     * Retorna Retorna la colección de transacciones realizadas por el cajero
     * <code>codigoCajero</code> enla fecha <code>fechaProceso</code>
     *
     * @return Collection<Transaccion>
     * @throws EntityServicioExcepcion
     * @param codigoCajero
     * @param fechaInicial
     * @param fechaFinal
     */
    @SuppressWarnings("unchecked")
    public Collection<TransaccionTemp> getColeccionTransaccion(Integer codigoCajero, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {
        return getColeccionTransaccion(codigoCajero, fechaInicial, fechaFinal, null, null);
    }

    /**
     * Retorna Retorna la colección de transacciones realizadas por el cajero
     * <code>codigoCajero</code> enla fecha <code>fechaProceso</code>
     *
     * @return Collection<Transaccion>
     * @throws EntityServicioExcepcion
     * @param codigoCajero
     * @param fecha
     */
    @SuppressWarnings(value = "unchecked")
    public Collection<TransaccionTemp> getColeccionTransaccion(Integer codigoCajero, Date fecha) throws EntityServicioExcepcion {
        return getColeccionTransaccion(codigoCajero, fecha, null, null, null);
    }

    /**
     * Retorna un objeto Transacciontemp que cumpla con los parámetros
     *
     * @param codigoCajero
     * @param fechaProceso
     * @param numeroTransaccion
     * @return Transaccion
     * @throws EntityServicioExcepcion
     */
    public TransaccionTemp getTransaccion(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion) throws EntityServicioExcepcion {
        TransaccionTemp reg = null;
        try {
            Date fInicial = (fechaProceso != null) ? fechaProceso : Fecha.getDateHoy();
            Date fFin = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fInicial);
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0000;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery("TransaccionTemp.TransaccionBuscarReintegro");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
                query.setParameter("numeroTransaccion", nTran);
                reg = (TransaccionTemp) query.getSingleResult();
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
     *
     * @param codigoCajero
     * @param fechaInicial
     * @param fechaFinal
     * @param numeroTransaccion
     * @param criterios Map con el conjunto de paramtros extras para la consulta
     * @return Collection<Transaccion>
     * @throws EntityServicioExcepcion
     */
    @SuppressWarnings("unchecked")
    public Collection<TransaccionTemp> getColeccionTransaccion(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer numeroTransaccion, Map criterios) throws EntityServicioExcepcion {
        Collection<TransaccionTemp> regs = null;
        String strQuery = "select object(obj) from TransaccionTemp obj "
                + "        where  obj.transaccionTempPK.fechatransaccion between :fechaInicial and :fechaFinal ";
        String orderQuery = "        order by obj.transaccionTempPK.fechatransaccion";
        String referencia = "%" + (String) criterios.get("referencia");
        String productoOrigen = "%" + (String) criterios.get("productoOrigen");
        String tarjeta = "%" + (String) criterios.get("tarjeta");
        try {
            if (codigoCajero != 0) {
                strQuery += "          and obj.transaccionTempPK.codigocajero =:codigoCajero";
            }
            if (numeroTransaccion != null) {
                strQuery += "          and obj.transaccionTempPK.numerotransaccion = :numeroTransaccion";
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
                //createNamedQuery("Transaccion.CajeroRangoFecha");
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

    public Date getFechaMinimaTx(Integer codigoCajero) throws EntityServicioExcepcion, IllegalArgumentException {

        Date result = null;

        Query query = null;

        if (codigoCajero == null || codigoCajero == 0) {
            throw new IllegalArgumentException("Se debe seleccionar un cajero");
        }

        try {
            query = em.createNamedQuery("TransaccionTemp.minFechaTransaccion");
            query.setParameter("codigoCajero", codigoCajero);
            result = (Date) query.getSingleResult();

//            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

        return result;
    }

    public void cargarCicloTempXStoreP() throws EntityServicioExcepcion {

        try {
//            Query query = em.createNativeQuery("BEGIN ADMINATM.CARGUETRANSACCIONTEMP_INIT; END;");
//            query.executeUpdate();
            ejecutarSpDesdeJob("ADMINATM.CARGUETRANSACCIONTEMP_INIT;");
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al ejecutar el store procedure CARGUETRANSACCIONTEMP No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al ejecutar el store procedure CARGUETRANSACCIONTEMP El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void mantenimientoDiariosStoreP() throws EntityServicioExcepcion {

        try {
            Query query = em.createNativeQuery("BEGIN ADMINATM.MANTENIMIENTODIARIOS; END;");
            query.executeUpdate();
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al ejecutar el store procedure MANTENIMIENTODIARIOS No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al ejecutar el store procedure MANTENIMIENTODIARIOS El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void mantenimientoDiariosBorraStoreP() throws EntityServicioExcepcion {
        
        try {
//            Query query = em.createNativeQuery("BEGIN ADMINATM.MANTENIMIENTOTR_TEMP; END;");
//            query.executeUpdate();
            ejecutarSpDesdeJob("ADMINATM.MANTENIMIENTOTR_TEMP;");
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al ejecutar el store procedure MANTENIMIENTOTR_TEMP No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al ejecutar el store procedure MANTENIMIENTOTR_TEMP El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        
    }

    public void cargarDiariosElectronicosXStoreP() throws EntityServicioExcepcion {

        try {
            Query query = em.createNativeQuery("BEGIN ADMINATM.CARGUEDIARIOSELECTRONICOS_INIT; END;");
            query.executeUpdate();
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al ejecutar el store procedure CARGUEDIARIOSELECTRONICOS_INIT No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al ejecutar el store procedure CARGUEDIARIOSELECTRONICOS_INIT El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void cargarDiariosElectronicosXStoreP_Automatico() throws EntityServicioExcepcion {

        try {
//            Query query = em.createNativeQuery("BEGIN ADMINATM.CARGUETRANSACCIONTEMP_AUTO; END;");
//            query.executeUpdate();
            ejecutarSpDesdeJob("ADMINATM.CARGUETRANSACCIONTEMP_AUTO;");
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al ejecutar el store procedure CARGUEDIARIOSELECTRONICOS_INIT No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al ejecutar el store procedure CARGUEDIARIOSELECTRONICOS_INIT El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
    }
    
    public void calcReintegrosDAuto() throws EntityServicioExcepcion {
        
        try {
//            Query query = em.createNativeQuery("BEGIN ADMINATM.SP_REINTEGROS_AUTO; END;");
//            query.executeUpdate();
            ejecutarSpDesdeJob("ADMINATM.SP_REINTEGROS_AUTO;");
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void descomprimirEDC() throws EntityServicioExcepcion {

        try {
            Query query = em.createNativeQuery("BEGIN ADMINATM.SP_CARGA_EDC; END;");
            query.executeUpdate();
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void descomprimirEDC_Automatico() throws EntityServicioExcepcion {

        try {
            Query query = em.createNativeQuery("BEGIN ADMINATM.SP_CARGA_EDC_AUTO; END;");
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
