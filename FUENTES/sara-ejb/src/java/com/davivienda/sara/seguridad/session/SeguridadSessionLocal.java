/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.seguridad.session;

import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.entitys.seguridad.ServicioAplicacion;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import java.util.Collection;
import javax.ejb.Local;

/**
 * SeguridadSessionLocal - 16/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface SeguridadSessionLocal {
    
        /**
     * Valida el Acceso a la aplicación 
     * @param idUsuario
     * @param direccionIP
     * @return
     */
    UsuarioAplicacion validarAccesoAplicacion(String idUsuario, String direccionIP);

    /**
     * Valida el Acceso a la aplicación 
     * @param idUsuario
     * @param direccionIP
     * @param token
     * @param claveEstatica
     * @return
     */
    UsuarioAplicacion validarAccesoAplicacion(String idUsuario, String direccionIP, String token, String claveEstatica);

    String getPrivilegiosUsuarioJSon(String usuario);
    
    public Collection<ConfAccesoAplicacion> getConfAccesoAplicacion(String idUsuario);
    
    public Collection<ServicioAplicacion> geServiciosAplicacion();

    
}
