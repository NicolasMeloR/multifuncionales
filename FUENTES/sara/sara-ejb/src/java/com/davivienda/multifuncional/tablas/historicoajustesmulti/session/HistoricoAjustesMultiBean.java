// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.historicoajustesmulti.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.multifuncional.tablas.historicoajustesmulti.servicio.HistoricoAjustesMultiServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Historicoajustesmulti;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class HistoricoAjustesMultiBean extends BaseAdministracionTablas<Historicoajustesmulti> implements HistoricoAjustesMultiLocal
{
    @PersistenceContext
    private EntityManager em;
    private HistoricoAjustesMultiServicio historicoAjustesMultiServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.historicoAjustesMultiServicio = new HistoricoAjustesMultiServicio(this.em);
        super.servicio = this.historicoAjustesMultiServicio;
    }
    
    @Override
    public Collection<Historicoajustesmulti> getColeccionHistoricoAjustes(final Integer codigoCajero, final Integer codigoOficina, final Date fechaInicial, final Date fechaFinal, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.historicoAjustesMultiServicio.getColeccionHistoricoAjustes(codigoCajero, codigoOficina, fechaInicial, fechaFinal, fechaHisto);
    }
    
    @Override
    public void guardarHistoricoAjustes(final String usuario, final Integer codigoCajero, final Integer codigoOficina, final String tipoAjuste, final Date fecha, final Long valor, final String talon, final String error, final String descripcionError) throws EntityServicioExcepcion {
        this.historicoAjustesMultiServicio.guardarHistoricoAjustes(usuario, codigoCajero, codigoOficina, tipoAjuste, fecha, valor, talon, error, descripcionError);
    }
}
