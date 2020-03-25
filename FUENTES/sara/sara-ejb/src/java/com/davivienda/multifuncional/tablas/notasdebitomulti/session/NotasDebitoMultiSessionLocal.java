// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.notasdebitomulti.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface NotasDebitoMultiSessionLocal extends AdministracionTablasInterface<Notasdebitomultifuncional>
{
    Collection<Notasdebitomultifuncional> getNotasDebito(final Date p0, final Date p1, final Integer p2, final Date p3) throws EntityServicioExcepcion;
    
    Notasdebitomultifuncional getNotasDebitoXLlave(final Integer p0, final Date p1, final Date p2) throws EntityServicioExcepcion;
    
    Notasdebitomultifuncional getNotasDebitoXCuentaValor(final Integer p0, final Date p1, final String p2, final Long p3, final Date p4) throws EntityServicioExcepcion;
}
