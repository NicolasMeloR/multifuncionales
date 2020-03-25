/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.sara.tablas.confmodulosaplicacion.session;

import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import java.util.Collection;
import javax.ejb.Local;
import com.davivienda.sara.base.AdministracionTablasInterface;

/**
 *
 * @author jjvargas
 */
@Local
public interface ConfModulosAplicacionLocal extends AdministracionTablasInterface<ConfModulosAplicacion> {

    public Collection<ConfModulosAplicacion> getTodos(Integer pagina, Integer regsPorPagina) throws Exception;

}
