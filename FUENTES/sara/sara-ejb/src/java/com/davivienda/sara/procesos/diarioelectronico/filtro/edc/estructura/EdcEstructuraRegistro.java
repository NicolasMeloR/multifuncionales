// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura;

public enum EdcEstructuraRegistro
{
    TIPO_REGISTRO("Tipo_registro", 0, 0, 1, false), 
    SECUENCIA("Secuencia", 1, 1, 6, false), 
    FECHA("Fecha", 2, 7, 6, false), 
    HORA("Hora", 3, 13, 6, false), 
    CODIGO_CAJERO("Codigo_Cajero", 4, 23, 4, false), 
    INFORMACION("Informacion", 5, 28, -1, false);
    
    public String nombre;
    public int orden;
    public int posIni;
    public int longitud;
    public boolean esFiltro;
    
    private EdcEstructuraRegistro(final String nombre, final int orden, final int posIni, final int longitud, final boolean esFiltro) {
        this.nombre = nombre;
        this.orden = orden;
        this.posIni = posIni;
        this.longitud = longitud;
        this.esFiltro = esFiltro;
    }
}
