// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cajero.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Cajero;
import javax.ejb.Local;

@Local
public interface CajeroProcesosSessionLocal
{
    Cajero actualizarCajero(final Cajero p0) throws EntityServicioExcepcion;
}
