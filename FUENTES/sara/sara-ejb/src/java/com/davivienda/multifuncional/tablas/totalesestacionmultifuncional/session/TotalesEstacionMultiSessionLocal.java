// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.totalesestacionmultifuncional.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Calendar;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Totalesestacionmultifuncional;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface TotalesEstacionMultiSessionLocal extends AdministracionTablasInterface<Totalesestacionmultifuncional>
{
    Collection<Totalesestacionmultifuncional> getTotalesEstacionMultiRangoFecha(final Calendar p0, final Calendar p1) throws EntityServicioExcepcion;
}
