/*
 * CuadreCifrasSessionBean.java
 *
 * Fecha       : 25/08/2007, 10:41:24 AM
 * Descripción :
 *
 * Babel Ver   :1.0
 */
package com.davivienda.sara.cuadrecifras.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.tablas.conceptomovimientocuadre.servicio.ConceptoMovimientoCuadreServicio;
import com.davivienda.sara.tablas.movimientocuadre.servicio.MovimientoCuadreServicio;
import com.davivienda.sara.tablas.tipomovimientocuadre.servicio.TipoMovimientoCuadreServicio;
import com.davivienda.sara.tablas.resumencuadrecifras.servicio.ResumenCuadreCifrasServicio;

//import com.davivienda.sara.entitys.ResumenCuadreCifras;
import com.davivienda.sara.entitys.ResumenCuadreCifras;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import com.davivienda.sara.entitys.ConceptoMovimientoCuadre;
import com.davivienda.sara.procesos.cuadrecifras.servicio.ProcesoCuadreCifrasServicio;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jjvargas
 */
@Stateless
@Local(value = CuadreCifrasSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class CuadreCifrasSessionBean implements CuadreCifrasSessionLocal {

    @PersistenceContext
    private EntityManager em;

    private MovimientoCuadreServicio servicio;
    private ResumenCuadreCifrasServicio servicioResumen;
    private TipoMovimientoCuadreServicio servicioTipoMov;
    private ProcesoCuadreCifrasServicio servicioProcesoCuadreCifras;
    private ConceptoMovimientoCuadreServicio servicioConceptoMov;
    
    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
        servicio = new MovimientoCuadreServicio(em);
        servicioResumen = new ResumenCuadreCifrasServicio(em);
        servicioTipoMov = new TipoMovimientoCuadreServicio(em);
        servicioProcesoCuadreCifras = new ProcesoCuadreCifrasServicio(em);
        servicioConceptoMov = new ConceptoMovimientoCuadreServicio(em);
    }

    /**
     * Retorna todos los registros de ResumenCuadreDiferencia que cumplan con
     * los parámetros dados transformadolos en ResumenCuadreCifrasBean
     *
     * @param codigoCajero si es null se retornan todos
     * @param fechaInicial si es null se toma la fecha del dia
     * @param fechaFinal si es null se toma la fecha del dia
     * @return colección de ResumenCuadreCifrasBean
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public Collection<ResumenCuadreCifras> getResumenCuadreCifras(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return servicioResumen.getResumenCuadreDiferencia(codigoCajero, fechaInicial, fechaFinal);
    }

    /**
     * Retorna el movimiento de un cajero
     *
     * @param codigoCajero
     * @param fechaInicial
     * @param fechaFinal
     * @return Collection MovimientoCuadre
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public Collection<MovimientoCuadre> getMovimientoCuadreCifras(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return servicio.getMovimientoCuadreCifras(codigoCajero, fechaInicial, fechaFinal);
    }

    /**
     * Retorna el movimiento de un cajero con datos a mostrar
     *
     * @param codigoCajero
     * @param fechaInicial
     * @param fechaFinal
     * @return Collection MovimientoCuadre
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasMostrar(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return servicio.getMovimientoCuadreCifrasMostrar(codigoCajero, fechaInicial, fechaFinal);
    }

    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasOccaMostrar(Integer codigoCajero, Integer codigoOcca, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {

        return servicio.getMovimientoCuadreCifrasOccaMostrar(codigoCajero, codigoOcca, fechaInicial, fechaFinal);

    }

    /**
     * Retorna todos los registros de movimiento de contadores de un cajero
     *
     * @param codigoCajero
     * @param fechaInicial
     * @param fechaFinal
     * @return Collection MovimientoCuadre
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasContadores(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return servicio.getMovimientoCuadreCifrasContadores(codigoCajero, fechaInicial, fechaFinal);
    }

    /**
     * Retorna todos los registros de movimiento de Ajustes de un cajero
     *
     * @param codigoCajero
     * @param fechaInicial
     * @param fechaFinal
     * @return
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public Collection<MovimientoCuadre> getMovimientoAjustes(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return servicio.getMovimientoAjustes(codigoCajero, fechaInicial, fechaFinal);
    }

    /**
     * Retorna los movimiento de provisiones timbrados en línea
     *
     * @param codigoCajero
     * @param fechaInicial
     * @param fechaFinal
     * @return
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasProvision(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return servicio.getMovimientoCuadreCifrasProvision(codigoCajero, fechaInicial, fechaFinal);
    }

    /**
     * Retorna todos los registros de movimiento de línea de un cajero
     *
     * @param codigoCajero
     * @param fechaInicial
     * @param fechaFinal
     * @return Collection MovimientoCuadre
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public Collection<MovimientoCuadre> getMovimientoCuadreCifrasDatosLinea(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        return servicio.getMovimientoCuadreCifrasDatosLinea(codigoCajero, fechaInicial, fechaFinal);
    }

    /**
     * Graba una nota de observación en el movimiento
     *
     * @param idRegistro
     * @param observacion
     * @param idUsuario
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public void grabarNotaMovimiento(BigInteger idRegistro, String observacion, String idUsuario) throws EntityServicioExcepcion {
        servicio.grabarNotaMovimiento(idRegistro, observacion, idUsuario);
    }

    /**
     * Consulta la nota del movimiento
     *
     * @param idRegistro
     * @return MovimientoCuadre
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public MovimientoCuadre consultarNotaMovimiento(BigInteger idRegistro) throws EntityServicioExcepcion {
        return servicio.consultarNotaMovimiento(idRegistro);
    }

    /**
     * Graba el movimiento de cuadre
     *
     * @param mc
     * @param actualizar
     * @return
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public MovimientoCuadre grabarMovimientoCuadre(MovimientoCuadre mc, boolean actualizar) throws EntityServicioExcepcion {
        MovimientoCuadre mCuadre = servicio.grabarMovimientoCuadre(mc, actualizar);
        return mCuadre;
    }

    /**
     * cuadra un cajero en un fecha determinada
     *
     * @param mc
     * @param actualizar
     * @return
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public void cuadrarCajero(Cajero cajero, Date fecha) throws EntityServicioExcepcion {

        servicioProcesoCuadreCifras.cuadrarCajero(cajero, fecha);

    }

    /**
     * Retorna los entitys de tipo TipoMovimientoCuadre
     *
     * @return Collection de TipoMovimientoCuadre
     */
    public Collection<TipoMovimientoCuadre> getTiposMovimientoCuadre() {
        return servicioTipoMov.getTiposMovimientoCuadre();
    }

//    /**
//     * Retona un objeto String conformato JSON de todos los entitys de tipo TipoMovimientoCuadre
//     * @return String JSON
//     */
//    public String getTiposMovimientoCuadreComboBox() {
//        return servicioTipoMov.getTiposMovimientoCuadreComboBox();
//    }
//    
//    public String getConceptosMovimientoCuadreComboBox() {
//        return servicioConceptoMov.getConceptosMovimientoCuadreComboBox();
//    };
    /**
     * Retorna el tipo de movimiento según la llave dada
     *
     * @param codigoTipoMovimientoCuadre
     * @return TipoMovimientoCuadre
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public TipoMovimientoCuadre buscarTipoMovimientoCuadre(Integer codigoTipoMovimientoCuadre) throws EntityServicioExcepcion {
        return servicioTipoMov.buscar(codigoTipoMovimientoCuadre);
    }

    /**
     * Retorna todos los entitys de TiposMovimientoCuadre
     *
     * @return Collection de getTiposMovimientoCuadre
     */
    public Collection<ConceptoMovimientoCuadre> getConceptosMovimientoCuadre() {
        return servicioConceptoMov.getConceptosMovimientoCuadre();
    }

    public MovimientoCuadre getMovimientoCuadre(Integer codigoCajero, Calendar fecha, Integer codigoTipoMovimiento) throws EntityServicioExcepcion {
        return servicio.getMovimientoCuadre(codigoCajero, fecha, codigoTipoMovimiento);
    }
    
      
    

}
