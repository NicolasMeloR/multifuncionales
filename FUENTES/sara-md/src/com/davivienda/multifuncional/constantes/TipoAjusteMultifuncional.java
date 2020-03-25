/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.multifuncional.constantes;

import com.davivienda.sara.constantes.*;

/**
 * TipoAjusteMultifuncional - 02/06/2011
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-CCHAPA
 * Davivienda 2011
 */

public enum TipoAjusteMultifuncional {

    Inicio(0, "0 - Inicio"),
    Sobrante(1, "1 - Sobrante"),
    Faltante(2, "2 - Faltante"),
    Sobrante_Arqueo(3, "3 - Sobrante X Arqueo"),
    Faltante_Arqueo(4, "4 - Faltante X Arqueo"),
    Disminucion(5, "5 - Disminucion");


    public Integer codigo;
    public String nombre;
    
    TipoAjusteMultifuncional(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static TipoAjusteMultifuncional getTipoAjusteMultifuncional(Integer codigo) {
        TipoAjusteMultifuncional tipoAjuste = TipoAjusteMultifuncional.Inicio;
        for (TipoAjusteMultifuncional item : TipoAjusteMultifuncional.values()) {
            if (item.codigo.equals(codigo)) {
                tipoAjuste = item;
                break;
            }
        }
        return tipoAjuste;
    }
     public static TipoAjusteMultifuncional getTipoAjusteMutifuncional(String nombre) {
        TipoAjusteMultifuncional tipoAjuste = TipoAjusteMultifuncional.Inicio;
        for (TipoAjusteMultifuncional item : TipoAjusteMultifuncional.values()) {
            if (item.nombre.equals(nombre)) {
                tipoAjuste = item;
                break;
            }
        }
        return tipoAjuste;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}



