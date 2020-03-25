package com.davivienda.sara.tareas.contingencia.carguearchivos.session;


import com.davivienda.multifuncional.tablas.txmultifuncionalefectivo.servicio.TxMultifuncionalEfectivoServicio;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import com.davivienda.sara.tareas.contingencia.carguearchivos.servicio.ContingenciaCargueArchivosServicio;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * TxMultifuncionalEfectivoSessionBean 
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-MDRUIZ
 * Davivienda 2011 
 */
@Stateless

public class ContingenciaCargueArchivosSessionBean extends BaseAdministracionTablas<Txmultifuncionalefectivo> implements ContingenciaCargueArchivosSessionLocal {

    @PersistenceContext
    private EntityManager em;
    private ContingenciaCargueArchivosServicio contingenciaCargueArchivosServicio;

    @PostConstruct
    public void postConstructor() {
        contingenciaCargueArchivosServicio = new ContingenciaCargueArchivosServicio(em);
        super.servicio = contingenciaCargueArchivosServicio;

    }


    public void cargarArchivoCfamoMulti() throws EntityServicioExcepcion 
    {
         contingenciaCargueArchivosServicio.cargarArchivoCfamoMulti();
    }
          
    public void cargarArchivoGeatoMulti() throws EntityServicioExcepcion 
    {
         contingenciaCargueArchivosServicio.cargarArchivoGeatoMulti();
    }

    public void cargarArchivoOtbmoMulti() throws EntityServicioExcepcion 
    {
         contingenciaCargueArchivosServicio.cargarArchivoOtbmoMulti();
    }

    public void cargarArchivoHostDispensa() throws EntityServicioExcepcion
    {
         contingenciaCargueArchivosServicio.cargarArchivoHostDispensa();
    }

    public void cargarArchivoHostMulti() throws EntityServicioExcepcion
    {
         contingenciaCargueArchivosServicio.cargarArchivoHostMulti();
    }
    
    public void calcReintegrosDispensa() throws EntityServicioExcepcion
     {
         contingenciaCargueArchivosServicio.calcReintegrosDispensa();
    }
     
      public Collection<Regcarguearchivo> getRegCargueArchivoPorFecha(Date fechaInicial, Date fechaFinal)throws EntityServicioExcepcion
    {
         return contingenciaCargueArchivosServicio.getRegCargueArchivoPorFecha(fechaInicial, fechaFinal); 
    }
      
    public void cargarDiarioEfectivoMulti() throws EntityServicioExcepcion {
          contingenciaCargueArchivosServicio.cargarDiarioEfectivoMulti(); 
    }
    
    public void cargarDiarioChequeMulti() throws EntityServicioExcepcion {
          contingenciaCargueArchivosServicio.cargarDiarioChequeMulti(); 
    }
    public void cargarLogMulti() throws EntityServicioExcepcion 
    {
         contingenciaCargueArchivosServicio.cargarLogMulti();
    }
    public void cargarReintegrosMultifuncional(String fechaConsulta) throws EntityServicioExcepcion
     {
         contingenciaCargueArchivosServicio.cargarReintegrosMultifuncional(fechaConsulta);
    }
   
   
}
