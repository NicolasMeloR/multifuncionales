// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.administracion.tareas.programadas.util;

import com.davivienda.sara.administracion.tareas.programadas.excepcion.TimerServiceExcepcion;

public class ClassLoaderUtil
{
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
    
    public static Class getClass(final String classname) throws TimerServiceExcepcion {
        try {
            return getClassLoader().loadClass(classname);
        }
        catch (ClassNotFoundException ex) {
            throw new TimerServiceExcepcion("Error al invocar el metodo getClass. No se puede encontrar la clase", ex);
        }
    }
}
