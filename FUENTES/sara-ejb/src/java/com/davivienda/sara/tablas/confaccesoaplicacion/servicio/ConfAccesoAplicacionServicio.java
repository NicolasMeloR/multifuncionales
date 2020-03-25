package com.davivienda.sara.tablas.confaccesoaplicacion.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.entitys.seguridad.ServicioAplicacion;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import com.davivienda.sara.tablas.servicioaplicacion.servicio.ServicioAplicacionServicio;
import com.davivienda.sara.tablas.usuarioaplicacion.servicio.UsuarioAplicacionServicio;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import java.util.Collection;

/**
 * ConfAccesoAplicacionServicio - 7/07/2008 Descripción : Servicio para todos
 * los procesos de administración de la información de ConfAccesoAplicacion
 * Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ConfAccesoAplicacionServicio extends BaseEntityServicio<ConfAccesoAplicacion> implements AdministracionTablasInterface<ConfAccesoAplicacion> {

    UsuarioAplicacionServicio usuarioAplicacionServicio;
    ServicioAplicacionServicio servicioAplicacionServicio;

    public ConfAccesoAplicacionServicio(EntityManager em) {
        super(em, ConfAccesoAplicacion.class);
        usuarioAplicacionServicio = new UsuarioAplicacionServicio(em);
        servicioAplicacionServicio = new ServicioAplicacionServicio(em);
    }

    @Override
    public ConfAccesoAplicacion actualizar(ConfAccesoAplicacion objeto) throws EntityServicioExcepcion {
        ConfAccesoAplicacion objetoActual = super.buscar(objeto.getConfAccesoAplicacionPK());
        String accion = (objetoActual == null) ? "NUEVO" : "ACTUALIZAR";
        if (accion.equals("NUEVO")) {
            // Es nuevo y debo asociar las relaciones
            ServicioAplicacion sa = servicioAplicacionServicio.buscar(objeto.getConfAccesoAplicacionPK().getServicio());
            objeto.setServicioAplicacion(sa);
            UsuarioAplicacion ua = usuarioAplicacionServicio.buscar(objeto.getConfAccesoAplicacionPK().getUsuario());
            objeto.setUsuarioAplicacion(ua);
            super.adicionar(objeto);
            objetoActual = super.buscar(objeto.getConfAccesoAplicacionPK());
        } else {
            // Se actualizan solo datos
            objetoActual = objetoActual.actualizarEntity(objeto);
            super.actualizar(objetoActual);
        }
        return objetoActual;
    }

    @Override
    public void borrar(ConfAccesoAplicacion entity) throws EntityServicioExcepcion {
        ConfAccesoAplicacion confAccesoAplicacion = super.buscar(entity.getConfAccesoAplicacionPK());
        super.borrar(confAccesoAplicacion);
        confAccesoAplicacion = super.buscar(entity.getConfAccesoAplicacionPK());
    }

    //alvaro 18 de Enero
    public int borrarPorUsuario(String usuario) throws EntityServicioExcepcion {
        int respuesta = 0;
        Collection<ConfAccesoAplicacion> items = null;

        String strQuery = "DELETE  FROM ConfAccesoAplicacion s "
                + "   where s.confAccesoAplicacionPK.usuario = :usuario ";

        try {

            //se revisa que  halla registros para el usuario
            Query query = null;

            query = em.createNamedQuery("ConfAccesoAplicacion.findByUsuario");
            query.setParameter("usuario", usuario);
            items = query.getResultList();
            respuesta = items.size();
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

    public int getNumElementosPorUsuario(String usuario) throws EntityServicioExcepcion {

        int respuesta = 0;

        Collection<ConfAccesoAplicacion> items = null;
        try {
            Query query = null;

            query = em.createNamedQuery("ConfAccesoAplicacion.findByUsuario");
            query.setParameter("usuario", usuario);
            items = query.getResultList();
            respuesta = items.size();
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return respuesta;

    }

    public Collection<ConfAccesoAplicacion> getElementosPorUsuario(String usuario) throws EntityServicioExcepcion {

        Collection<ConfAccesoAplicacion> items = null;
        try {
            Query query = null;

            query = em.createNamedQuery("ConfAccesoAplicacion.findByUsuario");
            query.setParameter("usuario", usuario);
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

    public void AddBorrarRegAccesoUsuario() throws EntityServicioExcepcion {

        try {
//                Query query = em.createNativeQuery("BEGIN ADMINATM.SP_ADDBORRA_ACCESOUSUARIO; END;");
//                query.executeUpdate();
            ejecutarSpDesdeJob("ADMINATM.SP_ADDBORRA_ACCESOUSUARIO;");
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public int borrarPorUsuarioServicio(String usuario, Long servicio) throws EntityServicioExcepcion {
        int respuesta = 0;

        String strQuery = "DELETE FROM ConfAccesoAplicacion c "
                + "WHERE c.confAccesoAplicacionPK.servicio = :servicio AND "
                + "c.confAccesoAplicacionPK.usuario = :usuario";

        try {

            //se revisa que  halla registros para el usuario
            Query query = null;

            query = em.createNamedQuery("ConfAccesoAplicacion.deleteByUsuarioServicio");
            query.setParameter("usuario", usuario);
            query.setParameter("servicio", servicio);
            respuesta = query.executeUpdate();
            //si hay borra
            if (respuesta > 0) {
                query = null;
                query = em.createQuery(strQuery);
                query.setParameter("usuario", usuario);
                query.setParameter("servicio", servicio);
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
