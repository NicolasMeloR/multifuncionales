// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.reintegrosmultifuncional.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import com.davivienda.sara.entitys.ReintegrosMultifuncional;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface ReintegrosMultifuncionalSessionLocal extends AdministracionTablasInterface<ReintegrosMultifuncional>
{
    Collection<ReintegrosMultifuncional> getReintegrosMultifuncional(final Date p0, final Date p1, final Integer p2) throws EntityServicioExcepcion;
}
