/*
 * EstructuraBase.java
 * Estructura de campos en el archivo EDC de Agilis
 * Created on 24/08/2007, 04:47:55 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.constantes;

/**
 *
 * @author jjvargas
 */
public enum EstructuraBase {
    TIPO_REGISTRO(0, 1),
    SECUENCIA(1, 7),
    FECHA(7, 18),
    CAJERO(23, 27),
    INFORMACION(28, 1096);
    
    public int posIni;
    public int posFin;
    
    EstructuraBase(int posIni, int posFin) {
        this.posIni = posIni;
        this.posFin = posFin;
    }
}



