// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.procesos.session;

import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import javax.ejb.Local;

@Local
public interface ProcesoReintegrosMultiSessionLocal
{
    void actualizarReintegro(final Reintegrosmultiefectivo p0) throws EntityServicioExcepcion;
    
    void guardarReintegro(final Integer p0, final Long p1, final String p2, final Integer p3, final String p4, final String p5, final Integer p6) throws EntityServicioExcepcion;
    
    void actualizarNotaDebito(final Notasdebitomultifuncional p0) throws EntityServicioExcepcion;
    
    void guardarNotaDebito(final Integer p0, final Long p1, final String p2, final Integer p3, final String p4) throws EntityServicioExcepcion;
}
