// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cuadrecifras.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Calendar;
import javax.ejb.Local;

@Local
public interface ProcesoCuadreCifrasSessionLocal
{
    Integer procesarCuadreCifras(final Calendar p0) throws EntityServicioExcepcion;
}
