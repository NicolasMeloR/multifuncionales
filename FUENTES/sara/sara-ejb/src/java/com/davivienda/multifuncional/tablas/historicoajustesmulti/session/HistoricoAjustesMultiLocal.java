// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.historicoajustesmulti.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Historicoajustesmulti;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface HistoricoAjustesMultiLocal extends AdministracionTablasInterface<Historicoajustesmulti>
{
    Collection<Historicoajustesmulti> getColeccionHistoricoAjustes(final Integer p0, final Integer p1, final Date p2, final Date p3, final Date p4) throws EntityServicioExcepcion;
    
    void guardarHistoricoAjustes(final String p0, final Integer p1, final Integer p2, final String p3, final Date p4, final Long p5, final String p6, final String p7, final String p8) throws EntityServicioExcepcion;
}
