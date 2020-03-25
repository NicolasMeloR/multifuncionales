package com.davivienda.sara.constantes;



public enum TareaBdConfAccesoAplicacion {
    
    /**
     * borra un registro en la tabla CONF_ACCESO_APLICACION
     */
    BORRAREGCONFACCESO(0),
    /**
     * borra todos los registros de un usuarioa en la tabla CONF_ACCESO_APLICACION y el registro de la tabla USUARIO_APLICACION
     */
    BORRATODOSUSUARIO(1),
    /**
     * adiciona un registro en la tabla CONF_ACCESO_APLICACION
     */
    INSERTAREGCONFACCESO(2),
    
      /**
     * adiciona un registro en la tabla  USUARIO_APLICACION
     */
    INSERTAREGUSUARIO(3);
    
    
    
    
    /**
     * Código del estado del proceso
     */
    public int codigo;
   
    
   
    TareaBdConfAccesoAplicacion(Integer codigo) {
        this.codigo = codigo;
      
    }
    public Integer getTareaBD() {
        return this.codigo;
    }
    
    public static TareaBdConfAccesoAplicacion getTareaBD(int codigo) {
        TareaBdConfAccesoAplicacion tareabd =  TareaBdConfAccesoAplicacion.BORRAREGCONFACCESO ;
        for (TareaBdConfAccesoAplicacion elem : TareaBdConfAccesoAplicacion.values()) {
            if (elem.codigo == codigo) {
                tareabd = elem;
                break;
            }
        } 
       return tareabd;
    }


  
    

}


