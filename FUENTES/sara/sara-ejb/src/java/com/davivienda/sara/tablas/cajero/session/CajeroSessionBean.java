// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.cajero.session;

import java.util.Collection;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class CajeroSessionBean extends BaseAdministracionTablas<Cajero> implements CajeroSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private CajeroServicio CajeroServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.CajeroServicio = new CajeroServicio(this.em);
        super.servicio = this.CajeroServicio;
    }
    
    @Override
    public Collection<Cajero> getTodosActivos(final Integer pagina, final Integer regsPorPagina) throws Exception {
        return this.servicio.consultarPorQuery(pagina, regsPorPagina, "Cajero.AllActivos");
    }
    
    @Override
    public Collection<Cajero> getCajerosSnTransmitir(final Integer codigoCiclo) throws Exception {
        return this.CajeroServicio.getCajerosSnTransmitir(codigoCiclo);
    }
    
    @Override
    public Collection<Cajero> getCajerosMultiSnTransmitir(final Integer codigoCiclo) throws Exception {
        return this.CajeroServicio.getCajerosMultiSnTransmitir(codigoCiclo);
    }
    
    @Override
    public Collection<Cajero> getTodosActivosMulti(final Integer pagina, final Integer regsPorPagina) throws Exception {
        return this.servicio.consultarPorQuery(pagina, regsPorPagina, "Cajero.AllActivMulti");
    }
}
