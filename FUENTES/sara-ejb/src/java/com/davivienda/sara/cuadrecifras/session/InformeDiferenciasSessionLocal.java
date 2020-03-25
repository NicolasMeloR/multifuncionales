/*
 * CuadreCifrasSession.java
 * 
 * Fecha       : 25/08/2007, 10:41:24 AM
 * Descripción :
 * 
 * Babel Ver   :1.0
 */
package com.davivienda.sara.cuadrecifras.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.InformeDiferencias;
import java.util.Calendar;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author jjvargas
 */
@Local
public interface InformeDiferenciasSessionLocal extends AdministracionTablasInterface<InformeDiferencias>  {

   
    public Collection<InformeDiferencias> getInformeDiferenciaXFecha(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion;
}
