// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.notasdebito.servicio;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.Query;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.dto.NotasDebitoMultiDTO;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.entitys.NotasdebitomultifuncionalHisto;
import java.util.logging.Level;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import com.davivienda.sara.base.BaseEntityServicio;

public class NotasDebitoMultiServicio extends BaseEntityServicio<Notasdebitomultifuncional> implements AdministracionTablasInterface<Notasdebitomultifuncional>
{
    public NotasDebitoMultiServicio(final EntityManager em) {
        super(em, Notasdebitomultifuncional.class);
    }
    
    public Collection<Notasdebitomultifuncional> getNotasDebito(final Date fechaInicial, final Date fechaFinal, final Integer codigCajero, final Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        NotasDebitoMultiServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - getNotasDebito fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal + " codigCajero: " + codigCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Consultando tabla historico: " + NotasdebitomultifuncionalHisto.class.getSimpleName());
            nombreTabla = "NotasdebitomultifuncionalHisto";
            tablaHisto = true;
        }
        else {
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Consultando tabla : " + Notasdebitomultifuncional.class.getSimpleName());
            nombreTabla = "Notasdebitomultifuncional";
        }
        final Date fFinalCiclo = Fecha.getFechaFinDia(fechaInicial);
        Collection<Notasdebitomultifuncional> items = null;
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
                items = (Collection<Notasdebitomultifuncional>)new NotasDebitoMultiDTO(NotasDebitoMultiServicio.configApp.loggerApp).notasdebitoMultiHistoANotasdebitoMulti((Collection)query.getResultList());
            }
            else {
                items = (Collection<Notasdebitomultifuncional>)query.getResultList();
            }
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - getReintegros items: " + items.size());
        }
        catch (IllegalStateException ex) {
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
    
    public Notasdebitomultifuncional getNotasDebitoXLlave(final Integer codigoCajero, final Date fechaProceso, final Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        NotasDebitoMultiServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - getNotasDebitoXLlave fechaProceso: " + fechaProceso + " codigCajero: " + fechaProceso + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Consultando tabla historico: " + NotasdebitomultifuncionalHisto.class.getSimpleName());
            nombreTabla = "NotasdebitomultifuncionalHisto";
            tablaHisto = true;
        }
        else {
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Consultando tabla : " + Notasdebitomultifuncional.class.getSimpleName());
            nombreTabla = "Notasdebitomultifuncional";
        }
        Notasdebitomultifuncional reg = null;
        try {
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createNamedQuery(nombreTabla + ".RegistroUnico");
                query.setParameter("codigoCajero", (Object)cCajero);
                query.setParameter("fecha", (Object)fechaProceso);
                reg = (Notasdebitomultifuncional)query.getSingleResult();
                if (tablaHisto) {
                    reg = new NotasDebitoMultiDTO(NotasDebitoMultiServicio.configApp.loggerApp).notasdebitoMultiHistoANotasdebitoMulti((NotasdebitomultifuncionalHisto)query.getResultList());
                }
                else {
                    reg = (Notasdebitomultifuncional)query.getSingleResult();
                }
                NotasDebitoMultiServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Retornando reg : " + reg);
            }
        }
        catch (IllegalStateException ex) {
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        catch (Exception ex3) {
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.SEVERE, "Error no conocido ", ex3);
            throw new EntityServicioExcepcion(ex3);
        }
        return reg;
    }
    
    public Notasdebitomultifuncional getNotasDebitoXCuentaValor(final Integer codigoCajero, final Date fechaProceso, final String numeroCuenta, final Long valor, final Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        NotasDebitoMultiServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - getNotasDebitoXCuentaValor fechaProceso: " + fechaProceso + " codigCajero: " + fechaProceso + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Consultando tabla historico: " + NotasdebitomultifuncionalHisto.class.getSimpleName());
            nombreTabla = "NotasdebitomultifuncionalHisto";
            tablaHisto = true;
        }
        else {
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Consultando tabla : " + Notasdebitomultifuncional.class.getSimpleName());
            nombreTabla = "Notasdebitomultifuncional";
        }
        Notasdebitomultifuncional reg = null;
        try {
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            final Date fechaInicial = Fecha.getFechaFinDia(Fecha.getCalendar(Fecha.getCalendar(fechaProceso), -1)).getTime();
            final Date fechaFinal = Fecha.getFechaFinDia(Fecha.getCalendar(fechaProceso)).getTime();
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createNamedQuery(nombreTabla + ".CajeroCuentaFechaValor");
                query.setParameter("codigoCajero", (Object)cCajero);
                query.setParameter("fechaInicial", (Object)fechaInicial);
                query.setParameter("fechaFinal", (Object)fechaFinal);
                query.setParameter("numeroCuenta", (Object)numeroCuenta);
                query.setParameter("valor", (Object)valor);
                if (tablaHisto) {
                    List result = new ArrayList();
                    result = query.getResultList();
                    if (result.size() > 0) {
                        reg = new NotasDebitoMultiDTO(NotasDebitoMultiServicio.configApp.loggerApp).notasdebitoMultiHistoANotasdebitoMulti((NotasdebitomultifuncionalHisto)result.get(0));
                    }
                }
                else {
                    List result = new ArrayList();
                    result = query.getResultList();
                    if (result != null && result.size() > 0) {
                        reg = (Notasdebitomultifuncional) result.get(0);
                    }
                }
            }
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Retornando reg : " + reg);
        }
        catch (IllegalStateException ex) {
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        catch (Exception ex3) {
            NotasDebitoMultiServicio.configApp.loggerApp.log(Level.SEVERE, "Error no conocido ", ex3);
            throw new EntityServicioExcepcion(ex3);
        }
        return reg;
    }
}
