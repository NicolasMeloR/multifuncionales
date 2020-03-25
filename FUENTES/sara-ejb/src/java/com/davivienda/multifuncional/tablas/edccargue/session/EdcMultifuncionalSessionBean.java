package com.davivienda.multifuncional.tablas.edccargue.session;

import com.davivienda.multifuncional.tablas.edccargue.servicio.EdcMultifuncionalServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;

import com.davivienda.sara.base.BaseAdministracionTablas;

import com.davivienda.sara.entitys.Edcarguemultifuncional;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Date;

/**
 * EdcMultifuncionalSessionBean
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-MDRUIZ
 * Davivienda 2011
 */
@Stateless
public class EdcMultifuncionalSessionBean extends BaseAdministracionTablas<Edcarguemultifuncional> implements EDCCargueMultifuncionalLocal {
    
    @PersistenceContext
    private EntityManager em;
    private EdcMultifuncionalServicio EdcCargueServicio;


    @PostConstruct
    public void postConstructor() {
             
         EdcCargueServicio = new EdcMultifuncionalServicio(em);
         super.servicio = EdcCargueServicio;
    }
   
    public Collection<Edcarguemultifuncional> getEDCCargueXFecha(Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {
        return EdcCargueServicio.getEDCCargueXFecha(fechaInicial, fechaFinal);
    }

 
    
 
}
