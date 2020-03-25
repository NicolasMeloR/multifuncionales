package com.davivienda.sara.procesos.edccargue.servicio;

import com.davivienda.sara.base.BaseServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoProceso;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import java.io.FileNotFoundException;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.annotation.Resource;

import javax.transaction.UserTransaction;

import com.davivienda.sara.entitys.EdcCargue;
import com.davivienda.sara.tablas.edccargue.servicio.EdcCargueServicio;
        


/**
 * AdministradorProcesosEdcCargueServicio - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class AdministradorProcesosEdcCargueServicio extends BaseServicio {

    private CajeroServicio cajeroServicio;

    
    private EdcCargueServicio edcCargueServicio;
    
    @Resource
    private UserTransaction utx;

    public AdministradorProcesosEdcCargueServicio(EntityManager em) {
        super(em);
        cajeroServicio = new CajeroServicio(em);
        edcCargueServicio=new EdcCargueServicio(em);
    }

    /**
     * Carga los datos de un archivo en EdcCargue
     * @param codigoCajero
     * @param fecha
     * @param nombreArchivo
     * @param tamano
     * @param ciclo
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
    
    public Integer guardarProcesoTransmisionTiras(String nombreArchivo,Integer tamano,Integer ciclo,Date fecha,Integer codigoCajero ,Integer estado,boolean actualiza)  throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {

        
      
       Integer codigoErrorProceso=0;
       Cajero cajero = cajeroServicio.buscar(codigoCajero);
       Integer regGuardados=0;
      
      
     
        
        EdcCargue regProcesoTransmisionTira=new EdcCargue();
     
        if (cajero == null)
        {
      
        codigoErrorProceso=CodigoError.CAJERO_NO_EXISTE.getCodigo();
        }
        //OJO DESCOMENTAREAR 27
              
        regProcesoTransmisionTira.setCodigoCajero(codigoCajero);
        regProcesoTransmisionTira.setCiclo(ciclo);
        regProcesoTransmisionTira.setError(codigoErrorProceso);
        //regProcesoTransmisionTira.setEstadoproceso(EstadoProceso.INICIADO.getEstado());
         regProcesoTransmisionTira.setEstadoproceso(estado);
        regProcesoTransmisionTira.setFechaEdcCargue(fecha); 
        regProcesoTransmisionTira.setNombrearchivo(nombreArchivo);
        regProcesoTransmisionTira.setTamano(tamano);
        regProcesoTransmisionTira.setUltimaSecuencia(0);
       // regProcesoTransmisionTira.setIdEdcCargue(7);
         regProcesoTransmisionTira.setVersion(0);
                
    
       try
            {
          
           //se revisa que ese registro no exista ya
           EdcCargue edcCargue=edcCargueServicio.buscarPorArchivo(nombreArchivo) ;
           if (edcCargue == null )
           {
               edcCargueServicio.adicionar(regProcesoTransmisionTira);
               regGuardados=1;
           }
            else
           {
           //revisar cual es el estado deberia ser 15
             if(actualiza)
             {
                 edcCargue.setEstadoproceso(estado);
                 edcCargueServicio.actualizar(edcCargue);
             }
           }
               
           


          

        } catch (Exception ex)
        {
         java.util.logging.Logger.getLogger("globalApp").info("Error cargando en EDCCARGUE registro datos archivos  :" + nombreArchivo + " descripcion Error : " + ex.getMessage());           
        }
       
           return regGuardados;
           
    }

   
}


