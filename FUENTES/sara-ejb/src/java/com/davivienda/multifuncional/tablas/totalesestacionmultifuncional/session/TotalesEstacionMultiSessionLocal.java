/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operaci�n Cajeros Autom�ticos
 * Versi�n  1.0
 */

package com.davivienda.multifuncional.tablas.totalesestacionmultifuncional.session;

import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Totalesestacionmultifuncional;
import java.util.Calendar;

/**
 *
 * @author mdruiz
 */
@Local
public interface TotalesEstacionMultiSessionLocal extends AdministracionTablasInterface<Totalesestacionmultifuncional> {
//public interface RegionalSessionLocal {
    
 
 public Collection<Totalesestacionmultifuncional> getTotalesEstacionMultiRangoFecha(Calendar fechaInicial,Calendar fechaFinal) throws EntityServicioExcepcion;
}
    
    

