package com.davivienda.sara.administracion.constantes;

/**
 * AccionMantenimientoTabla
 * Descripción : Tipos de solicitudes enviadas por las páginas de montenimirnto de tablas
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008
 */
public enum AccionMantenimientoTabla {

    CONSULTAR("consultar"),
    BUSCAR("buscar"),
    GRABAR("grabar"),
    ACTUALIZAR("actualizar"),
    BORRAR("borrar"),
    SIN_ACCION("No Encontrada"),
    GENERAR_XLS("generarXLS");
   

    private String accion;

    AccionMantenimientoTabla(String accion) {
        this.accion = accion;
    }
    
    public static AccionMantenimientoTabla getAccionMantenimientoTabla(String accion){
        AccionMantenimientoTabla amt = null;
        for (AccionMantenimientoTabla item : AccionMantenimientoTabla.values()) {
            if (item.accion.equals(accion)) {
                amt = item;
            }            
        }
        if (amt == null) amt = AccionMantenimientoTabla.SIN_ACCION;
        return amt;
    }
}
