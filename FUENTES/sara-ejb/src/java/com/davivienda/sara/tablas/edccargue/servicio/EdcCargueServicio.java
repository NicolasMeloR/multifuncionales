package com.davivienda.sara.tablas.edccargue.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.EdcCargue;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
/**
 * TipoCajeroServicio - 21/08/2008
 * Descripción : Servicio para la administración de datos en la tabla TipoCajero
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class EdcCargueServicio extends BaseEntityServicio<EdcCargue> implements AdministracionTablasInterface<EdcCargue> {

    
    public EdcCargueServicio(EntityManager em) {
        super(em, EdcCargue.class);
    }

    @Override
    public EdcCargue actualizar(EdcCargue objeto) throws EntityServicioExcepcion {
        EdcCargue objetoActual = super.buscar(objeto.getIdEdcCargue());
        String accion = (objetoActual == null) ? "NUEVO" : "ACTUALIZAR";
        if (accion.equals("NUEVO")) {
            // Es nuevo y debo asociar las relaciones
            super.adicionar(objeto);
            objetoActual = super.buscar(objeto.getIdEdcCargue());
        } else {
            // Se actualizan solo datos
            
            objetoActual = objetoActual.actualizarEntity(objeto);
            super.actualizar(objetoActual);
        }
        return objetoActual;
    }
        public Collection<EdcCargue> getCicloSnError(Integer ciclo) throws EntityServicioExcepcion {
        
    
        Collection<EdcCargue> items = null;
        try {
            Query query = null;
         
                query = em.createNamedQuery("EdcCargue.CicloSnError");
                query.setParameter("ciclo", ciclo);
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
    public Collection<EdcCargue> getEDCCarguePorFecha(Date fechaInicial, Date fechaFinal)throws EntityServicioExcepcion
    {
   
        Collection<EdcCargue> items = null;
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
         
                query = em.createNamedQuery("EdcCargue.RangoFecha").setHint("toplink.refresh", "true");
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
    public EdcCargue buscarPorArchivo(String nombreArchivo)throws EntityServicioExcepcion
    {
   
        EdcCargue item = null;
       
        try {
            Query query = null;
         
                query = em.createNamedQuery("EdcCargue.Archivo");
                query.setParameter("nombreArchivo", nombreArchivo);
                if(nombreArchivo.equals(""))
                {
                     item =null; 
                }
                else
                {
                    //verifico si existe el archivo
                   if( !query.getResultList().isEmpty())
                   {  
                       item =(EdcCargue) query.getResultList().get(0);
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
    
   


     public int borrarCiclo(Integer ciclo) throws EntityServicioExcepcion {
         int respuesta=0;
    

//        String strQuery = "delete object(obj) from EdcCargue obj " +
//                "        where obj.ciclo = : codigoCiclo ";
         String strQuery = "DELETE  FROM EdcCargue s " +
                           "   where s.ciclo = :ciclo ";

       try {
             
            Query query = null;
            query = em.createQuery(strQuery);
            query.setParameter("ciclo", ciclo);
            respuesta = query.executeUpdate();
          
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (TransactionRequiredException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "no es una transaccion ", ex);
            throw new EntityServicioExcepcion(ex);
        }
            return respuesta;

    }


}
