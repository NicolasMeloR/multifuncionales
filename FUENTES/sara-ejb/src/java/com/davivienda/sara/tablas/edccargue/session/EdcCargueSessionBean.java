package com.davivienda.sara.tablas.edccargue.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.entitys.EdcCargue;
import com.davivienda.sara.tablas.edccargue.servicio.EdcCargueServicio;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Date;

/**
 * EdcCargueSessionBean - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Stateless
public class EdcCargueSessionBean extends BaseAdministracionTablas<EdcCargue> implements EdcCargueSessionLocal {
    
    @PersistenceContext
    private EntityManager em;
    private EdcCargueServicio EdcCargueServicio;


    @PostConstruct
    public void postConstructor() {
             
         EdcCargueServicio = new EdcCargueServicio(em);
         super.servicio = EdcCargueServicio;
    }
    @SuppressWarnings("unchecked")
    public Collection<EdcCargue> getCicloSnError(Integer ciclo) throws Exception {
       
      return EdcCargueServicio.getCicloSnError(ciclo);
    }
    public Collection<EdcCargue> getEDCCarguePorFecha(Date fechaInicial, Date fechaFinal)throws Exception
    {
     return EdcCargueServicio.getEDCCarguePorFecha(fechaInicial,fechaFinal);
    
    } 
       public int borrarCiclo(Integer codigoCiclo) throws Exception{
     return EdcCargueServicio.borrarCiclo(codigoCiclo);
    } 

  
    
 
}
