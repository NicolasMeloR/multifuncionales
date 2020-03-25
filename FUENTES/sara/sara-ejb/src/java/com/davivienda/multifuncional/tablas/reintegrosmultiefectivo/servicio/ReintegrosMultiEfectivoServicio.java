// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.servicio;

import java.util.List;
import java.util.ArrayList;
import com.davivienda.sara.entitys.ReintegrosMultifuncional;
import javax.persistence.Query;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.dto.ReintegrosMultiEfeDTO;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.entitys.ReintegrosmultiefectivoHisto;
import java.util.logging.Level;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import com.davivienda.sara.base.BaseEntityServicio;

public class ReintegrosMultiEfectivoServicio extends BaseEntityServicio<Reintegrosmultiefectivo> implements AdministracionTablasInterface<Reintegrosmultiefectivo>
{
    public ReintegrosMultiEfectivoServicio(final EntityManager em) {
        super(em, Reintegrosmultiefectivo.class);
    }
    
    public Collection<Reintegrosmultiefectivo> getReintegros(final Date fechaInicial, final Date fechaFinal, final Integer codigCajero, final Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - getReintegros fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal + " codigCajero: " + codigCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Consultando tabla historico: " + ReintegrosmultiefectivoHisto.class.getSimpleName());
            nombreTabla = "ReintegrosmultiefectivoHisto";
            tablaHisto = true;
        }
        else {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Consultando tabla : " + Reintegrosmultiefectivo.class.getSimpleName());
            nombreTabla = "Reintegrosmultiefectivo";
        }
        Date fFinalCiclo = fechaFinal;
        if (fechaInicial.equals(fechaFinal)) {
            fFinalCiclo = Fecha.getFechaFinDia(fechaInicial);
        }
        Collection<Reintegrosmultiefectivo> items = null;
        try {
            Query query = null;
            if (codigCajero == 0) {
                query = this.em.createNamedQuery(nombreTabla + ".RangoFecha");
            }
            else {
                query = this.em.createNamedQuery(nombreTabla + ".CajeroFecha");
                query.setParameter("codigocajero", (Object)codigCajero);
            }
            query.setParameter("fechaInicial", (Object)fechaInicial);
            query.setParameter("fechaFinal", (Object)fFinalCiclo);
            if (tablaHisto) {
                items = (Collection<Reintegrosmultiefectivo>)new ReintegrosMultiEfeDTO(ReintegrosMultiEfectivoServicio.configApp.loggerApp).reintegrosMultiEfeHistoAReintegrosMultiEfe((Collection)query.getResultList());
            }
            else {
                items = (Collection<Reintegrosmultiefectivo>)query.getResultList();
            }
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - getReintegros items: " + items.size());
        }
        catch (IllegalStateException ex) {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
    
    public Collection<ReintegrosMultifuncional> getReintegrosMultifuncional(final Date fechaInicial, final Date fechaFinal, final Integer codigCajero) throws EntityServicioExcepcion {
        Date fFinalCiclo = fechaFinal;
        if (fechaInicial.equals(fechaFinal)) {
            fFinalCiclo = Fecha.getFechaFinDia(fechaInicial);
        }
        Collection<ReintegrosMultifuncional> items = null;
        try {
            Query query = null;
            if (codigCajero == 0 || codigCajero == 9999) {
                query = this.em.createNamedQuery("ReintegrosMultifuncional.RangoFecha");
                ReintegrosMultiEfectivoServicio.configApp.loggerApp.info("getReintegrosMultifuncional: RangoFecha");
            }
            else {
                query = this.em.createNamedQuery("ReintegrosMultifuncional.CajeroFecha");
                ReintegrosMultiEfectivoServicio.configApp.loggerApp.info("getReintegrosMultifuncional: CajeroFecha");
                query.setParameter("codigocajero", (Object)codigCajero);
            }
            query.setParameter("fechaInicial", (Object)fechaInicial);
            query.setParameter("fechaFinal", (Object)fFinalCiclo);
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.info("getReintegrosMultifuncional: fechaInicial " + fechaInicial);
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.info("getReintegrosMultifuncional: fFinalCiclo " + fFinalCiclo);
            query.setHint("javax.persistence.cache.storeMode", (Object)"REFRESH");
            items = (Collection<ReintegrosMultifuncional>)query.getResultList();
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - getReintegrosMultifuncional items: " + items.size());
        }
        catch (IllegalStateException ex) {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
    
    public Reintegrosmultiefectivo getReintegroXLlave(final Integer codigoCajero, final Date fechaProceso, final Integer numeroTransaccion, final Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - getReintegros fechaProceso: " + fechaProceso + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Consultando tabla historico: " + ReintegrosmultiefectivoHisto.class.getSimpleName());
            nombreTabla = "ReintegrosmultiefectivoHisto";
            tablaHisto = true;
        }
        else {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Consultando tabla : " + Reintegrosmultiefectivo.class.getSimpleName());
            nombreTabla = "Reintegrosmultiefectivo";
        }
        Reintegrosmultiefectivo reg = null;
        try {
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            final Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createNamedQuery(nombreTabla + ".RegistroUnico");
                query.setParameter("codigoCajero", (Object)cCajero);
                query.setParameter("fechaSistema", (Object)fechaProceso);
                query.setParameter("talon", (Object)nTran);
                if (tablaHisto) {
                    reg = new ReintegrosMultiEfeDTO(ReintegrosMultiEfectivoServicio.configApp.loggerApp).reintegrosMultiEfeHistoAReintegrosMultiEfe((ReintegrosmultiefectivoHisto)query.getSingleResult());
                }
                else {
                    reg = (Reintegrosmultiefectivo)query.getSingleResult();
                }
                ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Retornando reg : " + reg);
            }
        }
        catch (IllegalStateException ex) {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        catch (Exception ex3) {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "Error no conocido ", ex3);
            throw new EntityServicioExcepcion(ex3);
        }
        return reg;
    }
    
