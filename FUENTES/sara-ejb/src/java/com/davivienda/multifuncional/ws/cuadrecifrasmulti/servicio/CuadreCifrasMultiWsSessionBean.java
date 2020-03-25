package com.davivienda.multifuncional.ws.cuadrecifrasmulti.servicio;


import com.davivienda.utilidades.ws.gestor.cliente.ResumenAjustesMulti;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.tablas.occa.servicio.OccaServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.Occa;
import javax.ejb.Local;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.PersistenceContext;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.ws.WebServiceException;
import com.davivienda.sara.config.SaraConfig;
import javax.ejb.EJB;
import java.util.Date;
import com.davivienda.multifuncional.tablas.historicoajustesmulti.session.HistoricoAjustesMultiLocal;
import com.davivienda.utilidades.ws.gestor.cliente.InvocacionServicios;
import com.davivienda.utilidades.ws.gestor.cliente.ResumenAjustes;



/**
 * ProcesadorDiarioElectronicoServicio - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2011 
 */

@Stateless
@Local(value = CuadreCifrasMultiWsSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CuadreCifrasMultiWsSessionBean implements CuadreCifrasMultiWsSessionLocal {
    @PersistenceContext private EntityManager em;
    @Resource private UserTransaction utx;
    private OccaServicio occaServicio ;
    private InvocacionServicios invocacionServicios;
    String servidor="";
    String puerto="";
   
    

  private static SaraConfig configApp;
 
  @EJB
  private HistoricoAjustesMultiLocal administradorProcesosHistoricoAjustesSessionLocal;
    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
       
        
        occaServicio = new OccaServicio(em);
        servidor= em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.SERVIDOR_WS")).getValor().trim();
        puerto= em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.PUERTO_SERVIDOR_WS")).getValor().trim();
        iniciarLog();

     }
    
    /**
     * Método para obtener el Codigo de terminal caja
     * @param codigoOcca
     * @return Integer CodigoTerminalCaja
     
     */
    private Integer obtenerCodigoTerminal(Integer codigoOcca)
    {
        Integer codTerminal=0;
        try{
            Occa occa = occaServicio.buscar(codigoOcca);
            if (occa != null)
            {

                codTerminal=occa.getCodigoTerminal(); 
            }
        }catch (Exception ex) {
               java.util.logging.Logger.getLogger("globalApp").info("No se pudo obtener el CodigoTerminal de la occa" + ex.getMessage());   

        }
        return codTerminal;
    }
    
    private void iniciarLog() 
    {
      if (configApp == null) {
          try {
                configApp = SaraConfig.obtenerInstancia();
          } catch (IllegalAccessException ex) {
                Logger.getLogger(CuadreCifrasMultiWsSessionBean.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
          } catch (Exception ex) {
                Logger.getLogger(CuadreCifrasMultiWsSessionBean.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
          }
      }
    }
    
     /**
     * Método para obtener el talon codigoCajero + consecutivo debe ser de 6 posiciones
     * @param codigoCajero
     * @return String Talon
    */
    private String armarTalon( Integer codigoCajero)
    {
        String strCodigoCajero ="0";
        String talon="";
        strCodigoCajero=codigoCajero.toString();
        Integer milisegundos=com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().get(Calendar.MILLISECOND);
        if (codigoCajero<10)
        {
            strCodigoCajero ="000"+strCodigoCajero;
        }
        else
        {
            if (codigoCajero<100)
            {
               strCodigoCajero ="00"+strCodigoCajero;
            }
            else
            {
                 if (codigoCajero<1000)
                 {
                    strCodigoCajero ="0"+strCodigoCajero;
                 }
            }
        }
        talon=strCodigoCajero+milisegundos.toString().substring(1);
        //EL TALON DEBE SER DE 6 CARACTERES
        if(talon.length()==4)
        {
          talon=talon+"0";    
        }
        if(talon.length()==5)
        {
          talon=talon+"0";    
        }
        return talon;
    }
    
    private void guardarHistoricoAjustes(String usuario, Integer codigoCajero, Integer codigoOficina, String tipoAjuste, Date fecha, BigDecimal valor,String talon, String error, String descripcionError){
       try {
                administradorProcesosHistoricoAjustesSessionLocal.guardarHistoricoAjustes(usuario, codigoCajero, codigoOficina, tipoAjuste, fecha,  valor.longValue(),talon,error , descripcionError);
        } catch (EntityServicioExcepcion ex) {
                configApp.loggerApp.log(java.util.logging.Level.SEVERE," Error al guardar datos en la tabla HistoricoAjustes:" + ex.getMessage());
        } catch (SecurityException ex) {
                  configApp.loggerApp.log(java.util.logging.Level.SEVERE," Error al guardar datos en la tabla HistoricoAjustes:" + ex.getMessage());
        }   
    }
    
   /**
     * Método para realizar el ajuste sobrante de efectivo del cajero automático
     * @param codigoCajero
     * @param codigoOcca
     * @param valorAjuste
     * @param fechaAjuste formato AAAAMMDD
     * @param talon codigoCajero + consecutivo debe ser de 6 posiciones
     * @return
     * @throws com.davivienda.procesadortransacciones.impl.servicios.ServicioException_Exception
     */
      public String realizarAjustePorSobrante(String usuario, Integer codigoCajero, Integer codigoOficina, BigDecimal valorAjuste ){
         
       
         String fechaActual="";
         String talon="";
         String respuesta="";
         String respuestaDescripcion="";
         String respuestaError="";
	 Date fecha=null;	
         try {
                 //invocacionServicios=new InvocacionServicios(servidor, puerto, codigoCajero);
                 invocacionServicios = new InvocacionServicios(servidor, puerto, codigoCajero);
                 fecha=com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().getTime();
                 fechaActual=com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AAAAMMDD);
                 talon=armarTalon(codigoCajero);
                 
              //   respuesta= invocacionServicios.ajustarIngreso(codigoCajero, codigoOficina, valorAjuste,"215", fechaActual, talon,codigoCajero.toString());
                 respuesta= invocacionServicios.ajustarIngresoESB(codigoCajero, codigoOficina, valorAjuste, fechaActual, talon,"26", new Short("215"),"0"); // Canal (0) concepto (0) nitTransportadora(0)
                 configApp.loggerApp.log(java.util.logging.Level.FINE,"DATA EN AjustePorSobrante para codigoCajero : " + codigoCajero.toString() + " por : "+valorAjuste.toString()+ "la respuesta del webservice es "+respuesta );
                 respuestaDescripcion=respuesta;
                 respuestaError= respuesta;
                 if(respuesta!=null){	 
                       if(respuesta.length()>0){
                           if(respuesta.substring(0, 1).equals("M"))
                           {
                                  respuestaDescripcion="No se pudo realizar el ajuste por Sobrante";
                           }
                           else 
                           {
                                 if(respuesta.substring(0, 1).equals("B"))
                                 {
                                    respuestaDescripcion=respuestaDescripcion+="Ajuste Realizado con Exito";
                                 }
                           }
                        } 	
                   }
	} catch (SecurityException ex) {
                  respuestaError="F";
                  respuestaDescripcion ="EN AjustePorSobrante Error al ejecutar el requerimiento:" + ex.getMessage();  
                  configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN AjustePorSobrante Error al ejecutar el requerimiento:" + ex.getMessage());
                  respuesta= "F"+respuestaDescripcion;
         } catch (WebServiceException ex) {
                   respuestaError="F";
                   respuestaDescripcion ="EN AjustePorSobrante Error al llamar al WS:" + ex.getMessage();  
                   configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN AjustePorSobrante Error al llamar al WS:" + ex.getMessage());
                   respuesta="F"+ respuestaDescripcion;

         }catch (Exception ex) {
                    //diarioElectronicoServicio.getConfigApp().loggerApp.info("No se grabaran los registros procesados de archivo Diario Electronico" + ex.getMessage());            
                   respuestaError="F";
                   respuestaDescripcion ="EN AjustePorSobrante Error: " + ex.getMessage(); 
                   configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN AjustePorSobrante Error: " + ex.getMessage());   
                   respuesta="F"+respuestaDescripcion;
          }finally{
                  guardarHistoricoAjustes(usuario,codigoCajero,codigoOficina,"Ajuste por Sobrante",fecha,valorAjuste,talon,respuestaError,respuestaDescripcion);
         }
         
        return respuesta;
     }
    
    public String realizarAjustePorFaltante(String usuario, Integer codigoCajero, Integer codigoOficina, BigDecimal valorAjuste ){
      
         String fechaActual="";
         String talon="";
         String respuesta="";
         String respuestaDescripcion="";
         String respuestaError="";
         Date fecha=null;
         try {
             //invocacionServicios=new InvocacionServicios(servidor, puerto, codigoCajero);
             invocacionServicios = new InvocacionServicios(servidor, puerto, codigoCajero);
             fecha=com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().getTime();
             fechaActual=com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AAAAMMDD);
             talon=armarTalon(codigoCajero);
             
             //respuesta=invocacionServicios.ajustarEgreso(codigoCajero, codigoOficina, valorAjuste, "201", fechaActual, talon,codigoCajero.toString());
             respuesta= invocacionServicios.ajustarEgresoESB(codigoCajero, codigoOficina, valorAjuste, fechaActual, talon,"26", new Short("201"),"0"); // Canal (0) concepto (0) nitTransportadora(0)
             configApp.loggerApp.log(java.util.logging.Level.FINE,"EN AjustePorFaltante para codigoCajero : " + codigoCajero.toString() + " por : "+valorAjuste.toString()+ "la respuesta del webservice es "+respuesta );  
             respuestaDescripcion=respuesta;
             respuestaError= respuesta;
             if(respuesta!=null){	 
               if(respuesta.length()>0){
                       if(respuesta.substring(0, 1).equals("M"))
                       {
                           respuestaDescripcion="No se pudo realizar el ajuste por Faltante";

                       }
                       else 
                       {      if(respuesta.substring(0, 1).equals("B"))
                             {
                                respuestaDescripcion=respuestaDescripcion+="Ajuste Realizado con Exito";
                             }
                       }
                 }
             }
         } catch (SecurityException ex) {
                  respuestaError="F";
                  respuestaDescripcion ="EN AjustePorFaltante Error al ejecutar el requerimiento:" + ex.getMessage();  
                  configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN AjustePorFaltante Error al ejecutar el requerimiento:" + ex.getMessage());
                  respuesta= "F"+respuestaDescripcion;
         } catch (WebServiceException ex) {
                   respuestaError="F";
                   respuestaDescripcion ="EN AjustePorFaltante Error al llamar al WS:" + ex.getMessage();  
                   configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN AjustePorFaltante Error al llamar al WS:" + ex.getMessage());
                   respuesta="F"+ respuestaDescripcion;
         }catch (Exception ex) {
                    
                   respuestaError="F";
                   respuestaDescripcion ="EN AjustePorFaltante Error: " + ex.getMessage(); 
                   configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN AjustePorFaltante Error: " + ex.getMessage());   
                   respuesta="F"+respuestaDescripcion;
        }finally{
                guardarHistoricoAjustes(usuario,codigoCajero,codigoOficina,"Ajuste por Faltante",fecha,valorAjuste,talon,respuestaError,respuestaDescripcion);
        }
        return respuesta;
   }
         
  public String realizarAjustePorSobranteArqueo(String usuario, Integer codigoCajero, Integer codigoOficina, BigDecimal valorAjuste,String nitTransportadora ){
         
         String fechaActual="";
         String talon="";
         String respuesta="";
         String respuestaDescripcion="";
         String respuestaError="";
	 Date fecha=null;	
         try {
             //invocacionServicios=new InvocacionServicios(servidor, puerto, codigoCajero);
             invocacionServicios = new InvocacionServicios(servidor, puerto, codigoCajero);
             fecha=com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().getTime();
             fechaActual=com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AAAAMMDD);
             talon=armarTalon(codigoCajero);
             //respuesta= invocacionServicios.ajustarIngreso(codigoCajero, codigoOficina, valorAjuste,"218", fechaActual, talon,nitTransportadora);
             respuesta= invocacionServicios.ajustarIngresoESB(codigoCajero, codigoOficina, valorAjuste, fechaActual, talon,"26", new Short("218"),nitTransportadora); // Canal (0) concepto (0)
  
             configApp.loggerApp.log(java.util.logging.Level.FINE,"EN AjustePorSobranteArqueo para codigoCajero : " + codigoCajero.toString() + " por : "+valorAjuste.toString()+ "la respuesta del webservice es "+respuesta );
             respuestaDescripcion=respuesta;
             respuestaError= respuesta;
             if(respuesta!=null){	 
                if(respuesta.length()>0){
                      if(respuesta.substring(0, 1).equals("M")){
                          respuestaDescripcion="No se pudo realizar el ajuste por Sobrante Arqueo";
                      }
                      else 
                      {
                         if(respuesta.substring(0, 1).equals("B")){
                            respuestaDescripcion=respuestaDescripcion+="Ajuste Realizado con Exito";
                         }
                      }
                 } 	
             }	
         }catch (SecurityException ex) {
                  respuestaError="F";
                  respuestaDescripcion ="EN AjustePorSobranteArqueo Error al ejecutar el requerimiento:" + ex.getMessage();  
                  configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN AjustePorSobranteArqueo Error al ejecutar el requerimiento:" + ex.getMessage());
                  respuesta= "F"+respuestaDescripcion;
         }catch (WebServiceException ex) {
                   respuestaError="F";
                   respuestaDescripcion ="EN AjustePorSobranteArqueo Error al llamar al WS:" + ex.getMessage();  
                   configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN AjustePorSobranteArqueo Error al llamar al WS:" + ex.getMessage());
                   respuesta="F"+ respuestaDescripcion;
         }catch (Exception ex) {
                   respuestaError="F";
                   respuestaDescripcion ="EN AjustePorSobranteArqueo Error: " + ex.getMessage(); 
                   configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN AjustePorSobranteArqueo Error: " + ex.getMessage());   
                   respuesta="F"+respuestaDescripcion;
          }finally{
                   guardarHistoricoAjustes(usuario,codigoCajero,codigoOficina,"Ajuste por Sobrante Arqueo",fecha,valorAjuste,talon,respuestaError,respuestaDescripcion);
         }
        return respuesta;
  }
        
      public String realizarAjustePorFaltanteArqueo(String usuario, Integer codigoCajero, Integer codigoOficina, BigDecimal valorAjuste,String nitTransportadora  ){
      
         String fechaActual="";
         String talon="";
         String respuesta="";
         String respuestaDescripcion="";
         String respuestaError="";
         Date fecha=null;
         try {
             //invocacionServicios=new InvocacionServicios(servidor, puerto, codigoCajero);
             invocacionServicios = new InvocacionServicios(servidor, puerto, codigoCajero);

             fecha=com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().getTime();
             fechaActual=com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AAAAMMDD);
             talon=armarTalon(codigoCajero);
             //respuesta=invocacionServicios.ajustarEgreso(codigoCajero, codigoOficina, valorAjuste, "202", fechaActual, talon,nitTransportadora);
             respuesta= invocacionServicios.ajustarEgresoESB(codigoCajero, codigoOficina, valorAjuste, fechaActual, talon,"26", new Short("202"),nitTransportadora); // Canal (0) concepto (0)

             configApp.loggerApp.log(java.util.logging.Level.FINE,"EN AjustePorFaltanteArqueo para codigoCajero : " + codigoCajero.toString() + " por : "+valorAjuste.toString()+ "la respuesta del webservice es "+respuesta );  
             respuestaDescripcion=respuesta;
             respuestaError= respuesta;
             if(respuesta!=null){	 
               if(respuesta.length()>0){
                   if(respuesta.substring(0, 1).equals("M"))
                   {
                       respuestaDescripcion="No se pudo realizar el ajuste por Faltante Arqueo";
                   }
                   else 
                   {
                         if(respuesta.substring(0, 1).equals("B"))
                         {
                            respuestaDescripcion=respuestaDescripcion+="Ajuste Realizado con Exito";
                         }
                   }
               }
             }
         }catch (SecurityException ex) {
                  respuestaError="F";
                  respuestaDescripcion ="EN AjustePorFaltanteArqueo Error al ejecutar el requerimiento:" + ex.getMessage();  
                  configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN AjustePorFaltante Error al ejecutar el requerimiento:" + ex.getMessage());
                  respuesta= "F"+respuestaDescripcion;
        } catch (WebServiceException ex) {
                   respuestaError="F";
                   respuestaDescripcion ="EN AjustePorFaltanteArqueo Error al llamar al WS:" + ex.getMessage();  
                   configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN AjustePorFaltante Error al llamar al WS:" + ex.getMessage());
                   respuesta="F"+ respuestaDescripcion;
         }catch (Exception ex) {
                  respuestaError="F";
                   respuestaDescripcion ="EN AjustePorFaltanteArqueo Error: " + ex.getMessage(); 
                   configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN AjustePorFaltante Error: " + ex.getMessage());   
                   respuesta="F"+respuestaDescripcion;
         }finally{
                 guardarHistoricoAjustes(usuario,codigoCajero,codigoOficina,"Ajuste por Faltante Arqueo",fecha,valorAjuste,talon,respuestaError,respuestaDescripcion);
         }
         return respuesta;
    }
      
     public String realizarDisminucionDeposito(String usuario,long codigoTransportadora,Integer codigoCajero, BigDecimal valorEfectivo, Integer codigoOficina){
         String respuesta="";
         String respuestaDescripcion="";
         String respuestaError="";
         Integer talon=0;
         Date fecha=null;
         fecha=com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().getTime();
         try {
             invocacionServicios=new InvocacionServicios(servidor, puerto,codigoCajero);
             talon=new Integer(armarTalon((Integer)codigoCajero));
             respuesta= invocacionServicios.realizarEnvioTransportadora(codigoTransportadora,valorEfectivo, talon);
             respuestaDescripcion=respuesta;
             respuestaError= respuesta;
             if(respuesta!=null){	 
                 if(respuesta.length()>0){
                    if(respuesta.substring(0, 1).equals("M")){  
                       respuestaDescripcion="NO se pudo Realizar DisminucionDeposito"; 
                    } 
                    else{       
                        if(respuesta.substring(0, 1).equals("B")){
                           respuestaDescripcion=respuestaDescripcion+="DisminucionDeposito Realizado con Exito";
                        }
                   }
                }		
             }
         }catch (SecurityException ex) {
                  respuestaError="F";
                  respuestaDescripcion ="EN DisminucionDeposito Error al ejecutar el requerimiento:" + ex.getMessage();  
                  configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN DisminucionDeposito Error al ejecutar el requerimiento:" + ex.getMessage());
                  respuesta= "F"+respuestaDescripcion;
         }catch (WebServiceException ex) {
		   respuestaError="F";
                   respuestaDescripcion ="EN DisminucionDeposito Error al llamar al WS:" + ex.getMessage();  
                   configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN DisminucionDeposito Error al llamar al WS:" + ex.getMessage());
                   respuesta="F"+ respuestaDescripcion;
         }catch (Exception ex) {
                   respuestaError="F";
                   respuestaDescripcion ="EN DisminucionDeposito Error: " + ex.getMessage(); 
                   configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN DisminucionDeposito Error: " + ex.getMessage());   
                   respuesta="F"+respuestaDescripcion;
         }finally{
               	  guardarHistoricoAjustes(usuario,codigoCajero,codigoOficina,"Ajuste Disminucion Provision",fecha,valorEfectivo,"000000",respuestaError,respuestaDescripcion);
         }
         return respuesta;
   }

    public ResumenAjustesMulti[] consultarInformeTotalesMultifuncional( Integer codigoCajero, Integer codigoOficina, Integer tipoConsulta, Integer indicadorTotales){
  	  ResumenAjustesMulti[] resumen=null;
          try {
             invocacionServicios=new InvocacionServicios(servidor, puerto, obtenerCodigoTerminal(codigoOficina));
             resumen = invocacionServicios.consultarInformeTotalesATM(codigoCajero,tipoConsulta,indicadorTotales);
         } catch (SecurityException ex) {
                configApp.loggerApp.log(java.util.logging.Level.SEVERE,"Error al ejecutar el requerimiento:" + ex.getMessage());
         } catch (WebServiceException ex) {
                   configApp.loggerApp.log(java.util.logging.Level.SEVERE,"Error al llamar al WS:" + ex.getMessage());
         }catch (Exception ex) {
                   configApp.loggerApp.log(java.util.logging.Level.SEVERE,"Error: " + ex.getMessage());   
         }
      return resumen;
    }
   

}  
   

   

   