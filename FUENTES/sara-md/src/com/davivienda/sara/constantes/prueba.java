package com.davivienda.sara.constantes;

/**
 * Tipos de cuenta
 * {"0":"Cuenta Ahorros","1":"Cuenta Corriente"}
 * @author jjvargas
 */
public enum prueba {

    prueba1(1, "prueba1"),
    prueba2(2, "prueba2"),
    prueba3(3, "prueba3");
    public Integer codigo;
    public String nombre;

    prueba(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static prueba getPrueba(Integer codigo) {
        prueba pruebar = prueba.prueba1;
        for (prueba item : prueba.values()) {
            if (item.codigo.equals(codigo)) {
                pruebar = item;
                break;
            }
        }
        return pruebar;
    }
    
     public static prueba getPrueba(String nombre) {
      prueba pruebar = prueba.prueba1;
        for (prueba item : prueba.values()) {
            if (item.nombre.equals(nombre)) {
                pruebar = item;
                break;
            }
        }
        return pruebar;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    
}