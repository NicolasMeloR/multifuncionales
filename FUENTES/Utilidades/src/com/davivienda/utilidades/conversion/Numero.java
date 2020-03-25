package com.davivienda.utilidades.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * Numero.java
 *
 * Fecha : 29 de abril de 2007, 07:12 PM
 *
 * Descripción : utilidades para la conversión de números
 *
 * @author : jjvargas
 *
 * @version : $Id: Numero.java,v 1.2 2007/05/08 23:07:01 jjvargas Exp $
 */
public class Numero {

    /**
     * Crea una nueva instancia de Numero
     */
    public Numero() {
    }

    /**
     * Convierte el número de tipo Long a un formato moneda $###,###,###.##
     *
     * @param valor Long valor
     * @return String formato $###,###,####.##
     */
    public static String aMoneda(Long valor) {
        String resp = "";
        try {
            DecimalFormat formato = new DecimalFormat("$###,###,###.##");
            resp = formato.format(valor);
        } catch (Exception ex) {
            resp = ex.getMessage();
        }
        return resp;
    }

    public static String aMonedaDecimal(BigDecimal valor) {
        String resp = "";
        try {
            DecimalFormat formato = new DecimalFormat("$###,###,###.00");
            resp = formato.format(valor);
        } catch (Exception ex) {
            resp = ex.getMessage();
        }
        return resp;
    }

    /**
     * Convierte el número de tipo BigDecimal a un formato moneda
     * $###,###,###.##
     *
     * @param valor BigDecimal valor
     * @return String formato $###,###,####.##
     */
    public static String aMoneda(BigDecimal valor) {
        return aMoneda(valor.longValue());
    }

    /**
     * Convierte el número de tipo BigDecimal a un formato moneda
     * $###,###,###.##
     *
     * @param valor BigDecimal valor
     * @return String formato $###,###,####.##
     */
    public static String aMoneda(BigInteger valor) {
        return aMoneda(valor.longValue());
    }

    /**
     * Convierte el número de tipo Integer a un formato moneda $###,###.###
     *
     * @param valor Integer valor
     * @return String formato $###,###,####.##
     */
    public static String aMoneda(Integer valor) {
        return aMoneda(valor.longValue());
    }

    /**
     * Convierte el número de tipo Long a un formato Decimal ###,###,###.##
     *
     * @param valor
     * @return String formato ###,###,###.##
     */
    public static String aFormatoDecimal(Long valor) {
        String resp = "";
        try {
            DecimalFormat formato = new DecimalFormat("###,###,###.##");
            resp = formato.format(valor);
        } catch (Exception ex) {
            resp = ex.getMessage();
        }
        return resp;
    }
    
    public static String aFormatoMiles(Long valor) {
        String resp = "";
        try {
            DecimalFormat formato = new DecimalFormat("###,###,###");
            resp = formato.format(valor);
        } catch (Exception ex) {
            resp = ex.getMessage();
        }
        return resp;
    }

    /**
     * Convierte el número de tipo BigDecimal a un formato Decimal
     * ###,###,###.##
     *
     * @param BigDecimal valor
     * @return String formato ###,###,###.##
     */
    public static String aFormatoDecimal(BigDecimal valor) {
        return aFormatoDecimal(valor.longValue());
    }

    /**
     * Convierte el número de tipo Integer a un formato Decimal ###,###,###.##
     *
     * @param Integer valor
     * @return String formato ###,###,###.##
     */
    public static String aFormatoDecimal(Integer valor) {
        return aFormatoDecimal(valor.longValue());
    }

    public static BigDecimal aBigDecimal(String value, String pattern) {
        BigDecimal valor = new BigDecimal(0);
        try {
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            decimalFormat.setParseBigDecimal(true);
            valor = (BigDecimal) decimalFormat.parse(value);
        } catch (Exception e) {
            valor = new BigDecimal(0);
        }

        return valor;
    }
   
}
