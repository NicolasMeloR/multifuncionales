package com.davivienda.sara.tablas.historicoajustes.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.entitys.HistoricoAjustes;
import com.davivienda.sara.tablas.historicoajustes.servicio.HistoricoAjustesServicio ;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
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
public class HistoricoAjustesSessionBean extends BaseAdministracionTablas<HistoricoAjustes > implements HistoricoAjustesLocal {
    
    @PersistenceContext
    private EntityManager em;
    private HistoricoAjustesServicio historicoAjustesServicio;


    @PostConstruct
    public void postConstructor() {
             
         historicoAjustesServicio = new HistoricoAjustesServicio(em);
         super.servicio = historicoAjustesServicio;
    }
    public Collection<HistoricoAjustes> getColeccionHistoricoAjustes(Integer codigoCajero,Integer codigoOcca, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion{
     return historicoAjustesServicio.getColeccionHistoricoAjustes(codigoCajero,codigoOcca, fechaInicial, fechaFinal,fechaHisto);
    }   
  
  
    
 
}
