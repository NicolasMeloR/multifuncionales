package com.davivienda.sara.tablas.binentidad.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.entitys.BinEntidad;
import com.davivienda.sara.tablas.binentidad.servicio.BinEntidadServicio;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * BinEntidadSessionBean - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Stateless
public class BinEntidadSessionBean extends BaseAdministracionTablas<BinEntidad> implements BinEntidadSessionLocal {
    
    @PersistenceContext
    private EntityManager em;
    private BinEntidadServicio binEntidadServicio;


    @PostConstruct
    public void postConstructor() {
             
         binEntidadServicio = new BinEntidadServicio(em);
         super.servicio = binEntidadServicio;
    }
    
    
           /**
     * Retorna la entidad del bin
        * @param bin
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
  
    public BinEntidad getEntidadXBin(String bin)  throws Exception {
        return binEntidadServicio.getEntidadXBin(bin);
    }
    
 
}
