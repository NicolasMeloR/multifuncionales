// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.txmultifuncionalcheque.servicio;

import javax.persistence.Query;
import java.util.logging.Level;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Txmultifuncionalcheque;
import com.davivienda.sara.base.BaseEntityServicio;

public class TxMultifuncionalChequeServicio extends BaseEntityServicio<Txmultifuncionalcheque> implements AdministracionTablasInterface<Txmultifuncionalcheque>
{
    public TxMultifuncionalChequeServicio(final EntityManager em) {
        super(em, Txmultifuncionalcheque.class);
    }
    
    public Collection<Txmultifuncionalcheque> getColeccionTxCheque(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal) throws EntityServicioExcepcion {
        return this.getColeccionTxCheque(codigoCajero, fechaInicial, fechaFinal, null, null);
    }
    
    public Collection<Txmultifuncionalcheque> getColeccionTxCheque(final Integer codigoCajero, final Date fecha) throws EntityServicioExcepcion {
        return this.getColeccionTxCheque(codigoCajero, fecha, null, null, null);
    }
    
    public Txmultifuncionalcheque getTxCheque(final Integer codigoCajero, final Date fechaProceso, final Integer numeroTransaccion) throws EntityServicioExcepcion {
        Txmultifuncionalcheque reg = null;
        try {
            final Date fInicial = (fechaProceso != null) ? fechaProceso : Fecha.getDateHoy();
            final Date fFin = Fecha.getFechaFinDia(fInicial);
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            final Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createNamedQuery("Txmultifuncionalcheque.TransaccionBuscarReintegro");
                query.setParameter("codigoCajero", (Object)cCajero);
                query.setParameter("fechaInicial", (Object)fInicial).setParameter("fechaFinal", (Object)fFin);
                query.setParameter("numeroTransaccion", (Object)nTran);
                reg = (Txmultifuncionalcheque)query.getSingleResult();
            }
        }
        catch (IllegalStateException ex) {
            TxMultifuncionalChequeServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TxMultifuncionalChequeServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return reg;
    }
    
    public Collection<Txmultifuncionalcheque> getColeccionTxCheque(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal, final Integer numeroTransaccion, final String cuenta) throws EntityServicioExcepcion {
        Collection<Txmultifuncionalcheque> regs = null;
        String strQuery = "select object(obj) from Txmultifuncionalcheque obj         where  obj.txmultifuncionalchequePK.fechacajero between :fechaInicial and :fechaFinal ";
        final String orderQuery = "        order by obj.txmultifuncionalchequePK.fechacajero";
        try {
            if (codigoCajero != 0) {
                strQuery += "          and obj.txmultifuncionalchequePK.codigocajero =:codigoCajero";
            }
            if (numeroTransaccion != null) {
                strQuery += "          and obj.txmultifuncionalchequePK.codigotransaccion = :numeroTransaccion";
            }
            if (cuenta != null && !cuenta.equals("")) {
                strQuery += "          and obj.numerocuentaconsignar = :cuenta";
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
                if (cuenta != null && !cuenta.equals("")) {
                    query.setParameter("cuenta", (Object)cuenta);
                }
                regs = (Collection<Txmultifuncionalcheque>)query.getResultList();
            }
        }
        catch (IllegalStateException ex) {
            TxMultifuncionalChequeServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TxMultifuncionalChequeServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return regs;
    }
}
