package com.davivienda.multifuncional.tablas.notasdebitomulti.session;

import com.davivienda.sara.tablas.notasdebito.session.*;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

/**
 * NotasDebitoMultiSessionLocal 
 * Descripción : 
 * Versión : 1.0 
 *
 * @author mdruiz
 * Davivienda 2011 
 */
@Local
public interface NotasDebitoMultiSessionLocal extends AdministracionTablasInterface<Notasdebitomultifuncional> {

    /**
     * Retorna los Notasdebitomultifuncional de la fecha dada
        * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
   /**
     * Retorna los NotasDebito de la fecha dada
        * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
   public Collection<Notasdebitomultifuncional> getNotasDebito(Date fechaInicial ,Date fechaFinal,Integer codigCajero, Date fechaHisto) throws EntityServicioExcepcion ;
   
   
   public Notasdebitomultifuncional getNotasDebitoXLlave(Integer codigoCajero, Date fechaProceso, Date fechaHisto) throws EntityServicioExcepcion ;

   
   public Notasdebitomultifuncional getNotasDebitoXCuentaValor(Integer codigoCajero, Date fechaProceso, String numeroCuenta,Long valor, Date fechaHisto) throws EntityServicioExcepcion;
    
    

     
}