package com.davivienda.sara.constantes;

/**
 * Tipos de ubicación de un cajero
 * {"0":"Oficina","1":"Remoto","2":"Isla"}
 * @author jjvargas
 */
public enum TipoProvision {

    POSTERIOR(0, "Posterior"),
    FRONTAL(1, "Frontal");
    public Integer codigo;
    public String nombre;

    TipoProvision(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static TipoProvision getTipoProvision(Integer codigo) {
        TipoProvision tipoProvision = TipoProvision.POSTERIOR;
        for (TipoProvision item : TipoProvision.values()) {
            if (item.codigo.equals(codigo)) {
                tipoProvision = item;
                break;
            }
        }
        return tipoProvision;
    }
    
     public static TipoProvision getTipoProvision(String nombre) {
        TipoProvision tipoProvision = TipoProvision.POSTERIOR;
        for (TipoProvision item : TipoProvision.values()) {
            if (item.nombre.equals(nombre)) {
                tipoProvision = item;
                break;
            }
        }
        return tipoProvision;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    
}
