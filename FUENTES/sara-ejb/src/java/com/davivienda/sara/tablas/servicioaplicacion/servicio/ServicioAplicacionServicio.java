package com.davivienda.sara.tablas.servicioaplicacion.servicio;

import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.entitys.seguridad.ServicioAplicacion;
import javax.persistence.EntityManager;

/**
 * ServicioAplicacionServicio - 5/07/2008
 * Descripci�n : 
 * Versi�n : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class ServicioAplicacionServicio extends BaseEntityServicio<ServicioAplicacion> {

    public ServicioAplicacionServicio(EntityManager em) {
        super(em, ServicioAplicacion.class);
    }

}
