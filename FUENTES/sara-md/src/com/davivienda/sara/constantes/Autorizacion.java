package com.davivienda.sara.constantes;

/**
 * Descripción : Tipos de autorización de acceso a la aplicación
 * @author jjvargas
 */
public enum Autorizacion {
    DENEGADA(0),
    CLIENTE_CONFIABLE(1),
    CLIENTE_CONFIABLE_IP_RESTINGIDA(2),
    CLIENTE_CONFIABLE_IP_RESTINGIDA_TOKEN(3),
    CLIENTE(4),
    CLIENTE_IP_RESTINGIDA(5),
    CLIENTE_IP_RESTINGIDA_TOKEN(6);
    
    
    
    private Integer codigo;
    
    Autorizacion(Integer codigo){
        this.codigo = codigo;
    }
    
    public Autorizacion getAutorizacion(Integer codigoAutorizacion){
        for (Autorizacion autorizacion : Autorizacion.values() ) {
            if (autorizacion.codigo == codigoAutorizacion) {
                return autorizacion;
            }
        }
        return null;
    }
    
            

}
