// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura;

public enum EdcTipoRegistro
{
    BEGIN_FILE('*', "INICIO DIARIO ELECTRONICO"), 
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
    
    private EdcTipoRegistro(final char codigo, final String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    
    public static EdcTipoRegistro getTipoRegistro(final char unChar) {
        EdcTipoRegistro tipoRegistro = EdcTipoRegistro.TIPO_REGISTRO_NO_DEFINIDO;
        for (final EdcTipoRegistro elem : values()) {
            if (elem.codigo == unChar) {
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
