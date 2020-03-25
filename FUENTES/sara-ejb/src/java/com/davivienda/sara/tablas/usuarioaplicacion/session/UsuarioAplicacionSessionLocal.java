package com.davivienda.sara.tablas.usuarioaplicacion.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import javax.ejb.Local;
import java.util.Collection;


/**
 * UsuarioAplicacionSessionLocal - 19/08/2008
 Descripción : 
 Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface UsuarioAplicacionSessionLocal extends AdministracionTablasInterface<UsuarioAplicacion> {

    
 
    public Collection<UsuarioAplicacion> getTodos(Integer pagina, Integer regsPorPagina) throws Exception;
}