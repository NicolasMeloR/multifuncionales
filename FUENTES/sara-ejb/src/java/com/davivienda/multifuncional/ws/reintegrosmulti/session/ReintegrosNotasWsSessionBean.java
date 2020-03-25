package com.davivienda.multifuncional.ws.reintegrosmulti.session;

import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.tablas.occa.servicio.OccaServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.Cajero;
import javax.ejb.Local;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.PersistenceContext;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import java.math.BigDecimal;
import java.util.Calendar;
import com.davivienda.sara.constantes.TipoCuenta;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.WebServiceException;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.multifuncional.constantes.TipoCuentaMultifuncional;
import com.davivienda.utilidades.ws.gestor.cliente.InvocacionServicios;
import java.util.Date;

/**
 * ProcesadorDiarioElectronicoServicio - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

@Stateless
@Local(value = ReintegrosNotasWsSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ReintegrosNotasWsSessionBean implements ReintegrosNotasWsSessionLocal {
    @PersistenceContext private EntityManager em;
 
    private CajeroServicio cajeroServicio;
    private OccaServicio occaServicio ;
    private InvocacionServicios invocacionServicios;
    String servidor="";
    String puerto="";
    String canal="";
    String concepto="";
   
    

  private static SaraConfig configApp;
  /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
       
        cajeroServicio = new CajeroServicio(em);
	occaServicio = new OccaServicio(em);
        servidor= em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.SERVIDOR_WS")).getValor().trim();
        puerto= em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.PUERTO_SERVIDOR_WS")).getValor().trim();
        canal="26";
        concepto="0352";
        iniciarLog();
        

    }
    
    private Integer obtenerCodigoOficina(Integer codigoCajero)
    {
       Integer codTerminal=0;
       try{
          Cajero cajero = cajeroServicio.buscar(codigoCajero);
          if (cajero != null)
	  {
            codTerminal=cajero.getOficinaMulti().getCodigooficinamulti(); 
	  }
       }catch (Exception ex) {
              java.util.logging.Logger.getLogger("globalApp").info("No se pudo obtener el CodigoOcca de el codigoCajero" + ex.getMessage());   
       }
       return codTerminal;
    }
    
    private void iniciarLog() {
        if (configApp == null) {
            try {
                configApp = SaraConfig.obtenerInstancia();
              
         
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ReintegrosNotasWsSessionBean.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            } catch (Exception ex) {
                Logger.getLogger(ReintegrosNotasWsSessionBean.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
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
    
    /**
     * Método para completar el talon a  6 posiciones
     * @param codigoCajero
     * @return String Talon
    */
    private String armarTalonNotaCredito( String talon)
    {
      for(int i=0;talon.length()<6;i++)
      {
        talon="0"+talon;
      }
      return talon;
    }
    
   public String realizarNotaDebito(Integer codigoCajero, BigDecimal valor,String cuenta,Integer tipoCuenta,String usuario)
   {
         String fechaActual="";
         String talon="";
         String respuesta="";
         String respuestaDescripcion="";
       	 Integer codigoOficina =0;
         Date fecha=null;
         TipoCuenta tipoCuentaR=TipoCuenta.CuentaAhorros;
         tipoCuentaR=TipoCuenta.getTipoCuenta(tipoCuenta);
         try 
         {
             codigoOficina=  obtenerCodigoOficina(codigoCajero);   
             fecha=com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().getTime();
             fechaActual=com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AAAAMMDD);
             talon=armarTalon(codigoCajero);
             respuesta=aplicarNotaDebito(codigoCajero,codigoOficina,   valor, fechaActual, talon,cuenta,tipoCuentaR,usuario);
             configApp.loggerApp.log(java.util.logging.Level.FINE,"EN realizarNotaDebito "+tipoCuentaR.nombre+ " para codigoCajero : " + codigoCajero.toString() + " por : "+valor.toString()+" Canal: "+canal + " la respuesta del webservice es "+respuesta );
             respuestaDescripcion=respuesta;
             if(respuesta!=null)
             {	 
                 if(respuesta.length()>0)
                 { 
                          if(respuesta.substring(0, 1).equals("M"))
                         {
                             respuestaDescripcion="NO se pudo Realizar la NotaDebito "+tipoCuentaR.nombre; 
                         }
                         else
                         {       
                            if(respuesta.substring(0, 1).equals("B"))
                            {
                              respuestaDescripcion=respuestaDescripcion+="NotaDebito "+tipoCuentaR.nombre+ "Realizado con Exito";
                            }
                         }
                 }
             } 
         }catch (Exception ex) {
             respuestaDescripcion ="EN NotaDebito "+tipoCuentaR.nombre+ "Error: " + ex.getMessage(); 
             configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN NotaDebito "+tipoCuentaR.nombre+" Error: " + ex.getMessage());   
             respuesta="F"+respuestaDescripcion; 
         }
         return respuesta;
  }   
   
   
   public String aplicarNotaDebito(Integer codigoCajero, Integer  codigoOcca, BigDecimal valor,String fechaActual,String talon,   String cuenta, TipoCuenta tipoCuenta,String usuario)
   {
        String respuesta="";
        String respuestaDescripcion="";
        try 
        {
              invocacionServicios =new InvocacionServicios(servidor, puerto, codigoCajero);
              switch (tipoCuenta) {
                    case CuentaAhorros:
                         respuesta=invocacionServicios.realizarNotaDebitoCtaAhorrosESB(codigoCajero,   valor, fechaActual, talon,cuenta,usuario,concepto,canal);
                    break;
                   case CuentaCorriente:
                          respuesta=invocacionServicios.realizarNotaDebitoCtaCorrienteESB(codigoCajero,   valor, fechaActual, talon,cuenta,usuario,concepto,canal);  
                   break;  

                   default:
                   break;
               }  
         }catch (SecurityException ex) {
                  respuestaDescripcion ="EN NotaDebito "+tipoCuenta.nombre+" Error al ejecutar el requerimiento:" + ex.getMessage();  
                  configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN NotaDebito "+tipoCuenta.nombre+ "Error al ejecutar el requerimiento:" + ex.getMessage());
                  respuesta= "F"+respuestaDescripcion;
         }catch (WebServiceException ex) {
                   respuestaDescripcion ="EN NotaDebito "+tipoCuenta.nombre+ "Error al llamar al WS:" + ex.getMessage();  
                   configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN NotaDebito "+tipoCuenta.nombre+ "Error al llamar al WS:" + ex.getMessage());
                   respuesta="F"+ respuestaDescripcion;
          }
//          catch (DatatypeConfigurationException ex) {
//                   respuestaDescripcion ="EN NotaDebito "+tipoCuenta.nombre+ "Error en DatatypeConfigurationException:" + ex.getMessage();  
//                   configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN NotaDebitoCtaAhorros Error al llamar al WS:" + ex.getMessage());
//                   respuesta="F"+ respuestaDescripcion;
//          }
          return respuesta;
  }
     
   public String realizarNotaCredito(Integer codigoCajero, BigDecimal valor,String cuenta,Integer tipoCuenta,String usuario,String talon)
   {
          String fechaActual="";
          String respuesta="";
          String respuestaDescripcion="";
          Integer codigoOficina =0;  
          Date fecha=null;
          TipoCuentaMultifuncional tipoCuentaR=TipoCuentaMultifuncional.CuentaAhorros;
          tipoCuentaR=TipoCuentaMultifuncional.getTipoCuentaMultifuncional(tipoCuenta);
          try 
          {
             codigoOficina=  obtenerCodigoOficina(codigoCajero);   
             fecha=com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().getTime();
             fechaActual=com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AAAAMMDD);
             talon=armarTalonNotaCredito(talon);
             respuesta=aplicarNotaCredito(codigoCajero,codigoOficina,   valor, fechaActual, talon,cuenta,tipoCuentaR,usuario,concepto);
             configApp.loggerApp.log(java.util.logging.Level.FINE,"EN realizarNotaCredito "+tipoCuentaR.nombre+ " para codigoCajero : " + codigoCajero.toString() + " por : "+valor.toString()+ " Para la fecha: " +fechaActual.toString()+" la respuesta del webservice es "+respuesta );
             respuestaDescripcion=respuesta;
             if(respuesta!=null){	 
                 if(respuesta.length()>0)
                 { 
                        if(respuesta.substring(0, 1).equals("M"))
                        {
                             respuestaDescripcion="NO se pudo Realizar la NotaCredito "+tipoCuentaR.nombre; 
                        }
                        else
                        {       
                            if(respuesta.substring(0, 1).equals("B"))
                            {
                              respuestaDescripcion=respuestaDescripcion+="NotaCredito "+tipoCuentaR.nombre+ "Realizado con Exito";
                            }
                        }
                 }
             } 
         }catch (Exception ex) {
               respuestaDescripcion ="EN NotaCredito "+tipoCuentaR.nombre+ "Error: " + ex.getMessage(); 
               configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN NotaCredito "+tipoCuentaR.nombre+" Error: " + ex.getMessage());   
               respuesta="F"+respuestaDescripcion; 
         }
         return respuesta;
   }   
   
   
   public String aplicarNotaCredito(Integer codigoCajero, Integer  codigoOficina, BigDecimal valor,String fechaActual,String talon,   String cuenta, TipoCuentaMultifuncional tipoCuenta,String usuario,String conceptoNC )
   {
        String respuesta="";
        String respuestaDescripcion="";
        try 
        {
           invocacionServicios=new InvocacionServicios(servidor, puerto, codigoCajero);
           switch (tipoCuenta) {
               case CuentaAhorros:
                   respuesta=invocacionServicios.realizarNotaCreditoCtaAhorrosESB(codigoCajero, codigoOficina, valor, fechaActual, talon, cuenta,usuario,conceptoNC,canal);
               break;
               case CuentaCorriente:
                    respuesta=invocacionServicios.realizarNotaCreditoCtaCorrienteESB(codigoCajero,codigoOficina,   valor, fechaActual, talon,cuenta,usuario,conceptoNC,canal);   
               break;  
               case CreditoFM:
                    respuesta=invocacionServicios.realizarNotaCreditoFM(codigoCajero,codigoCajero, valor, fechaActual, new Integer(talon), cuenta,usuario);
               break;
               case TarjetaCredito:
                     respuesta=invocacionServicios.realizarNotaCreditoTarjetaCredito(new Long(cuenta),codigoCajero,valor,fechaActual,codigoCajero.longValue(),codigoOficina.longValue());
               break; 
               case CrediExpress:
                    respuesta=invocacionServicios.realizarNotaCreditoFM(codigoCajero,codigoCajero, valor, fechaActual, new Integer(talon), cuenta,usuario);
               break;  
               default:
               break;
             }  
         }catch (SecurityException ex) {
                  respuestaDescripcion ="EN NotaCredito "+tipoCuenta.nombre+" Error al ejecutar el requerimiento:" + ex.getMessage();  
                  configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN NotaCredito "+tipoCuenta.nombre+ "Error al ejecutar el requerimiento:" + ex.getMessage());
                  respuesta= "F"+respuestaDescripcion;
         }catch (WebServiceException ex) {
                 respuestaDescripcion ="EN NotaCredito "+tipoCuenta.nombre+ "Error al llamar al WS:" + ex.getMessage();  
                  configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN NotaCredito "+tipoCuenta.nombre+ "Error al llamar al WS:" + ex.getMessage());
                  respuesta="F"+ respuestaDescripcion;
         }catch (DatatypeConfigurationException ex) {
                 respuestaDescripcion ="EN NotaCredito "+tipoCuenta.nombre+ "Error en DatatypeConfigurationException:" + ex.getMessage();  
                  configApp.loggerApp.log(java.util.logging.Level.SEVERE,"EN NotaCreditoCtaAhorros Error al llamar al WS:" + ex.getMessage());
                  respuesta="F"+ respuestaDescripcion;
        }
        return respuesta;
   }
     
}  
   

   

   