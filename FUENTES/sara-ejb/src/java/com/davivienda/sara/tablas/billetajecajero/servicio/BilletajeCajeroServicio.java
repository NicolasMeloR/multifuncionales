package com.davivienda.sara.tablas.billetajecajero.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.BilletajeCajero;
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

public class BilletajeCajeroServicio extends BaseEntityServicio<BilletajeCajero> implements AdministracionTablasInterface<BilletajeCajero> {

    
    public BilletajeCajeroServicio(EntityManager em) {
        super(em, BilletajeCajero.class);
    }
    

   
    

}
