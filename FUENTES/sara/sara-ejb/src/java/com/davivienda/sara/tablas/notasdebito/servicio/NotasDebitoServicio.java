// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.notasdebito.servicio;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.Query;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.dto.NotasDebitoDTO;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.entitys.NotasdebitoHisto;
import java.util.logging.Level;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.base.BaseEntityServicio;
import java.text.SimpleDateFormat;

public class NotasDebitoServicio extends BaseEntityServicio<Notasdebito> implements AdministracionTablasInterface<Notasdebito>
{
    public NotasDebitoServicio(final EntityManager em) {
        super(em, Notasdebito.class);
    }
    
    public Collection<Notasdebito> getNotasDebito(final Date fechaInicial, final Date fechaFinal, final Integer codigCajero, final Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        NotasDebitoServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - getNotasDebito fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal + " codigCajero: " + codigCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            NotasDebitoServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Consultando tabla historico: " + NotasdebitoHisto.class.getSimpleName());
            nombreTabla = "NotasdebitoHisto";
            tablaHisto = true;
        }
        else {
            NotasDebitoServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Consultando tabla : " + Notasdebito.class.getSimpleName());
            nombreTabla = "Notasdebito";
        }
        final Date fFinalCiclo = Fecha.getFechaFinDia(fechaInicial);
        Collection<Notasdebito> items = null;
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
            items = (Collection<Notasdebito>)query.getResultList();
            if (tablaHisto) {
                items = (Collection<Notasdebito>)new NotasDebitoDTO(NotasDebitoServicio.configApp.loggerApp).notasdebitoHistoANotasdebito((Collection)query.getResultList());
            }
            else {
                items = (Collection<Notasdebito>)query.getResultList();
            }
            NotasDebitoServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - getNotasDebito items: " + items.size());
        }
        catch (IllegalStateException ex) {
            NotasDebitoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            NotasDebitoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
    
    public Notasdebito getNotasDebitoXLlave(final Integer codigoCajero, final Date fechaProceso, final Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        NotasDebitoServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - getNotasDebitoXLlave fechaProceso: " + fechaProceso + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            NotasDebitoServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Consultando tabla historico: " + NotasdebitoHisto.class.getSimpleName());
            nombreTabla = "NotasdebitoHisto";
            tablaHisto = true;
        }
        else {
            NotasDebitoServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Consultando tabla : " + Notasdebito.class.getSimpleName());
            nombreTabla = "Notasdebito";
        }
        Notasdebito reg = null;
        try {
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createNamedQuery(nombreTabla + ".RegistroUnico");
                query.setParameter("codigoCajero", (Object)cCajero);
                query.setParameter("fecha", (Object)fechaProceso);
                if (tablaHisto) {
                    reg = new NotasDebitoDTO(NotasDebitoServicio.configApp.loggerApp).notasdebitoHistoANotasdebito((NotasdebitoHisto)query.getSingleResult());
                }
                else {
                    reg = (Notasdebito)query.getSingleResult();
                }
            }
            NotasDebitoServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Retornando reg : " + reg);
        }
        catch (IllegalStateException ex) {
            NotasDebitoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            NotasDebitoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        catch (Exception ex3) {
            NotasDebitoServicio.configApp.loggerApp.log(Level.SEVERE, "Error no conocido ", ex3);
            throw new EntityServicioExcepcion(ex3);
        }
        return reg;
    }
    
    public Notasdebito getNotasDebitoXCuentaValor(final Integer codigoCajero, final Date fechaProceso, final String numeroCuenta, final Long valor, final Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        NotasDebitoServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - getNotasDebitoXCuentaValor fechaProceso: " + fechaProceso + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            NotasDebitoServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Consultando tabla historico: " + NotasdebitoHisto.class.getSimpleName());
            nombreTabla = "NotasdebitoHisto";
            tablaHisto = true;
        }
        else {
            NotasDebitoServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Consultando tabla : " + Notasdebito.class.getSimpleName());
            nombreTabla = "Notasdebito";
        }
        Notasdebito reg = null;
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
                        reg = new NotasDebitoDTO(NotasDebitoServicio.configApp.loggerApp).notasdebitoHistoANotasdebito((NotasdebitoHisto)result.get(0));
                    }
                }
                else {
                    List result = new ArrayList();
                    result = query.getResultList();
                    if (result != null && result.size() > 0) {
                        reg = (Notasdebito) result.get(0);
                    }
                }
                NotasDebitoServicio.configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Retornando reg : " + reg);
            }
        }
        catch (IllegalStateException ex) {
            NotasDebitoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            NotasDebitoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        catch (Exception ex3) {
            NotasDebitoServicio.configApp.loggerApp.log(Level.SEVERE, "Error no conocido ", ex3);
            throw new EntityServicioExcepcion(ex3);
        }
        return reg;
    }

    public String findByPrimayKey(Integer codigoCajero, Date fechaSistema, String numeroCuenta) throws EntityServicioExcepcion {
        Notasdebito reg = null;
        try {
            String formatDate = "dd-MM-YY HH:mm:ss.SSS";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
            String nuevaFechaSistema = simpleDateFormat.format(fechaSistema);
            String queryString = "select r.* from notasdebito r  where r.codigocajero = ? and r.numerocuenta = ? and to_char(r.fecha,'DD-mm-RR HH24:MI:SS.FF3') = ?";
            configApp.loggerApp.log(Level.INFO, "queryString :" + queryString);
            Query query = em.createNativeQuery(queryString, Notasdebito.class);
            query.setParameter(1, codigoCajero);
            query.setParameter(2, numeroCuenta);
            query.setParameter(3, nuevaFechaSistema);
            reg = (Notasdebito) query.getSingleResult();
            if (reg != null) {
                return reg.toString();
            } else {
                return null;
            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (Exception ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error no conocido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
    }
}
