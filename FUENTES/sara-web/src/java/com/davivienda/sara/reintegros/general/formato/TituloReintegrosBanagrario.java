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
public class TituloReintegrosBanagrario {

    public static String[] tituloHoja ;
    public static String[] subtituloInferiorHoja;
    public static String[] firmaHoja;
    public static String[] subtituloTotalesHoja=null;
    
    static{
       
          tituloHoja = new String[5] ;
          tituloHoja[0] = "MEMORANDO REMISORIO DE SOBRANTE" ;
          tituloHoja[1] = "DE:               DAVIVIENDA RED BANCAFE" ;
          tituloHoja[2] = "PARA:          BANAGRARIO";
          tituloHoja[3] = "REMISORIO:";
          tituloHoja[4] = "FECHA:         "+com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(),"MM-dd-yyyy");
          
          subtituloInferiorHoja = new String[3] ;
          subtituloInferiorHoja[0] = "0  SOBRANTE CONTABLE" ;
          subtituloInferiorHoja[1] = "6  M STATUS 05-14-18 Y 19" ;
          subtituloInferiorHoja[2] = "7  TRANS. CON RETRAC";
          
          subtituloTotalesHoja = new String[3] ;
          subtituloTotalesHoja[0] = "TOTAL TRANSACCIONES   " ;
          subtituloTotalesHoja[1] = "TOTAL COMISIONES            " ;
          subtituloTotalesHoja[2] = "TOTAL                                     ";
          
          firmaHoja = new String[2] ;
          firmaHoja[0] = "Neslie Rodriguez" ;
          firmaHoja[1] = "Elaborado por:" ;
          
          
          
         
        
       
    }
     public static String subtituloHoja ="Atentamente nos permitimos relacionar transacciones para ser abonadas asi:";
    
    /**
     * Titulos de las columnas
     */
    public static String[] tituloColumnas = {"TRANS",  "CAUSAL","FECHA", "HORA","VALOR","COMISION","TARJETA","CAJERO","OFICINA"};
    
    
    
    
   
}
