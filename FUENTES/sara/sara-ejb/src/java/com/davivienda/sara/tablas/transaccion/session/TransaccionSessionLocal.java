// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.transaccion.session;

import java.util.Map;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface TransaccionSessionLocal extends AdministracionTablasInterface<Transaccion>
{
    Collection<Transaccion> getColeccionTransaccion(final Integer p0, final Date p1, final Date p2) throws EntityServicioExcepcion;
    
    Collection<Transaccion> getColeccionTransaccion(final Integer p0, final Date p1) throws EntityServicioExcepcion;
    
    Transaccion getTransaccion(final Integer p0, final Date p1, final Integer p2) throws EntityServicioExcepcion;
    
    Collection<Transaccion> getColeccionTransaccion(final Integer p0, final Date p1, final Date p2, final Integer p3, final Map p4) throws EntityServicioExcepcion;
}
