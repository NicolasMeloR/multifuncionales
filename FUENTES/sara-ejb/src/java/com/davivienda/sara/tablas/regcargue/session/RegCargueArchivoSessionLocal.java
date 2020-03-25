package com.davivienda.sara.tablas.regcargue.session;


import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Regcarguearchivo;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

/**
 * EDCCargueMultifuncionalLocal 
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-MDRUIZ
 * Davivienda 2011 
 */
@Local
public interface RegCargueArchivoSessionLocal extends AdministracionTablasInterface<Regcarguearchivo> {

    /**
     * Retorna Retorna la colección de transacciones realizadas por el cajero <code>codigoCajero</code> enla fecha <code>fechaProceso</code>
     * @return Collection<TraEdcarguemultifuncionalnsaccion> 
     * @throws EntityServicioExcepcion 
     * @param fechaInicial 
     * @param fechaFinal 
     */
    public Collection<Regcarguearchivo> getRegCargueXFecha( Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion;
    
  
       
    

   
    
    
}
