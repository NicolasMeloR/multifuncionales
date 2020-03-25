/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */

package com.davivienda.sara.tablas.provisionhost.session;

import com.davivienda.sara.entitys.ProvisionHost;
import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Calendar;

/**
 *
 * @author jjvargas
 */
@Local
public interface ProvisionHostLocal extends AdministracionTablasInterface<ProvisionHost> {
//public interface RegionalSessionLocal {
    
 
 public Collection<ProvisionHost> getProvisionHostRangoFecha(Calendar fechaInicial,Calendar fechaFinal) throws EntityServicioExcepcion;
}
    
    

