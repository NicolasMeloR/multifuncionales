package com.davivienda.sara.constantes;

/**
 * tipoDiarioReintegro
 * {"3":"Cuenta Ahorros","1":"Cuenta Corriente"}
 * @author jjvargas
 */
public enum TipoDiario {

    Efectivo(0, "Efectivo"),
    Cheque(1, "Cheque"),
    Log(2, "Log"),
    Dispensador(3, "Dispensador");
    
    
    public Integer codigo;
    public String nombre;

    TipoDiario(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static TipoDiario getTipoDiario(Integer codigo) {
        TipoDiario tipoDiario = TipoDiario.Efectivo;
        for (TipoDiario item : TipoDiario.values()) {
            if (item.codigo.equals(codigo)) {
                tipoDiario = item;
                break;
            }
        }
        return tipoDiario;
    }
    
     public static TipoDiario getTipoDiario(String nombre) {
        TipoDiario tipoDiario = TipoDiario.Cheque;
        for (TipoDiario item : TipoDiario.values()) {
            if (item.nombre.equals(nombre)) {
                tipoDiario = item;
                break;
            }
        }
        return tipoDiario;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    

}

