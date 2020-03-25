// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.controlusuarioaplicacion.servicio;

import javax.persistence.Query;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.persistence.NoResultException;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.ControlUsuarioAplicacion;
import com.davivienda.sara.base.BaseEntityServicio;

public class ControlUsuarioAplicacionServicio extends BaseEntityServicio<ControlUsuarioAplicacion> implements AdministracionTablasInterface<ControlUsuarioAplicacion>
{
    public ControlUsuarioAplicacionServicio(final EntityManager em) {
        super(em, ControlUsuarioAplicacion.class);
    }
    
    public ControlUsuarioAplicacion getControlUsuarioAplicacion(final String usuario) throws EntityServicioExcepcion {
        ControlUsuarioAplicacion item = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("ControlUsuarioAplicacion.RegistroUnico");
            query.setParameter("usuario", (Object)usuario);
            item = (ControlUsuarioAplicacion)query.getSingleResult();
        }
        catch (NoResultException nre) {
            ControlUsuarioAplicacionServicio.configApp.loggerApp.log(Level.INFO, "No existen registros de login asociados al usuario {0}", usuario);
            return null;
        }
        catch (IllegalStateException ex) {
            ControlUsuarioAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ControlUsuarioAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "Error interno en la consulta", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return item;
    }
    
    public void eliminarDatosControlUsuario() {
        try {
            Query query = null;
            query = this.em.createQuery("DELETE FROM ControlUsuarioAplicacion");
            query.executeUpdate();
        }
        catch (Exception nre) {
            ControlUsuarioAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "Error eliminando datos control usuario");
        }
    }
    
    public void eliminarDatosControlXUsuario(final String usuario) {
        try {
            Query query = null;
            query = this.em.createNamedQuery("ControlUsuarioAplicacion.EliminarRegistroUnico");
            query.setParameter("usuario", (Object)usuario);
            query.executeUpdate();
        }
        catch (Exception nre) {
            ControlUsuarioAplicacionServicio.configApp.loggerApp.log(Level.SEVERE, "Error eliminando datos control usuario");
        }
    }
}
