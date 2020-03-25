// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cuadrecifras.filtro.totalesestacion.estructura;

public enum TotalesEstacionEstructuraRegistro
{
    ESTACION("Estacion", 0, 0, 6, false), 
    TOTAL("Codigo_Total", 1, 7, 6, true), 
    CANAL("Canal", 2, 17, 1, true), 
    CANTIDAD_EVENTOS("Cantidad_Eventos", 3, 18, 6, false), 
    VALOR_EVENTOS("Valor_Eventos", 4, 24, 16, false);
    
    public String nombre;
    public int orden;
    public int posIni;
    public int longitud;
    public boolean esFiltro;
    
    private TotalesEstacionEstructuraRegistro(final String nombre, final int orden, final int posIni, final int longitud, final boolean esFiltro) {
        this.nombre = nombre;
        this.orden = orden;
        this.posIni = posIni;
        this.longitud = longitud;
        this.esFiltro = esFiltro;
    }
}
