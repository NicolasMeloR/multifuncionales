// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.confmodulosaplicacion.session;

import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface ConfModulosAplicacionLocal extends AdministracionTablasInterface<ConfModulosAplicacion>
{
    Collection<ConfModulosAplicacion> getTodos(final Integer p0, final Integer p1) throws Exception;
}
