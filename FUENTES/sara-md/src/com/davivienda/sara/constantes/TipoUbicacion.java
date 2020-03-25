package com.davivienda.sara.constantes;

/**
 * Tipos de ubicación de un cajero
 * {"0":"Oficina","1":"Remoto","2":"Isla"}
 * @author jjvargas
 */
public enum TipoUbicacion {

    OFICINA(0, "Oficina"),
    REMOTO(1, "Remoto"),
    ISLA(2, "Isla"),
    EMPRESARIAL(3, "Empresarial'"),
    OFICINAPR(4, "Oficina_PR'"),
    OFICINAMOVIL(5, "Oficina_Movil"),
    BANAGRARIO(6, "Banagrario");
    public Integer codigo;
    public String nombre;

    TipoUbicacion(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static TipoUbicacion getTipoUbicacion(Integer codigo) {
        TipoUbicacion tipoUbicacion = TipoUbicacion.OFICINA;
        for (TipoUbicacion item : TipoUbicacion.values()) {
            if (item.codigo.equals(codigo)) {
                tipoUbicacion = item;
                break;
            }
        }
        return tipoUbicacion;
    }
    
     public static TipoUbicacion getTipoUbicacion(String nombre) {
        TipoUbicacion tipoUbicacion = TipoUbicacion.OFICINA;
        for (TipoUbicacion item : TipoUbicacion.values()) {
            if (item.nombre.equals(nombre)) {
                tipoUbicacion = item;
                break;
            }
        }
        return tipoUbicacion;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    
}
