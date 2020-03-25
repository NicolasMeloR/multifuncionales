/*
 * TimerServiceExcepcion.java
 *
 * Created on 31 de octubre de 2007, 10:04 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.davivienda.sara.administracion.tareas.programadas.excepcion;

/**
 *
 * @author Fernando Ocampo
 */
public class TimerServiceExcepcion extends Exception{
    
    /** Creates a new instance of TimerServiceExcepcion */
    public TimerServiceExcepcion() {
    }
    
    public TimerServiceExcepcion(String mensaje) {
        super(mensaje);
    }
    
    public TimerServiceExcepcion(String mensaje, Throwable th) {
        super(mensaje,th);
    }
    
}
