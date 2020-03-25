/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.multifuncional.tablas.biletajemulti.session;



import com.davivienda.multifuncional.tablas.biletajemulti.servicio.BilletajeMultiSessionServicio;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Billetajemulti;
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
public class BilletajeMultiSessionBean extends BaseAdministracionTablas<Billetajemulti> implements BilletajeMultiSessionLocal {
    

   
    @PersistenceContext
    private EntityManager em;
    BilletajeMultiSessionServicio billetajeMultiSessionServicio;

    @PostConstruct
    public void postConstructor() {
        billetajeMultiSessionServicio = new BilletajeMultiSessionServicio(em);
        super.servicio = billetajeMultiSessionServicio;
        
    }

    public Collection<Billetajemulti> getBilletajeMultiRangoFecha(Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return billetajeMultiSessionServicio.getBilletajeMultiRangoFecha(fechaInicial, fechaFinal);
    }
    
}
