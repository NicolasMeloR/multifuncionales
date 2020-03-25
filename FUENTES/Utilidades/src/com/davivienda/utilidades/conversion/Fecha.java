package com.davivienda.utilidades.conversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Fecha.java
 *
 * Fecha : 7 de mayo de 2007, 11:56 AM
 *
 * Descripción : Conversion de fechas
 *
 * @author : jjvargas
 *
 * @version : $Id: Fecha.java,v 1.1 2007/05/08 23:07:01 jjvargas Exp $
 */
public class Fecha {

    public static final Locale ZONA_FECHA = new Locale("ES", "CO");

    /**
     * Crea una nueva instancia de Fecha
     */
    public Fecha() {
    }

    /**
     * Retorna un objeto Calendar con la fecha actual
     *
     * @return
     */
    public static Calendar getCalendarHoy() {
        Calendar fecha = Calendar.getInstance(ZONA_FECHA);
        return fecha;
    }

    /**
     * Retorna la fecha de ayer en un objeto Calendar
     *
     * @return Calendar ayer
     */
    public static Calendar getCalendarAyer() {
        Calendar fecha = getCalendarHoy();
        fecha.add(Calendar.DAY_OF_MONTH, -1);
        return fecha;
    }

    /**
     * Retorna la fecha sumando o restando los días a hoy
     *
     * @param dias
     * @return
     */
    public static Calendar getCalendar(int dias) {
        Calendar fecha = getCalendarHoy();
        fecha.add(Calendar.DAY_OF_MONTH, dias);
        return fecha;
    }

    public static Calendar getCalendar(Calendar fecha, int dias) {
        Calendar fechaTmp = (Calendar) fecha.clone();
        fechaTmp.add(Calendar.DAY_OF_MONTH, dias);
        return fechaTmp;
    }

    /**
     * Retorna un objeto Date con la fecha actual
     *
     * @return
     */
    public static java.util.Date getDateHoy() {
        Calendar cal = getCalendarHoy();
        return cal.getTime();
    }

    /**
     * Retorna la fecha de ayer en un objeto java.util.Date
     *
     * @return fecha ayer
     */
    public static java.util.Date getDateAyer() {
        return getCalendarAyer().getTime();
    }

    /**
     * Retorna la fecha sumando o restando los días a hoy
     *
     * @param dias
     * @return
     */
    public static java.util.Date getDate(int dias) {
        Calendar fecha = getCalendar(dias);
        return fecha.getTime();
    }

    public static java.util.Date getDate(java.util.Date fecha, int dias) {
        return getCalendar(getCalendar(fecha), dias).getTime();
    }

    /**
     * Retorna un objeto Calendar a partir de un java.util.Date
     *
     * @param fecha
     * @return calendar
     */
    public static Calendar getCalendar(java.util.Date fecha) {
        Calendar tmp = getCalendarHoy();
        tmp.setTime(fecha);
        return tmp;
    }

    /**
     * Retorna la fecha en el formato dado
     *
     * @param fecha
     * @param formato
     * @return String
     */
    public static String aCadena(Calendar fecha, String formato) {
        if (fecha == null) {
            fecha = getCalendarHoy();
        }
        if (formato == null) {
            formato = com.davivienda.utilidades.conversion.FormatoFecha.DEFECTO.formato;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formato, Fecha.ZONA_FECHA);
        sdf.setLenient(false);
        return sdf.format(fecha.getTime());
    }

    public static String aCadena(Calendar fecha, FormatoFecha formato) {
        return aCadena(fecha, formato.formato);
    }

    /**
     * Retorna la fecha en el formato dado
     *
     * @param fecha
     * @param formato
     * @return String
     */
    public static String aCadena(java.util.Date fecha, String formato) {
        return aCadena(getCalendar(fecha), formato);
    }

    public static String aCadena(java.util.Date fecha, FormatoFecha formato) {
        return aCadena(getCalendar(fecha), formato);
    }

    /**
     * Retorna una fecha fin de dia seteando la hora a las 23:59:59
     *
     * @param fecha inicio
     * @return Date fecha fin de día
     */
    public static java.util.Date getFechaFinDia(java.util.Date fecha) {
        Calendar fecha2 = Calendar.getInstance();
        fecha2.setTime(fecha);
        return getFechaFinDia(fecha2).getTime();
    }

    /**
     * Retorna una fecha fin de dia seteando la hora a las 23:59:59
     *
     * @param fecha inicio
     * @return Calendar fecha fin de día
     */
    public static Calendar getFechaFinDia(Calendar fecha) {
        Calendar fecha2 = Calendar.getInstance();
        fecha2.setTimeInMillis(fecha.getTimeInMillis());
        fecha2.set(Calendar.HOUR, 23);
        fecha2.set(Calendar.MINUTE, 59);
        fecha2.set(Calendar.SECOND, 59);
        return fecha2;
    }

    /**
     * Retorna una fecha fin del dia enviado seteando la hora a las 23:59:59
     *
     * @param fecha inicio
     * @return Calendar fecha fin de día
     */
    public static Calendar getFechaFinOtroDia(Calendar fecha) {
        // Calendar fecha2 = Calendar.getInstance();
        fecha.setTimeInMillis(fecha.getTimeInMillis());
        fecha.set(Calendar.HOUR, 23);
        fecha.set(Calendar.MINUTE, 59);
        fecha.set(Calendar.SECOND, 59);
        return fecha;
    }

    /**
     * Retorna una fecha fin de dia seteando la hora a las 23:59:59
     *
     * @param fecha inicio
     * @return Calendar fecha fin de día
     */
    public static Calendar getFechaInicioDia(Calendar fecha) {
        Calendar fechaTmp = (Calendar) fecha.clone();
        fechaTmp.set(Calendar.MINUTE, 0);
        fechaTmp.set(Calendar.SECOND, 0);
        fechaTmp.set(Calendar.HOUR_OF_DAY, 0);
        fechaTmp.set(Calendar.MILLISECOND, 0);

        return fechaTmp;
    }

    public static Date aDate(String fecha, String pattern) {
        Date date = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            date = formatter.parse(fecha);
        } catch (ParseException e) {
                return null;
        }
        return date;
    }

}
