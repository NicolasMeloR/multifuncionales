package com.davivienda.sara.tablas.cajero.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/**

/**
 * CajeroSessionBean - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author mdruiz
 * Davivienda 2008 
 */
@Stateless
public class CajeroSessionBean extends BaseAdministracionTablas<Cajero> implements CajeroSessionLocal {

   @PersistenceContext
    private EntityManager em;
   private CajeroServicio CajeroServicio;
   
    @PostConstruct
    public void postConstructor() {
       
        CajeroServicio = new CajeroServicio(em);
        super.servicio = CajeroServicio;
        
    }

     
 

    // retorna los datos de cajeros activos e inactivos
     @SuppressWarnings("unchecked")
    public Collection<Cajero> getTodosActivos(Integer pagina, Integer regsPorPagina)throws Exception {
       
      return servicio.consultarPorQuery(pagina, regsPorPagina, "Cajero.AllActivos"); 
    }
     public Collection<Cajero> getCajerosSnTransmitir(Integer codigoCiclo) throws Exception{
     return CajeroServicio.getCajerosSnTransmitir(codigoCiclo);
    }

    public Collection<Cajero> getCajerosMultiSnTransmitir(Integer codigoCiclo) throws Exception {
        return CajeroServicio.getCajerosMultiSnTransmitir(codigoCiclo);
    }
    public Collection<Cajero> getTodosActivosMulti(Integer pagina, Integer regsPorPagina) throws Exception {
       
      return servicio.consultarPorQuery(pagina, regsPorPagina, "Cajero.AllActivMulti"); 
    }
}
