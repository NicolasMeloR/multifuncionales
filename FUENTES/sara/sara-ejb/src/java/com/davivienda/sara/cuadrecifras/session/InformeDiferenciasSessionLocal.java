// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.cuadrecifras.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Calendar;
import javax.ejb.Local;
import com.davivienda.sara.entitys.InformeDiferencias;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface InformeDiferenciasSessionLocal extends AdministracionTablasInterface<InformeDiferencias>
{
    Collection<InformeDiferencias> getInformeDiferenciaXFecha(final Integer p0, final Calendar p1, final Calendar p2) throws EntityServicioExcepcion;
}
