package com.davivienda.sara.reintegros.general.helper;


import com.davivienda.sara.constantes.CodigoError;

import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.utilidades.conversion.JSon;

import java.util.Calendar;
import java.util.logging.Level;
import javax.ejb.EJBException;


import com.davivienda.sara.procesos.reintegros.session.ProcesoReintegrosSessionLocal;

import com.davivienda.sara.reintegros.general.ReintegrosGeneralObjectContext;
import com.davivienda.utilidades.Constantes;

/**
 * DiarioElectronicoGeneralDiarioElectronicoServletHelper - 27/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class ReintegrosCalcularReintegrosServletHelper implements ReintegrosHelperInterface {
    
    private ProcesoReintegrosSessionLocal session;
    private ReintegrosGeneralObjectContext objectContext = null;
  
  

    
    public ReintegrosCalcularReintegrosServletHelper(ProcesoReintegrosSessionLocal session, ReintegrosGeneralObjectContext objectContext) {
        this.session = session;
        this.objectContext = objectContext;
        
    
    }

    public String obtenerDatos() {
      
     
        String respuesta = "";
        int intRegProcesadosHost = -1;
        
        String strExepcion="";
      
       
        try {

            Calendar fechaInicial = null;
          
            try {
                fechaInicial =  com.davivienda.utilidades.conversion.Fecha.getCalendar(objectContext.getAtributoFechaInicial().getTime());
            } catch (IllegalArgumentException ex) {
//                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia( com.davivienda.utilidades.conversion.Fecha.getCalendar(com.davivienda.utilidades.conversion.Fecha.getCalendarAyer(),-2));
//                indFechaFin=false;
               fechaInicial =  com.davivienda.utilidades.conversion.Fecha.getFechaInicioDia( com.davivienda.utilidades.conversion.Fecha.getCalendarAyer());
            }
         
            try {
                // Consulta los registros según los parámetros tomados del request
                
                // if(ConsultarEstadoCargue().equals("0")){
                      
               //      CambiarEstadoCargue("1");
     
     
            
            
                 intRegProcesadosHost= session.calcularReintegros(fechaInicial);                   
                  
                   //VALIDAR ERRORES
                   
                   if (intRegProcesadosHost >= 0)
                   {
                       
                       
                       
                     respuesta = "Se Actualizaron con exito en la bd : " + intRegProcesadosHost + " Registros  Reintegros ";
                     
                     objectContext.setError( respuesta,0);
                     respuesta = JSon.getJSonRespuesta(CodigoError.SIN_ERROR.getCodigo(),  respuesta);
                               
                   }
                 }   

            //}
            
            catch (EJBException ex) {
                
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getMessage() == null)
                {
                strExepcion=ex.getCause().getMessage();
                }
                else
                {
                 strExepcion=ex.getMessage();
                }
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE,strExepcion); 
                objectContext.setError( strExepcion,CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
                //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(),  strExepcion);
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(),Constantes.MSJ_QUERY_ERROR);
           
               
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                objectContext.setError( ex.getMessage(),CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
                //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(),  ex.getMessage());
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(),Constantes.MSJ_QUERY_ERROR);
            }

        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            objectContext.setError( ex.getMessage(),CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
            //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(),Constantes.MSJ_QUERY_ERROR);
        }
        finally
        {
        
               //  CambiarEstadoCargue("0");
        
        }
      
        return respuesta;
    }
//     private void CambiarEstadoCargue(String strEstado)
//    {
//       
//           
//        
//       
//        try {
////         ConfModulosAplicacion registroEntity =em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGADIARIO"));
////                  // new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
////         registroEntity.setValor(strEstado);
////         em.persist(registroEntity);
////         em.flush();
//         
//         ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGACUADRE");
//         registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
//            
//         //ConfModulosAplicacion registroEntityConsulta=confModulosAplicacionSession.buscar(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGADIARIO"));
//         ConfModulosAplicacion registroEntity =new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGACUADRE");
//                  // new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
//         registroEntity.setValor(strEstado);
//         registroEntity.setDescripcion(registroEntityConsulta.getDescripcion());
////       utx.begin();
//         confModulosAplicacionSession.actualizar(registroEntity);
//        
//         } catch (Exception ex) {
//                java.util.logging.Logger.getLogger("globalApp").info("Error cambiando estado cargue : " + ex.getMessage());
//              
//         }
//      
//    
//    }
    
   
    
}
