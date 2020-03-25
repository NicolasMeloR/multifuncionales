package com.davivienda.sara.tablas.transportadora.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Transportadora;
import javax.ejb.Local;
import java.util.Collection;

/**
 * TransportadoraSessionLocal - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface TransportadoraSessionLocal extends AdministracionTablasInterface<Transportadora>{
    
public Collection<Transportadora> getTodos(Integer pagina, Integer regsPorPagina) throws Exception;
}
    


