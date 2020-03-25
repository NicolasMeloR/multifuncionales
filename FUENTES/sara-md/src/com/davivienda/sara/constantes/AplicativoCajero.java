package com.davivienda.sara.constantes;

/**
 * AplicativoCajero.java
 *
 * Fecha       :23 de enero de 2007, 08:14 PM
 *
 * Descripción :
 *
 * @author     :jjvargas
 *
 */

public enum AplicativoCajero {
    AGILIS(0,"AGILIS"),
    PACE(1,"PACE"),
    NDC(2, "NDC"),
    PAS(3, "PAS"),
    EMPOWER(4,"EmPower");
    
    public Integer codigo;
    public String nombre ;
    
    AplicativoCajero(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    
    public static boolean esAplicativoCajero(Integer codigo) {
        boolean rep = false;
        for (AplicativoCajero elem : AplicativoCajero.values()) {
            if (elem.codigo.equals(codigo)) {
                rep = true;
                break;
            }
        }
        return rep;
    }
    
    public static AplicativoCajero getAplicativoCajero(Integer codigo) {
        AplicativoCajero aplicativoCajero = AplicativoCajero.PACE;
        for (AplicativoCajero elem : AplicativoCajero.values()) {
            if (elem.codigo.equals(codigo)) {
                aplicativoCajero = elem;
                break;
            }
        }
        return aplicativoCajero;
    }
  
      public static AplicativoCajero getAplicativoCajero(String nombre) {
        AplicativoCajero aplicativoCajero = AplicativoCajero.PACE;
        for (AplicativoCajero elem : AplicativoCajero.values()) {
            if (elem.nombre.equals(nombre)) {
                aplicativoCajero = elem;
                break;
            }
        }
        return aplicativoCajero;
    }          
            
    
    @Override
    public String toString() {
        return this.nombre;
    }
    
    
    
    
    
}
