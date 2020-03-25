package com.davivienda.sara.tablas.diarioelectronicotemp.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.DiarioelectronicoTemp;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

/**
 * DiarioElectronicoSessionLocal - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface DiarioElectronicoTempSessionLocal extends AdministracionTablasInterface<DiarioelectronicoTemp> {

    /**
     * Retorna el DiarioElectronico del cajero y fecha dado
     * @param codigoCajero
     * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
    public Collection<DiarioelectronicoTemp> getDiarioElectronicoTemp(Integer codigoCajero, Date fechaInicial) throws EntityServicioExcepcion ;
    
}