package com.davivienda.sara.tablas.edccargue.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.EdcCargue;
import javax.ejb.Local;
import java.util.Collection;
import java.util.Date;

/**
 * EdcCargueSessionLocal - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface EdcCargueSessionLocal extends AdministracionTablasInterface<EdcCargue>{
public Collection<EdcCargue> getCicloSnError(Integer ciclo)throws Exception;   
public Collection<EdcCargue> getEDCCarguePorFecha(Date fechaInicial, Date fechaFinal)throws Exception;   
public int borrarCiclo(Integer codigoCiclo) throws Exception ;    
}
