package com.davivienda.sara.tablas.ubicacion.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Ubicacion;
import javax.ejb.Local;
import java.util.Collection;

/**
 * UbicacionSessionLocal - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface UbicacionSessionLocal extends AdministracionTablasInterface<Ubicacion>{
    public Collection<Ubicacion> getTodos(Integer pagina, Integer regsPorPagina) throws Exception;

    
}
