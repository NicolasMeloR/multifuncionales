package com.davivienda.sara.constantes;

/**
 * Tipos de ubicación de un cajero
 * {"0":"Oficina","1":"Remoto","2":"Isla"}
 * @author jjvargas
 */
public enum TipoCajeroMulti {

    DISPENSADOR(0, "Dispensador"),
    MULTIFUNCIONAL(1, "Multifuncional");
    
    public Integer codigo;
    public String nombre;

    TipoCajeroMulti(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static TipoCajeroMulti getTipoCajeroMulti(Integer codigo) {
        TipoCajeroMulti tipoCajeroMulti = TipoCajeroMulti.DISPENSADOR;
        for (TipoCajeroMulti item : TipoCajeroMulti.values()) {
            if (item.codigo.equals(codigo)) {
                tipoCajeroMulti = item;
                break;
            }
        }
        return tipoCajeroMulti;
    }
    
     public static TipoCajeroMulti getTipoCajeroMulti(String nombre) {
      TipoCajeroMulti tipoCajeroMulti = TipoCajeroMulti.DISPENSADOR;
        for (TipoCajeroMulti item : TipoCajeroMulti.values()) {
            if (item.nombre.equals(nombre)) {
                tipoCajeroMulti = item;
                break;
            }
        }
        return tipoCajeroMulti;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    
}

