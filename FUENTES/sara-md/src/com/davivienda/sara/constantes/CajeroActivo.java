package com.davivienda.sara.constantes;

/**
 * Tipos de ubicación de un cajero
 * {"0":"Oficina","1":"Remoto","2":"Isla"}
 * @author jjvargas
 */
public enum CajeroActivo {

    INACTIVO(0, "Inactivo"),
    ACTIVO(1, "Activo");
    public Integer codigo;
    public String nombre;

    CajeroActivo(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static CajeroActivo getCajeroActivo(Integer codigo) {
        CajeroActivo cajeroActivo = CajeroActivo.INACTIVO;
        for (CajeroActivo item : CajeroActivo.values()) {
            if (item.codigo.equals(codigo)) {
                cajeroActivo = item;
                break;
            }
        }
        return cajeroActivo;
    }
    
     public static CajeroActivo getCajeroActivo(String nombre) {
      CajeroActivo cajeroActivo = CajeroActivo.INACTIVO;
        for (CajeroActivo item : CajeroActivo.values()) {
            if (item.nombre.equals(nombre)) {
                cajeroActivo = item;
                break;
            }
        }
        return cajeroActivo;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    
}

