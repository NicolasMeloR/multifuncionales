// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tareas.usuarioaplicacion.session;

import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Date;
import javax.ejb.Local;

@Local
public interface UsuarioAplicacionTareasSessionLocal
{
    void guardarRegTareasAdminUsuario(final String p0, final long p1, final short p2, final String p3, final String p4, final Date p5) throws EntityServicioExcepcion, IllegalArgumentException;
    
    void addBorrarRegAccesoUsuarioDesdeApp() throws EntityServicioExcepcion, IllegalArgumentException;
    
    void crearActualizarAccesoUsuario(final ConfAccesoAplicacion p0) throws EntityServicioExcepcion, IllegalArgumentException;
}
