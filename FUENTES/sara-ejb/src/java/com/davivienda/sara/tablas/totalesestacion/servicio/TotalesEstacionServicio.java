package com.davivienda.sara.tablas.totalesestacion.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.TotalesEstacion;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * DiarioElectronicoServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class TotalesEstacionServicio extends BaseEntityServicio<TotalesEstacion> implements AdministracionTablasInterface<TotalesEstacion> {

    
    public TotalesEstacionServicio(EntityManager em) {
        super(em, TotalesEstacion.class);
    }
    

   
    

}
