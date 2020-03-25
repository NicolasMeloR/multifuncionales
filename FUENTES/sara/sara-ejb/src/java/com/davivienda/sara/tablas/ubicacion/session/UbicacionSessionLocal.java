// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.ubicacion.session;

import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Ubicacion;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface UbicacionSessionLocal extends AdministracionTablasInterface<Ubicacion>
{
    Collection<Ubicacion> getTodos(final Integer p0, final Integer p1) throws Exception;
}
