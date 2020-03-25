/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.multifuncional.constantes;

/**
 *
 * @author P-CCHAPA
 */
public enum EstadosDiarioMultifuncional {
    
    
    
        
//    TransaccionExitosa(0, "TransaccionExitosa"),
//    RetractEfectivo(66, "RetractEfectivo"),
//    RetractCheque(67, "RetractCheque"),
//    ErrorDeHost(98, "ErrorDeHost"),
//    Timeout(1, "Timeout"),
//    DepNoAutorizado(9, "DepNoAutorizado"),
//    CtaRestringida(3, "CtaRestringida"),
//    UsrNoRetiraDoc(9, "UsrNoRetiraDoc"),
//    ErrorEnLaCuenta(99, "ErrorEnLaCuenta"),
//    ErrorSoftware(96, "ErrorSoftware"),
//    ErrorHardware(50, "ErrorHardware"),
//    ErrorNoDefinido(51, "ErrorNoDefinido"),

    
    TransaccionExitosa(0, "TransaccionExitosa"),
    Retract(66, "Retract"),
    PlazaIncorrecta(98, "PlazaIncorrecta"),
    TimeOut(1, "TimeOut"),
    TimeOutModulo(9, "TimeOutModulo"),
    ErrorEnLaCuenta(99, "ErrorEnLaCuenta"),
    ErrorSoftware(96, "ErrorSoftware"),
    ErrorHardware(50, "ErrorHardware"),
    ErrorNoDefinido(88, "ErrorNoDefinido");
   
    
    
    
    public Integer codigo;
    public String nombre;

    EstadosDiarioMultifuncional(Integer codigo, String nombre) {
        this.codigo = codigo;
        
        this.nombre = nombre;
    }
    
    public static EstadosDiarioMultifuncional getEstadoDiarioMultifuncional(Integer codigo) {
        EstadosDiarioMultifuncional estado = EstadosDiarioMultifuncional.ErrorNoDefinido;
        for (EstadosDiarioMultifuncional item : EstadosDiarioMultifuncional.values()) {
            if (item.codigo.equals(codigo)) {
                estado = item;
                break;
            }
        }
        return estado;
    }

    public static EstadosDiarioMultifuncional getEstadoDiarioMultifuncional(String nombre) {
        EstadosDiarioMultifuncional estado = EstadosDiarioMultifuncional.ErrorNoDefinido;
        for (EstadosDiarioMultifuncional item : EstadosDiarioMultifuncional.values()) {
            if (item.nombre.equals(nombre)) {
                estado = item;
                break;
            }
        }
        return estado;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
}
