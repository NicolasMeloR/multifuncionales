package com.davivienda.sara.tablas.hostatm.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.HostAtm;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * DiarioElectronicoServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class HostAtmServicio extends BaseEntityServicio<HostAtm> implements AdministracionTablasInterface<HostAtm> {

    
    public HostAtmServicio(EntityManager em) {
        super(em, HostAtm.class);
    }
    
//
//    /**
//     * Retorna el DiarioElectronico del cajero y fecha dado
//     * @param codigoCajero
//     * @param fechaInicial
//     * @return
//     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
//     */
//    @SuppressWarnings("unchecked")
//    public Collection<DiarioElectronico> getDiarioElectronico(Integer codigoCajero, Date fechaInicial) throws EntityServicioExcepcion {
//        Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
//        Date fFinalCiclo = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
//        Collection<DiarioElectronico> items = null;
//        try {
//            Query query = null;
//            if (!cCajero.equals(9999)) {
//                query = em.createNamedQuery("DiarioElectronico.CajeroRangoFecha");
//                query.setParameter("codigoCajero", cCajero);
//                query.setParameter("fechaInicial", fechaInicial);
//                query.setParameter("fechaFinal", fFinalCiclo);
//                items = query.getResultList();
//            } else {
//                throw new EntityServicioExcepcion("Debe seleccionar un solo cajero");
//            }
//        } catch (IllegalStateException ex) {
//            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
//            throw new EntityServicioExcepcion(ex);
//        } catch (IllegalArgumentException ex) {
//            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
//            throw new EntityServicioExcepcion(ex);
//        }
//        return items;
//    }
    
        public HostAtm  getReintegrosHost(Integer codigoCajero, Date fechaInicial ,Date fechaFinal,Integer talon) throws EntityServicioExcepcion {
        //Collection<HostAtm>  regs = null ;
        List result=new ArrayList<Integer>();
        HostAtm   reg = null ;
        try {
            Date fInicial = (fechaInicial != null) ? fechaInicial : com.davivienda.utilidades.conversion.Fecha.getDateHoy();
            Date fFin = fechaFinal;
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
           Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery("HostAtm.CajeroTxFecha");
                query.setParameter("codigocajero", cCajero);
                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
                query.setParameter("talon", talon);
                
                //OJO SI DAN MAS REVERSOS HABILITAR OPCION
               // query.setParameter("valor", valor);
                result =query.getResultList();
                
             result.size();   
             if(result!=null)
            {
              if(result.size() > 0)
                 reg=(HostAtm) result.get(0);
            }
         }        
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return reg;
    }
        
        public List getTalonesHost(Integer codigoCajero, Date fechaInicial ,Date fechaFinal) throws EntityServicioExcepcion {
        //Collection<HostAtm>  regs = null ;
        List result=new ArrayList<Integer>();
        HostAtm   reg = null ;
        try {
            Date fInicial = (fechaInicial != null) ? fechaInicial : com.davivienda.utilidades.conversion.Fecha.getDateHoy();
            Date fFin = fechaFinal;
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
           Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery("HostAtm.TalonXFecha");
                query.setParameter("codigocajero", cCajero);
                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
               // query.setParameter("valor", valor);
                result =query.getResultList();
                
//             result.size();   
//             if(result!=null)
//            {
//              if(result.size() > 0)
//                 reg=(HostAtm) result.get(0);
//            }
         }        
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return result;
    }
    

}
