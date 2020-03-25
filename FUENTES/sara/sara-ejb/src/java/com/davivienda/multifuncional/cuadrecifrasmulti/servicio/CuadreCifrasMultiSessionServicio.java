// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.cuadrecifrasmulti.servicio;

import javax.persistence.Query;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Collection;
import java.util.Date;
import javax.persistence.TransactionRequiredException;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.davivienda.sara.entitys.Cajero;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Movimientocuadremulti;
import com.davivienda.sara.base.BaseEntityServicio;

public class CuadreCifrasMultiSessionServicio extends BaseEntityServicio<Movimientocuadremulti> implements AdministracionTablasInterface<Movimientocuadremulti>
{
    public CuadreCifrasMultiSessionServicio(final EntityManager em) {
        super(em, Movimientocuadremulti.class);
    }
    
    public Movimientocuadremulti grabarMovimientoCuadre(final Movimientocuadremulti movimientoCuadre, final boolean actualizar) throws EntityServicioExcepcion {
        Movimientocuadremulti mc = null;
        if (actualizar) {
            mc = this.buscar(movimientoCuadre.getIdmovimiento());
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
        }
        else {
            try {
                movimientoCuadre.setCajero((Cajero)this.em.merge((Object)movimientoCuadre.getCajero()));
                this.em.persist((Object)movimientoCuadre);
                mc = movimientoCuadre;
            }
            catch (IllegalStateException ex) {
                Logger.getLogger("globalApp").log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
                throw new EntityServicioExcepcion(ex);
            }
            catch (IllegalArgumentException ex2) {
                Logger.getLogger("globalApp").log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
                throw new EntityServicioExcepcion(ex2);
            }
            catch (TransactionRequiredException ex3) {
                Logger.getLogger("globalApp").log(Level.SEVERE, "Se requiere un entorno de transacci\u00f3n ", (Throwable)ex3);
                throw new EntityServicioExcepcion((Throwable)ex3);
            }
        }
        return mc;
    }
    
    public Collection<Movimientocuadremulti> getUsuarioFecha(final String usuario, final Date fechaInicial) throws EntityServicioExcepcion {
        final Date fInicialCiclo = Fecha.getFechaInicioDia(Fecha.getCalendar(fechaInicial)).getTime();
        final Date fFinalCiclo = Fecha.getFechaFinDia(fechaInicial);
        Collection<Movimientocuadremulti> items = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("Movimientocuadremulti.UsuarioRangoFecha");
            query.setParameter("idusuario", (Object)usuario);
            query.setParameter("fechaInicial", (Object)fInicialCiclo);
            query.setParameter("fechaFinal", (Object)fFinalCiclo);
            items = (Collection<Movimientocuadremulti>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            CuadreCifrasMultiSessionServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            CuadreCifrasMultiSessionServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
}
