// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.transaccion.servicio;

import javax.persistence.Query;
import java.util.logging.Level;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Map;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import com.davivienda.sara.base.BaseEntityServicio;

public class TransaccionServicio extends BaseEntityServicio<Transaccion> implements AdministracionTablasInterface<Transaccion>
{
    public TransaccionServicio(final EntityManager em) {
        super(em, Transaccion.class);
    }
    
    public Collection<Transaccion> getColeccionTransaccion(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal) throws EntityServicioExcepcion {
        return this.getColeccionTransaccion(codigoCajero, fechaInicial, fechaFinal, null, null);
    }
    
    public Collection<Transaccion> getColeccionTransaccion(final Integer codigoCajero, final Date fecha) throws EntityServicioExcepcion {
        return this.getColeccionTransaccion(codigoCajero, fecha, null, null, null);
    }
    
    public Transaccion getTransaccion(final Integer codigoCajero, final Date fechaProceso, final Integer numeroTransaccion) throws EntityServicioExcepcion {
        Transaccion reg = null;
        try {
            final Date fInicial = (fechaProceso != null) ? fechaProceso : Fecha.getDateHoy();
            final Date fFin = Fecha.getFechaFinDia(fInicial);
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            final Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createNamedQuery("Transaccion.TransaccionBuscarReintegro");
                query.setParameter("codigoCajero", (Object)cCajero);
                query.setParameter("fechaInicial", (Object)fInicial).setParameter("fechaFinal", (Object)fFin);
                query.setParameter("numeroTransaccion", (Object)nTran);
                reg = (Transaccion)query.getSingleResult();
            }
        }
        catch (IllegalStateException ex) {
            TransaccionServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TransaccionServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return reg;
    }
    
    public Collection<Transaccion> getColeccionTransaccion(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal, final Integer numeroTransaccion, final Map criterios) throws EntityServicioExcepcion {
        Collection<Transaccion> regs = null;
        String strQuery = "select object(obj) from Transaccion obj         where  obj.transaccionPK.fechaTransaccion between :fechaInicial and :fechaFinal ";
        final String orderQuery = "        order by obj.transaccionPK.fechaTransaccion";
        final String referencia = "%" + criterios.get("referencia");
        final String productoOrigen = "%" + criterios.get("productoOrigen");
        final String tarjeta = "%" + criterios.get("tarjeta");
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
                regs = (Collection<Transaccion>)query.getResultList();
            }
        }
        catch (IllegalStateException ex) {
            TransaccionServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TransaccionServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return regs;
    }
    
    public void copiarTablasTransaccion() throws EntityServicioExcepcion {
        final String strQuery = "Insert into Transaccion (select * from TransaccionTemp ) ";
        Query query = null;
        try {
            query = this.em.createQuery(strQuery);
            query.executeUpdate();
        }
        catch (IllegalStateException ex) {
            TransaccionServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TransaccionServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
}
