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
public class TituloInformeDiferencias {

    public static String[] tituloHoja ;
    
    static{
        tituloHoja = new String[2] ;
        tituloHoja[0] = "Informe Diferencias  " ;
        tituloHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA); 
    }
    
    /**
     * Titulos de las columnas
     */
   // public static String[] tituloColumnas = {"CAJERO", "PROVDIAANTERIOR", "PROVINIREAL", "DIFTRANSPORT", "DIFSOBRANTE"};
     public static String[] tituloColumnas = {"CAJERO", "RECIBIDO(LINEA)", "RECIBIDO(ATM)", "DIFERENCIA" ,"OBSERVACION"};
  
}

