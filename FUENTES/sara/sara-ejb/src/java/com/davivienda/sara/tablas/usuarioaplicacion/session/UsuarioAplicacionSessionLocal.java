// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.usuarioaplicacion.session;

import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface UsuarioAplicacionSessionLocal extends AdministracionTablasInterface<UsuarioAplicacion>
{
    Collection<UsuarioAplicacion> getTodos(final Integer p0, final Integer p1) throws Exception;
}
