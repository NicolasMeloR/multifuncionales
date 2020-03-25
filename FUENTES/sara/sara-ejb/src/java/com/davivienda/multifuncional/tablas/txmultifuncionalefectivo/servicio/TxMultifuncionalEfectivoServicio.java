// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.txmultifuncionalefectivo.servicio;

import javax.persistence.Query;
import com.davivienda.sara.dto.TxMultifuncionalEfeDTO;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.entitys.TxmultifuncionalefectivoHisto;
import java.util.logging.Level;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import com.davivienda.sara.base.BaseEntityServicio;

public class TxMultifuncionalEfectivoServicio extends BaseEntityServicio<Txmultifuncionalefectivo> implements AdministracionTablasInterface<Txmultifuncionalefectivo>
{
    public TxMultifuncionalEfectivoServicio(final EntityManager em) {
        super(em, Txmultifuncionalefectivo.class);
    }
    
    public Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.getColeccionTxEfectivo(codigoCajero, fechaInicial, fechaFinal, null, null, fechaHisto);
    }
    
    public Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(final Integer codigoCajero, final Date fecha, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.getColeccionTxEfectivo(codigoCajero, fecha, null, null, null, fechaHisto);
    }
    
    public Txmultifuncionalefectivo getTxEfectivo(final Integer codigoCajero, final Date fechaProceso, final Integer numeroTransaccion, final Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.INFO, "TxMultifuncionalEfectivoServicio - getTxEfectivo fechaProceso: " + fechaProceso + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.INFO, "TxMultifuncionalEfectivoServicio - Consultando tabla historico: " + TxmultifuncionalefectivoHisto.class.getSimpleName());
            nombreTabla = "TxmultifuncionalefectivoHisto";
            tablaHisto = true;
        }
        else {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.INFO, "TxMultifuncionalEfectivoServicio - Consultando tabla : " + Txmultifuncionalefectivo.class.getSimpleName());
            nombreTabla = "Txmultifuncionalefectivo";
        }
        Txmultifuncionalefectivo reg = null;
        try {
            final Date fInicial = (fechaProceso != null) ? fechaProceso : Fecha.getDateHoy();
            final Date fFin = Fecha.getFechaFinDia(fInicial);
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            final Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createNamedQuery(nombreTabla + ".TransaccionBuscarReintegro");
                query.setParameter("codigoCajero", (Object)cCajero);
                query.setParameter("fechaInicial", (Object)fInicial).setParameter("fechaFinal", (Object)fFin);
                query.setParameter("numeroTransaccion", (Object)nTran);
                if (tablaHisto) {
                    reg = new TxMultifuncionalEfeDTO(TxMultifuncionalEfectivoServicio.configApp.loggerApp).txMultiEfeHistoATxMultiEfe((TxmultifuncionalefectivoHisto)query.getSingleResult());
                }
                else {
                    reg = (Txmultifuncionalefectivo)query.getSingleResult();
                }
            }
        }
        catch (IllegalStateException ex) {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        catch (Exception ex3) {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "Error no conocido ", ex3);
            throw new EntityServicioExcepcion(ex3);
        }
        return reg;
    }
    
    public Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal, final Integer numeroTransaccion, final String cuenta, final Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - getTransaccionTalonUnico fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla historico: " + TxmultifuncionalefectivoHisto.class.getSimpleName());
            nombreTabla = "TxmultifuncionalefectivoHisto";
            tablaHisto = true;
        }
        else {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla : " + Txmultifuncionalefectivo.class.getSimpleName());
            nombreTabla = "Txmultifuncionalefectivo";
        }
        Collection<Txmultifuncionalefectivo> regs = null;
        String strQuery = "select object(obj) from " + nombreTabla + " obj " + "        where  obj.txmultifuncionalefectivoPK.fechacajero between :fechaInicial and :fechaFinal ";
        final String orderQuery = "        order by obj.txmultifuncionalefectivoPK.fechacajero";
        try {
            if (codigoCajero != 0) {
                strQuery += "          and obj.txmultifuncionalefectivoPK.codigocajero =:codigoCajero";
            }
            if (numeroTransaccion != null) {
                strQuery += "          and obj.txmultifuncionalefectivoPK.codigotx =:numeroTransaccion";
            }
            if (cuenta != null && !cuenta.equals("")) {
                strQuery += "           and obj.numerocuentaconsignar =:cuenta";
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
                if (tablaHisto) {
                    regs = (Collection<Txmultifuncionalefectivo>)new TxMultifuncionalEfeDTO(TxMultifuncionalEfectivoServicio.configApp.loggerApp).txMultiEfeHistoATxMultiEfe((Collection)query.getResultList());
                }
                else {
                    regs = (Collection<Txmultifuncionalefectivo>)query.getResultList();
                }
            }
        }
        catch (IllegalStateException ex) {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return regs;
    }
    
    public void guardarArchivoMultifuncionalEfectivo(final Integer codigoCajero) throws EntityServicioExcepcion {
        String strQuery = "select object(obj) from Txmultifuncionalefectivo obj         where  obj.txmultifuncionalefectivoPK.fechacajero between :fechaInicial and :fechaFinal ";
        final String orderQuery = "        order by obj.txmultifuncionalefectivoPK.fechacajero";
        try {
            if (codigoCajero != 0) {
                strQuery += "          and obj.txmultifuncionalefectivoPK.codigocajero =:codigoCajero";
            }
            strQuery += orderQuery;
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createQuery(strQuery);
                if (codigoCajero != 0) {
                    query.setParameter("codigoCajero", (Object)cCajero);
                }
                query.executeUpdate();
            }
        }
        catch (IllegalStateException ex) {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void cargarDiariosMultiEfectivo() throws EntityServicioExcepcion {
        try {
            this.ejecutarSpDesdeJob("SP_CARGARDIARIOEFECTIVOMULTI;");
        }
        catch (IllegalStateException ex) {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void cargarDiariosMultiCheque() throws EntityServicioExcepcion {
        try {
            final Query query = this.em.createNativeQuery("BEGIN SP_CARGARDIARIOCHEQUEMULTI; END;");
            query.executeUpdate();
        }
        catch (IllegalStateException ex) {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void cargarLogEventos() throws EntityServicioExcepcion {
        try {
            final Query query = this.em.createNativeQuery("BEGIN SP_CARGARLOGCAJEROSMULTI; END;");
            query.executeUpdate();
        }
        catch (IllegalStateException ex) {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            TxMultifuncionalEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
}
