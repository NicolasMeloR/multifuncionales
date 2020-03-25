// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.regional.session;

import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Regional;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface RegionalSessionLocal extends AdministracionTablasInterface<Regional>
{
    Collection<Regional> getTodos(final Integer p0, final Integer p1) throws Exception;
}
