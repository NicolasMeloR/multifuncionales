package com.davivienda.sara.estadisticas.transaccion;

import com.davivienda.sara.base.BaseEstadisticaServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.transaccion.CantidadTransaccionesBean;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.tablas.transaccion.servicio.TransaccionServicio;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * TransaccionEstadisticaServicio - 1/09/2008
 * Descripción : Estadísticas de las transacciones generadas por los cajeros
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class TransaccionEstadisticaServicio extends BaseEstadisticaServicio {

    TransaccionServicio transaccionServicio;


    public TransaccionEstadisticaServicio(EntityManager em) {
        super(em);
        transaccionServicio = new TransaccionServicio(em);
    }

    public Collection<CantidadTransaccionesBean> getTransaccionesRealizadasPorCajero(Integer pagina, Integer regsPorPagina, Integer codigoCajeroInicial, Integer codigoCajeroFinal, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion, IllegalArgumentException {
        Collection vectorRegs = null;
        Collection<CantidadTransaccionesBean> regs = new ArrayList<CantidadTransaccionesBean>();
        Query query = null;
        String strQuery =
                "select c.codigocajero as codigoCajero," +
                "   c.nombre as nombreCajero, " +
                "   to_char(t.fechatransaccion, 'yyyy-mm-dd') as fecha," +
                "   'Todas' as tipoTransaccion," +
                "   count(t.tipotransaccion) as cantidad" +
                "   from transaccion t, cajero c" +
                " where c.codigocajero  = t.codigocajero(+)  and" +
                "   c.codigocajero between ? and ? and " +
                "   c.activo = 1  and " +
                "   t.fechatransaccion (+) between ? and ?" +
                " group by to_char(t.fechatransaccion, 'yyyy-mm-dd'), c.nombre , c.codigocajero" +
                " order by to_char(t.fechatransaccion, 'yyyy-mm-dd'), cantidad, c.codigocajero";
                
        regsPorPagina = (regsPorPagina == null) ? 0 : regsPorPagina;
        pagina = (pagina == null) ? 1 : pagina;
        Integer regInicial = pagina * regsPorPagina;
        regInicial -= 1;
        if (codigoCajeroInicial == null || codigoCajeroInicial == 0) {
            throw new IllegalArgumentException("Se debe seleccionar un cajero");
        }
        if (codigoCajeroFinal == null || codigoCajeroFinal == 0) {
            codigoCajeroFinal = codigoCajeroInicial;
        }
        codigoCajeroFinal = 9999;
        if (fechaInicial == null) {
            fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
        }
        if (fechaFinal == null) {
            fechaFinal = fechaInicial;
        }
        if (fechaFinal.before(fechaInicial)) {
            throw new IllegalArgumentException("La fecha final debe ser despues de la fecha Inicial");
        }
        try {
            query = em.createNativeQuery(strQuery); 

            if (regInicial > 0) {
                query.setFirstResult(regInicial);
            }
            if (regsPorPagina > 0) {
                query.setMaxResults(regsPorPagina);
            }
            query.setParameter(1, codigoCajeroInicial).setParameter(2, codigoCajeroFinal);
            query.setParameter(3, fechaInicial).setParameter(4, fechaFinal);
            vectorRegs = query.getResultList();
                        
            for (Iterator it = vectorRegs.iterator(); it.hasNext();) {
                Object[] vec = (Object[])it.next();
                CantidadTransaccionesBean reg = new CantidadTransaccionesBean();
                reg.setCodigoCajero(((BigDecimal) vec[0]).intValue());
                reg.setNombreCajero((String) vec[1]);
                reg.setFecha((String) vec[2]);
                reg.setTipoTransacion((String) vec[3]);
                reg.setCantidad((BigDecimal) vec[4]);
                regs.add(reg);
            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return regs;
    }
}
