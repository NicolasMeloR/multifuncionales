/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.multifuncional.constantes;

/**
 *
 * @author P-CCHAPA
 */
 public enum TipoCuentaMultifuncional {

    
    CuentaCorriente(1, "Cuenta Corriente"),
    CuentaAhorros(3, "Cuenta Ahorros"),
    CreditoFM(4, "Credito FM"),
    TarjetaCredito(5, "Tarjeta Credito"),
    NoDefinida(0, "NoDefinida"),
//  Todas(9, "Todas"),
//  DaviviendaTodas(10, "DaviviendaTodas"),
//   NotasDebito(11, "Notas Debito");
     FondoInversion(9, "Fondo Inversion"),
     DaviviendaTodas(10, "DaviviendaTodas"),
     CrediExpress(11, "CrediExpress"),
     NotasDebito(12, "Notas Debito"),
     CashInDaviplata(70, "CashInDaviplata"),
     Todas(15, "Todas"),
     PorRevisar(20, "Por Revisar");
   
    
    
    
    public Integer codigo;
    public String nombre;

    TipoCuentaMultifuncional(Integer codigo, String nombre) {
        this.codigo = codigo;
        
        this.nombre = nombre;
    }
    
    public static TipoCuentaMultifuncional getTipoCuentaMultifuncional(Integer codigo) {
        TipoCuentaMultifuncional tipoCuenta = TipoCuentaMultifuncional.NoDefinida;
        for (TipoCuentaMultifuncional item : TipoCuentaMultifuncional.values()) {
            if (item.codigo.equals(codigo)) {
                tipoCuenta = item;
                break;
            }
        }
        return tipoCuenta;
    }

    public static TipoCuentaMultifuncional getTipoCuentaMultifuncional(String nombre) {
        TipoCuentaMultifuncional tipoCuenta = TipoCuentaMultifuncional.NoDefinida;
        for (TipoCuentaMultifuncional item : TipoCuentaMultifuncional.values()) {
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
