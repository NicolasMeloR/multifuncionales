package com.davivienda.sara.tablas.movimientocuadre.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.MovimientoCuadre;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import java.util.Calendar;
import com.davivienda.utilidades.conversion.Fecha;
import javax.persistence.NoResultException;
import java.math.BigInteger;
import javax.persistence.TemporalType;
import oracle.toplink.essentials.config.HintValues;

/**
 * DiarioElectronicoServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class MovimientoCuadreServicio extends BaseEntityServicio<MovimientoCuadre> implements AdministracionTablasInterface<MovimientoCuadre> {

    
    public MovimientoCuadreServicio(EntityManager em) {
        super(em, MovimientoCuadre.class);
    }
    
        /**
     * Retorna el movimiento de un cajero
     * @param codigoCajero
     * @param fechaInicial
     * @param fechaFinal
     * @return Collection MovimientoCuadre
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    @SuppressWarnings(value = "unchecked")
    public Collection<MovimientoCuadre> getMovimientoCuadreCifras(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.getMovimientoCuadre(codigoCajero, fechaInicial, fechaFinal);
    }
    
        
    public Collection<MovimientoCuadre> getMovimientoCuadre(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
       
         return this.buscar(codigoCajero, fechaInicial, fechaFinal, "MovimientoCuadre.RangoFecha", "MovimientoCuadre.CajeroRangoFecha");
    }
    
      public Collection<MovimientoCuadre> getMovimientoCuadreCifrasMostrar(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
       
        return this.buscar(codigoCajero, fechaInicial, fechaFinal,"MovimientoCuadre.RangoFechaMostrar", "MovimientoCuadre.CajeroRangoFechaMostrar");
        
    }
     public Collection<MovimientoCuadre> getMovimientoCuadreCifrasOccaMostrar(Integer codigoCajero, Integer codigoOcca,Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
       
        return this.buscarOcca(codigoCajero, codigoOcca,fechaInicial, fechaFinal, "MovimientoCuadre.RangoFechaOccaMostrar","MovimientoCuadre.RangoFechaMostrar", "MovimientoCuadre.CajeroRangoFechaMostrar");
        
    }
     
    @SuppressWarnings(value = "unchecked")
    private Collection<MovimientoCuadre> buscarOcca(Integer codigoCajero, Integer codigoOcca,Calendar fechaInicial, Calendar fechaFinal, String nombreQueryOccaRangoFecha, String nombreQueryRangoFecha, String nombreQueryCajeroRangoFecha) throws EntityServicioExcepcion {
        Collection<MovimientoCuadre> regs = null;
        try {
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Integer cOcca = (codigoOcca != null) ? codigoOcca : -1;
            Calendar fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getCalendarHoy();
            Calendar fFinal = (fechaFinal != null) ? fechaFinal : Fecha.getCalendarHoy();
            Query query = null;
            if (cCajero.equals(9999)) {
                 //NUEVO
                if(codigoOcca==-1)
                {
                 query = em.createNamedQuery(nombreQueryRangoFecha);
                }
                else
                {
                 query = em.createNamedQuery(nombreQueryOccaRangoFecha);
                 query.setParameter("codigoOcca", cOcca);   
              
                
                }

            } else {
                query = em.createNamedQuery(nombreQueryCajeroRangoFecha);
                query.setParameter("codigoCajero", cCajero);
            }
           
            query.setParameter("fechaInicial", fInicial.getTime(), TemporalType.DATE).setParameter("fechaFinal", fFinal.getTime(), TemporalType.DATE);
            regs = query.getResultList();
        } catch (IllegalStateException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.WARNING, "El parámetro no es válido ", ex);
        }
        return regs;
    }
    
       @SuppressWarnings(value = "unchecked")
    private Collection<MovimientoCuadre> buscar(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal, String nombreQueryRangoFecha, String nombreQueryCajeroRangoFecha) throws EntityServicioExcepcion {
        Collection<MovimientoCuadre> regs = null;
        try {
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Calendar fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getCalendarHoy();
            Calendar fFinal = (fechaFinal != null) ? fechaFinal : Fecha.getCalendarHoy();
            Query query = null;
            if (cCajero.equals(9999)) {
                query = em.createNamedQuery(nombreQueryRangoFecha);
            } else {
                query = em.createNamedQuery(nombreQueryCajeroRangoFecha);
                query.setParameter("codigoCajero", cCajero);
            }
            query.setParameter("fechaInicial", fInicial.getTime()).setParameter("fechaFinal", fFinal.getTime());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            regs = query.getResultList();
        } catch (IllegalStateException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.WARNING, "El parámetro no es válido ", ex);
        }
        return regs;
    }
    
       
           /**
     * Retorna un objeto MovimientoCuadre que cumpla con los datos dados
     * Si se encuentra más de uno retorna una excepción
     * @param codigoCajero 
     * @param fecha 
     * @param codigoTipoMovimiento 
     * @return MovimientoCuadre
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion 
     */
    public MovimientoCuadre getMovimientoCuadre(Integer codigoCajero, Calendar fecha, Integer codigoTipoMovimiento) throws EntityServicioExcepcion {
        MovimientoCuadre mc = null;
        try {
            Query query = null;
            query = em.createNamedQuery("MovimientoCuadre.RegistroUnico");
            query.setParameter("codigoCajero", codigoCajero);            
            query.setParameter("fecha", fecha.getTime());
            query.setParameter("codigoTipoMovimientoCuadre", codigoTipoMovimiento);
            mc = (MovimientoCuadre) query.getSingleResult();
        } catch (IllegalStateException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.WARNING, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex); 
        } catch (NoResultException ex) {
            // No existe se creará
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "No se obtiene una instancia ", ex);
            throw new EntityServicioExcepcion(ex);            
        }
        return mc;
    }
    
      
    /**
     * Graba el movimiento y lo adiciona al contenedor de persistencia
     * realiza la busqueda por el id del registro
     * @param movimientoCuadre
     * @param actualizar
     * @return MoviminetoCaudre manejado por la persistencia
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public MovimientoCuadre grabarMovimientoCuadre(MovimientoCuadre movimientoCuadre,boolean actualizar) throws EntityServicioExcepcion {
        MovimientoCuadre mc = null;
        if (actualizar) {
            mc = buscar(movimientoCuadre.getIdMovimientoCuadre());
            mc.setBilletes1(movimientoCuadre.getBilletes1());
            mc.setBilletes2(movimientoCuadre.getBilletes2());
            mc.setBilletes3(movimientoCuadre.getBilletes3());
            mc.setBilletes4(movimientoCuadre.getBilletes4());
            mc.setDenominacion1(movimientoCuadre.getDenominacion1());
            mc.setDenominacion2(movimientoCuadre.getDenominacion2());
            mc.setDenominacion3(movimientoCuadre.getDenominacion3());
            mc.setDenominacion4(movimientoCuadre.getDenominacion4());
            mc.setValorMovimiento(movimientoCuadre.getValorMovimiento());
            mc.setFechaAjuste(movimientoCuadre.getFechaAjuste());
            mc.setIdUsuario(movimientoCuadre.getIdUsuario());
        } else {
            try {
                
            
                    movimientoCuadre.setTipoMovimientoCuadre(em.merge(movimientoCuadre.getTipoMovimientoCuadre()));
                    movimientoCuadre.setCajero(em.merge(movimientoCuadre.getCajero()));
                    em.persist(movimientoCuadre);
                    mc = movimientoCuadre;
                
            } catch (IllegalStateException ex) {
                java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
                throw new EntityServicioExcepcion(ex);
            } catch (IllegalArgumentException ex) {
                java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
                throw new EntityServicioExcepcion(ex);
            } catch (TransactionRequiredException ex) {
                java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "Se requiere un entorno de transacción ", ex);
                throw new EntityServicioExcepcion(ex);
            }
        }
        return mc;
    }
    
        /**
     * Graba una nota de observación en el movimiento
     * @param idRegistro
     * @param observacion
     * @param idUsuario
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public void grabarNotaMovimiento(BigInteger idRegistro, String observacion, String idUsuario) throws EntityServicioExcepcion {
        try {
            MovimientoCuadre mc = super.buscar(idRegistro);
            if (mc.getObservacion() != null)
            {
               // mc.setObservacion( mc.getObservacion().toString() +"\n"  +"=== " + idUsuario + " " + Fecha.aCadena(Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.DEFECTO_SEPARADOR_GUION.formato) + " ->" + observacion.trim() + "=== ");
                 mc.setObservacion(  mc.getObservacion().toString() +"\n"  +" "   + observacion.trim() + "  ");
            
            }
            else
            {
               // mc.setObservacion( "=== " + idUsuario + " " + Fecha.aCadena(Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.DEFECTO_SEPARADOR_GUION.formato) + " ->" + observacion.trim() + "=== ");
                 mc.setObservacion(  " "   + observacion.trim() + "  ");
            }
            //mc.setObservacion((mc.getObservacion() != null)? mc.getObservacion():""  + "=== " + idUsuario + " " + Fecha.aCadena(Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.DEFECTO_SEPARADOR_GUION.formato) + " ->" + observacion.trim() + "=== ");
            mc.setIdUsuarioObservacion(idUsuario);
            
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "ha ocurrido un error mientras se actualiza la nota " + observacion, ex);
            throw new EntityServicioExcepcion(ex.getMessage());
        }
    }
    
    /**
     * Consulta la nota del movimiento
     * @param idRegistro
     * @return MovimientoCuadre
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public MovimientoCuadre consultarNotaMovimiento(BigInteger idRegistro) throws EntityServicioExcepcion {
        MovimientoCuadre mc = null;
        try {
            mc = super.buscar(idRegistro);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "ha ocurrido un error mientras se actualiza la nota ", ex);
            throw new EntityServicioExcepcion(ex.getMessage());
        }
        return mc;
    }
    
   
    /**
     * Retorna todos los registros de movimiento de Ajustes de un cajero
     * @param codigoCajero
     * @param fechaInicial
     * @param fechaFinal
     * @return Collection MovimientoCuadre
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    
    public Collection<MovimientoCuadre> getMovimientoAjustes(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.buscar(codigoCajero, fechaInicial, fechaFinal, "MovimientoCuadre.RangoFechaAjustes", "MovimientoCuadre.CajeroRangoFechaAjustes");
    }
    
    

       /**
     * Retorna todos los registros de movimiento de línea de un cajero
     * @param codigoCajero
     * @param fechaInicial
     * @param fechaFinal
     * @return Collection MovimientoCuadre
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
   
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasDatosLinea(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.buscar(codigoCajero, fechaInicial, fechaFinal, "MovimientoCuadre.RangoFechaLinea", "MovimientoCuadre.CajeroRangoFechaLinea");
    }
    
        
    /**
     * Retorna todos los registros de movimiento de contadores de un cajero
     * @param codigoCajero
     * @param fechaInicial
     * @param fechaFinal
     * @return Collection MovimientoCuadre
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
  
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasContadores(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.buscar(codigoCajero, fechaInicial, fechaFinal, "MovimientoCuadre.RangoFechaContadores", "MovimientoCuadre.CajeroRangoFechaContadores");
    }
    
        /**
     * Retorna los movimiento de provisiones timbrados en línea
     * @param codigoCajero
     * @param fechaInicial
     * @param fechaFinal
     * @return
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */

    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasProvision(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.buscar(codigoCajero, fechaInicial, fechaFinal, "MovimientoCuadre.RangoFechaProvision", "MovimientoCuadre.CajeroRangoFechaProvision");
    }

}
