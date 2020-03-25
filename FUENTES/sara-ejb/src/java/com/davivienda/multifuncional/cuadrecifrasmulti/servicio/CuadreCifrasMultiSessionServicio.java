package com.davivienda.multifuncional.cuadrecifrasmulti.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Movimientocuadremulti;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;


/**
 * DiarioElectronicoServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class CuadreCifrasMultiSessionServicio extends BaseEntityServicio<Movimientocuadremulti> implements AdministracionTablasInterface<Movimientocuadremulti> {

    
    public CuadreCifrasMultiSessionServicio(EntityManager em) {
        super(em, Movimientocuadremulti.class);
    }
    
   
     
    public Movimientocuadremulti grabarMovimientoCuadre(Movimientocuadremulti movimientoCuadre,boolean actualizar) throws EntityServicioExcepcion {
        Movimientocuadremulti mc = null;
        if (actualizar) {
            mc = buscar(movimientoCuadre.getIdmovimiento());
            
            mc.setCantbilletesrecibidos(movimientoCuadre.getCantbilletesrecibidos());
            mc.setCantbilletesretract(movimientoCuadre.getCantbilletesretract());
            
            mc.setCantidadbilletesdena(movimientoCuadre.getCantidadbilletesdena());
            mc.setCantidadbilletesdenb(movimientoCuadre.getCantidadbilletesdenb());
            mc.setCantidadbilletesdenc(movimientoCuadre.getCantidadbilletesdenc());
            mc.setCantidadbilletesdend(movimientoCuadre.getCantidadbilletesdend());
            mc.setCantidadbilletesdene(movimientoCuadre.getCantidadbilletesdene());
            mc.setCantidadbilletesdenf(movimientoCuadre.getCantidadbilletesdenf());
            mc.setCantidadbilletesdeng(movimientoCuadre.getCantidadbilletesdeng());
            
            mc.setValormovimiento(movimientoCuadre.getValormovimiento());
            mc.setFechaajsute(movimientoCuadre.getFechaajsute());
            
            mc.setIdusuario(movimientoCuadre.getIdusuario());
        } else {
            try {
                
            
                    //movimientoCuadre.setTipoMovimientoCuadre(em.merge(movimientoCuadre.getTipoMovimientoCuadre()));
                   // movimientoCuadre.setCodtipomovimientocuadremuti(em.merge(movimientoCuadre.getIdmovimiento()));
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
    
     
    public Collection<Movimientocuadremulti> getUsuarioFecha(String usuario, Date fechaInicial) throws EntityServicioExcepcion {
       
        Date fInicialCiclo = com.davivienda.utilidades.conversion.Fecha.getFechaInicioDia(com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaInicial)).getTime();
        Date fFinalCiclo = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
        Collection<Movimientocuadremulti> items = null;
        try {
            Query query = null;
           
                query = em.createNamedQuery("Movimientocuadremulti.UsuarioRangoFecha");
                                         
                query.setParameter("idusuario", usuario);
                query.setParameter("fechaInicial", fInicialCiclo);
                query.setParameter("fechaFinal", fFinalCiclo);
                items = query.getResultList();
           
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return items;
    }
    
    
}
