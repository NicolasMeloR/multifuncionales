/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.sara.tablas.provisionhost.session;



import com.davivienda.sara.entitys.ProvisionHost;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.tablas.provisionhost.servicio.ProvisionHostServicio;
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
public class ProvisionHostBean extends BaseAdministracionTablas<ProvisionHost> implements ProvisionHostLocal {
    

   
    @PersistenceContext
    private EntityManager em;
    ProvisionHostServicio provisionHostServicio;

    @PostConstruct
    public void postConstructor() {
        provisionHostServicio = new ProvisionHostServicio(em);
        super.servicio = provisionHostServicio;
        
    }

    @SuppressWarnings("unchecked")
    public Collection<ProvisionHost> getProvisionHostRangoFecha(Calendar fechaInicial,Calendar fechaFinal) throws EntityServicioExcepcion  {
        return provisionHostServicio.getProvisionHostRangoFecha(fechaInicial,fechaFinal);
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
}
