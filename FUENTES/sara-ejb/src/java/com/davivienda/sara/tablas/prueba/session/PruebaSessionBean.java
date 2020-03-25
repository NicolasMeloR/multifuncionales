/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.sara.tablas.prueba.session;



import com.davivienda.sara.entitys.Prueba2;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.tablas.prueba.servicio.PruebaServicio;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Juancho Voltio
 */

@Stateless
public class PruebaSessionBean extends BaseAdministracionTablas<Prueba2> implements PruebaSessionLocal {
    

   
    @PersistenceContext
    private EntityManager em;
    private PruebaServicio pruebaServicio;

    @PostConstruct
    public void postConstructor() {
        pruebaServicio = new PruebaServicio(em);
        super.servicio = pruebaServicio;
        
    }

    @SuppressWarnings("unchecked")
    public Collection<Prueba2> getTodos() {
        return servicio.getTodos();
    }
}
