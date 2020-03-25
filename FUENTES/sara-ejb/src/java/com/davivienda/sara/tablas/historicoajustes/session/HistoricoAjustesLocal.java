package com.davivienda.sara.tablas.historicoajustes.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.HistoricoAjustes;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
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
public interface HistoricoAjustesLocal extends AdministracionTablasInterface<HistoricoAjustes>{
   
public Collection<HistoricoAjustes> getColeccionHistoricoAjustes(Integer codigoCajero,Integer codigoOcca, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion;    


}
