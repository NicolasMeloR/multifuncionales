// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.diarioelectronico.session;

import java.io.FileNotFoundException;
import java.util.Calendar;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import com.davivienda.sara.entitys.DiarioElectronico;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface DiarioElectronicoSessionLocal extends AdministracionTablasInterface<DiarioElectronico>
{
    Collection<DiarioElectronico> getDiarioElectronico(final Integer p0, final Date p1) throws EntityServicioExcepcion;
    
    Collection<DiarioElectronico> getDiarioElectronicoDia(final Integer p0, final Calendar p1, final String p2) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException;
}
