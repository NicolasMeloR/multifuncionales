// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.cuadrecifrasmulti.session;

import java.util.Collection;
import java.util.Date;
import com.davivienda.sara.entitys.Tipomovimientocuadremulti;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Movimientocuadremulti;
import javax.annotation.PostConstruct;
import com.davivienda.multifuncional.tablas.tipomovimientocuadremulti.servicio.TipoMovimientoCuadreMultiServicio;
import com.davivienda.multifuncional.cuadrecifrasmulti.servicio.CuadreCifrasMultiSessionServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local({ CuadreCifrasMultiSessionLocal.class })
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CuadreCifrasMultiSessionBean implements CuadreCifrasMultiSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private CuadreCifrasMultiSessionServicio servicio;
    private TipoMovimientoCuadreMultiServicio servicioTipoMov;
    
    @PostConstruct
    public void postConstructor() {
        this.servicio = new CuadreCifrasMultiSessionServicio(this.em);
        this.servicioTipoMov = new TipoMovimientoCuadreMultiServicio(this.em);
    }
    
    @Override
    public Movimientocuadremulti grabarMovimientoCuadre(final Movimientocuadremulti mc, final boolean actualizar) throws EntityServicioExcepcion {
        final Movimientocuadremulti mCuadremulti = this.servicio.grabarMovimientoCuadre(mc, actualizar);
        return mCuadremulti;
    }
    
    @Override
    public Tipomovimientocuadremulti buscarTipoMovimientoCuadre(final Integer codigoTipoMovimientoCuadre) throws EntityServicioExcepcion {
        return this.servicioTipoMov.buscar(codigoTipoMovimientoCuadre);
    }
    
    @Override
    public Collection<Movimientocuadremulti> getUsuarioFecha(final String usuario, final Date fechaInicial) throws EntityServicioExcepcion {
        return this.servicio.getUsuarioFecha(usuario, fechaInicial);
    }
}
