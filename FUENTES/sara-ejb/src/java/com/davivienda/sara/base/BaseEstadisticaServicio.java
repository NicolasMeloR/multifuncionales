package com.davivienda.sara.base;

import javax.persistence.EntityManager;

/**
 * BaseEstadisticaServicio - 1/09/2008
 * Descripción : Clase base para los servicios de estadísticas
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class BaseEstadisticaServicio extends BaseServicio{

    public BaseEstadisticaServicio(EntityManager em) {
        super(em);
    }

}
