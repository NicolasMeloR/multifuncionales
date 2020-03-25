package com.davivienda.utilidades.conversion;


/**
 * SignoANumero.java
 * 
 * Fecha       :  1/08/2007, 11:09:12 PM
 * Descripción :  tabla de conversión de cifras de host con el signo incluído en el dato del valor
 * 
 * @author     : jjvargas
 * @version    : $Id$
 */


public enum SignoANumero {
    LLAVE_ABRE('{', 0),
    A('A',1),
    B('B',2),
    C('C',3),
    D('D',4),
    E('E',5),
    F('F',6),
    G('G',7),
    H('H',8),
    I('I',9);
    
    public char signo ;
    public int numero ;

    /**
     * Constructor de <code>SignoANumero</code>.
     */
    SignoANumero(char signo ,int numero) {
        this.numero = numero;
        this.signo = signo;
    }
    
    public static int getNumero(char letra) {
        int temp = 0;
        for (SignoANumero signoANumero : SignoANumero.values()) {
            if (signoANumero.signo == letra) {
                temp = signoANumero.numero;
                break;
            }
        }
        return temp;
    }


}
