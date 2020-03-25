

package com.davivienda.sara.procesos.reintegros.servicio;


import com.davivienda.sara.base.BaseEntityServicio;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.CarmasNotasDebitoTemp;

/**
 * TransaccionEstadisticaServicio - 1/09/2008
 * Descripci�n : Estad�sticas de las transacciones generadas por los cajeros
 * Versi�n : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class CarguesMasivosNotasDebitoTempServicio extends BaseEntityServicio<CarmasNotasDebitoTemp>{

    public CarguesMasivosNotasDebitoTempServicio(EntityManager em, Class claseEntity) {
        super(em, claseEntity);
    }

}

