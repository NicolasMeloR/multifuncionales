/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.reintegros.general.formato;

/**
 * TituloLogGeneral - 24/10/2019 Descripcion : Formato de configuracion de los
 * reportes ejecucion cargue masivo de timbres Version : 1.0
 *
 * @author juan jaime Davivienda 2019
 */
public class TituloCargasMasivasTimbres {

    public static String[] tituloHoja;

    static {
        tituloHoja = new String[2];
        tituloHoja[0] = "Reporte cargas masivas timbres.";
        tituloHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
    }

    /**
     * Titulos de las columnas
     */
    public static String[] tituloColumnas = {
        "CAJERO",
        "PROVDIASGTE_MAQUINA",
        "PROVDIASGTE_LINEA",
        "DIFERENCIAS",
        "OBSERVACION",
        "OCCA",
        "AUMENTO",
        "DISMINUCION",
        "SOBRANTE",
        "FALTANTE",
        "NOVEDAD",
        "ASIGNADO_A",
        "PROVEEDOR",
        "CLASIFICACION",
        "TIPIFICACION_TRANSPORTADORA",
        "RESULTADO_EJECUCION"
    };

    public static String[] tituloColumnasIngreso = {
        "CAJERO",
        "PROVDIASGTE_MAQUINA",
        "PROVDIASGTE_LINEA",
        "DIFERENCIAS",
        "OBSERVACION",
        "OCCA",
        "AUMENTO",
        "DISMINUCION",
        "SOBRANTE",
        "FALTANTE",
        "NOVEDAD",
        "ASIGNADO_A",
        "PROVEEDOR",
        "CLASIFICACION",
        "TIPIFICACION_TRANSPORTADORA"};

}
