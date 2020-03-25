// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.diarioelectronicotemp.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import com.davivienda.sara.entitys.DiarioelectronicoTemp;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface DiarioElectronicoTempSessionLocal extends AdministracionTablasInterface<DiarioelectronicoTemp>
{
    Collection<DiarioelectronicoTemp> getDiarioElectronicoTemp(final Integer p0, final Date p1) throws EntityServicioExcepcion;
}
