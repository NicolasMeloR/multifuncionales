// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.tipocajero.session;

import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.entitys.TipoCajero;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface TipoCajeroSessionLocal extends AdministracionTablasInterface<TipoCajero>
{
    Collection<TipoCajero> getTodos(final Integer p0, final Integer p1) throws Exception;
}
