package com.davivienda.sara.tablas.diarioelectronico.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.ProcesadorArchivoDiarioElectronicoInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoProceso;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.DiarioElectronico;
import com.davivienda.sara.entitys.EdcCargue;
import com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura.EdcArchivo;
import com.davivienda.sara.procesos.diarioelectronico.filtro.edc.procesador.EdcProcesadorArchivo;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.tablas.edccargue.servicio.EdcCargueServicio;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * DiarioElectronicoServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class DiarioElectronicoServicio extends BaseEntityServicio<DiarioElectronico> implements AdministracionTablasInterface<DiarioElectronico> {

 
   private CajeroServicio cajeroServicio;
  
    
    public DiarioElectronicoServicio(EntityManager em) {
        super(em, DiarioElectronico.class);
        cajeroServicio = new CajeroServicio(em);
        
    }
    

    /**
     * Retorna el DiarioElectronico del cajero y fecha dado
     * @param codigoCajero
     * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
    @SuppressWarnings("unchecked")
    public Collection<DiarioElectronico> getDiarioElectronico(Integer codigoCajero, Date fechaInicial) throws EntityServicioExcepcion {
        Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
        Date fFinalCiclo = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
        Collection<DiarioElectronico> items = null;
        try {
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery("DiarioElectronico.CajeroRangoFecha");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaInicial", fechaInicial);
                query.setParameter("fechaFinal", fFinalCiclo);
                items = query.getResultList();
            } else {
                throw new EntityServicioExcepcion("Debe seleccionar un solo cajero");
            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return items;
    }
    
      public Collection<DiarioElectronico> getDiarioElectronicoDia(Integer codigoCajero, Calendar fecha,String nombreArchivo) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException 
      {
       
       
        Integer regsProcesados = -1;
          
         //Alvaro Garcia 31 Marzo
    
      
        Cajero cajero = cajeroServicio.buscar(codigoCajero);
        ArchivoPlano archivo = null;
        java.util.logging.Logger.getLogger("globalApp").info("Se inicia la consulta para el cajero " + cajero.getCodigoCajero());
        ProcesadorArchivoDiarioElectronicoInterface procesador = null;
        Collection<DiarioElectronico> regsDiarioElectronico = null;
    
     
        
       try
            {
               
        
                String directorio = "";

                if(cajero.getUbicacionEDC() != null)
                {
                     if(cajero.getUbicacionEDC().trim().length() > 1 )
                {
                         directorio =cajero.getUbicacionEDC();

                }

                }
                if(directorio.equals(""))
                {
                 directorio =configApp.DIRECTORIO_UPLOAD;
                }


                archivo = new EdcArchivo(codigoCajero, fecha, directorio,nombreArchivo);
                //Alvaro Garcia 17 de marzo
                procesador = new EdcProcesadorArchivo(archivo,com.davivienda.utilidades.conversion.Fecha.getCalendar(fecha, -1));
        

            // Leo los registros en el archivo asignado


            if (procesador != null) {
                  //Alvaro Garcia 16 Marzo
                regsDiarioElectronico = procesador.getRegistrosDiarioElectronico();
             
            }
  
            

        } catch (Exception ex)
        {
         java.util.logging.Logger.getLogger("globalApp").info("Error leyendo  el archivo diario electronico para el cajero :" + cajero.getCodigoCajero() + " descripcion Error : " + ex.getMessage());;            
        }
       
       
       // java.util.logging.Logger.getLogger("globalApp").info("Finaliza el proceso de lectura  para el cajero " + cajero.getCodigoCajero() + " con " + regsProcesados + " registros procesados");
        
        return regsDiarioElectronico;
           
    }
    
    

}
