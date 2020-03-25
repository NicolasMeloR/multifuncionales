// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.estadisticas;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.transaccion.CantidadTransaccionesBean;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

@Local
public interface EstadisticasGeneralesSessionLocal
{
    Collection<CantidadTransaccionesBean> getTransaccionesRealizadasPorCajero(final Integer p0, final Integer p1, final Integer p2, final Integer p3, final Date p4, final Date p5) throws EntityServicioExcepcion, IllegalArgumentException;
}
