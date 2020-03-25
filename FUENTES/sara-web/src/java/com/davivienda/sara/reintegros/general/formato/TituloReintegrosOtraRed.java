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
public class TituloReintegrosOtraRed {

    public static String[] tituloHoja ;
    
    static{
       
          tituloHoja = new String[6] ;
          tituloHoja[0] = "DAVIVIENDA" ;
          tituloHoja[1] = "CONTROL SOBRANTES ATMS" ;
          tituloHoja[2] = "DE                                  NOMBRE DEPENDENCIA      No 518";
          tituloHoja[3] = "CODIGO OF                 CODIGO DEPENDENCIA          4599";
          tituloHoja[4] = "FECHA DE ELABORACI�N:    "+com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(),"dd/MM/yyyy");
          tituloHoja[5] = "FECHA DE ENVIO:                     "+com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(),"yyyy/MM/dd");
        
       
    }
    
    /**
     * Titulos de las columnas
     */
    public static String[] tituloColumnas = {"FECHA MOV",  "HORA","TARJETA", "ATM","TALON","VALOR A ABONAR","ESTADO DE LA TRANSACCION","RED"};
    public static String[] tituloColumnasObservacion = {"FECHA MOV",  "HORA","TARJETA", "ATM","TALON","VALOR A ABONAR","OBSERVACION","RED"};
    public static String[] tituloColumnasORed = {"FECHA MOV",  "HORA","TARJETA", "ATM","TALON","VALOR A ABONAR","ESTADO DE LA TRANSACCION","RED","# CUENTA HOST"};
    
   
}
