// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.estadisticas;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.transaccion.CantidadTransaccionesBean;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.sara.estadisticas.transaccion.TransaccionEstadisticaServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;

@Stateless
public class EstadisticasGeneralesSessionBean implements EstadisticasGeneralesSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    TransaccionEstadisticaServicio transaccionEstadisticaServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.transaccionEstadisticaServicio = new TransaccionEstadisticaServicio(this.em);
    }
    
    @Override
    public Collection<CantidadTransaccionesBean> getTransaccionesRealizadasPorCajero(final Integer pagina, final Integer regsPorPagina, final Integer codigoCajeroInicial, final Integer codigoCajeroFinal, final Date fechaInicial, final Date fechaFinal) throws EntityServicioExcepcion, IllegalArgumentException {
        return this.transaccionEstadisticaServicio.getTransaccionesRealizadasPorCajero(pagina, regsPorPagina, codigoCajeroInicial, codigoCajeroFinal, fechaInicial, fechaFinal);
    }
}
