// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.movimientocuadre.servicio;

import java.math.BigInteger;
import javax.persistence.TransactionRequiredException;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TemporalType;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Calendar;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.base.BaseEntityServicio;

public class MovimientoCuadreServicio extends BaseEntityServicio<MovimientoCuadre> implements AdministracionTablasInterface<MovimientoCuadre>
{
    public MovimientoCuadreServicio(final EntityManager em) {
        super(em, MovimientoCuadre.class);
    }
    
    public Collection<MovimientoCuadre> getMovimientoCuadreCifras(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.getMovimientoCuadre(codigoCajero, fechaInicial, fechaFinal);
    }
    
    public Collection<MovimientoCuadre> getMovimientoCuadre(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.buscar(codigoCajero, fechaInicial, fechaFinal, "MovimientoCuadre.RangoFecha", "MovimientoCuadre.CajeroRangoFecha");
    }
    
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasMostrar(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.buscar(codigoCajero, fechaInicial, fechaFinal, "MovimientoCuadre.RangoFechaMostrar", "MovimientoCuadre.CajeroRangoFechaMostrar");
    }
    
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasOccaMostrar(final Integer codigoCajero, final Integer codigoOcca, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.buscarOcca(codigoCajero, codigoOcca, fechaInicial, fechaFinal, "MovimientoCuadre.RangoFechaOccaMostrar", "MovimientoCuadre.RangoFechaMostrar", "MovimientoCuadre.CajeroRangoFechaMostrar");
    }
    
    private Collection<MovimientoCuadre> buscarOcca(final Integer codigoCajero, final Integer codigoOcca, final Calendar fechaInicial, final Calendar fechaFinal, final String nombreQueryOccaRangoFecha, final String nombreQueryRangoFecha, final String nombreQueryCajeroRangoFecha) throws EntityServicioExcepcion {
        Collection<MovimientoCuadre> regs = null;
        try {
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            final Integer cOcca = (codigoOcca != null) ? codigoOcca : -1;
            final Calendar fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getCalendarHoy();
            final Calendar fFinal = (fechaFinal != null) ? fechaFinal : Fecha.getCalendarHoy();
            Query query = null;
            if (cCajero.equals(9999)) {
                if (codigoOcca == -1) {
                    query = this.em.createNamedQuery(nombreQueryRangoFecha);
                }
                else {
                    query = this.em.createNamedQuery(nombreQueryOccaRangoFecha);
                    query.setParameter("codigoOcca", (Object)cOcca);
                }
            }
            else {
                query = this.em.createNamedQuery(nombreQueryCajeroRangoFecha);
                query.setParameter("codigoCajero", (Object)cCajero);
            }
            query.setParameter("fechaInicial", fInicial.getTime(), TemporalType.DATE).setParameter("fechaFinal", fFinal.getTime(), TemporalType.DATE);
            regs = (Collection<MovimientoCuadre>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            Logger.getLogger("globalApp").log(Level.WARNING, "El par\u00e1metro no es v\u00e1lido ", ex2);
        }
        return regs;
    }
    
    private Collection<MovimientoCuadre> buscar(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal, final String nombreQueryRangoFecha, final String nombreQueryCajeroRangoFecha) throws EntityServicioExcepcion {
        Collection<MovimientoCuadre> regs = null;
        try {
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            final Calendar fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getCalendarHoy();
            final Calendar fFinal = (fechaFinal != null) ? fechaFinal : Fecha.getCalendarHoy();
            Query query = null;
            if (cCajero.equals(9999)) {
                query = this.em.createNamedQuery(nombreQueryRangoFecha);
            }
            else {
                query = this.em.createNamedQuery(nombreQueryCajeroRangoFecha);
                query.setParameter("codigoCajero", (Object)cCajero);
            }
            query.setParameter("fechaInicial", (Object)fInicial.getTime()).setParameter("fechaFinal", (Object)fFinal.getTime());
            query.setHint("javax.persistence.cache.storeMode", (Object)"REFRESH");
            regs = (Collection<MovimientoCuadre>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            Logger.getLogger("globalApp").log(Level.WARNING, "El par\u00e1metro no es v\u00e1lido ", ex2);
        }
        return regs;
    }
    
    public MovimientoCuadre getMovimientoCuadre(final Integer codigoCajero, final Calendar fecha, final Integer codigoTipoMovimiento) throws EntityServicioExcepcion {
        MovimientoCuadre mc = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("MovimientoCuadre.RegistroUnico");
            query.setParameter("codigoCajero", (Object)codigoCajero);
            query.setParameter("fecha", (Object)fecha.getTime());
            query.setParameter("codigoTipoMovimientoCuadre", (Object)codigoTipoMovimiento);
            mc = (MovimientoCuadre)query.getSingleResult();
        }
        catch (IllegalStateException ex) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            Logger.getLogger("globalApp").log(Level.WARNING, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        catch (NoResultException ex4) {}
        catch (Exception ex3) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "No se obtiene una instancia ", ex3);
            throw new EntityServicioExcepcion(ex3);
        }
        return mc;
    }
    
    public MovimientoCuadre grabarMovimientoCuadre(final MovimientoCuadre movimientoCuadre, final boolean actualizar) throws EntityServicioExcepcion {
        MovimientoCuadre mc = null;
        if (actualizar) {
            mc = this.buscar(movimientoCuadre.getIdMovimientoCuadre());
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
        }
        else {
            try {
                movimientoCuadre.setTipoMovimientoCuadre((TipoMovimientoCuadre)this.em.merge((Object)movimientoCuadre.getTipoMovimientoCuadre()));
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
    
    public void grabarNotaMovimiento(final BigInteger idRegistro, final String observacion, final String idUsuario) throws EntityServicioExcepcion {
        try {
            final MovimientoCuadre mc = super.buscar(idRegistro);
            if (mc.getObservacion() != null) {
                mc.setObservacion(mc.getObservacion().toString() + "\n" + " " + observacion.trim() + "  ");
            }
            else {
                mc.setObservacion(" " + observacion.trim() + "  ");
            }
            mc.setIdUsuarioObservacion(idUsuario);
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "ha ocurrido un error mientras se actualiza la nota " + observacion, ex);
            throw new EntityServicioExcepcion(ex.getMessage());
        }
    }
    
    public MovimientoCuadre consultarNotaMovimiento(final BigInteger idRegistro) throws EntityServicioExcepcion {
        MovimientoCuadre mc = null;
        try {
            mc = super.buscar(idRegistro);
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "ha ocurrido un error mientras se actualiza la nota ", ex);
            throw new EntityServicioExcepcion(ex.getMessage());
        }
        return mc;
    }
    
    public Collection<MovimientoCuadre> getMovimientoAjustes(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.buscar(codigoCajero, fechaInicial, fechaFinal, "MovimientoCuadre.RangoFechaAjustes", "MovimientoCuadre.CajeroRangoFechaAjustes");
    }
    
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasDatosLinea(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.buscar(codigoCajero, fechaInicial, fechaFinal, "MovimientoCuadre.RangoFechaLinea", "MovimientoCuadre.CajeroRangoFechaLinea");
    }
    
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasContadores(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.buscar(codigoCajero, fechaInicial, fechaFinal, "MovimientoCuadre.RangoFechaContadores", "MovimientoCuadre.CajeroRangoFechaContadores");
    }
    
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasProvision(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.buscar(codigoCajero, fechaInicial, fechaFinal, "MovimientoCuadre.RangoFechaProvision", "MovimientoCuadre.CajeroRangoFechaProvision");
    }
}
