/*
 * CuadreCifrasSession.java
 * 
 * Fecha       : 25/08/2007, 10:41:24 AM
 * Descripción :
 * 
 * Babel Ver   :1.0
 */
package com.davivienda.sara.cuadrecifras.session;

import com.davivienda.sara.entitys.ResumenCuadreCifras;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import com.davivienda.sara.entitys.ConceptoMovimientoCuadre;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Cajero;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author jjvargas
 */
@Local
public interface CuadreCifrasSessionLocal {

    public Collection<ResumenCuadreCifras> getResumenCuadreCifras(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion;

    public Collection<MovimientoCuadre> getMovimientoCuadreCifras(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion;

    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasMostrar(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion;

    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasContadores(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion;

    public Collection<MovimientoCuadre> getMovimientoAjustes(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion;

    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasProvision(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion;

    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasDatosLinea(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion;

    public void grabarNotaMovimiento(BigInteger idRegistro, String observacion, String idUsuario) throws EntityServicioExcepcion;

    public MovimientoCuadre consultarNotaMovimiento(BigInteger idRegistro) throws EntityServicioExcepcion;

    public MovimientoCuadre grabarMovimientoCuadre(MovimientoCuadre mc, boolean actualizar) throws EntityServicioExcepcion;

    public Collection<TipoMovimientoCuadre> getTiposMovimientoCuadre();

    public TipoMovimientoCuadre buscarTipoMovimientoCuadre(Integer codigoTipoMovimientoCuadre) throws EntityServicioExcepcion;

    public Collection<ConceptoMovimientoCuadre> getConceptosMovimientoCuadre() throws EntityServicioExcepcion;

    public void cuadrarCajero(Cajero cajero, Date fecha) throws EntityServicioExcepcion;

    public MovimientoCuadre getMovimientoCuadre(Integer codigoCajero, Calendar fecha, Integer codigoTipoMovimiento) throws EntityServicioExcepcion;

    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasOccaMostrar(Integer codigoCajero, Integer codigoOcca, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion;
}
