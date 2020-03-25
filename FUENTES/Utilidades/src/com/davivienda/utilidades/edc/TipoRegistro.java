package com.davivienda.utilidades.edc;


/**
 * TipoRegistro.java
 * 
 * Fecha       :  30/05/2007, 07:26:48 PM
 * Descripción :  Identificación de los tipos de registro del EDC
 * 
 * @author     : jjvargas
 * @version    : $Id$
 */


public enum TipoRegistro {
    
    BEGIN_FILE('*',"INICIO DIARIO ELECTRONICO"),
    STATUS_DATA('0', "ESTADO"),
    TRANSACTION_DATA('1', "TRANSACCION"),
    DEVICE_ACTIVITY_COUNTER_DATA('2', "DATOS CONTADOR"),
    DEVICE_ACTIVITY_LOG_DATA('3', "DATOS LOG"),
    DIAGNOSTIC_DATA('4', "DIAGNOSTICO"),
    CONTINUATION_OF_PREVIOUS_RECORD('5', "CONTINUACION"),
    CONFIGURATION_DATA('6', "CONFIGURACION"),
    DIAGNOSTICS_DATA('7', "DIAGNOSTICO"),
    CONTADORES_CORTE('8', "CONTADORES CORTE"),
    TIPO_REGISTRO_NO_DEFINIDO('9', "TIPO REGISTRO NO DEFINIDO");
    
    public char codigo;
    public String nombre;
    /**
     * Constructor de <code>TipoRegistro</code>.
     */
    TipoRegistro(char codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    
    public static TipoRegistro getTipoRegistro(char unChar) {
                TipoRegistro tipoRegistro = TipoRegistro.TIPO_REGISTRO_NO_DEFINIDO;
        for (TipoRegistro elem : TipoRegistro.values()) {
            if (elem.codigo == unChar){
                tipoRegistro = elem;
                break;
            }
        }
        return tipoRegistro;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    

}
