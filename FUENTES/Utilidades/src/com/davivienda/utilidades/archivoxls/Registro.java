/*
 * Banco Davivienda 2008
 * Proyecto Utilidades
 * Versi�n  1.0
 */

package com.davivienda.utilidades.archivoxls;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Registro - 11/08/2008
 * Descripci�n : Registro que comprende una l�neaXLS
 * Versi�n : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class Registro {
    private Collection<Celda> celdas;

    public Registro() {
        celdas = new ArrayList<Celda>();
    }
        
    public void addCelda(Celda celda) {
        celdas.add(celda);
    }

    public Collection<Celda> getCeldas() {
        return celdas;
    }

    public void setCeldas(ArrayList<Celda> celdas) {
        this.celdas = celdas;
    }
    
    

}
