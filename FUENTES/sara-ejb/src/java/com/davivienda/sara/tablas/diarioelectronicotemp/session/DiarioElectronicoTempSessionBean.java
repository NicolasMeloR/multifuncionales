package com.davivienda.sara.tablas.diarioelectronicotemp.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.DiarioelectronicoTemp;
import com.davivienda.sara.tablas.diarioelectronicotemp.servicio.DiarioElectronicoTempServicio;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * DiarioElectronicoSessionBean - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Stateless
public class DiarioElectronicoTempSessionBean extends BaseAdministracionTablas<DiarioelectronicoTemp> implements DiarioElectronicoTempSessionLocal {

    @PersistenceContext
    private EntityManager em;
    DiarioElectronicoTempServicio diarioElectronicoServicio;

    @PostConstruct
    public void postConstructor() {
        diarioElectronicoServicio = new DiarioElectronicoTempServicio(em);
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
    public Collection<DiarioelectronicoTemp> getDiarioElectronicoTemp(Integer codigoCajero, Date fechaInicial) throws EntityServicioExcepcion {
        return diarioElectronicoServicio.getDiarioElectronicoTemp(codigoCajero, fechaInicial);
    }
}
