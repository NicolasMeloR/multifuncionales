/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.multifuncional.tablas.totalesestacionmultifuncional.session;



import com.davivienda.multifuncional.tablas.totalesestacionmultifuncional.servicio.TotalesEstacionMultiSessionServicio;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Totalesestacionmultifuncional;
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
public class TotalesEstacionMultiSessionBean extends BaseAdministracionTablas<Totalesestacionmultifuncional> implements TotalesEstacionMultiSessionLocal {
    

   
    @PersistenceContext
    private EntityManager em;
    TotalesEstacionMultiSessionServicio totalesEstacionMultiSessionServicio;

    @PostConstruct
    public void postConstructor() {
        totalesEstacionMultiSessionServicio = new TotalesEstacionMultiSessionServicio(em);
        super.servicio = totalesEstacionMultiSessionServicio;
        
    }

    public Collection<Totalesestacionmultifuncional> getTotalesEstacionMultiRangoFecha(Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return totalesEstacionMultiSessionServicio.getTotalesEstacionMultiRangoFecha(fechaInicial, fechaFinal);
    }
    
}
