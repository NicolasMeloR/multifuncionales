package com.davivienda.utilidades.edc;


/**
 * TipoRegistro.java
 * 
 * Fecha       :  30/05/2007, 07:26:48 PM
 * Descripción :  Identificación de los tipos de registro del EDC
 * 
 * @author     : jjvargas
 * @version    : $Id$
 */


public enum DenominacionBillete {
    
    Diez('B',"10000"),
    Veinte('C', "20000"),
    Cincuenta('E', "50000");
    
    public char codigo;
    public String nombre;
    /**
     * Constructor de <code>TipoRegistro</code>.
     */
    DenominacionBillete(char codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    
    public static  DenominacionBillete getDenominacionBillete(char unChar) {
                 DenominacionBillete denominacionBilletes =  DenominacionBillete.Diez ;
        for (DenominacionBillete elem : DenominacionBillete.values()) {
            if (elem.codigo == unChar){
                denominacionBilletes = elem;
                break;
            }
        }
        return denominacionBilletes;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    

}
