package com.davivienda.sara.tablas.regcargue.servicio;


import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.constantes.EstadoProceso;
import com.davivienda.sara.entitys.Regcarguearchivo;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * EdcMultifuncionalServicio 
 * Versión : 1.0 
 *
 * @author P-MDRUIZ
 * Davivienda 2011 
 */

public class RegCargueArchivoServicio extends BaseEntityServicio<Regcarguearchivo> {

    
    public RegCargueArchivoServicio(EntityManager em) {
        super(em, Regcarguearchivo.class);
    }


    @Override
    public Regcarguearchivo actualizar(Regcarguearchivo objetoModificado) throws EntityServicioExcepcion {
    
        Regcarguearchivo objetoActual = super.buscar(objetoModificado.getIdregcarguearchivo());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getIdregcarguearchivo());
        } else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }        
        return objetoActual;
    }
    

    public Collection<Regcarguearchivo> getRegCargueXFecha(Date fechaInicial, Date fechaFinal)throws EntityServicioExcepcion
    {
   
        Collection<Regcarguearchivo> items = null;
        if (fechaInicial == null) {
            fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
        }
        if (fechaFinal == null) {
            fechaFinal = fechaInicial;
        }
        if (fechaFinal.before(fechaInicial)) {
            throw new IllegalArgumentException("La fecha final debe ser mayor a la fecha Inicial");
        }
        try {
            Query query = null;
                                          
                query = em.createNamedQuery("Regcarguearchivo.RangoFecha");
                query.setParameter("fechaInicial", fechaInicial);
                query.setParameter("fechaFinal", fechaFinal);
                items = query.getResultList();
           
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return items;
    
    }
    
//    public void guardarRegCargueArchivo(String archivoTarea,boolean IndAuto,String fechaTarea ,String tarea,Date fecha )  throws  EntityServicioExcepcion, IllegalArgumentException {
//      
//        Regcarguearchivo regcarguearchivo=new Regcarguearchivo();
//        
//        Integer stIndAuto;
//     
//        regcarguearchivo.setArchivotarea(archivoTarea);
//        regcarguearchivo.setTarea(tarea);
//        regcarguearchivo.setFecha(fecha);
//        regcarguearchivo.setEstadocarga(EstadoProceso.INICIADO.getEstado());
//        
//        if(IndAuto)
//        {
//            regcarguearchivo.setFechaautomatica(fechaTarea);
//            stIndAuto=1;
//        }
//        else
//        {
//            stIndAuto=0;     
//            regcarguearchivo.setFechamanual(fechaTarea);
//        }
//        
//        regcarguearchivo.setIndautomatico(stIndAuto);
//        
//        try
//        {
//          
//              adicionar(regcarguearchivo);
//          
//        } catch (Exception ex)
//        {
//         java.util.logging.Logger.getLogger("globalApp").info("Error cargando en Regcarguearchivo registro datos archivo  :" + archivoTarea+fechaTarea + " descripcion Error : " + ex.getMessage());           
//        }
//      
//     
//           
//    }
    public Regcarguearchivo buscarPorArchivoFecha(String archivoTarea,String fecha ,boolean IndAuto)throws EntityServicioExcepcion
    {
   
        Regcarguearchivo item = null;
        String strNamedQuery="";
        Short stIndAuto;
        String strParamFecha="";
       
        try {
            Query query = null;
            if(IndAuto)
            {
                            //Regcarguearchivo.ArchivoFechaAuto
               strNamedQuery="Regcarguearchivo.ArchivoFechaAuto";
               stIndAuto=1;
               strParamFecha="fechaautomatica";
            }
            else
            {
               strNamedQuery="Regcarguearchivo.ArchivoFechaManual";
               stIndAuto=0;
               strParamFecha="fechamanual";
            }    
         
                query = em.createNamedQuery(strNamedQuery);
                query.setParameter("archivotarea", archivoTarea);
                query.setParameter("indautomatico",stIndAuto );
                query.setParameter(strParamFecha,fecha );
                
                if(archivoTarea.equals(""))
                {
                     item =null; 
                }
                else
                {
                    //verifico si existe el archivo
                   if( !query.getResultList().isEmpty())
                   {  
                       item =(Regcarguearchivo) query.getResultList().get(0);
                   }
                      
                }
            
           
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return item;
    
    }
    

    
    



}
