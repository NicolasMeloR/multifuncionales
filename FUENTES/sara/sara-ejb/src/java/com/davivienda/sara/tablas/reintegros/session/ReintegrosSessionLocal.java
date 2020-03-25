// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.reintegros.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface ReintegrosSessionLocal extends AdministracionTablasInterface<Reintegros>
{
    Collection<Reintegros> getReintegros(final Date p0, final Date p1, final Integer p2, final Date p3) throws EntityServicioExcepcion;
    
    Collection<Reintegros> getReintegros(final Date p0, final Integer p1, final Date p2) throws EntityServicioExcepcion;
    
    Reintegros getReintegroXLlave(final Integer p0, final Date p1, final Integer p2, final Date p3) throws EntityServicioExcepcion;
    
    Reintegros getReintegroXCuentaValor(final Integer p0, final Date p1, final Integer p2, final String p3, final Long p4, final Date p5) throws EntityServicioExcepcion;
    
    Collection<Object> getReintegrosXCajero(final Date p0, final Date p1, final Date p2) throws EntityServicioExcepcion;
    
    void actualizarEstadoReintegros(final Date p0, final Integer p1, final Date p2) throws EntityServicioExcepcion;

    public Reintegros findByPrimayKey(Integer codigoCajero, Date fechaSistema, Integer talon, Date fechaHisto) throws EntityServicioExcepcion;
}
