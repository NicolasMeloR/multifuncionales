/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.multifuncional.tablas.provisionhostmultifuncional.session;

 

import com.davivienda.multifuncional.tablas.provisionhostmultifuncional.servicio.ProvisionHostMultifuncionalServicio;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Provisionhostmulti;
import java.util.Calendar;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mdruiz
 */

@Stateless
public class ProvisionHostMultifuncionalBean extends BaseAdministracionTablas<Provisionhostmulti> implements ProvisionHostMultifuncionalLocal {
    

   
    @PersistenceContext
    private EntityManager em;
    ProvisionHostMultifuncionalServicio provisionHostMultiServicio;

    @PostConstruct
    public void postConstructor() {
        provisionHostMultiServicio = new ProvisionHostMultifuncionalServicio(em);
        super.servicio = provisionHostMultiServicio;
        
    }

    public Collection<Provisionhostmulti> getProvisionHostRangoFecha(Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return provisionHostMultiServicio.getProvisionHostRangoFecha(fechaInicial, fechaFinal);
    }
    
}
