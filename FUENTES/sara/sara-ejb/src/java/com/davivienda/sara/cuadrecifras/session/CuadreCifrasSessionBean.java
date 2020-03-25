// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.cuadrecifras.session;

import com.davivienda.sara.entitys.ConceptoMovimientoCuadre;
import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import java.util.Date;
import com.davivienda.sara.entitys.Cajero;
import java.math.BigInteger;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.ResumenCuadreCifras;
import java.util.Collection;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.conceptomovimientocuadre.servicio.ConceptoMovimientoCuadreServicio;
import com.davivienda.sara.procesos.cuadrecifras.servicio.ProcesoCuadreCifrasServicio;
import com.davivienda.sara.tablas.tipomovimientocuadre.servicio.TipoMovimientoCuadreServicio;
import com.davivienda.sara.tablas.resumencuadrecifras.servicio.ResumenCuadreCifrasServicio;
import com.davivienda.sara.tablas.movimientocuadre.servicio.MovimientoCuadreServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local({ CuadreCifrasSessionLocal.class })
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CuadreCifrasSessionBean implements CuadreCifrasSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private MovimientoCuadreServicio servicio;
    private ResumenCuadreCifrasServicio servicioResumen;
    private TipoMovimientoCuadreServicio servicioTipoMov;
    private ProcesoCuadreCifrasServicio servicioProcesoCuadreCifras;
    private ConceptoMovimientoCuadreServicio servicioConceptoMov;
    
    @PostConstruct
    public void postConstructor() {
        this.servicio = new MovimientoCuadreServicio(this.em);
        this.servicioResumen = new ResumenCuadreCifrasServicio(this.em);
        this.servicioTipoMov = new TipoMovimientoCuadreServicio(this.em);
        this.servicioProcesoCuadreCifras = new ProcesoCuadreCifrasServicio(this.em);
        this.servicioConceptoMov = new ConceptoMovimientoCuadreServicio(this.em);
    }
    
    @Override
    public Collection<ResumenCuadreCifras> getResumenCuadreCifras(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.servicioResumen.getResumenCuadreDiferencia(codigoCajero, fechaInicial, fechaFinal);
    }
    
    @Override
    public Collection<MovimientoCuadre> getMovimientoCuadreCifras(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.servicio.getMovimientoCuadreCifras(codigoCajero, fechaInicial, fechaFinal);
    }
    
    @Override
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasMostrar(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.servicio.getMovimientoCuadreCifrasMostrar(codigoCajero, fechaInicial, fechaFinal);
    }
    
    @Override
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasOccaMostrar(final Integer codigoCajero, final Integer codigoOcca, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.servicio.getMovimientoCuadreCifrasOccaMostrar(codigoCajero, codigoOcca, fechaInicial, fechaFinal);
    }
    
    @Override
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasContadores(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.servicio.getMovimientoCuadreCifrasContadores(codigoCajero, fechaInicial, fechaFinal);
    }
    
    @Override
    public Collection<MovimientoCuadre> getMovimientoAjustes(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.servicio.getMovimientoAjustes(codigoCajero, fechaInicial, fechaFinal);
    }
    
    @Override
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasProvision(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.servicio.getMovimientoCuadreCifrasProvision(codigoCajero, fechaInicial, fechaFinal);
    }
    
    @Override
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasDatosLinea(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        return this.servicio.getMovimientoCuadreCifrasDatosLinea(codigoCajero, fechaInicial, fechaFinal);
    }
    
    @Override
    public void grabarNotaMovimiento(final BigInteger idRegistro, final String observacion, final String idUsuario) throws EntityServicioExcepcion {
        this.servicio.grabarNotaMovimiento(idRegistro, observacion, idUsuario);
    }
    
    @Override
    public MovimientoCuadre consultarNotaMovimiento(final BigInteger idRegistro) throws EntityServicioExcepcion {
        return this.servicio.consultarNotaMovimiento(idRegistro);
    }
    
    @Override
    public MovimientoCuadre grabarMovimientoCuadre(final MovimientoCuadre mc, final boolean actualizar) throws EntityServicioExcepcion {
        final MovimientoCuadre mCuadre = this.servicio.grabarMovimientoCuadre(mc, actualizar);
        return mCuadre;
    }
    
    @Override
    public void cuadrarCajero(final Cajero cajero, final Date fecha) throws EntityServicioExcepcion {
        this.servicioProcesoCuadreCifras.cuadrarCajero(cajero, fecha);
    }
    
    @Override
    public Collection<TipoMovimientoCuadre> getTiposMovimientoCuadre() {
        return this.servicioTipoMov.getTiposMovimientoCuadre();
    }
    
    @Override
    public TipoMovimientoCuadre buscarTipoMovimientoCuadre(final Integer codigoTipoMovimientoCuadre) throws EntityServicioExcepcion {
        return this.servicioTipoMov.buscar(codigoTipoMovimientoCuadre);
    }
    
    @Override
    public Collection<ConceptoMovimientoCuadre> getConceptosMovimientoCuadre() {
        return this.servicioConceptoMov.getConceptosMovimientoCuadre();
    }
    
    @Override
    public MovimientoCuadre getMovimientoCuadre(final Integer codigoCajero, final Calendar fecha, final Integer codigoTipoMovimiento) throws EntityServicioExcepcion {
        return this.servicio.getMovimientoCuadre(codigoCajero, fecha, codigoTipoMovimiento);
    }
}
