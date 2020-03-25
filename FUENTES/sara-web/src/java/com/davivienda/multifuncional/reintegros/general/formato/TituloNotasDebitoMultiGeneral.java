/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.multifuncional.reintegros.formato;

/**
 * TituloLogGeneral - 15/09/2008
 * Descripción : Formato de configuración de los reportes de diario electronico
 * Versión : 1.0 
 * @author AA.Garcia
 * Davivienda 2008 
 */
public class TituloNotasDebitoMultiGeneral {

    public static String[] tituloHoja ;
    
    static{
        tituloHoja = new String[2] ;
        tituloHoja[0] = "Notas Debito Multifuncional" ;
        tituloHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA); 
    }
    
    /**
     * Titulos de las columnas
     */
    public static String[] tituloColumnas = {"CAJERO", "CUENTA","FECHA","VALOR","VALOR AJUSTAR","USUARIOCREANOTA","USUARIOAUTORIZA","FECHAAPLICA","ERROR","ESTADO","CLASIFICACION"};
   
}

