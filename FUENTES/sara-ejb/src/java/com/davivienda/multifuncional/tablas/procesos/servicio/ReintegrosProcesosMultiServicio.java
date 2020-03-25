package com.davivienda.multifuncional.tablas.procesos.servicio;


import com.davivienda.multifuncional.tablas.notasdebito.servicio.NotasDebitoMultiServicio;
import com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.servicio.ReintegrosMultiEfectivoServicio;
import com.davivienda.sara.base.BaseEstadisticaServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.tablas.transaccion.servicio.TransaccionServicio;
import com.davivienda.sara.tablas.hostatm.servicio.HostAtmServicio;

import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.Cadena;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import com.davivienda.sara.entitys.NotasdebitomultifuncionalPK;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import com.davivienda.sara.entitys.ReintegrosmultiefectivoPK;

/**
 * ReintegrosProcesosMultiServicio
 * Descripción : Estadísticas de las transacciones generadas por los cajeros
 * Versión : 1.0 
 *
 * @author mdruiz
 * Davivienda 2011
 */
public class ReintegrosProcesosMultiServicio extends BaseEstadisticaServicio {

    TransaccionServicio transaccionServicio;
    private CajeroServicio cajeroServicio;
    HostAtmServicio hostAtmServicio;
    ReintegrosMultiEfectivoServicio reintegrosMultiEfectivoServicio;
    NotasDebitoMultiServicio notasDebitoMultiServicio;
    //variable que maneja el rango de diferencia minima para reinetgos
    //OJO VALIDAR SI ESTE RANGO SE GUARDA EN BASE  DE DATOS
    private long valorMinimoReintegros= 9000; 
    private long valorMinimoReintegrosNegativo= -9000; 
    List<String> binesBanagrario = new ArrayList<String>();

    public ReintegrosProcesosMultiServicio(EntityManager em) {
        super(em);
        transaccionServicio = new TransaccionServicio(em);
         cajeroServicio = new CajeroServicio(em);
         hostAtmServicio= new HostAtmServicio(em);
         reintegrosMultiEfectivoServicio=new ReintegrosMultiEfectivoServicio(em);
         notasDebitoMultiServicio=new NotasDebitoMultiServicio(em);
    }

  public void guardarReintegro(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario, String talon,Integer codigoOficinaMulti) throws EntityServicioExcepcion
 {
 
     Reintegrosmultiefectivo newReintegro= new Reintegrosmultiefectivo();
     ReintegrosmultiefectivoPK newReintegroPK= new ReintegrosmultiefectivoPK();
  try 
  {
      Cajero cajero = cajeroServicio.buscar(codigoCajero);
      Date fecha=Fecha.getDateHoy();
      newReintegroPK.setHCodigocajero(codigoCajero);
      newReintegroPK.setHFechasistema(fecha);
      newReintegroPK.setHTalon(Cadena.aInteger(talon));
      newReintegro.setReintegrosmultiefectivoPK(newReintegroPK);
      
      newReintegro.setHDatostarjeta(cuenta);
      newReintegro.setHFecha(fecha);
      newReintegro.setHFiller("000000");
      newReintegro.setHIndices("00");
      newReintegro.setHNumerocuenta(cuenta);
      newReintegro.setHTipotarjeta("A");
      newReintegro.setHTipotransaccion(46);
      newReintegro.setHValor(valor);
      newReintegro.setTCodigocajero(codigoCajero);
      newReintegro.setTFechacajero(fecha);
      newReintegro.setTSecuencia(new Long("0"));
      newReintegro.setTCodigotransaccion(new Integer(talon));
      newReintegro.setTNumerodecuentaconsignar(cuenta);
      newReintegro.setTValorconsignacion(valor);
      newReintegro.setTTipocuenta(tipoCuenta);
      newReintegro.setEstadoreintegro(EstadoReintegro.INICIADO.getEstado());
      newReintegro.setValorajustado(valor);
      newReintegro.setHCodigooficina(codigoOficinaMulti);
      newReintegro.setTipocuentareintegro(tipoCuenta);
      
      
      
      reintegrosMultiEfectivoServicio.adicionar(newReintegro);


  } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
  } catch (IllegalArgumentException ex) {
   configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
  } 
 
 }
  
 public  void actualizar(Reintegrosmultiefectivo objetoModificado) throws  EntityServicioExcepcion{
    
              
            reintegrosMultiEfectivoServicio.actualizar(objetoModificado);
        
    }
    
     
  public  void actualizarNotaDebito(Notasdebitomultifuncional objetoModificado) throws  EntityServicioExcepcion{
    
              
            notasDebitoMultiServicio.actualizar(objetoModificado);
        
    }
       
  public void guardarNotaDebito(Integer codigoCajero, Long valor,String cuenta,Integer tipoCuenta,String usuario) throws EntityServicioExcepcion
 {

     Notasdebitomultifuncional newNotaDebito= new Notasdebitomultifuncional();
     NotasdebitomultifuncionalPK newNotaDebitoPK= new NotasdebitomultifuncionalPK();
  try 
  {
      Cajero cajero = cajeroServicio.buscar(codigoCajero);
      Date fecha=Fecha.getDateHoy();
      newNotaDebitoPK.setCodigocajero(codigoCajero);
      newNotaDebitoPK.setFecha(fecha);
      
      newNotaDebito.setNotasdebitomultifuncionalPK(newNotaDebitoPK);
      newNotaDebito.setUsuariocrea(usuario);
      
      newNotaDebito.setCodigooficina(cajero.getOficinaMulti().getCodigooficinamulti());
      newNotaDebito.setNumerocuenta(cuenta);
      newNotaDebito.setValor(valor);
      newNotaDebito.setValorajustado(valor);
      newNotaDebito.setEstado(EstadoReintegro.INICIADO.getEstado());
      newNotaDebito.setTipocuenta(tipoCuenta);
       
      
     notasDebitoMultiServicio.adicionar(newNotaDebito);


  } catch (IllegalStateException ex) {
           configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
  } catch (IllegalArgumentException ex) {
    configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
  } 
 
 }
  
}
