package com.davivienda.sara.tareas.usuarioaplicacion.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.EdcCargue;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import javax.ejb.Local;
import java.util.Collection;
import java.util.Date;

/**
 * EdcCargueSessionLocal - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface UsuarioAplicacionTareasSessionLocal 
{
 
  public void guardarRegTareasAdminUsuario(String usuario, long servicio,short borratodousuario,String usuarioSession,String nombreUsuario,Date fecha )  throws  EntityServicioExcepcion, IllegalArgumentException ;
  
  public void addBorrarRegAccesoUsuarioDesdeApp()throws EntityServicioExcepcion, IllegalArgumentException;
  
  public void crearActualizarAccesoUsuario(ConfAccesoAplicacion pEntity)throws EntityServicioExcepcion, IllegalArgumentException;
}
