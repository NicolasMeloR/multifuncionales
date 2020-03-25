/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.tablas.TiraAuditoria.session;

import java.util.Collection;
import com.davivienda.sara.entitys.TiraAuditoria;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author nmelo
 */
@Local
public interface TiraAuditoriaSessionLocal extends AdministracionTablasInterface<TiraAuditoria>{
    List<TiraAuditoria> getTira(Integer p0, Date p1, Date p2) throws EntityServicioExcepcion;
}
