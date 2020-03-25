/*
 * Inicial.java
 * 
 * Created on 18/05/2007, 04:30:43 PM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.davivienda.utilidades.conversion;

import java.math.BigDecimal;
import java.util.Formatter;

/**
 *
 * @author jjvargas
 */
public class Inicial {

    /** Creates a new instance of Inicial */
    public Inicial() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        String resp = JSon.getJSonRespuesta(0, "xxx");
          Long newl2 =new Long(755620);
        BigDecimal newl =new BigDecimal(800008955.234);
        resp=com.davivienda.utilidades.conversion.Numero.aMonedaDecimal(newl);
        System.out.print(newl2.toString().substring(newl2.toString().length()-2));
        
    
    }

}
