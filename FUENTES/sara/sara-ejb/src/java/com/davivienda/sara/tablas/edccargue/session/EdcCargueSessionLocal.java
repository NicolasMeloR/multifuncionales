// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.edccargue.session;

import java.util.Date;
import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.entitys.EdcCargue;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface EdcCargueSessionLocal extends AdministracionTablasInterface<EdcCargue>
{
    Collection<EdcCargue> getCicloSnError(final Integer p0) throws Exception;
    
    Collection<EdcCargue> getEDCCarguePorFecha(final Date p0, final Date p1) throws Exception;
    
    int borrarCiclo(final Integer p0) throws Exception;
}
