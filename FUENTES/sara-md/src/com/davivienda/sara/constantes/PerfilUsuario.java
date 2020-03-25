package com.davivienda.sara.constantes;


/**
 * PerfilUsuario.java
 * 
 * Fecha       :  7/06/2007, 01:31:09 PM
 * Descripción :  Enumeración de los perfiles de Usuarios
 * 
 * @author     : jjvargas
 * @version    : $Id$
 */


public enum PerfilUsuario {
    
    ANONIMO(0,"0-0-0-0-0-0-0-0-0-0" , "Invitado", false),
    CONSULTA_DIARIO_ELECTRONIO(11,"1-1-0-0-0-0-0-0-0-0", "Consulta Diario Electrónico", false),
    CONSULTA_CUADRE_CAJEROS(12,"1-2-0-0-0-0-0-0-0-0", "Consulta Cuadre Cajeros", false),
    CONSULTA_REPORTES_CAJEROS(15,"1-5-0-0-0-0-0-0-0-0", "Consulta  Reportes cajeros", false),    
    CONSULTA(19,"1-9-0-0-0-0-0-0-0-0","Consulta general", false),    
    ANALISTA_DIARIO_ELECTRONICO(21,"2-1-0-0-0-0-0-0-0-0","Analista Diario Electrónico", false),
    ANALISTA_CUADRE_CAJEROS(22,"2-2-0-0-0-0-0-0-0-0","Analista Cuadre Cajeros", false),
    ANALISTA_REINTEGROS(23,"2-3-0-0-0-0-0-0-0-0","Analista Reintegros", false),
    ANALISTA(29,"2-9-0-0-0-0-0-0-0-0","Analista general", false),
    ADMINISTRACION_CAJEROS(79,"7-9-0-0-0-0-0-0-0-0", "Administrador de Cajeros", true),
    ADMINISTRACION_USUARIOS(89,"8-9-0-0-0-0-0-0-0-0","Administrador de Usuarios", true),
    AUDITORIA(91,"9-1-0-0-0-0-0-0-0-0","Auditoria", false),
    ADMINISTRACION_APLICACION(99,"9-9-0-0-0-0-0-0-0-0","Administrador Aplicación", true);

    
    public Integer perfil;
    public String codigoPerfil ;
    public String nombre ;
    public boolean validarIP;

    /**
     * Constructor de <code>PerfilUsuario</code>.
     */
    PerfilUsuario(Integer perfil ,String codigoPerfil, String nombre, boolean validarIP) {
        this.perfil = perfil;
        this.codigoPerfil = codigoPerfil;
        this.nombre = nombre;
        this.validarIP = validarIP;
        
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    public static PerfilUsuario getPerfilUsuario(Integer perfil) {
        PerfilUsuario perfilUsuario = PerfilUsuario.ANONIMO;
        for (PerfilUsuario perfilItem : PerfilUsuario.values()) {
            if (perfilItem.perfil.equals(perfil)) {
                perfilUsuario = perfilItem;
                break;
            }
        }
        return perfilUsuario;
    }
    
    


}
