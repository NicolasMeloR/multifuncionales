// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.reintegros.filtro.host.estructura;

public enum HostEstructuraRegistro
{
    CODIGO_CAJERO("Codigo_Cajero", 0, 0, 6, false), 
    FECHA_SISTEMA("Fecha_sistema", 1, 6, 8, false), 
    HORA_SISTEMA("Hora_sistema", 2, 14, 6, false), 
    TALON("Talon", 3, 20, 6, false), 
    NUMERO_CUENTA("Numero_cuenta", 4, 26, 12, false), 
    DATOS_TARJETA("Datos_tarjeta", 5, 38, 19, false), 
    FECHA("Fecha", 6, 57, 8, false), 
    TIPO_TRANSACCION("Tipo_transaccion", 7, 65, 4, true), 
    OCCA("Occa", 8, 69, 4, false), 
    TIPO_TARJETA("Tipo_tarjeta", 9, 73, 1, false), 
    INDICES("Indices", 10, 74, 2, false), 
    VALOR("Valor", 11, 84, 7, false), 
    FILLER("Filler", 12, 91, 7, false);
    
    public String nombre;
    public int orden;
    public int posIni;
    public int longitud;
    public boolean esFiltro;
    
    private HostEstructuraRegistro(final String nombre, final int orden, final int posIni, final int longitud, final boolean esFiltro) {
        this.nombre = nombre;
        this.orden = orden;
        this.posIni = posIni;
        this.longitud = longitud;
        this.esFiltro = esFiltro;
    }
}
