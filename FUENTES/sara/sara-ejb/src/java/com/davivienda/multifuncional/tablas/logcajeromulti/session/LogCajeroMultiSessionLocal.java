// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.logcajeromulti.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Logcajeromulti;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface LogCajeroMultiSessionLocal extends AdministracionTablasInterface<Logcajeromulti>
{
    Collection<Logcajeromulti> getColeccionLogCajeroMulti(final Integer p0, final Date p1) throws EntityServicioExcepcion;
}
