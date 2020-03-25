/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.multifuncional.tablas.reintegrosmultifuncional.session;

import com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.session.*;
import com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.servicio.ReintegrosMultiEfectivoServicio;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.ReintegrosMultifuncional;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import com.davivienda.sara.tablas.reintegros.servicio.ReintegrosServicio;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author P-CCHAPA
 */
@Stateless
public class ReintegrosMultifuncionalSessionBean extends BaseAdministracionTablas<ReintegrosMultifuncional> implements ReintegrosMultifuncionalSessionLocal {

    @PersistenceContext
    private EntityManager em;
    ReintegrosMultiEfectivoServicio reintegrosServicio;

    @PostConstruct
    public void postConstructor() {
        reintegrosServicio = new ReintegrosMultiEfectivoServicio(em);
        super.servicio = reintegrosServicio;

    }
    @SuppressWarnings("unchecked")

    public Collection<ReintegrosMultifuncional> getReintegrosMultifuncional( Date fechaInicial ,Date fechaFinal,Integer codigCajero) throws EntityServicioExcepcion {
       return reintegrosServicio.getReintegrosMultifuncional(fechaInicial, fechaFinal, codigCajero);
    }
     
}
