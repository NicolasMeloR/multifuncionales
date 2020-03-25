/*
 * ProcesoCuadreCifrasSessionLocal.java
 *
 * Created on 25/09/2007, 09:32:24 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.davivienda.multifuncional.tablas.procesos.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;

import javax.ejb.Local;

/**
 *
 * @author mdruiz
 */
@Local
public interface ProcesoReintegrosMultiSessionLocal {
    
     public  void  actualizarReintegro(Reintegrosmultiefectivo objetoModificado) throws  EntityServicioExcepcion;
 
     public void guardarReintegro(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta,
                                                   String usuario, String talon,Integer codigoOficinaMulti ) throws EntityServicioExcepcion;
    
     
      public  void actualizarNotaDebito(Notasdebitomultifuncional objetoModificado) throws  EntityServicioExcepcion;
    
      public void guardarNotaDebito(Integer codigoCajero, Long valor,String cuenta,
                                              Integer tipoCuenta,String usuario) throws EntityServicioExcepcion;

}
