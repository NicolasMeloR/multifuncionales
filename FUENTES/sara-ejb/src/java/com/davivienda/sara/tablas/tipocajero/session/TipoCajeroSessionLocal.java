package com.davivienda.sara.tablas.tipocajero.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.TipoCajero;
import javax.ejb.Local;
import java.util.Collection;

/**
 * TipoCajeroSessionLocal - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface TipoCajeroSessionLocal extends AdministracionTablasInterface<TipoCajero>{
 public Collection<TipoCajero> getTodos(Integer pagina, Integer regsPorPagina) throws Exception;
    
}
