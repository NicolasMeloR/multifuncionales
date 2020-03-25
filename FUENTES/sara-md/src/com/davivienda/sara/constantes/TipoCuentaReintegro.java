package com.davivienda.sara.constantes;

/**
 * TipoCuentaReintegro
 * {"3":"Cuenta Ahorros","1":"Cuenta Corriente"}
 * @author jjvargas
 */
public enum TipoCuentaReintegro {

    OtraRed(0, "Otra Red"),
    CuentaCorriente(1, "Cuenta Corriente"),
    CuentaAhorros(3, "Cuenta Ahorros"),
    TarjetaCredito(4, "Tarjeta Credito"),
    VisaPagos(5, "Visa Pagos"),
    Banagrario(6, "Banagrario"),
    NoDefinida(8, "NoDefinida"),
    //en interfazGrafica
    Todas(9, "Todas"),
    DaviviendaTodas(10, "DaviviendaTodas"),
    NotasDebito(11, "Notas Debito"),
   // Bancafe(7, "Banagrario"),
    GiroDirecto(15, "Giro Directo"),
    Daviplata(16, "Daviplata"),
    SinMedio(17, "Sin Medio"),
    CIBC(18, "CIBC");
    
    public Integer codigo;
    public String nombre;

    TipoCuentaReintegro(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static TipoCuentaReintegro getTipoCuentaReintegro(Integer codigo) {
        TipoCuentaReintegro tipoCuenta = TipoCuentaReintegro.NoDefinida;
        for (TipoCuentaReintegro item : TipoCuentaReintegro.values()) {
            if (item.codigo.equals(codigo)) {
                tipoCuenta = item;
                break;
            }
        }
        return tipoCuenta;
    }
    
     public static TipoCuentaReintegro getTipoCuentaReintegro(String nombre) {
        TipoCuentaReintegro tipoCuenta = TipoCuentaReintegro.NoDefinida;
        for (TipoCuentaReintegro item : TipoCuentaReintegro.values()) {
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

