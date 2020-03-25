/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.multifuncional.constantes;

/**
 *
 * @author P-CCHAPA
 */
public enum TotalesMultifuncional {

    TransaccionesXoficina(0, "TransaccionesXoficina "),
    ProductoXoficina(1, "ProductoXoficina"),
    TransaccionesXTerminal(2, "TransaccionesXTerminal"),
    ProductoXTerminal(3, "ProductoXTerminal");
    public Integer codigo;
    public String nombre;

    TotalesMultifuncional(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static TotalesMultifuncional getConsultaAjuste(Integer codigo) {
        TotalesMultifuncional consultaAjuste = TotalesMultifuncional.TransaccionesXoficina;
        for (TotalesMultifuncional item : TotalesMultifuncional.values()) {
            if (item.codigo.equals(codigo)) {
                consultaAjuste = item;
                break;
            }
        }
        return consultaAjuste;
    }

    public static TotalesMultifuncional getConsultaAjuste(String nombre) {
        TotalesMultifuncional totalesMultifuncional = TotalesMultifuncional.TransaccionesXoficina;
        for (TotalesMultifuncional item : TotalesMultifuncional.values()) {
            if (item.nombre.equals(nombre)) {
                totalesMultifuncional = item;
                break;
            }
        }
        return totalesMultifuncional;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
