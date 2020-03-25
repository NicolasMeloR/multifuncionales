package com.davivienda.sara.tablas.controlusuarioaplicacion.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.ControlUsuarioAplicacion;
import javax.ejb.Local;
import java.util.Collection;

/**
 * ControlUsuarioAplicacionSessionLocal - 06/09/2017 Descripción : Versión : 1.0
 *
 * @author jediazs@co.ibm.com IBM 2017
 */
@Local
public interface ControlUsuarioAplicacionSessionLocal extends AdministracionTablasInterface<ControlUsuarioAplicacion> {

    public Collection<ControlUsuarioAplicacion> getTodos(Integer pagina, Integer regsPorPagina) throws Exception;

    public ControlUsuarioAplicacion getControlUsuarioAplicacion(String usuario) throws EntityServicioExcepcion;

    public void eliminarDatosControlUsuario();

    public void eliminarDatosControlXUsuario(String usuario);

}
