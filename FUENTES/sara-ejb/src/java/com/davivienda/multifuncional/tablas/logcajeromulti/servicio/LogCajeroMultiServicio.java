package com.davivienda.multifuncional.tablas.logcajeromulti.servicio;


import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;

import com.davivienda.sara.entitys.Logcajeromulti;
import com.davivienda.utilidades.conversion.Fecha;
import java.sql.CallableStatement;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * TxMultifuncionalEfectivoServicio
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-MDRUIZ 
 * Davivienda 2011 
 */
public class LogCajeroMultiServicio extends BaseEntityServicio<Logcajeromulti> implements AdministracionTablasInterface<Logcajeromulti> {


    public LogCajeroMultiServicio(EntityManager em) {
        super(em, Logcajeromulti.class);
    }

    
   

     @SuppressWarnings("unchecked")
     public Collection<Logcajeromulti> getColeccionLogCajeroMulti(Integer codigoCajero, Date fechaInicial) throws EntityServicioExcepcion {
        Collection<Logcajeromulti> regs = null;
        String strQuery = "select object(obj) from Logcajeromulti obj " +
              
                "        where  obj.logcajeromultiPK.fechacargue between :fechaInicial and :fechaFinal ";
        String orderQuery = "        order by obj.logcajeromultiPK.secuencia";
        
        try {
           if (codigoCajero != 0) {
                strQuery += "          and obj.logcajeromultiPK.codigocajero =:codigoCajero";
            }
          
           
           
           
            strQuery += orderQuery;
            Date fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getDateHoy();
            Date fFin = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fInicial);
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                //createNamedQuery("Transaccion.CajeroRangoFecha");
                query = em.createQuery(strQuery);
           
                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
                 if (codigoCajero != 0) {
                      query.setParameter("codigoCajero", cCajero);
                }
             
                regs = query.getResultList();
            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return regs;
    }
 
    

  
    
}
