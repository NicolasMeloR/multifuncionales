package com.davivienda.sara.tablas.diarioelectronico.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.DiarioElectronico;
import com.davivienda.sara.tablas.diarioelectronico.servicio.DiarioElectronicoServicio;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.io.FileNotFoundException;

/**
 * DiarioElectronicoSessionBean - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Stateless
public class DiarioElectronicoSessionBean extends BaseAdministracionTablas<DiarioElectronico> implements DiarioElectronicoSessionLocal {

    @PersistenceContext
    private EntityManager em;
    DiarioElectronicoServicio diarioElectronicoServicio;

    @PostConstruct
    public void postConstructor() {
        diarioElectronicoServicio = new DiarioElectronicoServicio(em);
        super.servicio = diarioElectronicoServicio;

    }

    /**
     * Retorna el DiarioElectronico del cajero y fecha dado
     * @param codigoCajero
     * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
    @SuppressWarnings("unchecked")
    public Collection<DiarioElectronico> getDiarioElectronico(Integer codigoCajero, Date fechaInicial) throws EntityServicioExcepcion {
        return diarioElectronicoServicio.getDiarioElectronico(codigoCajero, fechaInicial);
    }
    public  Collection<DiarioElectronico>  getDiarioElectronicoDia(Integer codigoCajero, Calendar fecha,String nombreArchivo) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException{
           return diarioElectronicoServicio.getDiarioElectronicoDia(codigoCajero, fecha,nombreArchivo);
    }
    
}
