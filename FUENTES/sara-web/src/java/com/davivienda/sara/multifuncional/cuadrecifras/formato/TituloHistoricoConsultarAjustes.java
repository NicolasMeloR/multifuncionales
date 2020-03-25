/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.multifuncional.cuadrecifras.formato;

/**
 *
 * @author jediazs
 */
public class TituloHistoricoConsultarAjustes {
    
      public static String[] tituloHoja;
    

    static {
        tituloHoja = new String[2];
        tituloHoja[0] = "Ajustes Usuario  Multifuncional";
        tituloHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
    }
    /**
     * Titulos de las columnas
     */
    public static String[] tituloColumnas = {"#", "TIPO", "USUARIO", "OFICINA", "CAJERO", "FECHA","TALON", "VALOR", "COD ERROR", "DESCRIPCION"};
      
}
