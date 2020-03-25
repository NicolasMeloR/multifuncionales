// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.usuarioaplicacion.servicio;

import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import java.util.Collection;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import com.davivienda.sara.base.BaseEntityServicio;

public class UsuarioAplicacionServicio extends BaseEntityServicio<UsuarioAplicacion>
{
    public UsuarioAplicacionServicio(final EntityManager em) {
        super(em, UsuarioAplicacion.class);
    }
    
    public Collection<ConfAccesoAplicacion> getConfAccesoAplicacion(final String idUsuario) {
        UsuarioAplicacion usuario = null;
        Collection<ConfAccesoAplicacion> respuesta = null;
        try {
            usuario = this.buscar(idUsuario);
            if (idUsuario != null) {
                usuario.getConfAccesoAplicacionCollection().size();
                respuesta = (Collection<ConfAccesoAplicacion>)usuario.getConfAccesoAplicacionCollection();
            }
        }
        catch (EntityServicioExcepcion ex) {
            UsuarioAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }
    
    @Override
    public UsuarioAplicacion actualizar(final UsuarioAplicacion objetoModificado) throws EntityServicioExcepcion {
        UsuarioAplicacion objetoActual = super.buscar(objetoModificado.getUsuario());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getUsuario());
        }
        else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    @Override
    public void borrar(final UsuarioAplicacion entity) throws EntityServicioExcepcion {
        final UsuarioAplicacion objetoActual = super.buscar(entity.getUsuario());
        super.borrar(objetoActual);
    }
    
    public int borrarPorUsuario(final String usuario) throws EntityServicioExcepcion {
        int respuesta = 0;
        final String strQuery = "DELETE FROM UsuarioAplicacion  u WHERE u.usuario = :usuario";
        try {
            Query query = null;
            query = this.em.createNamedQuery("UsuarioAplicacion.deleteByUsuario");
            query.setParameter("usuario", (Object)usuario);
            respuesta = query.executeUpdate();
            if (respuesta > 0) {
                query = null;
                query = this.em.createQuery(strQuery);
                query.setParameter("usuario", (Object)usuario);
                respuesta = query.executeUpdate();
            }
        }
        catch (IllegalStateException ex) {
            UsuarioAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (TransactionRequiredException ex2) {
            UsuarioAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "no es una transaccion ", (Throwable)ex2);
            throw new EntityServicioExcepcion((Throwable)ex2);
        }
        return respuesta;
    }
}
