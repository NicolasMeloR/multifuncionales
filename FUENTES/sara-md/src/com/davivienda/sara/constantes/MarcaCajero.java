package com.davivienda.sara.constantes;

/**
 * MarcaCajero.java
 *
 * Fecha       :23 de enero de 2007, 07:53 PM
 *
 * Descripción :
 *
 * @author     :jjvargas
 *
 */
public enum MarcaCajero {

    DIEBOLD(0, "Diebold"),
    NCR(1, "NCR");
    public Integer codigo;
    public String nombre;

    MarcaCajero(Integer codigo, String inicial) {
        this.codigo = codigo;
        this.nombre = inicial;
    }

    public static MarcaCajero getMarcaCajero(Integer codigo) {
        MarcaCajero marcaCajero = MarcaCajero.DIEBOLD;
        for (MarcaCajero elem : MarcaCajero.values()) {
            if (elem.codigo.equals(codigo)) {
                marcaCajero = elem;
                break;
            }
        }
        return marcaCajero;
    }
    
        public static MarcaCajero getMarcaCajero(String nombre) {
        MarcaCajero marcaCajero =  MarcaCajero.DIEBOLD;
        for (MarcaCajero elem : MarcaCajero.values()) {
            if (elem.nombre.equals(nombre)) {
                marcaCajero = elem;
                break;
            }
        }
        return marcaCajero;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
    
}
