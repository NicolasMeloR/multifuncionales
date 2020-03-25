// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.regcargue.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface RegCargueArchivoSessionLocal extends AdministracionTablasInterface<Regcarguearchivo>
{
    Collection<Regcarguearchivo> getRegCargueXFecha(final Date p0, final Date p1) throws EntityServicioExcepcion;
}
