// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.estructura;

public enum ProvisionesEstructuraRegistro
{
    CUENTA("Cuenta", 0, 0, 16, false), 
    FECHA("Fecha", 1, 16, 8, false), 
    TIPO_MOVIMIENTO("Tipo_Movimiento", 2, 24, 4, true), 
    TALON("Talon", 3, 28, 8, false), 
    OFICINA("Oficina", 4, 36, 4, false), 
    JORNADA("Jornada", 5, 40, 1, false), 
    CONSECUTIVO("Consecutivo", 6, 41, 4, false), 
    USUARIO("Usuario", 7, 45, 6, false), 
    ESPACIO_1("Espacio_1", 8, 51, 10, false), 
    IDENTIFICACION_TERMINAL("Identificacion_Terminal", 9, 61, 6, false), 
    ESPACIO_2("Epacio_2", 10, 67, 7, false), 
    FECHA_SISTEMA("Fecha_Sistema", 11, 74, 8, false), 
    HORA_SISTEMA("Hora_Sistema", 12, 82, 6, false), 
    ESPACIO_3("Epacio_3", 13, 88, 48, false), 
    REFERENCIA("Referencia", 14, 136, 16, false), 
    ESPACION_4("Espacio_4", 15, 152, 82, false), 
    VALOR("Valor", 16, 234, 18, false), 
    ESPACIO_5("Espacio_5", 17, 252, 36, false), 
    MOTIVO_MOVIMIENTO("Motivo_Movimiento", 18, 288, 4, true), 
    FECHA_APLICACION("Fecha_Aplicacion", 19, 292, 8, false);
    
    public String nombre;
    public int orden;
    public int posIni;
    public int longitud;
    public boolean esFiltro;
    
    private ProvisionesEstructuraRegistro(final String nombre, final int orden, final int posIni, final int longitud, final boolean esFiltro) {
        this.nombre = nombre;
        this.orden = orden;
        this.posIni = posIni;
        this.longitud = longitud;
        this.esFiltro = esFiltro;
    }
}
