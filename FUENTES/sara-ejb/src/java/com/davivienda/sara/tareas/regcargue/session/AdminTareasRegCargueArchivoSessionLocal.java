package com.davivienda.sara.tareas.regcargue.session;



import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Regcarguearchivo;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

/**
 * EDCCargueMultifuncionalLocal 
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-MDRUIZ
 * Davivienda 2011 
 */
@Local
public interface AdminTareasRegCargueArchivoSessionLocal  {

    /**
     * Retorna Retorna la colección de transacciones realizadas por el cajero <code>codigoCajero</code> enla fecha <code>fechaProceso</code>
     * @return Collection<TraEdcarguemultifuncionalnsaccion> 
     * @throws EntityServicioExcepcion 
     * @param fechaInicial 
     * @param fechaFinal 
     */
   
    
    public void guardarRegCargueArchivo(String archivoTarea,boolean IndAuto,String fechaTarea ,String tarea,Date fecha ,String usuario ,boolean esCargueDipensador ,String descrpcionError)  throws  EntityServicioExcepcion, IllegalArgumentException ;
    
    public  void actualizar(Regcarguearchivo objetoModificado) throws  EntityServicioExcepcion;
    
    public Regcarguearchivo buscarPorArchivoFecha(String archivoTarea,String fecha ,boolean IndAuto)throws EntityServicioExcepcion;
    
    public Collection<Regcarguearchivo> getRegCargueArchivoPorFecha(Date fechaInicial, Date fechaFinal)throws EntityServicioExcepcion;
        
    
       
    

   
    
    
}
