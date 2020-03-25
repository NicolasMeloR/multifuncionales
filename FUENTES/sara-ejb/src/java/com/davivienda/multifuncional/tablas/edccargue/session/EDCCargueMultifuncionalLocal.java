package com.davivienda.multifuncional.tablas.edccargue.session;


import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Edcarguemultifuncional;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
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
public interface EDCCargueMultifuncionalLocal extends AdministracionTablasInterface<Edcarguemultifuncional> {

    /**
     * Retorna Retorna la colección de transacciones realizadas por el cajero <code>codigoCajero</code> enla fecha <code>fechaProceso</code>
     * @return Collection<TraEdcarguemultifuncionalnsaccion> 
     * @throws EntityServicioExcepcion 
     * @param fechaInicial 
     * @param fechaFinal 
     */
    public Collection<Edcarguemultifuncional> getEDCCargueXFecha( Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion;

   
    
    
}
