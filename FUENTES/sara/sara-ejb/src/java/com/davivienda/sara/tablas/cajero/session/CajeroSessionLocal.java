// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.cajero.session;

import java.util.Collection;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.base.AdministracionTablasInterface;

public interface CajeroSessionLocal extends AdministracionTablasInterface<Cajero>
{
    Collection<Cajero> getTodosActivos(final Integer p0, final Integer p1) throws Exception;
    
    Collection<Cajero> getCajerosSnTransmitir(final Integer p0) throws Exception;
    
    Collection<Cajero> getCajerosMultiSnTransmitir(final Integer p0) throws Exception;
    
    Collection<Cajero> getTodosActivosMulti(final Integer p0, final Integer p1) throws Exception;
}
