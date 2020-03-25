// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.cajero.servicio;

import javax.persistence.Query;
import java.util.logging.Level;
import java.util.Collection;
import com.davivienda.sara.entitys.Transportadora;
import com.davivienda.sara.entitys.Ubicacion;
import com.davivienda.sara.entitys.TipoCajero;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Occa;
import javax.persistence.EntityManager;
import com.davivienda.sara.tablas.transportadora.servicio.TransportadoraServicio;
import com.davivienda.sara.tablas.ubicacion.servicio.UbicacionServicio;
import com.davivienda.sara.tablas.tipocajero.servicio.TipoCajeroServicio;
import com.davivienda.sara.tablas.occa.servicio.OccaServicio;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.base.BaseEntityServicio;

public class CajeroServicio extends BaseEntityServicio<Cajero>
{
    OccaServicio occaServicio;
    TipoCajeroServicio tipoCajeroServicio;
    UbicacionServicio ubicacionServicio;
    TransportadoraServicio transportadoraServicio;
    
    public CajeroServicio(final EntityManager em) {
        super(em, Cajero.class);
        this.occaServicio = new OccaServicio(em);
        this.tipoCajeroServicio = new TipoCajeroServicio(em);
        this.ubicacionServicio = new UbicacionServicio(em);
        this.transportadoraServicio = new TransportadoraServicio(em);
    }
    
    @Override
    public Cajero actualizar(final Cajero objeto) throws EntityServicioExcepcion {
        Cajero objetoActual = super.buscar(objeto.getCodigoCajero());
        final String accion = (objetoActual == null) ? "NUEVO" : "ACTUALIZAR";
        if (accion.equals("NUEVO")) {
            final Occa occa = this.occaServicio.buscar(objeto.getOcca().getCodigoOcca());
            if (occa == null) {
                throw new EntityServicioExcepcion("Debe asignar el c\u00f3digo de la occa");
            }
            final TipoCajero tipoCajero = this.tipoCajeroServicio.buscar(objeto.getTipoCajero().getCodigoTipoCajero());
            if (tipoCajero == null) {
                throw new EntityServicioExcepcion("Debe asignar el c\u00f3digo del Tipo de Cajero");
            }
            final Ubicacion ubicacion = this.ubicacionServicio.buscar(objeto.getUbicacion().getCodigoUbicacion());
            if (ubicacion == null) {
                throw new EntityServicioExcepcion("Debe asignar el c\u00f3digo de la Ubicacion");
            }
            final Transportadora transportadora = this.transportadoraServicio.buscar(objeto.getTransportadora().getIdTransportadora());
            if (transportadora == null) {
                throw new EntityServicioExcepcion("Debe asignar el c\u00f3digo de la Transportadora");
            }
            objeto.setUbicacion(ubicacion);
            objeto.setTipoCajero(tipoCajero);
            objeto.setOcca(occa);
            objeto.setTransportadora(transportadora);
            super.adicionar(objeto);
            objetoActual = super.buscar(objeto.getCodigoCajero());
        }
        else {
            final Occa occa = this.occaServicio.buscar(objeto.getOcca().getCodigoOcca());
            if (occa == null) {
                throw new EntityServicioExcepcion("c\u00f3digo de Occa no Valido");
            }
            final TipoCajero tipoCajero = this.tipoCajeroServicio.buscar(objeto.getTipoCajero().getCodigoTipoCajero());
            if (tipoCajero == null) {
                throw new EntityServicioExcepcion("c\u00f3digo del Tipo de Cajero no Valido");
            }
            final Ubicacion ubicacion = this.ubicacionServicio.buscar(objeto.getUbicacion().getCodigoUbicacion());
            if (ubicacion == null) {
                throw new EntityServicioExcepcion("c\u00f3digo de la Ubicacion no Valido");
            }
            final Transportadora transportadora = this.transportadoraServicio.buscar(objeto.getTransportadora().getIdTransportadora());
            if (transportadora == null) {
                throw new EntityServicioExcepcion("c\u00f3digo de la Transportadora no Valido");
            }
            objetoActual = objetoActual.actualizarEntity(objeto);
            super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    @Override
    public void borrar(final Cajero entity) throws EntityServicioExcepcion {
        Cajero confAccesoAplicacion = super.buscar(entity.getCodigoCajero());
        super.borrar(confAccesoAplicacion);
        confAccesoAplicacion = super.buscar(entity.getCodigoCajero());
    }
    
    public Collection<Cajero> getCajerosSnTransmitir(final Integer ciclo) throws EntityServicioExcepcion {
        Collection<Cajero> items = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("Cajero.NoTransmitidos");
            query.setParameter("ciclo", (Object)ciclo);
            items = (Collection<Cajero>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            CajeroServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            CajeroServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
    
    public Collection<Cajero> getCajerosMultiSnTransmitir(final Integer ciclo) throws EntityServicioExcepcion {
        Collection<Cajero> items = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("Cajero.MultiNoTrans");
            query.setParameter("ciclo", (Object)ciclo);
            items = (Collection<Cajero>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            CajeroServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            CajeroServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
    
    public Collection<Cajero> getCajerosActivos() throws EntityServicioExcepcion {
        Collection<Cajero> items = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("Cajero.AllActivos");
            items = (Collection<Cajero>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            CajeroServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            CajeroServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
    
    public Collection<Cajero> getCajerosActivosMulti() {
        Collection<Cajero> items = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("Cajero.AllActivMulti");
            items = (Collection<Cajero>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            CajeroServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
        }
        catch (IllegalArgumentException ex2) {
            CajeroServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
        }
        return items;
    }
}
