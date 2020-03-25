/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */

package com.davivienda.sara.tablas.regional.session;

import com.davivienda.sara.entitys.Regional;
import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.base.AdministracionTablasInterface;

/**
 *
 * @author jjvargas
 */
@Local
public interface RegionalSessionLocal extends AdministracionTablasInterface<Regional> {
//public interface RegionalSessionLocal {
    
 
public Collection<Regional> getTodos(Integer pagina, Integer regsPorPagina) throws Exception;
}
    
    

