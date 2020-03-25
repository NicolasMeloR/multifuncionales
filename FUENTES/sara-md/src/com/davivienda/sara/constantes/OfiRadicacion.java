package com.davivienda.sara.constantes;

public enum OfiRadicacion {

    OtraRed(0, "Otra Red"),
    CIBC(4909, "CIBC"),
    REDEBAN(34, "REDEBAN"),
    SERVIBANCA(35, "SERVIBANCA"),
    CREDIBANCO(31, "CREDIBANCO");
    public Integer codigo;
    public String nombre;

    OfiRadicacion(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static OfiRadicacion getOfiRadicacion(Integer codigo) {
        OfiRadicacion ofiRadicacion = OfiRadicacion.OtraRed;
        for (OfiRadicacion item : OfiRadicacion.values()) {
            if (item.codigo.equals(codigo)) {
                ofiRadicacion = item;
                break;
            }
        }
        return ofiRadicacion;
    }

    public static OfiRadicacion getOfiRadicacion(String nombre) {
        OfiRadicacion ofiRadicacion = OfiRadicacion.OtraRed;
        for (OfiRadicacion item : OfiRadicacion.values()) {
            if (item.nombre.equals(nombre)) {
                ofiRadicacion = item;
                break;
            }
        }
        return ofiRadicacion;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
