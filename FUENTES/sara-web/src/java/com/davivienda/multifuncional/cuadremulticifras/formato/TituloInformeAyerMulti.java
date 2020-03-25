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
public class TituloInformeAyerMulti {

    public static String[] tituloHoja ;
    
    static{
        tituloHoja = new String[2] ;
        tituloHoja[0] = "Informe Ayer Multifuncionales" ;
        tituloHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA); 
    }
    
    /**
     * Titulos de las columnas
     */
   
     public static String[] tituloColumnas = {"ATM","OFICINA", "PROV.CAJA", "DISMI.PROV", "EF.RECIBIDO", "EF.PAGADO","EF.CAJA","EF.REC.HOR.ANT","EF.REC.HOR.SIG"};
  
}

