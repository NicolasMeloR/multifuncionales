// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.binentidad.session;

import javax.ejb.Local;
import com.davivienda.sara.entitys.BinEntidad;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface BinEntidadSessionLocal extends AdministracionTablasInterface<BinEntidad>
{
    BinEntidad getEntidadXBin(final String p0) throws Exception;
}
