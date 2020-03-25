// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.occa.session;

import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Occa;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface OccaSessionLocal extends AdministracionTablasInterface<Occa>
{
    Collection<Occa> getTodos(final Integer p0, final Integer p1) throws Exception;
}
