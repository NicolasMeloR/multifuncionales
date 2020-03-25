package com.davivienda.sara.procesos.reintegros.filtro.host.servicio;



import com.davivienda.sara.base.BaseServicio;

import com.davivienda.sara.base.ProcesadorArchivoHostInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;

import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.entitys.Cajero;


import com.davivienda.sara.procesos.reintegros.filtro.host.estructura.HostArchivo;
import com.davivienda.sara.procesos.reintegros.filtro.host.procesador.HostProcesadorArchivo;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.EntityManager;
import com.davivienda.utilidades.conversion.Fecha;


import com.davivienda.sara.tablas.hostatm.servicio.HostAtmServicio;

import com.davivienda.sara.entitys.HostAtm;
/**
 * ProcesadorDiarioElectronicoServicio - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class ProcesadorHostServicio extends BaseServicio {

 

    private CajeroServicio cajeroServicio;
    
    private HostAtmServicio hostAtmServicio ;

    public ProcesadorHostServicio(EntityManager em) {
        super(em);
        hostAtmServicio =new HostAtmServicio(em);
        cajeroServicio = new CajeroServicio(em);
        

    }

    /**
    * Carga un archivo de movmientos atm mvtoatm01 en la tabla hostatm
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
      public Integer cargarArchivoHostAnterior( Calendar fecha) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
     
        if (fecha == null || fecha.after(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy())) {
            throw new IllegalArgumentException("Debe ingresar una fecha válida");
        }
        Integer regsProcesados = -1;
         Integer regsNOProces = -1;
      
       
        
      ArchivoPlano archivo = null;
      Collection<HostAtm> regsHostAtm = null;
       ProcesadorArchivoHostInterface procesador = null;
      
        String directorio = "";
            
          
        if(directorio.equals(""))
        {
                 directorio =configApp.DIRECTORIO_CUADRE;
        }
        //RECORO LOS 6 ARCHIVOS HOST A,B,C,D,E,F
      
     
             archivo = new HostArchivo( fecha, directorio);
             procesador = new HostProcesadorArchivo(archivo);

         configApp.loggerApp.info("Inicia el proceso de carga para el archivo host : " + archivo.getNombreArchivo() + "  a las "+ Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"));

            // Leo los registros en el archivo asignado

            if (procesador != null) {

                  regsHostAtm =  procesador.getRegistrosHost();



            }
              Cajero cajero ;

                if (regsHostAtm != null) {


                    for (HostAtm regHost : regsHostAtm) {
                        cajero=cajeroServicio.buscar(regHost.getCajero().getCodigoCajero());
                        if(cajero!=null)
                        {
                         hostAtmServicio.adicionar(regHost);
                         regsProcesados++;
                        }
                        else
                       {
                           regsNOProces++;
                       }  
                    }



            }



    //            if (regsHostAtm != null) {
    //               
    //                    regsProcesados++;
    //                   }
    //                   else
    //                   {
    //                       regsNOProces++;
    //                   }    
    //          

            configApp.loggerApp.info("Finaliza el proceso de carga para el archivo host : " + archivo.getNombreArchivo() + " con " + regsProcesados + " registros procesados a las "+ Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"));
       
        return regsProcesados;
    }
      
        /**
      * Carga un archivo de movmientos atm mvtoatm01 en la tabla hostatm x cantidad de filas
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
      public Integer cargarArchivoHost( Calendar fecha,int numRegistros) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException, IOException {
     
        if (fecha == null || fecha.after(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy())) {
            throw new IllegalArgumentException("Debe ingresar una fecha válida");
        }
        Integer regsProcesados = -1;
       // Integer regsProcesadosTotal = -1;
         Integer regsNOProces = -1;
      
       
        
      ArchivoPlano archivo = null;
      Collection<HostAtm> regsHostAtm = null;
       ProcesadorArchivoHostInterface procesador = null;
      
        String directorio = "";
            
          
        if(directorio.equals(""))
        {
                 directorio =configApp.DIRECTORIO_CUADRE;
        }
        //RECORO LOS 6 ARCHIVOS HOST A,B,C,D,E,F
      
         archivo = new HostArchivo( fecha, directorio);
         procesador = new HostProcesadorArchivo(archivo); 
         configApp.loggerApp.info("Inicia el proceso de cargar para el archivo host : " + archivo.getNombreArchivo() + " para  "+(int)(numRegistros+20000)+ " registros a las "+ Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"));
        
       
            
           
                regsHostAtm =  procesador.getRegistrosHostXFilas(numRegistros,20000);
                

//                if (procesador != null) {
//
//                     regsHostAtm =  procesador.getRegistrosHostXFilas(i,10);
//
//
//
//                }
                  Cajero cajero ;

                    if (regsHostAtm != null) {


                        for (HostAtm regHost : regsHostAtm) {
                            cajero=cajeroServicio.buscar(regHost.getCajero().getCodigoCajero());
                            if(cajero!=null)
                            {
                             hostAtmServicio.adicionar(regHost);
                             regsProcesados++;
                            }
                            else
                           {
                               regsNOProces++;
                           }  
                        }



                }



            configApp.loggerApp.info("Finaliza el proceso de cargar para el archivo host : " + archivo.getNombreArchivo() + " " + (int)(numRegistros+20000) + " registros procesados a las "+ Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"));
       
        return regsProcesados;
    }
      
        public int cuentaRegistros (Calendar fecha) throws FileNotFoundException, IOException
        {
         ArchivoPlano archivo = null;
         int numRegistros=0;
          String directorio = "";
          if(directorio.equals(""))
        {
                 directorio =configApp.DIRECTORIO_CUADRE;
        }
      
      
         archivo = new HostArchivo( fecha, directorio);
         numRegistros=archivo.cuentaRegistros();

         return  numRegistros;
        
        }
      

}  
   

   