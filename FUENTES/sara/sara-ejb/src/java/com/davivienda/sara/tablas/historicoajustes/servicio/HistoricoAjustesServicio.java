// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.historicoajustes.servicio;

import javax.persistence.Query;
import com.davivienda.sara.dto.HistoricoAjustesDTO;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.entitys.HistoricoAjustesHisto;
import java.util.logging.Level;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.HistoricoAjustes;
import com.davivienda.sara.base.BaseEntityServicio;

public class HistoricoAjustesServicio extends BaseEntityServicio<HistoricoAjustes> implements AdministracionTablasInterface<HistoricoAjustes>
{
    public HistoricoAjustesServicio(final EntityManager em) {
        super(em, HistoricoAjustes.class);
    }
    
    public Collection<HistoricoAjustes> getColeccionHistoricoAjustes(final Integer codigoCajero, final Integer codigoOcca, final Date fechaInicial, final Date fechaFinal, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.get_ColeccionHistoricoAjustes(codigoCajero, codigoOcca, fechaInicial, fechaFinal, fechaHisto);
    }
    
    public Collection<HistoricoAjustes> get_ColeccionHistoricoAjustes(final Integer codigoCajero, final Integer codigoOcca, final Date fechaInicial, final Date fechaFinal, final Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        HistoricoAjustesServicio.configApp.loggerApp.log(Level.INFO, "HistoricoAjustesServicio - get_ColeccionHistoricoAjustes fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            HistoricoAjustesServicio.configApp.loggerApp.log(Level.INFO, "HistoricoAjustesServicio - Consultando tabla historico: " + HistoricoAjustesHisto.class.getSimpleName());
            nombreTabla = "HistoricoAjustesHisto";
            tablaHisto = true;
        }
        else {
            HistoricoAjustesServicio.configApp.loggerApp.log(Level.INFO, "HistoricoAjustesServicio - Consultando tabla : " + HistoricoAjustes.class.getSimpleName());
            nombreTabla = "HistoricoAjustes";
        }
        Collection<HistoricoAjustes> regs = null;
        final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
        String strQuery = "select object(obj) from " + nombreTabla + " obj " + "        where obj.fecha between :fechaInicial and :fechaFinal ";
        final String orderQuery = " order by obj.fecha";
        try {
            if (codigoOcca != null) {
                strQuery += "          and obj.codigoOcca = :codigoOcca ";
            }
            if (!cCajero.equals(9999)) {
                strQuery += "          and obj.codigoCajero = :codigoCajero ";
            }
            strQuery += orderQuery;
            final Date fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getDateHoy();
            final Date fFin = (fechaFinal != null) ? fechaFinal : Fecha.getFechaFinDia(fInicial);
            Query query = null;
            query = this.em.createQuery(strQuery);
            query.setParameter("fechaInicial", (Object)fInicial).setParameter("fechaFinal", (Object)fFin);
            if (codigoOcca != null) {
                query.setParameter("codigoOcca", (Object)codigoOcca);
            }
            if (!cCajero.equals(9999)) {
                query.setParameter("codigoCajero", (Object)codigoCajero);
            }
            if (tablaHisto) {
                regs = (Collection<HistoricoAjustes>)new HistoricoAjustesDTO(HistoricoAjustesServicio.configApp.loggerApp).historicoAjustesHistoAHistoricoAjustes((Collection)query.getResultList());
            }
            else {
                regs = (Collection<HistoricoAjustes>)query.getResultList();
            }
            HistoricoAjustesServicio.configApp.loggerApp.log(Level.INFO, "HistoricoAjustesServicio - getReintegros get_ColeccionHistoricoAjustes: " + regs.size());
        }
        catch (IllegalStateException ex) {
            HistoricoAjustesServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            HistoricoAjustesServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return regs;
    }
}
