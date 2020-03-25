/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.multifuncional.constantes;

import com.davivienda.sara.constantes.*;

/**
 * TipoMovimientoCuadreMultifuncional - 02/06/2011
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-CCHAPA
 * Davivienda 2011
 */
public enum TipoMovimientoCuadreMultifuncional {

    Sobrante(215),
    Faltante(201),
    Sobrante_Arqueo(210),
    Faltante_Arqueo(202),
    Disiminucion(211);

    public Integer codigo;

    TipoMovimientoCuadreMultifuncional(Integer codigo) {
        this.codigo = codigo;
    }

    public static TipoMovimientoCuadre getTipoMovimientoCuadreMultifuncional(final int codigo) {
        for (TipoMovimientoCuadre tipoMovimientoCuadre : TipoMovimientoCuadre.values()) {
            if (tipoMovimientoCuadre.codigo == codigo) {
                return tipoMovimientoCuadre;
            }
        }
        return null;
    }
}
