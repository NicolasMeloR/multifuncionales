/*
 * ClassLoaderUtil.java
 *
 */
package com.davivienda.sara.administracion.tareas.programadas.util;

import com.davivienda.sara.administracion.tareas.programadas.excepcion.TimerServiceExcepcion;


/**
 * Clase utilitaria para obtener el cargador de clases.
 *
 * @author Fernando Ocampo [fernando.ocampo@itac.com.co]
 */
public class ClassLoaderUtil {

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

  /**
   * Retorna un objeto de tipo Class que corresponde al nombre de clase dado.
   *
   * @param classname String
   * @return Class
   * @throws TimerServiceExcepcion
   */
  public static Class getClass(String classname) throws TimerServiceExcepcion {
        try {
            return getClassLoader().loadClass(classname);
        } catch(ClassNotFoundException ex) {
            throw new TimerServiceExcepcion(
                "Error al invocar el metodo getClass. No se puede encontrar la clase", ex);
        }
    }

}
