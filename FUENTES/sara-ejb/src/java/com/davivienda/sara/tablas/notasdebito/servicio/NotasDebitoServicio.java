package com.davivienda.sara.tablas.notasdebito.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.base.BaseEntityServicio;

import com.davivienda.sara.entitys.Notasdebito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Calendar;
import com.davivienda.sara.base.AdministracionTablasInterface;
import static com.davivienda.sara.constantes.TipoCuentaReintegro.NotasDebito;
import com.davivienda.sara.dto.NotasDebitoDTO;
import com.davivienda.sara.dto.ReintegrosDTO;
import com.davivienda.sara.entitys.NotasdebitoHisto;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.ReintegrosHisto;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

/**
 * OccaServicio - 21/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class NotasDebitoServicio extends BaseEntityServicio<Notasdebito> implements AdministracionTablasInterface<Notasdebito> {

    {

    }

    public NotasDebitoServicio(EntityManager em) {
        super(em, Notasdebito.class);

    }

    /**
     * Retorna los NotasDebito de la fecha dada
     *
     * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
    @SuppressWarnings("unchecked")
    public Collection<Notasdebito> getNotasDebito(Date fechaInicial, Date fechaFinal, Integer codigCajero, Date fechaHisto) throws EntityServicioExcepcion {

        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - getNotasDebito fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal
                + " codigCajero: " + codigCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Consultando tabla historico: " + NotasdebitoHisto.class.getSimpleName());
            nombreTabla = "NotasdebitoHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Consultando tabla : " + Notasdebito.class.getSimpleName());
            nombreTabla = "Notasdebito";
        }
        Date fFinalCiclo = null;
        if (fechaFinal != null) {
            fFinalCiclo = fechaFinal;
        } else {
            fFinalCiclo = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
        }

        Collection<Notasdebito> items = null;
        try {
            Query query = null;
            if (codigCajero == 0) {
                query = em.createNamedQuery(nombreTabla + ".RangoFecha");
            } else {
                query = em.createNamedQuery(nombreTabla + ".CajeroFecha");
                query.setParameter("codigocajero", codigCajero);
            }
            query.setParameter("fechaInicial", fechaInicial);
            query.setParameter("fechaFinal", fFinalCiclo);
            items = query.getResultList();

            if (tablaHisto) {
                items = new NotasDebitoDTO(configApp.loggerApp).notasdebitoHistoANotasdebito(query.getResultList());
            } else {
                items = query.getResultList();
            }
            configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - getNotasDebito items: " + items.size());

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return items;
    }

    /**
     * Retorna un objeto Reintegro que cumpla con los parámetros
     *
     * @param codigoCajero
     * @param fechaProceso
     * @param numeroTransaccion
     * @return NotasDebito
     * @throws EntityServicioExcepcion
     */
    public Notasdebito getNotasDebitoXLlave(Integer codigoCajero, Date fechaProceso, Date fechaHisto) throws EntityServicioExcepcion {

        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - getNotasDebitoXLlave fechaProceso: " + fechaProceso
                + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Consultando tabla historico: " + NotasdebitoHisto.class.getSimpleName());
            nombreTabla = "NotasdebitoHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Consultando tabla : " + Notasdebito.class.getSimpleName());
            nombreTabla = "Notasdebito";
        }

        Notasdebito reg = null;
        try {

            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;

            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery(nombreTabla + ".RegistroUnico");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fecha", fechaProceso);

                if (tablaHisto) {
                    reg = new NotasDebitoDTO(configApp.loggerApp).notasdebitoHistoANotasdebito((NotasdebitoHisto) query.getSingleResult());
                } else {
                    reg = (Notasdebito) query.getSingleResult();
                }
            }
            configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Retornando reg : " + reg);
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
        return reg;
    }

    public Notasdebito getNotasDebitoXCuentaValor(Integer codigoCajero, Date fechaProceso, String numeroCuenta, Long valor, Date fechaHisto) throws EntityServicioExcepcion {

        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - getNotasDebitoXCuentaValor fechaProceso: " + fechaProceso
                + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Consultando tabla historico: " + NotasdebitoHisto.class.getSimpleName());
            nombreTabla = "NotasdebitoHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Consultando tabla : " + Notasdebito.class.getSimpleName());
            nombreTabla = "Notasdebito";
        }

        Notasdebito reg = null;
        Date fechaInicial;
        Date fechaFinal;

        try {

            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            fechaInicial = Fecha.getFechaFinDia(Fecha.getCalendar(Fecha.getCalendar(fechaProceso), -1)).getTime();
            fechaFinal = Fecha.getFechaFinDia(Fecha.getCalendar(fechaProceso)).getTime();
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery(nombreTabla + ".CajeroCuentaFechaValor");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaInicial", fechaInicial);
                query.setParameter("fechaFinal", fechaFinal);
                query.setParameter("numeroCuenta", numeroCuenta);
                query.setParameter("valor", valor);

                if (tablaHisto) {
                    List result = new ArrayList<NotasdebitoHisto>();
                    result = query.getResultList();
                    if (result.size() > 0) {
                        reg = new NotasDebitoDTO(configApp.loggerApp).notasdebitoHistoANotasdebito((NotasdebitoHisto) result.get(0));
                    }
                } else {
                    List result = new ArrayList<Reintegros>();
                    result = query.getResultList();
                    if (result != null) {
                        if (result.size() > 0) {
                            reg = (Notasdebito) result.get(0);
                        }
                    }
                }

                configApp.loggerApp.log(Level.INFO, "NotasDebitoServicio - Retornando reg : " + reg);
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
