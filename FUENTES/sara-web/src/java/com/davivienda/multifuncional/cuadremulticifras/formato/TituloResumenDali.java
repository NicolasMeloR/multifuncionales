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
public class TituloResumenDali {

     public static String[] tituloHoja ;
    
    static{
        tituloHoja = new String[2] ;
        tituloHoja[0] = "Resumen de Estaciones con Sobrante o Faltante" ;
        tituloHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA); 
    }
    
    /**
     * Titulos de las columnas
     */
   // public static String[] tituloColumnas = {"CAJERO", "PROVDIAANTERIOR", "PROVINIREAL", "DIFTRANSPORT", "DIFSOBRANTE"};
       //CUENTA         FECHA    HORA TERMINAL  TALON TIPO MOV MOTIVO     VALOR MOVIMIENTO      REFERENCIA 1     REFERENCIA 2    
     public static String[] tituloColumnas = {"CAJERO","OFICINA","VLR SOBRANTE", "VLR FALTANTE"};
  
}


