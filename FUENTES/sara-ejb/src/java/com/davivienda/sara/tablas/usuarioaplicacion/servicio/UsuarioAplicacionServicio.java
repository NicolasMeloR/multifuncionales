package com.davivienda.sara.tablas.usuarioaplicacion.servicio;

import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import java.util.Collection;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

/**
 * ControlUsuarioAplicacionServicio - 24/05/2008 Descripción : Clase encargada
 * de todos los procesos de administración de la información de
 * UsuarioAplicacion Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class UsuarioAplicacionServicio extends BaseEntityServicio<UsuarioAplicacion> {

    public UsuarioAplicacionServicio(EntityManager em) {
        super(em, UsuarioAplicacion.class);
    }

    public Collection<ConfAccesoAplicacion> getConfAccesoAplicacion(String idUsuario) {
        UsuarioAplicacion usuario = null;
        Collection<ConfAccesoAplicacion> respuesta = null;
        try {
            usuario = buscar(idUsuario);
            if (idUsuario != null) {
                usuario.getConfAccesoAplicacionCollection().size();
                respuesta = usuario.getConfAccesoAplicacionCollection();

            }
        } catch (EntityServicioExcepcion ex) {
            configApp.loggerApp.log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    @Override
    public UsuarioAplicacion actualizar(UsuarioAplicacion objetoModificado) throws EntityServicioExcepcion {
        UsuarioAplicacion objetoActual = super.buscar(objetoModificado.getUsuario());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getUsuario());
        } else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }
        return objetoActual;
    }

    @Override
    public void borrar(UsuarioAplicacion entity) throws EntityServicioExcepcion {
        UsuarioAplicacion objetoActual = super.buscar(entity.getUsuario());
        super.borrar(objetoActual);
    }

    public int borrarPorUsuario(String usuario) throws EntityServicioExcepcion {
        int respuesta = 0;

        String strQuery = "DELETE FROM UsuarioAplicacion  u WHERE u.usuario = :usuario";

        try {

            //se revisa que  halla registros para el usuario
            Query query = null;

            query = em.createNamedQuery("UsuarioAplicacion.deleteByUsuario");
            query.setParameter("usuario", usuario);
            respuesta = query.executeUpdate();
            //si hay borra
            if (respuesta > 0) {
                query = null;
                query = em.createQuery(strQuery);
                query.setParameter("usuario", usuario);
                respuesta = query.executeUpdate();
            }

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (TransactionRequiredException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "no es una transaccion ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return respuesta;

    }

}
