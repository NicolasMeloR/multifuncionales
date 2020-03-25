/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */

package com.davivienda.multifuncional.tablas.biletajemulti.session;

import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Billetajemulti;
import java.util.Calendar;

/**
 *
 * @author mdruiz
 */
@Local
public interface BilletajeMultiSessionLocal extends AdministracionTablasInterface<Billetajemulti> {
//public interface RegionalSessionLocal {
    
 
 public Collection<Billetajemulti> getBilletajeMultiRangoFecha(Calendar fechaInicial,Calendar fechaFinal) throws EntityServicioExcepcion;
}
    
    

