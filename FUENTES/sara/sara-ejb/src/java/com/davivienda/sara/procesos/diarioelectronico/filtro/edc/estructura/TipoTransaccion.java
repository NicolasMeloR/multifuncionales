// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura;

public enum TipoTransaccion
{
    RETIRO_AHORRO(20, 20, "Retiro Ahorro"), 
    RETIRO_CORRIENTE(20, 21, "Retiro Corriente"), 
    RETIRO_RED_AHORRO(21, 22, "Retiro otra red Ahorro"), 
    RETIRO_RED_CTE(21, 21, "Retiro otra red Corriente"), 
    CONSULTA_SALDO(50, 50, "Consulta Saldo Cuenta"), 
    AVANCE_TC(20, 24, "Avance Tarjeta cr\u00e9dito"), 
    PRIMER_INF_TOTALES(1, 13, "Primer informe"), 
    DISMINUCION_PROVISION(1, 4, "Disminuci\u00f3n Provisi\u00f3n"), 
    AUMENTO_PROVISION(1, 3, "Aumento Provisi\u00f3n"), 
    CONTROL_VISITA_PROVEEDOR(1, 99, "Control visitas proveedor"), 
    AVANCE_TC_REDES(21, 23, "Avance Tarjeta cr\u00e9dito Redes"), 
    CONSULTA_MOVIMIENTO(58, 58, "Consulta Movimeinto"), 
    CAMBIO_PRIMERA_CLAVE(90, 90, "Consulta Movimeinto"), 
    TRAN_21_25(21, 25, "Tran 21 25"), 
    TRANSACCION_NO_DEFINIDA(0, 0, "Transaccion no definida"), 
    TODAS(500, 500, "Todas");
    
    public Integer tarea;
    public Integer transaccion;
    public String nombre;
    
    private TipoTransaccion(final int tarea, final int transaccion, final String nombre) {
        this.tarea = tarea;
        this.transaccion = transaccion;
        this.nombre = nombre;
    }
    
    public static TipoTransaccion getTipoTransaccion(final Integer transaccion) {
        TipoTransaccion tipoTransaccion = TipoTransaccion.TRANSACCION_NO_DEFINIDA;
        if (transaccion != null) {
            for (final TipoTransaccion elem : values()) {
                if (transaccion.equals(elem.transaccion)) {
                    tipoTransaccion = elem;
                    break;
                }
            }
        }
        return tipoTransaccion;
    }
    
    public static TipoTransaccion getTipoTransaccion(final String nombreTransaccion) {
        TipoTransaccion tipoTransaccion = TipoTransaccion.TRANSACCION_NO_DEFINIDA;
        if (nombreTransaccion != null) {
            for (final TipoTransaccion elem : values()) {
                if (nombreTransaccion.equals(elem.nombre)) {
                    tipoTransaccion = elem;
                    break;
                }
            }
        }
        return tipoTransaccion;
    }
}
