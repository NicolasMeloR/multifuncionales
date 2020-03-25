package com.davivienda.sara.procesos.reintegros.servicio;

import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.entitys.CarmasReintegrosTemp;
import com.davivienda.sara.entitys.CarmasTimbresTemp;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 * TransaccionEstadisticaServicio - 1/09/2008 Descripción : Estadísticas de las
 * transacciones generadas por los cajeros Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class CargarTimbresMasivosServicio extends BaseEntityServicio<CarmasTimbresTemp> {
    
    public CargarTimbresMasivosServicio(EntityManager em) {
        super(em, CarmasTimbresTemp.class);
    }
}
