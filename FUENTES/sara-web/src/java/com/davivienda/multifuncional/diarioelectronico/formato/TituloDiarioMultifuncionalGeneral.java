/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.multifuncional.diarioelectronico.formato;

/**
 * TituloLogGeneral - 15/09/2008
 * @author AA.Garcia
 * Davivienda 2008 
 */
public class TituloDiarioMultifuncionalGeneral {

    public static String[] tituloHojaEfectivo;
    

    static {
        tituloHojaEfectivo = new String[2];
        tituloHojaEfectivo[0] = "Multifuncional Efectivo  ";
        tituloHojaEfectivo[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
    }
    
    public static String[] tituloHojaCheque;
    

    static {
        tituloHojaCheque = new String[2];
        tituloHojaCheque[0] = "Multifuncional Cheque  ";
        tituloHojaCheque[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
    }
    
    
    public static String[] tituloHojaHistoricoCargue;
    

    static {
        tituloHojaHistoricoCargue = new String[2];
        tituloHojaHistoricoCargue[0] = "Multifuncional Historico Cargue  ";
        tituloHojaHistoricoCargue[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
    }
    
    
       public static String[] tituloHojaLogCajero;
    

    static {
        tituloHojaHistoricoCargue = new String[2];
        tituloHojaHistoricoCargue[0] = "Log Cajero Multifuncional  ";
        tituloHojaHistoricoCargue[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
    }
    
    public static String[] tituloHojaReintegrosMultifuncional ;
    
    static{
        tituloHojaReintegrosMultifuncional = new String[2] ;
        tituloHojaReintegrosMultifuncional[0] = "Informe Reintegros Multifuncional" ;
        tituloHojaReintegrosMultifuncional[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA); 
        
    }
    
    /**
     * Titulos de las columnas
     */
    public static String[] tituloColumnasEfectivo = {"TRANSACCION", "ESTADO", "CAJERO", "# CORTE", "TX CONSECUTIVO", "FECHA CIERRE", "FECHA CAJERO", "TIPO CUENTA", "NUMERO CUENTA",
        "CUENTA HOMOLOGA", "VALOR", "# BILLETES ND", "#  BILLETES 50.000", "#  BILLETES 20.000", "#  BILLETES 10.000",
        "# BILLETES 5.000", "#  BILLETES 2.000", "#  BILLETES 1.000", "TOTAL BILLETES"
    };
    
    public static String[] tituloColumnasCheque = {"TRANSACCION", "CAJERO", "FECHA CAJERO", "# CORTE", "TX CONSECUTIVO", "FECHA CIERRE", "ESTADO", "CONSECUTIVO CHEQUE", "TIPO CUENTA", "NUMERO CUENTA",
        "CUENTA HOMOLOGA", "CHEQUE PROPIO", "RUTA", "TRANSITO", "CUENTA", "SERIAL", "OFICINA", "VALOR CHEQUE",
        "PLAZA", "FECHA ARCHIVO", "MONTO ARCHIVO", "CANTIDAD CHEQUES"
    };
    
    public static String[] tituloColumnasHistoricoCargue = {"CODIGO TRANSACCION", "CODIGO CAJERO", "FECHA CAJERO", "NUMERO CORTE", "CONSECUTIVO TRANSACION", "FECHA CIERRE", "ESTADO", "CONSECUTIVO CHEQUE", "TIPO CUENTA", "NUEMERO CUENTA",
        "NUMERO CUENTA HOMOLOGA", "CHEQUE PROPIO", "RUTA", "TRANSITO", "CUENTA", "SERIAL", "OFICINA", "VALOR CHEQUE USUARIO",
        "PLAZA", "FECHA ARCHIVO", "MONTO ARCHIVO", "CANTIDAD CHEQUES"
    };
    
     public static String[] tituloColumnasLogCajero = { "CAJERO", "FECHA", "ARCHIVO", "SECUENCIA", "DATOS"
    };
     
      public static String[] tituloColumnasReintegrosMultifuncional = {"FECHA HORA DISPENSADOR","BILLETAJE DISPENSADOR","TOTAL DISPENSADOR",
          "TRANSACCION", "ESTADO", "CAJERO", "# CORTE", "TX CONSECUTIVO", "FECHA CIERRE", "FECHA CAJERO", "TIPO CUENTA", "NUMERO CUENTA",
        "CUENTA HOMOLOGA", "VALOR", "# BILLETES ND", "#  BILLETES 50.000", "#  BILLETES 20.000", "#  BILLETES 10.000",
        "# BILLETES 5.000", "#  BILLETES 2.000", "#  BILLETES 1.000", "TOTAL BILLETES"
     };
}