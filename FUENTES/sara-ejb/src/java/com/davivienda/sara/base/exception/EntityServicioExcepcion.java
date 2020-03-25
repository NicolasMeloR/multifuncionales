package com.davivienda.sara.base.exception;

/**
 *
 * @author jjvargas
 */
public class EntityServicioExcepcion extends Exception {

    /**
     * Creates a new instance of <code>EntityServicioExcepcion</code> without detail message.
     */
    public EntityServicioExcepcion() {
    }


    /**
     * Constructs an instance of <code>EntityServicioExcepcion</code> with the specified detail message.
     * @param msg the detail message.
     */
    public EntityServicioExcepcion(String msg) {
        super(msg);
    }

    public EntityServicioExcepcion(Throwable cause) {
        super(cause);
    }

    public EntityServicioExcepcion(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
