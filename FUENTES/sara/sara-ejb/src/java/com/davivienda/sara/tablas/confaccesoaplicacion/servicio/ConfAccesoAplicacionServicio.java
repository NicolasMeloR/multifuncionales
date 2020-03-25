// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.confaccesoaplicacion.servicio;

import javax.persistence.Query;
import java.util.Collection;
import javax.persistence.TransactionRequiredException;
import java.util.logging.Level;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import com.davivienda.sara.entitys.seguridad.ServicioAplicacion;
import javax.persistence.EntityManager;
import com.davivienda.sara.tablas.servicioaplicacion.servicio.ServicioAplicacionServicio;
import com.davivienda.sara.tablas.usuarioaplicacion.servicio.UsuarioAplicacionServicio;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.base.BaseEntityServicio;

public class ConfAccesoAplicacionServicio extends BaseEntityServicio<ConfAccesoAplicacion> implements AdministracionTablasInterface<ConfAccesoAplicacion>
{
    UsuarioAplicacionServicio usuarioAplicacionServicio;
    ServicioAplicacionServicio servicioAplicacionServicio;
    
    public ConfAccesoAplicacionServicio(final EntityManager em) {
        super(em, ConfAccesoAplicacion.class);
        this.usuarioAplicacionServicio = new UsuarioAplicacionServicio(em);
        this.servicioAplicacionServicio = new ServicioAplicacionServicio(em);
    }
    
    @Override
    public ConfAccesoAplicacion actualizar(final ConfAccesoAplicacion objeto) throws EntityServicioExcepcion {
        ConfAccesoAplicacion objetoActual = super.buscar(objeto.getConfAccesoAplicacionPK());
        final String accion = (objetoActual == null) ? "NUEVO" : "ACTUALIZAR";
        if (accion.equals("NUEVO")) {
            final ServicioAplicacion sa = this.servicioAplicacionServicio.buscar(objeto.getConfAccesoAplicacionPK().getServicio());
            objeto.setServicioAplicacion(sa);
            final UsuarioAplicacion ua = this.usuarioAplicacionServicio.buscar(objeto.getConfAccesoAplicacionPK().getUsuario());
            objeto.setUsuarioAplicacion(ua);
            super.adicionar(objeto);
            objetoActual = super.buscar(objeto.getConfAccesoAplicacionPK());
        }
        else {
            objetoActual = objetoActual.actualizarEntity(objeto);
            super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    @Override
    public void borrar(final ConfAccesoAplicacion entity) throws EntityServicioExcepcion {
        ConfAccesoAplicacion confAccesoAplicacion = super.buscar(entity.getConfAccesoAplicacionPK());
        super.borrar(confAccesoAplicacion);
        confAccesoAplicacion = super.buscar(entity.getConfAccesoAplicacionPK());
    }
    
    public int borrarPorUsuario(final String usuario) throws EntityServicioExcepcion {
        int respuesta = 0;
        Collection<ConfAccesoAplicacion> items = null;
        final String strQuery = "DELETE  FROM ConfAccesoAplicacion s    where s.confAccesoAplicacionPK.usuario = :usuario ";
        try {
            Query query = null;
            query = this.em.createNamedQuery("ConfAccesoAplicacion.findByUsuario");
            query.setParameter("usuario", (Object)usuario);
            items = (Collection<ConfAccesoAplicacion>)query.getResultList();
            respuesta = items.size();
            if (respuesta > 0) {
                query = null;
                query = this.em.createQuery(strQuery);
                query.setParameter("usuario", (Object)usuario);
                respuesta = query.executeUpdate();
            }
        }
        catch (IllegalStateException ex) {
            ConfAccesoAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (TransactionRequiredException ex2) {
            ConfAccesoAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "no es una transaccion ", (Throwable)ex2);
            throw new EntityServicioExcepcion((Throwable)ex2);
        }
        return respuesta;
    }
    
    public int getNumElementosPorUsuario(final String usuario) throws EntityServicioExcepcion {
        int respuesta = 0;
        Collection<ConfAccesoAplicacion> items = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("ConfAccesoAplicacion.findByUsuario");
            query.setParameter("usuario", (Object)usuario);
            items = (Collection<ConfAccesoAplicacion>)query.getResultList();
            respuesta = items.size();
        }
        catch (IllegalStateException ex) {
            ConfAccesoAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ConfAccesoAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return respuesta;
    }
    
    public Collection<ConfAccesoAplicacion> getElementosPorUsuario(final String usuario) throws EntityServicioExcepcion {
        Collection<ConfAccesoAplicacion> items = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("ConfAccesoAplicacion.findByUsuario");
            query.setParameter("usuario", (Object)usuario);
            items = (Collection<ConfAccesoAplicacion>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            ConfAccesoAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ConfAccesoAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
    
    public void AddBorrarRegAccesoUsuario() throws EntityServicioExcepcion {
        try {
            this.ejecutarSpDesdeJob("ADMINATM.SP_ADDBORRA_ACCESOUSUARIO;");
        }
        catch (IllegalStateException ex) {
            ConfAccesoAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ConfAccesoAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public int borrarPorUsuarioServicio(final String usuario, final Long servicio) throws EntityServicioExcepcion {
        int respuesta = 0;
        final String strQuery = "DELETE FROM ConfAccesoAplicacion c WHERE c.confAccesoAplicacionPK.servicio = :servicio AND c.confAccesoAplicacionPK.usuario = :usuario";
        try {
            Query query = null;
            query = this.em.createNamedQuery("ConfAccesoAplicacion.deleteByUsuarioServicio");
            query.setParameter("usuario", (Object)usuario);
            query.setParameter("servicio", (Object)servicio);
            respuesta = query.executeUpdate();
            if (respuesta > 0) {
                query = null;
                query = this.em.createQuery(strQuery);
                query.setParameter("usuario", (Object)usuario);
                query.setParameter("servicio", (Object)servicio);
                respuesta = query.executeUpdate();
            }
        }
        catch (IllegalStateException ex) {
            ConfAccesoAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (TransactionRequiredException ex2) {
            ConfAccesoAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "no es una transaccion ", (Throwable)ex2);
            throw new EntityServicioExcepcion((Throwable)ex2);
        }
        return respuesta;
    }
}
