package com.davivienda.sara.tablas.diarioelectronico.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.DiarioElectronico;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import java.util.Calendar;
import java.io.FileNotFoundException;

/**
 * DiarioElectronicoSessionLocal - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface DiarioElectronicoSessionLocal extends AdministracionTablasInterface<DiarioElectronico> {

    /**
     * Retorna el DiarioElectronico del cajero y fecha dado
     * @param codigoCajero
     * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
    public Collection<DiarioElectronico> getDiarioElectronico(Integer codigoCajero, Date fechaInicial) throws EntityServicioExcepcion ;
    public  Collection<DiarioElectronico> getDiarioElectronicoDia(Integer codigoCajero, Calendar fecha,String nombreArchivo) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException;
    
}