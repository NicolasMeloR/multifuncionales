package com.davivienda.sara.tablas.historicoajustes.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.dto.HistoricoAjustesDTO;
import com.davivienda.sara.entitys.HistoricoAjustes;
import com.davivienda.sara.entitys.HistoricoAjustesHisto;
import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import com.davivienda.sara.entitys.NotasdebitomultifuncionalHisto;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;

import javax.persistence.Query;

import com.davivienda.utilidades.conversion.Fecha;
import java.util.logging.Level;

/**
 * TipoCajeroServicio - 21/08/2008 Descripción : Servicio para la administración
 * de datos en la tabla TipoCajero Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class HistoricoAjustesServicio extends BaseEntityServicio<HistoricoAjustes> implements AdministracionTablasInterface<HistoricoAjustes> {

    public HistoricoAjustesServicio(EntityManager em) {
        super(em, HistoricoAjustes.class);
    }

 

    public Collection<HistoricoAjustes> getColeccionHistoricoAjustes(Integer codigoCajero, Integer codigoOcca, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion {
        return get_ColeccionHistoricoAjustes(codigoCajero, codigoOcca, fechaInicial, fechaFinal, fechaHisto);
    }

    public Collection<HistoricoAjustes> get_ColeccionHistoricoAjustes(Integer codigoCajero, Integer codigoOcca, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion {

        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "HistoricoAjustesServicio - get_ColeccionHistoricoAjustes fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal
                + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "HistoricoAjustesServicio - Consultando tabla historico: " + HistoricoAjustesHisto.class.getSimpleName());
            nombreTabla = "HistoricoAjustesHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "HistoricoAjustesServicio - Consultando tabla : " + HistoricoAjustes.class.getSimpleName());
            nombreTabla = "HistoricoAjustes";
        }

        Collection<HistoricoAjustes> regs = null;

        Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;

        String strQuery = "select object(obj) from "+nombreTabla+" obj "
                + "        where obj.fecha between :fechaInicial and :fechaFinal ";

        String orderQuery = " order by obj.fecha";

        try {
            if (codigoOcca != null) {
                strQuery += "          and obj.codigoOcca = :codigoOcca ";
            }
            if (!cCajero.equals(9999)) {
                strQuery += "          and obj.codigoCajero = :codigoCajero ";
            }

            strQuery += orderQuery;

            Date fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getDateHoy();
            Date fFin = (fechaFinal != null) ? fechaFinal : Fecha.getFechaFinDia(fInicial);

            Query query = null;

            //createNamedQuery("Transaccion.CajeroRangoFecha");
            query = em.createQuery(strQuery);
            query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
            if (codigoOcca != null) {
                query.setParameter("codigoOcca", codigoOcca);
            }
            if (!cCajero.equals(9999)) {
                query.setParameter("codigoCajero", codigoCajero);

            }

            if (tablaHisto) {
                regs = new HistoricoAjustesDTO(configApp.loggerApp).historicoAjustesHistoAHistoricoAjustes(query.getResultList());
            } else {
                regs = query.getResultList();
            }

            configApp.loggerApp.log(Level.INFO, "HistoricoAjustesServicio - getReintegros get_ColeccionHistoricoAjustes: " + regs.size());
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
