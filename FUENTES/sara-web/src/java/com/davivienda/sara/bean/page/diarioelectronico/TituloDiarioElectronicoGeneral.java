/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.bean.page.diarioelectronico;

/**
 * TituloLogGeneral - 15/09/2008
 * Descripci�n : Formato de configuraci�n de los reportes de diario electronico
 * Versi�n : 1.0 
 * @author AA.Garcia
 * Davivienda 2008 
 */
public class TituloDiarioElectronicoGeneral {

    public static String[] tituloHoja ;
    
    static{
        tituloHoja = new String[2] ;
        tituloHoja[0] = "Transacciones Realizadas  " ;
        tituloHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA); 
    }
    
    /**
     * Titulos de las columnas
     */
    public static String[] tituloColumnas = {"CAJERO", "NOMBRE", "TRANSACCION", "FECHA", "TALON", "CUENTA", "TARJETA", "VALOR ENTREGADO","REFERENCIA","CODIGO ERROR","CODIGO TERMINACION"};
    
    public static String[] tituloColumnasCajero = {"CAJERO", "NOMBRE"};
}

