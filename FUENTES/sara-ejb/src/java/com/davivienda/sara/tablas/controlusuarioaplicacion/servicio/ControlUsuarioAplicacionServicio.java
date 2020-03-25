package com.davivienda.sara.tablas.controlusuarioaplicacion.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.ControlUsuarioAplicacion;
import com.davivienda.utilidades.Constantes;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * ControluarioAplicacionServicio - 05/09/2017
 * Descripción : Clase encargada de todos los procesos de administración de la información de ControlUsuarioAplicacion
 * Versión : 1.0 
 *
 * @author jediazs@co.ibm.com
 * IBM 2017
 */
public class ControlUsuarioAplicacionServicio extends BaseEntityServicio<ControlUsuarioAplicacion> implements AdministracionTablasInterface<ControlUsuarioAplicacion> { 

    public ControlUsuarioAplicacionServicio(EntityManager em) {
        super(em, ControlUsuarioAplicacion.class);
    }

    
    public ControlUsuarioAplicacion getControlUsuarioAplicacion(String usuario) throws EntityServicioExcepcion {

        ControlUsuarioAplicacion item = null;
        try {
            Query query = null;

            query = em.createNamedQuery("ControlUsuarioAplicacion.RegistroUnico");
            query.setParameter("usuario", usuario);
            item = (ControlUsuarioAplicacion) query.getSingleResult();

        } catch(NoResultException nre){
            configApp.loggerApp.log(java.util.logging.Level.INFO, "No existen registros de login asociados al usuario {0}", usuario);
            return null;           
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, Constantes.MSJ_QUERY_ERROR, ex);
            throw new EntityServicioExcepcion(ex);
        }
        return item;
    }
    
    
    public void eliminarDatosControlUsuario(){
        try {
            Query query = null;
            query = em.createQuery("DELETE FROM ControlUsuarioAplicacion");
            query.executeUpdate();
        } catch(Exception nre){
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error eliminando datos control usuario");
        }
    }
    
    public void eliminarDatosControlXUsuario(String usuario){
        try {
            Query query = null;
            query = em.createNamedQuery("ControlUsuarioAplicacion.EliminarRegistroUnico");
            query.setParameter("usuario", usuario);
            query.executeUpdate();
        } catch(Exception nre){
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error eliminando datos control usuario");
        }
    }
}
