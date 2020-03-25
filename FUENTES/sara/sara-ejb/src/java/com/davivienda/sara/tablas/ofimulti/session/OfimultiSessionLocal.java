// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.ofimulti.session;

import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Oficinamulti;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface OfimultiSessionLocal extends AdministracionTablasInterface<Oficinamulti>
{
    Collection<Oficinamulti> getTodos(final Integer p0, final Integer p1) throws Exception;
}