    public Reintegrosmultiefectivo getReintegroXCuentaValor(final Integer codigoCajero, final Date fechaProceso, final Integer numeroTransaccion, final String numeroCuenta, final Long valor, final Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - getReintegros fechaProceso: " + fechaProceso + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Consultando tabla historico: " + ReintegrosmultiefectivoHisto.class.getSimpleName());
            nombreTabla = "ReintegrosmultiefectivoHisto";
            tablaHisto = true;
        }
        else {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Consultando tabla : " + Reintegrosmultiefectivo.class.getSimpleName());
            nombreTabla = "Reintegrosmultiefectivo";
        }
        Reintegrosmultiefectivo reg = null;
        try {
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            final Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0;
            final Date fechaInicial = Fecha.getFechaFinDia(Fecha.getCalendar(Fecha.getCalendar(fechaProceso), -1)).getTime();
            final Date fechaFinal = Fecha.getFechaFinDia(Fecha.getCalendar(fechaProceso)).getTime();
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createNamedQuery(nombreTabla + ".CajeroTalonCuenta");
                query.setParameter("codigoCajero", (Object)cCajero);
                query.setParameter("fechaInicial", (Object)fechaInicial);
                query.setParameter("fechaFinal", (Object)fechaFinal);
                query.setParameter("talon", (Object)nTran);
                query.setParameter("numeroCuenta", (Object)numeroCuenta);
                query.setParameter("valor", (Object)valor);
                if (tablaHisto) {
                    List result = new ArrayList();
                    result = query.getResultList();
                    if (result.size() > 0) {
                        reg = new ReintegrosMultiEfeDTO(ReintegrosMultiEfectivoServicio.configApp.loggerApp).reintegrosMultiEfeHistoAReintegrosMultiEfe((ReintegrosmultiefectivoHisto)result.get(0));
                    }
                }
                else {
                    List result = new ArrayList();
                    result = query.getResultList();
                    if (result != null && result.size() > 0) {
                        reg = (Reintegrosmultiefectivo) result.get(0);
                    }
                }
                ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Retornando reg : " + reg);
            }
        }
        catch (IllegalStateException ex) {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        catch (Exception ex3) {
            ReintegrosMultiEfectivoServicio.configApp.loggerApp.log(Level.SEVERE, "Error no conocido ", ex3);
            throw new EntityServicioExcepcion(ex3);
        }
        return reg;
    }
}
