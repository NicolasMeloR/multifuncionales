/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.diarioelectronico.general.formato;


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
    
    public static String[] tituloColumnasTira = {
        "CODIGOBANCO", "IDZONA", "IDOCCA", "IDCAJERO", "TIPOMAQUINA", "TIPOREGISTRO", "NUMEROTRANSACCION", "IDTRANSACCION", "TIPOTRANSACCION", 
        "DESCTRANSACCION", "FECHAREALTRANSACCION", "FECHACONTABLETRANSACCION", "NUMEROTARJETA", "NUMEROPRODUCTO", "PTODUCTORIGEN", "MONTOTRANSACCIONRETIRO", 
        "MONTOTXRETIROENTREGADO", "VALORDONACION", "COSTOTRANSACCION", "VUELTASDESCRIPCION", "MONTOTXDEPOSITO", "MONTOTXDEPOSITORECIBIDO", "REFERENCIA1", "REFERENCIA2", 
        "REFERENCIA3", "PTODUCTODESTINO", "NUMEROPRODUCTODESTINO", "NUMEROAPROBACION", "CODIGOERRORHOST", "DESCRIPCIONERRORHOST", "CODIGOERRORATM", "DESCRIPCIONERRORATM", 
        "ESTADOIMPRESION", "LOGAUDITA", "CANTIDAD", "VALOR", "CANTIDAD_1", "VALOR_1", "CANTIDAD_2", "VALOR_2", "CANTIDAD_3", "VALOR_3", "CANTIDAD_4", "VALOR_4", 
        "CANTIDAD_5", "VALOR_5", "CANTIDAD_6", "VALOR_6", "CANTIDAD_7", "VALOR_7", "CANTIDAD_8", "VALOR_8", "DENOMINACIONBILLETES", "CANTBILLETESPROVISION", 
        "BILLETESDISPENSADOS", "ACUMBILLETESDISPENSADOS", "BILLETESDEPOSITADOS", "ACUMBILLETESDEPOSITADOS", "BILLETESREMANENTES", "BILLETESRECHAZADOS", 
        "ACUMBILLETESRECHAZADOS", "BILLETESRETRAC", "ACUMBILLETESRETRACT", "DENOMINACIONBILLETES_1", "CANTBILLETESPROVISION_1", "BILLETESDISPENSADOS_1", 
        "ACUMBILLETESDISPENSADOS_1", "BILLETESDEPOSITADOS_1", "ACUMBILLETESDEPOSITADOS_1", "BILLETESREMANENTES_1", "BILLETESRECHAZADOS_1", "ACUMBILLETESRECHAZADOS_1", 
        "BILLETESRETRAC_1", "ACUMBILLETESRETRACT_1", "DENOMINACIONBILLETES_2", "CANTBILLETESPROVISION_2", "BILLETESDISPENSADOS_2", "ACUMBILLETESDISPENSADOS_2", 
        "BILLETESDEPOSITADOS_2", "ACUMBILLETESDEPOSITADOS_2", "BILLETESREMANENTES_2", "BILLETESRECHAZADOS_2", "ACUMBILLETESRECHAZADOS_2", "BILLETESRETRAC_2", 
        "ACUMBILLETESRETRACT_2", "DENOMINACIONBILLETES_3", "CANTBILLETESPROVISION_3", "BILLETESDISPENSADOS_3", "ACUMBILLETESDISPENSADOS_3", "BILLETESDEPOSITADOS_3", 
        "ACUMBILLETESDEPOSITADOS_3", "BILLETESREMANENTES_3", "BILLETESRECHAZADOS_3", "ACUMBILLETESRECHAZADOS_3", "BILLETESRETRAC_3", "ACUMBILLETESRETRACT_3", 
        "DENOMINACIONBILLETES_4", "CANTBILLETESPROVISION_4", "BILLETESDISPENSADOS_4", "ACUMBILLETESDISPENSADOS_4", "BILLETESDEPOSITADOS_4", "ACUMBILLETESDEPOSITADOS_4", 
        "BILLETESREMANENTES_4", "BILLETESRECHAZADOS_4", "ACUMBILLETESRECHAZADOS_4", "BILLETESRETRAC_4", "ACUMBILLETESRETRACT_4", "DENOMINACIONBILLETES_5", 
        "CANTBILLETESPROVISION_5", "BILLETESDISPENSADOS_5", "ACUMBILLETESDISPENSADOS_5", "BILLETESDEPOSITADOS_5", "ACUMBILLETESDEPOSITADOS_5", "BILLETESREMANENTES_5", 
        "BILLETESRECHAZADOS_5", "ACUMBILLETESRECHAZADOS_5", "BILLETESRETRAC_5", "ACUMBILLETESRETRACT_5"};//, "FECHA", "OFICINA"};
    }

