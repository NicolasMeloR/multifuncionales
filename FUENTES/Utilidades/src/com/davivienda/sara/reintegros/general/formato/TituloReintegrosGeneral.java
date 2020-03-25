/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.reintegros.general.formato;

/**
 * TituloLogGeneral - 15/09/2008
 * Descripcion : Formato de configuracion de los reportes de diario electronico
 * Version : 1.0 
 * @author AA.Garcia
 * Davivienda 2008 
 */
public class TituloReintegrosGeneral {

    public static String[] tituloHoja ;
    
    static{
        tituloHoja = new String[2] ;
        tituloHoja[0] = "Reintegros" ;
        tituloHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA); 
    }
    
    /**
     * Titulos de las columnas
     */
    public static String[] tituloColumnas = {"CAJERO",  "TRANSACCION", "OCCA","CUENTA","TARJETA","FECHA","VALOR SOLICITADO","VALOR ENTREGADO","VALOR HOST","VALOR AJUSTADO","STATUS TX","RED ENRUTO","USUARIOREVISA","USUARIOAUTORIZA","FECHAREINTEGRO","ERROR","ESTADO","CLASIFICACION","CONCEPTO","COMISION","FECHAREVERSADO"};
    
    public static String[] tituloColumnasTodos = {"CAJERO",  "TRANSACCION", "OCCA","CUENTA","TARJETA","FECHA","VALOR SOLICITADO","VALOR ENTREGADO","VALOR HOST","VALOR AJUSTADO","STATUS TX","RED ENRUTO","USUARIOREVISA","USUARIOAUTORIZA","FECHAREINTEGRO","ERROR","ESTADO","CLASIFICACION","CONCEPTO","COMISION","FECHAREVERSADO","DISPENSED","REMAINING"};
   
}

