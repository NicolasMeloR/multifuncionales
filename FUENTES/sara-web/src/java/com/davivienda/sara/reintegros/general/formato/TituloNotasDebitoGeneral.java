/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.reintegros.general.formato;

/**
 * TituloLogGeneral - 15/09/2008
 * Descripcion : Formato de configuracion de los reportes de diario electronico
 * Version : 1.0
 *
 * @author AA.Garcia Davivienda 2008
 */
public class TituloNotasDebitoGeneral {

    public static String[] tituloHoja;

    static {
        tituloHoja = new String[2];
        tituloHoja[0] = "Notas Debito";
        tituloHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
    }

    /**
     * Titulos de las columnas
     */
    public static String[] tituloColumnas = {
        "CAJERO",
        "OCCA",
        "CUENTA",
        "FECHA",
        "VALOR",
        "VALOR AJUSTAR",
        "USUARIOCREANOTA",
        "USUARIOAUTORIZA",
        "FECHAAPLICA",
        "ERROR",
        "ESTADO",
        "CLASIFICACION",
        "CONCEPTO",
        "COMISION"
    };
}
