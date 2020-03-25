// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.administracion.tareas.programadas.excepcion;

public class TimerServiceExcepcion extends Exception
{
    public TimerServiceExcepcion() {
    }
    
    public TimerServiceExcepcion(final String mensaje) {
        super(mensaje);
    }
    
    public TimerServiceExcepcion(final String mensaje, final Throwable th) {
        super(mensaje, th);
    }
}
