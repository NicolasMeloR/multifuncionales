package com.davivienda.sara.constantes;

/**
 * SistemaOperativo.java
 *
 * Fecha       :23 de enero de 2007, 08:17 PM
 *
 * Descripción :
 *
 * @author     :jjvargas
 *
 */
public enum SistemaOperativo {

    WINDOWS(0, "WINDOWS_XP"),
    WIN2000(1, "WINDOWS_2000"),
    OS2(2, "OS2");
    public Integer codigo;
    public String nombre;

    SistemaOperativo(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static SistemaOperativo getSistemaOperativo(Integer codigo) {
        SistemaOperativo sistemaOperativo = SistemaOperativo.WINDOWS;
        for (SistemaOperativo item : SistemaOperativo.values()) {
            if (item.codigo.equals(codigo)) {
                sistemaOperativo = item;
                break;
            }
        }
        return sistemaOperativo;
    }
     public static SistemaOperativo getSistemaOperativo(String nombre) {
        SistemaOperativo sistemaOperativo = SistemaOperativo.WINDOWS;
        for (SistemaOperativo item : SistemaOperativo.values()) {
            if (item.nombre.equals(nombre)) {
                sistemaOperativo = item;
                break;
            }
        }
        return sistemaOperativo;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    
}
