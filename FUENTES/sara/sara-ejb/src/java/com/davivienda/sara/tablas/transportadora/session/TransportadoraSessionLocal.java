// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.transportadora.session;

import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Transportadora;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface TransportadoraSessionLocal extends AdministracionTablasInterface<Transportadora>
{
    Collection<Transportadora> getTodos(final Integer p0, final Integer p1) throws Exception;
}
