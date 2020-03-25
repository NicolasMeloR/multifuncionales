/*
 * CuadreCifrasSessionBean.java
 *
 * Fecha       : 25/08/2007, 10:41:24 AM
 * Descripción :
 *
 * Babel Ver   :1.0
 */
package com.davivienda.sara.cuadrecifras.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.InformeDiferencias;
import com.davivienda.sara.tablas.reintegros.servicio.InformeDiferenciasServicio;
import java.util.Calendar;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jjvargas
 */
@Stateless
public class InformeDiferenciasSessionBean extends BaseAdministracionTablas<InformeDiferencias>implements InformeDiferenciasSessionLocal {

    @PersistenceContext
    private EntityManager em;
    private InformeDiferenciasServicio servicioInformeDif;

    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
        servicioInformeDif = new InformeDiferenciasServicio(em);
        super.servicio = servicioInformeDif;
    }
    
     public Collection<InformeDiferencias> getInformeDiferenciaXFecha(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return servicioInformeDif.buscarDiferenciasXFecha(codigoCajero, fechaInicial, fechaFinal);
    }
}
