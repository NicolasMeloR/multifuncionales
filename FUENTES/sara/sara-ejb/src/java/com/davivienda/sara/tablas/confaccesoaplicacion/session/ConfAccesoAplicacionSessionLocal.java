// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.confaccesoaplicacion.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface ConfAccesoAplicacionSessionLocal extends AdministracionTablasInterface<ConfAccesoAplicacion>
{
    Collection<ConfAccesoAplicacion> getTodos(final Integer p0, final Integer p1) throws Exception;
    
    int borrarPorUsuario(final String p0) throws Exception;
    
    int getNumElementosPorUsuario(final String p0) throws Exception;
    
    Collection<ConfAccesoAplicacion> getElementosPorUsuario(final String p0) throws Exception;
    
    void AddBorrarRegAccesoUsuario() throws EntityServicioExcepcion;
}
