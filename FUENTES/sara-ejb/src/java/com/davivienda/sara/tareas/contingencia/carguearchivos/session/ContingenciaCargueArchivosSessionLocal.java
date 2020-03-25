package com.davivienda.sara.tareas.contingencia.carguearchivos.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

/**
 * TransaccionSessionLocal - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface ContingenciaCargueArchivosSessionLocal extends AdministracionTablasInterface<Txmultifuncionalefectivo> {


    public void cargarArchivoCfamoMulti() throws EntityServicioExcepcion ;
    
    public void cargarArchivoGeatoMulti() throws EntityServicioExcepcion; 

    public void cargarArchivoOtbmoMulti() throws EntityServicioExcepcion;

    public void cargarArchivoHostDispensa() throws EntityServicioExcepcion; 

    public void cargarArchivoHostMulti() throws EntityServicioExcepcion ;
    
    public void calcReintegrosDispensa() throws EntityServicioExcepcion; 
    
    public Collection<Regcarguearchivo> getRegCargueArchivoPorFecha(Date fechaInicial, Date fechaFinal)throws EntityServicioExcepcion;
    
    public void cargarDiarioEfectivoMulti() throws EntityServicioExcepcion ; 
    
    public void cargarDiarioChequeMulti() throws EntityServicioExcepcion ;
    
    public void cargarLogMulti() throws EntityServicioExcepcion ;
    
    public void cargarReintegrosMultifuncional(String fechaConsulta) throws EntityServicioExcepcion;

  
     
    
    
    
}
