/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.multifuncional.cuadremulticifras.formato;

/**
 * TituloLogGeneral - 15/09/2008
 * @author AA.Garcia
 * Davivienda 2008 
 */
public class TituloDali40LSGeneral {

    public static String[] tituloDali40 ;
    
    static{
         tituloDali40 = new String[2] ;
//        tituloHoja[0] = "Resumen Cuadre Diario  " ;
//        tituloHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA); 
          tituloDali40[0] = "TOTALES";
          tituloDali40[1]="";
    }
    
    /**
     * Titulos de las columnas
     */
 
     public static String[] tituloColumnas =  {"TOTALES", "ATM", "STRATUS", "DIFERENCIA", "INIJORN", "HAYJORN", "RECIB.", "RETRACT", "INIJORN",
                                    "HAYJORN", "RECIB.", "RETRACT", "EFECTIVO", "CHEQUES"};
     public static  String[] tituloFilas =  {"CODIGO CAJERO", "DEPOSITO EFE", "DEPOSITO CHQ", "PAGOS TC EFE", "PAGOS TC CHQ", "PAGOS FM EFE","PAGOS FM CHQ",
                                "RECAUDOS EFE", "RECAUDOS CHQ", "DETALLE EFECTIVO", "DENOMINACION 1000", "DENOMINACION 2000", "DENOMINACION 5000",
                                "DENOMINACION 10000", "DENOMINACION 20000", "DENOMINACION 50000", "TOTALES","DETALLE CHEQUES", "CANTIDAD", "VALOR",
                                "RESUMEN", "FALTANTES", "SOBRANTES"};
}

