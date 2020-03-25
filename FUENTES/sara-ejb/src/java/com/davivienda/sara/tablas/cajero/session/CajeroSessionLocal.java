/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.tablas.cajero.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Cajero;
import java.util.Collection;

/**
 *
 * @author jediazs
 */
public interface CajeroSessionLocal extends AdministracionTablasInterface<Cajero>{
    
     public Collection<Cajero> getTodosActivos(Integer pagina, Integer regsPorPagina) throws Exception;
     public Collection<Cajero> getCajerosSnTransmitir(Integer codigoCiclo) throws Exception;
     public Collection<Cajero> getCajerosMultiSnTransmitir(Integer codigoCiclo) throws Exception ;
     public Collection<Cajero> getTodosActivosMulti(Integer pagina, Integer regsPorPagina) throws Exception;
}
