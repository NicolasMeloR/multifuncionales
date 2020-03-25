// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.edccargue.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Edcarguemultifuncional;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface EDCCargueMultifuncionalLocal extends AdministracionTablasInterface<Edcarguemultifuncional>
{
    Collection<Edcarguemultifuncional> getEDCCargueXFecha(final Date p0, final Date p1) throws EntityServicioExcepcion;
}
