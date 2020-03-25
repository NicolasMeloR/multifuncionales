// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.seguridad.session;

import com.davivienda.sara.entitys.seguridad.ServicioAplicacion;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import java.util.Collection;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import javax.ejb.Local;

@Local
public interface SeguridadSessionLocal
{
    UsuarioAplicacion validarAccesoAplicacion(final String p0, final String p1);
    
    UsuarioAplicacion validarAccesoAplicacion(final String p0, final String p1, final String p2, final String p3);
    
    String getPrivilegiosUsuarioJSon(final String p0);
    
    Collection<ConfAccesoAplicacion> getConfAccesoAplicacion(final String p0);
    
    Collection<ServicioAplicacion> geServiciosAplicacion();
}
