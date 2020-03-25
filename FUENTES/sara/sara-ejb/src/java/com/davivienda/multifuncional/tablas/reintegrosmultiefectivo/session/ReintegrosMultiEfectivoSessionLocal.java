// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface ReintegrosMultiEfectivoSessionLocal extends AdministracionTablasInterface<Reintegrosmultiefectivo>
{
    Collection<Reintegrosmultiefectivo> getReintegros(final Date p0, final Date p1, final Integer p2, final Date p3) throws EntityServicioExcepcion;
    
    Reintegrosmultiefectivo getReintegroXLlave(final Integer p0, final Date p1, final Integer p2, final Date p3) throws EntityServicioExcepcion;
    
    Reintegrosmultiefectivo getReintegroXCuentaValor(final Integer p0, final Date p1, final Integer p2, final String p3, final Long p4, final Date p5) throws EntityServicioExcepcion;
}
