package com.davivienda.sara.tablas.reintegros.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.dto.ReintegrosDTO;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.ReintegrosHisto;
import com.davivienda.sara.entitys.ReintegrosPK;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.davivienda.utilidades.conversion.Fecha;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;

/**
 * OccaServicio - 21/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ReintegrosServicio extends BaseEntityServicio<Reintegros> {

    public ReintegrosServicio(EntityManager em) {
        super(em, Reintegros.class);
    }

    /**
     * Retorna los Reintegros de la fecha dada
     *
     * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
    @SuppressWarnings("unchecked")
    public Collection<Reintegros> getReintegros(Date fechaInicial, Date fechaFinal, Integer codigCajero, Date fechaHisto) throws EntityServicioExcepcion {

        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - getReintegros fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal
                + " codigCajero: " + codigCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla historico: " + ReintegrosHisto.class.getSimpleName());
            nombreTabla = "ReintegrosHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla : " + Reintegros.class.getSimpleName());
            nombreTabla = "Reintegros";
        }

        Date fFinalCiclo = fechaFinal;
        if (fechaInicial.equals(fechaFinal)) {
            fFinalCiclo = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
        }

        Collection<Reintegros> items = null;
        try {
            Query query = null;
            em.flush();
            if (codigCajero == 0) {
                query = em.createNamedQuery(nombreTabla + ".RangoFecha");
            } else {
                query = em.createNamedQuery(nombreTabla + ".CajeroFecha");
                query.setParameter("codigocajero", codigCajero);
            }
            query.setParameter("fechaInicial", fechaInicial);
            query.setParameter("fechaFinal", fFinalCiclo);

            configApp.loggerApp.log(Level.INFO, "Collection<Reintegros> getReintegros :: " + query.toString());

            if (tablaHisto) {
                items = new ReintegrosDTO(configApp.loggerApp).reintegrosHistoAReintegros(query.getResultList());
            } else {
                items = query.getResultList();
            }

            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - getReintegros items: " + items.size());

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return items;
    }

    @SuppressWarnings("unchecked")
    public Collection<Reintegros> getReintegros(Date fecha, Integer codigCajero, Date fechaHisto) throws EntityServicioExcepcion {

        configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - getReintegros fecha: " + fecha
                + " codigCajero: " + codigCajero + " fechaHisto: " + fechaHisto);

        String nombreTabla = "";
        boolean tablaHisto = false;
        if (fecha.compareTo(fechaHisto) < 0 || fecha.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla historico: " + ReintegrosHisto.class.getSimpleName());
            nombreTabla = "ReintegrosHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla : " + Reintegros.class.getSimpleName());
            nombreTabla = "Reintegros";
        }
        Collection<Reintegros> items = null;
        try {
            Calendar fechaInicial = Calendar.getInstance();
            fechaInicial.setTimeInMillis(fecha.getTime());
            fechaInicial.set(Calendar.HOUR_OF_DAY, 0);
            fechaInicial.set(Calendar.MINUTE, 0);
            fechaInicial.set(Calendar.SECOND, 0);
            Calendar fechaFinal = Calendar.getInstance();
            fechaFinal.setTimeInMillis(fecha.getTime());
            fechaFinal.set(Calendar.HOUR_OF_DAY, 23);
            fechaFinal.set(Calendar.MINUTE, 59);
            fechaFinal.set(Calendar.SECOND, 59);

            Query query = em.createNamedQuery(nombreTabla + ".RangoFechasXCajero");
            query.setParameter("codigoCajero", codigCajero);
            query.setParameter("fechaInicial", new Timestamp(fechaInicial.getTimeInMillis()));
            query.setParameter("fechaFinal", new Timestamp(fechaFinal.getTimeInMillis()));

            if (tablaHisto) {
                items = new ReintegrosDTO(configApp.loggerApp).reintegrosHistoAReintegros(query.getResultList());
            } else {
                items = query.getResultList();
            }
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - getReintegros items: " + items.size());

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return items;
    }

    public Reintegros getReintegroXLlave(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, Date fechaHisto) throws EntityServicioExcepcion {

        configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - getReintegroXLlave fechaInicial: " + fechaProceso
                + " codigoCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);

        String nombreTabla = "";
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla historico: " + ReintegrosHisto.class.getSimpleName());
            nombreTabla = "ReintegrosHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla : " + Reintegros.class.getSimpleName());
            nombreTabla = "Reintegros";
        }

        Reintegros reg = null;
        try {

            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0000;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery(nombreTabla + ".RegistroUnico");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaSistema", fechaProceso);
                query.setParameter("talon", nTran);

                if (tablaHisto) {
                    reg = new ReintegrosDTO(configApp.loggerApp).reintegrosHistoAReintegros((ReintegrosHisto) query.getSingleResult());
                } else {
                    reg = (Reintegros) query.getSingleResult();
                }
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

    /**
     * Retorna un objeto Reintegro que cumpla con los parámetros
     *
     * @param codigoCajero
     * @param fechaProceso
     * @param numeroTransaccion
     * @return Reintegros
     * @throws EntityServicioExcepcion
     */
    public Reintegros getReintegro(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, Date fechaHisto) throws EntityServicioExcepcion {

        configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - getReintegroXLlave fechaInicial: " + fechaProceso
                + " codigoCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);

        String nombreTabla = "";
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla historico: " + ReintegrosHisto.class.getSimpleName());
            nombreTabla = "ReintegrosHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla : " + Reintegros.class.getSimpleName());
            nombreTabla = "Reintegros";
        }

        Reintegros reg = null;
        try {

            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0000;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery(nombreTabla + ".RegistroUnico");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaSistema", fechaProceso);
                query.setParameter("talon", nTran);

                if (tablaHisto) {
                    reg = new ReintegrosDTO(configApp.loggerApp).reintegrosHistoAReintegros((ReintegrosHisto) query.getSingleResult());
                } else {
                    reg = (Reintegros) query.getSingleResult();
                }
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

    /**
     * Retorna un objeto Reintegro que cumpla con los parámetros
     *
     * @param codigoCajero
     * @param fechaProceso
     * @param numeroTransaccion
     * @return Reintegros
     * @throws EntityServicioExcepcion
     */
    public Reintegros findByPrimayKey(Integer codigoCajero, Date fechaSistema, Integer talon, Date fechaHisto) throws EntityServicioExcepcion {

        String nombreTabla = "";
        if (fechaSistema.compareTo(fechaHisto) < 0 || fechaSistema.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla historico: " + ReintegrosHisto.class.getSimpleName());
            nombreTabla = "ReintegrosHisto";
        } else {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla : " + Reintegros.class.getSimpleName());
            nombreTabla = "Reintegros";
        }

        Reintegros reg = null;
        try {
            String formatDate = "dd-MM-YY HH:mm:ss.SSS";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
            String nuevaFechaSistema = simpleDateFormat.format(fechaSistema);
            configApp.loggerApp.log(Level.INFO, "nuevaFechaSistema" + nuevaFechaSistema + " codigoCajero" + codigoCajero + " talon" + talon);

            Query query = em.createNativeQuery("select r.* from " + nombreTabla + " r  where r.h_codigocajero = ? and to_char(r.h_fechasistema,'DD-mm-RR HH24:MI:SS.FF3') = ? and r.h_talon = ?", Reintegros.class);
            query.setParameter(1, codigoCajero);
            query.setParameter(2, nuevaFechaSistema);
            query.setParameter(3, talon);
            configApp.loggerApp.log(Level.INFO, "query" + query.toString());
            reg = (Reintegros) query.getSingleResult();
//            String queryString = "select r.* from " + nombreTabla + " r  where r.h_codigocajero = " + codigoCajero + " and r.h_fechasistema like'%" + nuevaFechaSistema + "%' and h_talon = " + talon;
//            configApp.loggerApp.log(Level.INFO, "queryString " + queryString);
//            Query query = em.createNativeQuery(queryString, Reintegros.class);
//            reg = (Reintegros) query.getSingleResult();
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

    /**
     * Retorna un objeto Reintegro que cumpla con los parámetros
     *
     * @param codigoCajero
     * @param fechaProceso
     * @param numeroTransaccion
     * @return Reintegros
     * @throws EntityServicioExcepcion
     */
    public Reintegros getReintegroXLlave(Integer codigoCajero, Date fechaSistema, Integer talon) throws EntityServicioExcepcion {
        Reintegros reg = null;
        try {
            Query query = null;
            query = em.createNamedQuery("Reintegros.ReintegroXLlave");
            query.setParameter("codigoCajero", codigoCajero);
            query.setParameter("fechaSistema", fechaSistema);
            query.setParameter("talon", talon);
            reg = (Reintegros) query.getSingleResult();
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

    public Reintegros getReintegroXCuentaValor(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion,
            String numeroCuenta, Long valor, Date fechaHisto) throws EntityServicioExcepcion {

        configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - getReintegroXCuentaValor fechaInicial: " + fechaProceso
                + " codigoCajero:" + codigoCajero + " fechaHisto: " + fechaHisto);

        String nombreTabla = "";
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla historico: " + ReintegrosHisto.class.getSimpleName());
            nombreTabla = "ReintegrosHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla : " + Reintegros.class.getSimpleName());
            nombreTabla = "Reintegros";
        }

        Reintegros reg = null;
        Date fechaInicial;
        Date fechaFinal;

        try {

            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0000;
            fechaInicial = Fecha.getFechaFinDia(Fecha.getCalendar(Fecha.getCalendar(fechaProceso), -1)).getTime();
            fechaFinal = Fecha.getFechaFinDia(Fecha.getCalendar(fechaProceso)).getTime();
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery(nombreTabla + ".CajeroTalonCuenta");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaInicial", fechaInicial);
                query.setParameter("fechaFinal", fechaFinal);
                query.setParameter("talon", nTran);
                query.setParameter("numeroCuenta", numeroCuenta);
                query.setParameter("valor", valor);

                if (tablaHisto) {
                    List result = new ArrayList<ReintegrosHisto>();
                    result = query.getResultList();
                    if (result.size() > 0) {
                        reg = new ReintegrosDTO(configApp.loggerApp).reintegrosHistoAReintegros((ReintegrosHisto) result.get(0));
                    }
                } else {
                    List result = new ArrayList<Reintegros>();
                    result = query.getResultList();
                    if (result != null) {
                        if (result.size() > 0) {
                            reg = (Reintegros) result.get(0);
                        }
                    }
                }

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

    /**
     * Retorna los Reintegros de la fecha dada agrupados por cejro
     *
     * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
    @SuppressWarnings("unchecked")
    public Collection<Object> getReintegrosXCajero(Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion {

        configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - getReintegrosXCajero fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal
                + " fechaHisto: " + fechaHisto);

        String nombreTabla = "";
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla historico: " + ReintegrosHisto.class.getSimpleName());
            nombreTabla = "ReintegrosHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla : " + Reintegros.class.getSimpleName());
            nombreTabla = "Reintegros";
        }

        Date fFinalCiclo = fechaFinal;
        if (fechaInicial.equals(fechaFinal)) {
            fFinalCiclo = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
        }

        Collection<Object> items = null;
        try {
            Query query = null;

            query = em.createNamedQuery(nombreTabla + ".CreadosXCajero");
            query.setParameter("fechaInicial", fechaInicial);
            query.setParameter("fechaFinal", fFinalCiclo);

            items = query.getResultList();

            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - getReintegrosXCajero items: " + items.size());

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return items;
    }

    @SuppressWarnings("unchecked")
    public void actualizarEstadoReintegros(Date fechaInicial, Integer codigoCajero, Date fechaHisto) throws EntityServicioExcepcion {

        configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - actualizarEstadoReintegros fechaInicial: " + fechaInicial
                + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);

        String nombreTabla = "";
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla historico: " + ReintegrosHisto.class.getSimpleName());
            nombreTabla = "ReintegrosHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "ReintegrosServicio - Consultando tabla : " + Reintegros.class.getSimpleName());
            nombreTabla = "Reintegros";
        }

        Date fFinalCiclo = fechaInicial;
        fFinalCiclo = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);

        //REEMPLAZAR CON STORE PROCEDURE
        String strQuery = "Update " + nombreTabla + " obj "
                + " Set obj.estadoreintegro = 8 "
                + " where  obj.hFecha between :fechaInicial and :fechaFinal "
                + " and obj.estadoreintegro = 9 ";
        if (codigoCajero != 0) {
            strQuery = strQuery + " and  obj.reintegrosPK.hCodigocajero = :codigoCajero ";
        }

        Query query = null;
        try {

            query = em.createQuery(strQuery);
            query.setParameter("fechaInicial", fechaInicial).setParameter("fechaFinal", fFinalCiclo);
            if (codigoCajero != 0) {
                query.setParameter("codigoCajero", codigoCajero);
            }

            query.executeUpdate();

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public Reintegros getReintegroXNumeroTransaccion(Integer numeroTransaccion) {
        Reintegros reintegro = null;
//        try {
//            Query query = em.createNamedQuery("Reintegros.getXNumeroTransaccion", Reintegros.class);
//            query.setParameter("numeroTransaccion", numeroTransaccion);
//            reintegro = (Reintegros) query.getSingleResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return reintegro;
//        }
        return reintegro;
    }

}
