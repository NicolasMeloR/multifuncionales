package com.davivienda.sara.constantes;

/**
 * EstadoProceso.java
 *
 * Fecha       :11 de abril de 2007, 12:20 PM
 *
 * Descripción : Enumeración con los posibles estados de un proceso de reintegro
 *
 * @author     :jjvargas
 *
 * @vesion     : $Id
 *
 * Estados que puede presentar un proceso de reintegro
 * @see com.davivienda.adminatm.modelodatos.enumeraciones.CodigoError
 *
 */

public enum EstadoReintegro {
    
    /**
     * 
     */
    REVERSADOINICIADO(98,"Reversado Iniciado"),
    /**
     *Estado de Reintegro Reversado exitos
     */
    REVERSADOEXITOSO(100,"Reversado Exitoso"),
    
    /**
     *Estado de Inicio Reintegro Cancelado
     */
    REINTEGROCANCELADO(99,"Reintegro Cancelado"),
    
    /**
     * Proceso finalizado correctamente
     */
    FINALIZADOEXITOSO(10,"Finalizado Exitoso"),
    /**
     * Se inicia el proceso
     */
    INICIADO(1,"Iniciado"),
    /**
     * Reintegro Revisado Autorizado por parte del analista
     */
    REVISADOAUTORIZADO(2,"Revisado"),
    
    /**
     * Reintegro Revisado NO Autorizado por parte del analista
     */
    REVISADONOAUTORIZADO(3,"Revisado"),
    
     /**
     * Reintegro autorizado por parte del supervisor
     */
    AUTORIZADOSUPERVISOR(5,"Autorizado"),
    
     /**
     *Estado de Error 
     */
    ERROR(6,"Error"),
    
     /**
     *Estado de Error al guardar en stratus
     */
    ERRORSTRATUS(7,"Error Stratus"),

     /**
     *Estado de Error al guardar en stratus
     */
    REINTEGROTIPOCREADO(8,"Reintegro Tipo Creado"),
    
     /**
     *Estado de Inicio Reintegro Creado
     */
    REINTEGROINICIATCREADO(9,"Reintegro Inicia Creado");
        
    /**
     * Código del estado del proceso
     */
    public int codigo;
    public String nombre;
    
    /**
     * Estados que puede presentar un proceso
     * @see com.davivienda.adminatm.modelodatos.enumeraciones.CodigoError
     * {@link com.davivienda.adminatm.modelodatos.enumeraciones.CodigoError}
     * @param estado Código del estado del proceso
     */
    EstadoReintegro(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    public Integer getEstado() {
        return this.codigo;
    }
    
    public static EstadoReintegro getEstadoReintegro(int codigo) 
    {
        EstadoReintegro estadoReintegro =  EstadoReintegro.INICIADO ;
        for (EstadoReintegro elem : EstadoReintegro.values()) {
            if (elem.codigo == codigo) {
                estadoReintegro = elem;
                break;
            }
        } 
       return estadoReintegro;
    }
 public static EstadoReintegro getEstadoReintegro(String nombre) {
        EstadoReintegro estadoReintegro = EstadoReintegro.INICIADO ;
        for (EstadoReintegro item : EstadoReintegro.values()) {
            if (item.nombre.equals(nombre)) {
                estadoReintegro = item;
                break;
            }
        }
        return estadoReintegro;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    public static class REVERSADOEXITOSO {

        public REVERSADOEXITOSO() {
        }
    }
    

}


