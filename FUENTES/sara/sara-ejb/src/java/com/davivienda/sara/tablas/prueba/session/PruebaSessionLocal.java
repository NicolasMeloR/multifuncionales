// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.prueba.session;

import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Prueba2;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface PruebaSessionLocal extends AdministracionTablasInterface<Prueba2>
{
    Collection<Prueba2> getTodos();
}
