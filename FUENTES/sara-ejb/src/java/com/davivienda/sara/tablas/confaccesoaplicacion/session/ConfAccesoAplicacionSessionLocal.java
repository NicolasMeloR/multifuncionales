/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.tablas.confaccesoaplicacion.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import java.util.Collection;
import javax.ejb.Local;

/**
 * ConfAccesoAplicacionSessionLocal - 19/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface ConfAccesoAplicacionSessionLocal extends AdministracionTablasInterface<ConfAccesoAplicacion> {

    /**
     * Obtiene <code>regsPorPagina</code>registros de ConfAccesoAplicacion  desde el registro <code>pagina</code>
     * @param pagina
     * @param regsPorPagina
     * @return 
     * @throws java.lang.Exception
     */
    public Collection<ConfAccesoAplicacion> getTodos(Integer pagina, Integer regsPorPagina) throws Exception;
    public int borrarPorUsuario(String usuario) throws Exception;
    public int getNumElementosPorUsuario(String usuario)  throws Exception;
    public Collection<ConfAccesoAplicacion> getElementosPorUsuario(String usuario)throws Exception;
    public void AddBorrarRegAccesoUsuario() throws EntityServicioExcepcion ;

}

