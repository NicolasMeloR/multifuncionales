/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.multifuncional.reintegros.formato;

/**
 *
 * @author P-CCHAPA
 */
public class TituloReintegrosMultiGeneral {

    public static String[] tituloHoja ;
    
    static{
        tituloHoja = new String[2] ;
        tituloHoja[0] = "Multifuncional Informes General" ;
        tituloHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA); 
        
    }
    
    /**
     * Titulos de las columnas
     */
    public static String[] tituloColumnas = {"CODIGO CAJERO", "NUMERO TRANSACCION","FECHA CAJERO","NUMERO CUENTA","TIPO CUENTA","FECHA","VALOR HOST","TOTAL BILLETES CONSIGNADOS","VALOR CONSIGNACION","VALOR AJUSTA","USUARIO REVISA",
    "USUARIO AUTORIZA", "FECHA REINTEGRO", "DESCRIPCION", "ESTADO", "CLASIFICACION"};
}
