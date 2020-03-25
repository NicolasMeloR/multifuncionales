// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.cuadrecifrasmulti.session;

import java.util.Collection;
import java.util.Date;
import com.davivienda.sara.entitys.Tipomovimientocuadremulti;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Movimientocuadremulti;
import javax.ejb.Local;

@Local
public interface CuadreCifrasMultiSessionLocal
{
    Movimientocuadremulti grabarMovimientoCuadre(final Movimientocuadremulti p0, final boolean p1) throws EntityServicioExcepcion;
    
    Tipomovimientocuadremulti buscarTipoMovimientoCuadre(final Integer p0) throws EntityServicioExcepcion;
    
    Collection<Movimientocuadremulti> getUsuarioFecha(final String p0, final Date p1) throws EntityServicioExcepcion;
}
