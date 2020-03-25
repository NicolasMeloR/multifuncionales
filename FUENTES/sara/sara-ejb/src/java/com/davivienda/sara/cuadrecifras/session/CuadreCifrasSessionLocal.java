// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.cuadrecifras.session;

import java.util.Date;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.ConceptoMovimientoCuadre;
import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import java.math.BigInteger;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.ResumenCuadreCifras;
import java.util.Collection;
import java.util.Calendar;
import javax.ejb.Local;

@Local
public interface CuadreCifrasSessionLocal
{
    Collection<ResumenCuadreCifras> getResumenCuadreCifras(final Integer p0, final Calendar p1, final Calendar p2) throws EntityServicioExcepcion;
    
    Collection<MovimientoCuadre> getMovimientoCuadreCifras(final Integer p0, final Calendar p1, final Calendar p2) throws EntityServicioExcepcion;
    
    Collection<MovimientoCuadre> getMovimientoCuadreCifrasMostrar(final Integer p0, final Calendar p1, final Calendar p2) throws EntityServicioExcepcion;
    
    Collection<MovimientoCuadre> getMovimientoCuadreCifrasContadores(final Integer p0, final Calendar p1, final Calendar p2) throws EntityServicioExcepcion;
    
    Collection<MovimientoCuadre> getMovimientoAjustes(final Integer p0, final Calendar p1, final Calendar p2) throws EntityServicioExcepcion;
    
    Collection<MovimientoCuadre> getMovimientoCuadreCifrasProvision(final Integer p0, final Calendar p1, final Calendar p2) throws EntityServicioExcepcion;
    
    Collection<MovimientoCuadre> getMovimientoCuadreCifrasDatosLinea(final Integer p0, final Calendar p1, final Calendar p2) throws EntityServicioExcepcion;
    
    void grabarNotaMovimiento(final BigInteger p0, final String p1, final String p2) throws EntityServicioExcepcion;
    
    MovimientoCuadre consultarNotaMovimiento(final BigInteger p0) throws EntityServicioExcepcion;
    
    MovimientoCuadre grabarMovimientoCuadre(final MovimientoCuadre p0, final boolean p1) throws EntityServicioExcepcion;
    
    Collection<TipoMovimientoCuadre> getTiposMovimientoCuadre();
    
    TipoMovimientoCuadre buscarTipoMovimientoCuadre(final Integer p0) throws EntityServicioExcepcion;
    
    Collection<ConceptoMovimientoCuadre> getConceptosMovimientoCuadre() throws EntityServicioExcepcion;
    
    void cuadrarCajero(final Cajero p0, final Date p1) throws EntityServicioExcepcion;
    
    MovimientoCuadre getMovimientoCuadre(final Integer p0, final Calendar p1, final Integer p2) throws EntityServicioExcepcion;
    
    Collection<MovimientoCuadre> getMovimientoCuadreCifrasOccaMostrar(final Integer p0, final Integer p1, final Calendar p2, final Calendar p3) throws EntityServicioExcepcion;
}
