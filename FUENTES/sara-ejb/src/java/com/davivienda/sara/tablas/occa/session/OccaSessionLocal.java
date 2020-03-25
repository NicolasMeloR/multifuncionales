package com.davivienda.sara.tablas.occa.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Occa;
import javax.ejb.Local;
import java.util.Collection;

/**
 * OccaSessionLocal - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface OccaSessionLocal extends AdministracionTablasInterface<Occa>{
    public Collection<Occa> getTodos(Integer pagina, Integer regsPorPagina) throws Exception;
    
}
