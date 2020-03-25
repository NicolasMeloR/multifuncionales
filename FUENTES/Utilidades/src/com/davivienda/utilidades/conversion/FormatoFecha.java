package com.davivienda.utilidades.conversion;

/**
 * FormatoFecha.java
 *
 * Fecha :7 de mayo de 2007, 01:57 PM
 *
 * Descripción :Formato de fecha
 *
 * @author :jjvargas
 * @version : $Id: FormatoFecha.java,v 1.2 2007/05/18 23:23:45 jjvargas Exp $
 */
public enum FormatoFecha {

    /**
     * Formato yyyy/MM/dd es la utilizada por defecto
     */
    DEFECTO("yyyy/MM/dd"),
    /**
     * Formato yyyyMMdd
     */
    AAAAMMDD("yyyyMMdd"),
    /**
     * yyMMdd
     */
    AAMMDD("yyMMdd"),
    /**
     * Formato yyyy-MM-dd
     */
    DEFECTO_SEPARADOR_GUION("yyyy-MM-dd"),
    /**
     * Format fecha utilizado en el EDC
     */
    FECHA_EDC("yyMMddHHmmss"),
    /**
     * Format fecha utilizado en el ciclo EDC
     */
    CICLO_EDC("MMddyy"),
    /**
     * Formato yyyy/MM/dd HH:mm:ss
     */
    FECHA_HORA("yyyy/MM/dd HH:mm:ss.SSS"),
    /**
     * Formato yyyy-MM-ddHH:mm:ss
     */
    FECHA_HORA_DOJO("yyyy-MM-ddHH:mm:ss"),
    /**
     * Sufijo para nombres de archivo
     */
    MMDD("MMdd"),
    /**
     * Format fecha utilizado en el host
     */
    FECHA_HOST("yyyyMMddHHmmss"),
    /**
     * Format fecha PARA EL AÑO
     */
    YEAR("yyyy"),
    /**
     * Separado por guion, normalmente utilizado en los nombres de archivos de
     * host AA-MM-DD
     */
    AA_MM_DD("yy-MM-dd"),
    HOUR_REINTEGROS("HH:mm");

    /**
     * String con el formato asociado a cada enum
     */
    public String formato;

    /**
     * Contructor de cada formato
     *
     * @param formato el formato
     */
    FormatoFecha(String formato) {
        this.formato = formato;
    }

}
