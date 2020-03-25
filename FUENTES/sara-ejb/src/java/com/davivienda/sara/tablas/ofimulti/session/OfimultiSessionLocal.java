/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.tablas.ofimulti.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Oficinamulti;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author P-CCHAPA
 */
@Local
public interface OfimultiSessionLocal extends AdministracionTablasInterface<Oficinamulti>{
    public Collection<Oficinamulti> getTodos(Integer pagina, Integer regsPorPagina) throws Exception;
    
}

    