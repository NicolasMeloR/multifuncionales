package com.davivienda.sara.constantes;

/**
 * EstadoProceso.java
 *
 * Fecha       :11 de abril de 2007, 12:20 PM
 *
 * Descripción : Enumeración con los posibles estados de un proceso
 *
 * @author     :jjvargas
 *
 * @vesion     : $Id
 *
 * Estados que puede presentar un proceso
 * @see com.davivienda.adminatm.modelodatos.enumeraciones.CodigoError
 *
 */

public enum EstadoProceso {
    
    /**
     * Proceso finalizado correctamente
     */
    FINALIZADO(0),
    /**
     * Se inicia el proceso
     */
    INICIADO(1),
    /**
     * Proceso I/O Aperura de un archivo
     */
    APERTURA_ARCHIVO(2),
    /**
     * Proceso I/O Lectura de un archivo
     */
    LECTURA_ARCHIVO(3),
    /**
     * Proceso I/O Cierre de in archivo
     */
    CIERRE_ARCHIVO(4),
    /**
     * Proceso en espera
     */
    ESPERA(5),
    /**
     * Conectando con un recurso externo puede ser una base de datos
     */
    CONECTANDO(6),
    /**
     * Proceso de Persistencia - Graba el registro o entity
     */
    PERSISTENCIA_GRABAR_REGISTRO(7),
    /**
     * Proceso de Persistencia - Asocia el objeto a la unidad de persistencia
     */
    PERSISTENCIA_ASOCIAR_REGISTRO(8),
    /**
     * Tipo de proceso no definido, se retorna cuando se busca un proceso por código y no existe
     */
    ESTADO_PROCESO_INDEFINIDO(9),
    
        /**
     *Estado generico de procesando
     */
    PROCESANDO(10),
    
    //NUEVO ALVARO 22 MAYO
    
       /**
     *Estado error SOLO para RegCargueArchivo  
     */
     ERROR(11),
    
    /**
     * Código de error cuando no se cargan todos los archivos
     */
    
    ERROR_NO_SE_CARGARON_TODOS_LOSARCHIVOS(15),
    
    
     /**
     * Código de Cargue manual
     */
    
    
    CARGUEMANUAL(16);
    
    public int codigo;
    
    /**
     * Estados que puede presentar un proceso
     * @see com.davivienda.adminatm.modelodatos.enumeraciones.CodigoError
     * {@link com.davivienda.adminatm.modelodatos.enumeraciones.CodigoError}
     * @param estado Código del estado del proceso
     */
    EstadoProceso(int estado) {
        this.codigo = estado;
    }
    public Integer getEstado() {
        return this.codigo;
    }
    
    public static EstadoProceso getEstadoProceso(int codigo) {
        EstadoProceso estadoProceso =  EstadoProceso.ESTADO_PROCESO_INDEFINIDO ;
        for (EstadoProceso elem : EstadoProceso.values()) {
            if (elem.codigo == codigo) {
                estadoProceso = elem;
                break;
            }
        } 
       return estadoProceso;
    }
    
}
