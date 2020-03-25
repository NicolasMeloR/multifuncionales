// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.transacciontemp.servicio;

import javax.persistence.Query;
import java.util.logging.Level;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Map;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.TransaccionTemp;
import com.davivienda.sara.base.BaseEntityServicio;

public class TransaccionTempServicio extends BaseEntityServicio<TransaccionTemp> implements AdministracionTablasInterface<TransaccionTemp>
{
    public TransaccionTempServicio(final EntityManager em) {
        super(em, TransaccionTemp.class);
    }
    
    public Collection<TransaccionTemp> getColeccionTransaccion(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal) throws EntityServicioExcepcion {
        return this.getColeccionTransaccion(codigoCajero, fechaInicial, fechaFinal, null, null);
    }
    
    public Collection<TransaccionTemp> getColeccionTransaccion(final Integer codigoCajero, final Date fecha) throws EntityServicioExcepcion {
        return this.getColeccionTransaccion(codigoCajero, fecha, null, null, null);
    }
    
    public TransaccionTemp getTransaccion(final Integer codigoCajero, final Date fechaProceso, final Integer numeroTransaccion) throws EntityServicioExcepcion {
        TransaccionTemp reg = null;
        try {
            final Date fInicial = (fechaProceso != null) ? fechaProceso : Fecha.getDateHoy();
            final Date fFin = Fecha.getFechaFinDia(fInicial);
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            final Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createNamedQuery("TransaccionTemp.TransaccionBuscarReintegro");
                query.setParameter("codigoCajero", (Object)cCajero);
                query.setParameter("fechaInicial", (Object)fInicial).setParameter("fechaFinal", (Object)fFin);
                query.setParameter("numeroTransaccion", (Object)nTran);
                reg = (TransaccionTemp)query.getSingleResult();
            }
        }
        catch (IllegalStateException ex) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return reg;
    }
    
    public Collection<TransaccionTemp> getColeccionTransaccion(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal, final Integer numeroTransaccion, final Map criterios) throws EntityServicioExcepcion {
        Collection<TransaccionTemp> regs = null;
        String strQuery = "select object(obj) from TransaccionTemp obj         where  obj.transaccionTempPK.fechatransaccion between :fechaInicial and :fechaFinal ";
        final String orderQuery = "        order by obj.transaccionTempPK.fechatransaccion";
        final String referencia = "%" + criterios.get("referencia");
        final String productoOrigen = "%" + criterios.get("productoOrigen");
        final String tarjeta = "%" + criterios.get("tarjeta");
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
            final Date fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getDateHoy();
            final Date fFin = (fechaFinal != null) ? Fecha.getFechaFinDia(fechaFinal) : Fecha.getFechaFinDia(fInicial);
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createQuery(strQuery);
                query.setParameter("fechaInicial", (Object)fInicial).setParameter("fechaFinal", (Object)fFin);
                if (codigoCajero != 0) {
                    query.setParameter("codigoCajero", (Object)cCajero);
                }
                if (numeroTransaccion != null) {
                    query.setParameter("numeroTransaccion", (Object)numeroTransaccion);
                }
                if (!referencia.equals("%")) {
                    query.setParameter("referencia", (Object)referencia);
                }
                if (!productoOrigen.equals("%")) {
                    query.setParameter("productoOrigen", (Object)productoOrigen);
                }
                if (!tarjeta.equals("%")) {
                    query.setParameter("tarjeta", (Object)tarjeta);
                }
                regs = (Collection<TransaccionTemp>)query.getResultList();
            }
        }
        catch (IllegalStateException ex) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return regs;
    }
    
    public Date getFechaMinimaTx(final Integer codigoCajero) throws EntityServicioExcepcion, IllegalArgumentException {
        Date result = null;
        Query query = null;
        if (codigoCajero == null || codigoCajero == 0) {
            throw new IllegalArgumentException("Se debe seleccionar un cajero");
        }
        try {
            query = this.em.createNamedQuery("TransaccionTemp.minFechaTransaccion");
            query.setParameter("codigoCajero", (Object)codigoCajero);
            result = (Date)query.getSingleResult();
        }
        catch (IllegalStateException ex) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return result;
    }
    
    public void cargarCicloTempXStoreP() throws EntityServicioExcepcion {
        try {
            this.ejecutarSpDesdeJob("ADMINATM.CARGUETRANSACCIONTEMP_INIT;");
        }
        catch (IllegalStateException ex) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el store procedure CARGUETRANSACCIONTEMP No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el store procedure CARGUETRANSACCIONTEMP El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void mantenimientoDiariosStoreP() throws EntityServicioExcepcion {
        try {
            final Query query = this.em.createNativeQuery("BEGIN ADMINATM.MANTENIMIENTODIARIOS; END;");
            query.executeUpdate();
        }
        catch (IllegalStateException ex) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el store procedure MANTENIMIENTODIARIOS No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el store procedure MANTENIMIENTODIARIOS El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void mantenimientoDiariosBorraStoreP() throws EntityServicioExcepcion {
        try {
            this.ejecutarSpDesdeJob("ADMINATM.MANTENIMIENTOTR_TEMP;");
        }
        catch (IllegalStateException ex) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el store procedure MANTENIMIENTOTR_TEMP No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el store procedure MANTENIMIENTOTR_TEMP El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void cargarDiariosElectronicosXStoreP() throws EntityServicioExcepcion {
        try {
            final Query query = this.em.createNativeQuery("BEGIN ADMINATM.CARGUEDIARIOSELECTRONICOS_INIT; END;");
            query.executeUpdate();
        }
        catch (IllegalStateException ex) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el store procedure CARGUEDIARIOSELECTRONICOS_INIT No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el store procedure CARGUEDIARIOSELECTRONICOS_INIT El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void cargarDiariosElectronicosXStoreP_Automatico() throws EntityServicioExcepcion {
        try {
            this.ejecutarSpDesdeJob("ADMINATM.CARGUETRANSACCIONTEMP_AUTO;");
        }
        catch (IllegalStateException ex) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el store procedure CARGUEDIARIOSELECTRONICOS_INIT No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el store procedure CARGUEDIARIOSELECTRONICOS_INIT El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void calcReintegrosDAuto() throws EntityServicioExcepcion {
        try {
            this.ejecutarSpDesdeJob("ADMINATM.SP_REINTEGROS_AUTO;");
        }
        catch (IllegalStateException ex) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void descomprimirEDC() throws EntityServicioExcepcion {
        try {
            final Query query = this.em.createNativeQuery("BEGIN ADMINATM.SP_CARGA_EDC; END;");
            query.executeUpdate();
        }
        catch (IllegalStateException ex) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void descomprimirEDC_Automatico() throws EntityServicioExcepcion {
        try {
            final Query query = this.em.createNativeQuery("BEGIN ADMINATM.SP_CARGA_EDC_AUTO; END;");
            query.executeUpdate();
        }
        catch (IllegalStateException ex) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TransaccionTempServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
}
