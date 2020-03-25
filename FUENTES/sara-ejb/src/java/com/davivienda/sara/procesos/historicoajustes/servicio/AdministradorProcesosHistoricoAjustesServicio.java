package com.davivienda.sara.procesos.historicoajustes.servicio;

import com.davivienda.sara.base.BaseServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoProceso;
import java.math.BigDecimal;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import java.io.FileNotFoundException;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.annotation.Resource;

import javax.transaction.UserTransaction;

import com.davivienda.sara.entitys.HistoricoAjustes ;
import com.davivienda.sara.tablas.historicoajustes.servicio.HistoricoAjustesServicio;
import javax.transaction.SystemException;
        


/**
 * AdministradorProcesosEdcCargueServicio - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class AdministradorProcesosHistoricoAjustesServicio extends BaseServicio {

    private CajeroServicio cajeroServicio;

    
    private HistoricoAjustesServicio historicoAjusteServicio;
    
    @Resource
    private UserTransaction utx;

    public AdministradorProcesosHistoricoAjustesServicio(EntityManager em) {
        super(em);
        cajeroServicio = new CajeroServicio(em);
        historicoAjusteServicio=new HistoricoAjustesServicio(em);
    }

    /**
     * guarda en la tabla HistoricoAjustes
     * @param codigoCajero
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
    
//    public void guardarHistoricoAjustes(String usuario, Integer codigoCajero, Integer codigoOcca, String tipoAjuste,String cuenta, Date fecha, BigDecimal valor,String talon, String error, String descripcionError)  throws  EntityServicioExcepcion, IllegalArgumentException {
//
//        
//      
//        Integer codigoErrorProceso=0;
//         //OJO DESCOMENTAREAR PARA COMBO CAJERO
//    //   Cajero cajero = cajeroServicio.buscar(codigocajero);
//      
//      
//     
//        
//        HistoricoAjustes regHistoricoAjustes=new HistoricoAjustes();
//      //OJO DESCOMENTAREAR PARA COMBO CAJEROS
////        if (cajero == null)
////        {
////      
////        codigoErrorProceso=CodigoError.CAJERO_NO_EXITE.getCodigo();
////        }
//       
//       // String usuario, Integer codigoCajero, Integer codigoOcca, String tipoAjuste, Date fecha, BigDecimal valor,String talon, String error, String descripcionError
//        regHistoricoAjustes.setCodigoCajero(codigoCajero);
//        regHistoricoAjustes.setCodigoOcca(codigoOcca);
//        regHistoricoAjustes.setFecha(fecha); 
//        regHistoricoAjustes.setError(error);
//        regHistoricoAjustes.setTipoAjuste(tipoAjuste);
//        regHistoricoAjustes.setCuenta(cuenta);
//        regHistoricoAjustes.setUsuario(usuario);
//        regHistoricoAjustes.setValor(valor);
//        regHistoricoAjustes.setTalon(talon);
//        regHistoricoAjustes.setDescripcionError(descripcionError);
//                
//     
//       try {
//         
//               historicoAjusteServicio.adicionar(regHistoricoAjustes);
//               
//             
//      } catch (IllegalArgumentException ex) {
//          historicoAjusteServicio.getConfigApp().loggerApp.info("Error al grabar los datos en HistoricoAjustes para codigoCajero " + codigoCajero + " " + ex.getMessage());
//     
//        } catch (Exception ex)
//        {
//         java.util.logging.Logger.getLogger("globalApp").info("Error cargando en HistoricoAjustes registro datos codigoCajero  :" + codigoCajero + " descripcion Error : " + ex.getMessage());           
//        }
//         
//      
//             
//    }

  public void guardarHistoricoAjustes(String usuario, Integer codigoCajero, Integer codigoOcca, String tipoAjuste,Date fecha, BigDecimal valor,String talon, String error, String descripcionError)  throws  EntityServicioExcepcion, IllegalArgumentException {

        
      
        Integer codigoErrorProceso=0;
         //OJO DESCOMENTAREAR PARA COMBO CAJERO
    //   Cajero cajero = cajeroServicio.buscar(codigocajero);
      
      
     
        
        HistoricoAjustes regHistoricoAjustes=new HistoricoAjustes();
      //OJO DESCOMENTAREAR PARA COMBO CAJEROS
//        if (cajero == null)
//        {
//      
//        codigoErrorProceso=CodigoError.CAJERO_NO_EXITE.getCodigo();
//        }
       
       // String usuario, Integer codigoCajero, Integer codigoOcca, String tipoAjuste, Date fecha, BigDecimal valor,String talon, String error, String descripcionError
        regHistoricoAjustes.setCodigoCajero(codigoCajero);
        regHistoricoAjustes.setCodigoOcca(codigoOcca);
        regHistoricoAjustes.setFecha(fecha); 
        regHistoricoAjustes.setError(error);
        regHistoricoAjustes.setTipoAjuste(tipoAjuste);
         regHistoricoAjustes.setUsuario(usuario);
        regHistoricoAjustes.setValor(valor);
        regHistoricoAjustes.setTalon(talon);
        regHistoricoAjustes.setDescripcionError(descripcionError);
                
     
       try {
         
               historicoAjusteServicio.adicionar(regHistoricoAjustes);
               
             
      } catch (IllegalArgumentException ex) {
          historicoAjusteServicio.getConfigApp().loggerApp.info("Error al grabar los datos en HistoricoAjustes para codigoCajero " + codigoCajero + " " + ex.getMessage());
     
        } catch (Exception ex)
        {
         java.util.logging.Logger.getLogger("globalApp").info("Error cargando en HistoricoAjustes registro datos codigoCajero  :" + codigoCajero + " descripcion Error : " + ex.getMessage());           
        }
         
      
             
    }

}
