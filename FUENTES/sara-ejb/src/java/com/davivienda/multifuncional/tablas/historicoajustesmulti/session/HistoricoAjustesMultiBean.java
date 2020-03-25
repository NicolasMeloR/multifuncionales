package com.davivienda.multifuncional.tablas.historicoajustesmulti.session;

import com.davivienda.multifuncional.tablas.historicoajustesmulti.servicio.HistoricoAjustesMultiServicio;
import com.davivienda.sara.tablas.historicoajustes.session.*;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.entitys.Historicoajustesmulti;
import com.davivienda.sara.tablas.historicoajustes.servicio.HistoricoAjustesServicio ;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class HistoricoAjustesMultiBean extends BaseAdministracionTablas<Historicoajustesmulti > implements HistoricoAjustesMultiLocal {
    
    @PersistenceContext
    private EntityManager em;
    private HistoricoAjustesMultiServicio historicoAjustesMultiServicio;


    @PostConstruct
    public void postConstructor() {
             
         historicoAjustesMultiServicio = new HistoricoAjustesMultiServicio(em);
         super.servicio = historicoAjustesMultiServicio;
    }
    public Collection<Historicoajustesmulti> getColeccionHistoricoAjustes(Integer codigoCajero,Integer codigoOficina, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion{
     return historicoAjustesMultiServicio.getColeccionHistoricoAjustes(codigoCajero,codigoOficina, fechaInicial, fechaFinal, fechaHisto);
    }   
    public void guardarHistoricoAjustes(String usuario, Integer codigoCajero, Integer codigoOficina, String tipoAjuste, Date fecha, Long valor,String talon, String error, String descripcionError) throws EntityServicioExcepcion
    {
  
      
            historicoAjustesMultiServicio.guardarHistoricoAjustes(usuario, codigoCajero, codigoOficina, tipoAjuste, fecha, valor, talon, error, descripcionError);
    }

}

       
    

  
  
    
 

