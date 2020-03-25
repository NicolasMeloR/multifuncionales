package com.davivienda.sara.constantes;

/**
 * Tipos de cuenta
 * {"0":"Cuenta Ahorros","1":"Cuenta Corriente"}
 * @author jjvargas
 */
public enum TipoCuenta {

    CuentaAhorros(3, "Cuenta Ahorros"),
    CuentaCorriente(1, "Cuenta Corriente");
    public Integer codigo;
    public String nombre;

    TipoCuenta(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static TipoCuenta getTipoCuenta(Integer codigo) {
        TipoCuenta tipoCuenta = TipoCuenta.CuentaAhorros;
        for (TipoCuenta item : TipoCuenta.values()) {
            if (item.codigo.equals(codigo)) {
                tipoCuenta = item;
                break;
            }
        }
        return tipoCuenta;
    }
    
     public static TipoCuenta getTipoCuenta(String nombre) {
      TipoCuenta tipoCuenta = TipoCuenta.CuentaAhorros;
        for (TipoCuenta item : TipoCuenta.values()) {
            if (item.nombre.equals(nombre)) {
                tipoCuenta = item;
                break;
            }
        }
        return tipoCuenta;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    
}

