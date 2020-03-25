// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.biletajemulti.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Calendar;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Billetajemulti;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface BilletajeMultiSessionLocal extends AdministracionTablasInterface<Billetajemulti>
{
    Collection<Billetajemulti> getBilletajeMultiRangoFecha(final Calendar p0, final Calendar p1) throws EntityServicioExcepcion;
}
