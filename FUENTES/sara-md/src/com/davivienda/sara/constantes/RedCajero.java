package com.davivienda.sara.constantes;

/**
 * RedEnrutaReverso.java
 *
 *
 * Descripción :
 *
 *
 *
 */
public enum RedCajero {
    
   
   SERVIBANCA(34),
   REDEBANK(35),
   NODEFINIDA(0);
    
    /**
     * Código del estado del proceso
     */
    public int codigo;
    
    /**
     * Estados que puede presentar un proceso
     * @see com.davivienda.adminatm.modelodatos.enumeraciones.CodigoError
     * {@link com.davivienda.adminatm.modelodatos.enumeraciones.CodigoError}
     * @param estado Código del estado del proceso
     */
    RedCajero(int estado) {
        this.codigo = estado;
    }
    public Integer getRedCajero() {
        return this.codigo;
    }
    
    public static RedCajero getRedCajero(int codigo) {
        RedCajero redEnrutaReverso =  RedCajero.NODEFINIDA ;
        for (RedCajero elem : RedCajero.values()) {
            if (elem.codigo == codigo) {
                redEnrutaReverso = elem;
                break;
            }
        } 
       return redEnrutaReverso;
    }
    
}
