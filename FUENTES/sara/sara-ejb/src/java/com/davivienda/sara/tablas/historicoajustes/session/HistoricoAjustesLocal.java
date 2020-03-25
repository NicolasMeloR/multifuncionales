// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.historicoajustes.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import com.davivienda.sara.entitys.HistoricoAjustes;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface HistoricoAjustesLocal extends AdministracionTablasInterface<HistoricoAjustes>
{
    Collection<HistoricoAjustes> getColeccionHistoricoAjustes(final Integer p0, final Integer p1, final Date p2, final Date p3, final Date p4) throws EntityServicioExcepcion;
}
