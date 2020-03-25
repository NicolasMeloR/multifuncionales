package com.davivienda.multifuncional.tablas.historicoajustesmulti.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Historicoajustesmulti;
import java.math.BigDecimal;
import javax.ejb.Local;
import java.util.Collection;
import java.util.Date;

/**
 * HistoricoAjustesMultiLocal 
 * Descripción : 
 * Versión : 1.0 
 *
 * @author mdruiz
 * Davivienda 2011
 */
@Local
public interface HistoricoAjustesMultiLocal extends AdministracionTablasInterface<Historicoajustesmulti>{
   
public Collection<Historicoajustesmulti> getColeccionHistoricoAjustes(Integer codigoCajero,Integer codigoOficina, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion;    
public void guardarHistoricoAjustes(String usuario, Integer codigoCajero, Integer codigoOficina, String tipoAjuste, Date fecha, Long valor,String talon, String error, String descripcionError)throws EntityServicioExcepcion;
    

    
            
             
   


}
