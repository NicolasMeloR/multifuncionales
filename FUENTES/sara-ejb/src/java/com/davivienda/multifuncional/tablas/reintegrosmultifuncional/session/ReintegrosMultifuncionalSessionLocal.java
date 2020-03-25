package com.davivienda.multifuncional.tablas.reintegrosmultifuncional.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.ReintegrosMultifuncional;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

/**
 * ReintegrosMultifuncionalSessionLocal - 20/04/2017
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jediazs
 * Davivienda 2017 
 */
@Local
public interface ReintegrosMultifuncionalSessionLocal extends AdministracionTablasInterface<ReintegrosMultifuncional> {

    /**
     * Retorna los ReintegrosMultifuncional de la fecha dada
        * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
    public Collection<ReintegrosMultifuncional> getReintegrosMultifuncional( Date fechaInicial ,Date fechaFinal,Integer codigCajero) throws EntityServicioExcepcion ;
    
 
}