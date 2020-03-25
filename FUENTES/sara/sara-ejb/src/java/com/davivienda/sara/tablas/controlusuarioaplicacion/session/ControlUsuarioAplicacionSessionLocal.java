// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.controlusuarioaplicacion.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.entitys.ControlUsuarioAplicacion;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface ControlUsuarioAplicacionSessionLocal extends AdministracionTablasInterface<ControlUsuarioAplicacion>
{
    Collection<ControlUsuarioAplicacion> getTodos(final Integer p0, final Integer p1) throws Exception;
    
    ControlUsuarioAplicacion getControlUsuarioAplicacion(final String p0) throws EntityServicioExcepcion;
    
    void eliminarDatosControlUsuario();
    
    void eliminarDatosControlXUsuario(final String p0);
}
