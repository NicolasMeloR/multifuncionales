package com.davivienda.sara.estadisticas;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.transaccion.CantidadTransaccionesBean;
import com.davivienda.sara.estadisticas.transaccion.TransaccionEstadisticaServicio;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EstadisticasGeneralesSessionBean - 1/09/2008
 * Descripci�n : Session para exponer los m�todos de obtenci�n de datos para las estad�sticas generales.
 * Versi�n : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Stateless
public class EstadisticasGeneralesSessionBean implements EstadisticasGeneralesSessionLocal {

    @PersistenceContext
    private EntityManager em;
    
        TransaccionEstadisticaServicio transaccionEstadisticaServicio;

    @PostConstruct
    public void postConstructor() {
        transaccionEstadisticaServicio = new TransaccionEstadisticaServicio(em);

    }

    public Collection<CantidadTransaccionesBean> getTransaccionesRealizadasPorCajero(Integer pagina, Integer regsPorPagina, Integer codigoCajeroInicial, Integer codigoCajeroFinal, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion, IllegalArgumentException {
        return transaccionEstadisticaServicio.getTransaccionesRealizadasPorCajero(pagina, regsPorPagina, codigoCajeroInicial, codigoCajeroFinal, fechaInicial, fechaFinal);
    }
}
