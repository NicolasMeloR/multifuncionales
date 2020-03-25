/*
 * CuadreCifrasSession.java
 * 
 * Fecha       : 25/08/2007, 10:41:24 AM
 * Descripción :
 * 
 * Babel Ver   :1.0
 */

package com.davivienda.multifuncional.cuadrecifrasmulti.session;


import com.davivienda.sara.entitys.ResumenCuadreCifras;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.entitys.Tipomovimientocuadremulti;
import com.davivienda.sara.entitys.ConceptoMovimientoCuadre;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.Movimientocuadremulti;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author mdruiz
 */
@Local
public interface CuadreCifrasMultiSessionLocal {

    
  public Movimientocuadremulti grabarMovimientoCuadre(Movimientocuadremulti mc, boolean actualizar) throws EntityServicioExcepcion ;
    
  public Tipomovimientocuadremulti buscarTipoMovimientoCuadre(Integer codigoTipoMovimientoCuadre) throws EntityServicioExcepcion;

  public Collection<Movimientocuadremulti> getUsuarioFecha(String usuario, Date fechaInicial) throws EntityServicioExcepcion ;
    
}
