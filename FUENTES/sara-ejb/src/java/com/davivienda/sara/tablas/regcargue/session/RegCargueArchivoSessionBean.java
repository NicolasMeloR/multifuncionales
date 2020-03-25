package com.davivienda.sara.tablas.regcargue.session;


import com.davivienda.sara.base.exception.EntityServicioExcepcion;

import com.davivienda.sara.base.BaseAdministracionTablas;

import com.davivienda.sara.entitys.Regcarguearchivo;

import com.davivienda.sara.tablas.regcargue.servicio.RegCargueArchivoServicio;
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
public class RegCargueArchivoSessionBean extends BaseAdministracionTablas<Regcarguearchivo> implements RegCargueArchivoSessionLocal {
    
    @PersistenceContext
    private EntityManager em;
    private RegCargueArchivoServicio regCargueArchivoServicio;



    @PostConstruct
    public void postConstructor() {
             
         regCargueArchivoServicio = new RegCargueArchivoServicio(em);
         super.servicio = regCargueArchivoServicio ;
    }
   
    public Collection<Regcarguearchivo> getRegCargueXFecha(Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {
        return regCargueArchivoServicio.getRegCargueXFecha(fechaInicial, fechaFinal);
    }
     public Regcarguearchivo buscarPorArchivoFecha(String archivoTarea,String fecha ,boolean IndAuto)throws EntityServicioExcepcion
     {
         return regCargueArchivoServicio.buscarPorArchivoFecha(archivoTarea, fecha,IndAuto);
    }

 
    
 
}
