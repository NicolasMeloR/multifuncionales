package com.davivienda.sara.tablas.cajero.servicio;

import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.Occa;
import com.davivienda.sara.entitys.TipoCajero;
import com.davivienda.sara.entitys.Transportadora;
import com.davivienda.sara.entitys.Ubicacion;
import com.davivienda.sara.tablas.occa.servicio.OccaServicio;
import com.davivienda.sara.tablas.tipocajero.servicio.TipoCajeroServicio;
import com.davivienda.sara.tablas.transportadora.servicio.TransportadoraServicio;
import com.davivienda.sara.tablas.ubicacion.servicio.UbicacionServicio;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;

/**
 * CajeroServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class CajeroServicio extends BaseEntityServicio<Cajero> {

    OccaServicio occaServicio;
    TipoCajeroServicio tipoCajeroServicio;
    UbicacionServicio ubicacionServicio;
    TransportadoraServicio transportadoraServicio;

    public CajeroServicio(EntityManager em) {
        super(em, Cajero.class);
        occaServicio = new OccaServicio(em);
        tipoCajeroServicio = new TipoCajeroServicio(em);
        ubicacionServicio = new UbicacionServicio(em);
        transportadoraServicio = new TransportadoraServicio(em);
    }

    @Override
    public Cajero actualizar(Cajero objeto) throws EntityServicioExcepcion {
        Cajero objetoActual = super.buscar(objeto.getCodigoCajero());
        String accion = (objetoActual == null) ? "NUEVO" : "ACTUALIZAR";
       
        if (accion.equals("NUEVO")) {
            // Es nuevo y debo asociar las relaciones
             
            Occa occa = occaServicio.buscar(objeto.getOcca().getCodigoOcca());
            if (occa == null) {
                throw new EntityServicioExcepcion("Debe asignar el código de la occa");
            }
            TipoCajero tipoCajero = tipoCajeroServicio.buscar(objeto.getTipoCajero().getCodigoTipoCajero());
            if (tipoCajero == null) {
                throw new EntityServicioExcepcion("Debe asignar el código del Tipo de Cajero");
            }
            Ubicacion ubicacion = ubicacionServicio.buscar(objeto.getUbicacion().getCodigoUbicacion());
            if (ubicacion == null) {
                throw new EntityServicioExcepcion("Debe asignar el código de la Ubicacion");
            }
            Transportadora transportadora = transportadoraServicio.buscar(objeto.getTransportadora().getIdTransportadora());
            if (transportadora == null) {
                throw new EntityServicioExcepcion("Debe asignar el código de la Transportadora");
            }           
           
            objeto.setUbicacion(ubicacion);
            objeto.setTipoCajero(tipoCajero);
            objeto.setOcca(occa);
            objeto.setTransportadora(transportadora);
            super.adicionar(objeto);
            objetoActual = super.buscar(objeto.getCodigoCajero());
        } else {
             
          Occa occa = occaServicio.buscar(objeto.getOcca().getCodigoOcca());
            if (occa == null) {
                throw new EntityServicioExcepcion("código de Occa no Valido");
            }
            TipoCajero tipoCajero = tipoCajeroServicio.buscar(objeto.getTipoCajero().getCodigoTipoCajero());
            if (tipoCajero == null) {
                throw new EntityServicioExcepcion("código del Tipo de Cajero no Valido");
            }
            Ubicacion ubicacion = ubicacionServicio.buscar(objeto.getUbicacion().getCodigoUbicacion());
            if (ubicacion == null) {
                throw new EntityServicioExcepcion("código de la Ubicacion no Valido");
            }
            Transportadora transportadora = transportadoraServicio.buscar(objeto.getTransportadora().getIdTransportadora());
            if (transportadora == null) {
                throw new EntityServicioExcepcion("código de la Transportadora no Valido");
            }           
            // Se actualizan solo datos
            objetoActual = objetoActual.actualizarEntity(objeto);
           
            super.actualizar(objetoActual);
        }
        return objetoActual;
    }

    @Override
    public void borrar(Cajero entity) throws EntityServicioExcepcion {
        Cajero confAccesoAplicacion = super.buscar(entity.getCodigoCajero());
        super.borrar(confAccesoAplicacion);
        confAccesoAplicacion = super.buscar(entity.getCodigoCajero());
    }
    
     public Collection<Cajero> getCajerosSnTransmitir(Integer ciclo)throws EntityServicioExcepcion
    {
   
        Collection<Cajero> items = null;
          try {
                Query query = null;
                query = em.createNamedQuery("Cajero.NoTransmitidos");
                query.setParameter("ciclo", ciclo);
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
     
    public Collection<Cajero> getCajerosMultiSnTransmitir(Integer ciclo)throws EntityServicioExcepcion
    {
   
        Collection<Cajero> items = null;
          try {
                Query query = null;
                query = em.createNamedQuery("Cajero.MultiNoTrans");
                query.setParameter("ciclo", ciclo);
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
        public Collection<Cajero> getCajerosActivos()throws EntityServicioExcepcion
    {
   
        Collection<Cajero> items = null;
          try {
                Query query = null;
                query = em.createNamedQuery("Cajero.AllActivos");
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
        
   public Collection<Cajero> getCajerosActivosMulti()
    {

        Collection<Cajero> items = null;
          try {
                Query query = null;
                query = em.createNamedQuery("Cajero.AllActivMulti");
                items = query.getResultList();

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
         
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
           
        }
        return items;

    }
}
