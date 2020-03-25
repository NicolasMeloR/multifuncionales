package com.davivienda.sara.constantes;

/**
 * TipoRegistro.java
 * 
 * Fecha       :23 de enero de 2007, 07:41 PM
 * 
 * Descripción :Tipos de registro del EDC utilizado por Diebold
 * 
 * 
 * @author :jjvargas
 */

public enum TipoRegistro {
    REGISTRO_INICIAL('*', "REGISTRO INICIAL"),
    STATUS('0', "STATUS"),
    TRANSACCION('1', "TRANSACCION"),
    DEVICE_COUNTER('2', "DEVICE COUNTER"),
    DEVICE_LOG('3', "DEVICE LOG"),
    DIAGNOTICO('4', "DIAGNOTICO"),
    CONTINUACUON_REGISTRO('5', "CONTINUACUON REGISTRO"),
    CONFIGURACION('6', "CONFIGURACION"),
    DIAGNOSTICO_II('7', "DIAGNOSTICO II"),
    TRANSACCION_SUPERVISOR('8', "TRANSACCION SUPERVISOR");
    
    public char tipo;
    public String nombre;
    
    TipoRegistro(char tipo, String nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
    }


    
}
