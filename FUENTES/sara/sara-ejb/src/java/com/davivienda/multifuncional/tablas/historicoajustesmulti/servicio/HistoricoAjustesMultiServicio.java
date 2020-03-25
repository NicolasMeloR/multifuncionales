// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.historicoajustesmulti.servicio;

import java.util.logging.Logger;
import javax.persistence.Query;
import com.davivienda.sara.dto.HistoricoAjustesMultiDTO;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.entitys.HistoricoajustesmultiHisto;
import java.util.logging.Level;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Historicoajustesmulti;
import com.davivienda.sara.base.BaseEntityServicio;

public class HistoricoAjustesMultiServicio extends BaseEntityServicio<Historicoajustesmulti> implements AdministracionTablasInterface<Historicoajustesmulti>
{
    public HistoricoAjustesMultiServicio(final EntityManager em) {
        super(em, Historicoajustesmulti.class);
    }
    
    public Collection<Historicoajustesmulti> getColeccionHistoricoAjustes(final Integer codigoCajero, final Integer codigoOficina, final Date fechaInicial, final Date fechaFinal, final Date fechaHisto) throws EntityServicioExcepcion {
        return this.get_ColeccionHistoricoAjustes(codigoCajero, codigoOficina, fechaInicial, fechaFinal, fechaHisto);
    }
    
    public Collection<Historicoajustesmulti> get_ColeccionHistoricoAjustes(final Integer codigoCajero, final Integer codigoOficina, final Date fechaInicial, final Date fechaFinal, final Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        HistoricoAjustesMultiServicio.configApp.loggerApp.log(Level.INFO, "HistoricoAjustesMultiServicio - get_ColeccionHistoricoAjustes fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            HistoricoAjustesMultiServicio.configApp.loggerApp.log(Level.INFO, "HistoricoAjustesMultiServicio - Consultando tabla historico: " + HistoricoajustesmultiHisto.class.getSimpleName());
            nombreTabla = "HistoricoajustesmultiHisto";
            tablaHisto = true;
        }
        else {
            HistoricoAjustesMultiServicio.configApp.loggerApp.log(Level.INFO, "HistoricoAjustesMultiServicio - Consultando tabla : " + Historicoajustesmulti.class.getSimpleName());
            nombreTabla = "Historicoajustesmulti";
        }
        Collection<Historicoajustesmulti> regs = null;
        final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
        String strQuery = "select object(obj) from " + nombreTabla + " obj " + "        where obj.fecha between :fechaInicial and :fechaFinal ";
        final String orderQuery = " order by obj.fecha";
        try {
            if (codigoOficina != null) {
                strQuery += "          and  obj.codigooficinamulti = :codigoOficina";
            }
            if (!cCajero.equals(9999)) {
                strQuery += "          and obj.codigocajero = :codigoCajero  ";
            }
            strQuery += orderQuery;
            final Date fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getDateHoy();
            final Date fFin = (fechaFinal != null) ? fechaFinal : Fecha.getFechaFinDia(fInicial);
            Query query = null;
            query = this.em.createQuery(strQuery);
            query.setParameter("fechaInicial", (Object)fInicial).setParameter("fechaFinal", (Object)fFin);
            if (codigoOficina != null) {
                query.setParameter("codigoOficina", (Object)codigoOficina);
            }
            if (!cCajero.equals(9999)) {
                query.setParameter("codigoCajero", (Object)codigoCajero);
            }
            if (tablaHisto) {
                regs = (Collection<Historicoajustesmulti>)new HistoricoAjustesMultiDTO(HistoricoAjustesMultiServicio.configApp.loggerApp).historicoAjustesMultiHistoAHistoricoAjusteMulti((Collection)query.getResultList());
            }
            else {
                regs = (Collection<Historicoajustesmulti>)query.getResultList();
            }
            HistoricoAjustesMultiServicio.configApp.loggerApp.log(Level.INFO, "HistoricoAjustesMultiServicio - get_ColeccionHistoricoAjustes regs: " + regs.size());
        }
        catch (IllegalStateException ex) {
            HistoricoAjustesMultiServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            HistoricoAjustesMultiServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return regs;
    }
    
    public void guardarHistoricoAjustes(final String usuario, final Integer codigoCajero, final Integer codigoOficina, final String tipoAjuste, final Date fecha, final Long valor, final String talon, final String error, final String descripcionError) throws EntityServicioExcepcion, IllegalArgumentException {
        final Historicoajustesmulti regHistoricoAjustes = new Historicoajustesmulti();
        regHistoricoAjustes.setCodigocajero(codigoCajero);
        regHistoricoAjustes.setCodigooficinamulti(codigoOficina);
        regHistoricoAjustes.setFecha(fecha);
        regHistoricoAjustes.setError(error);
        regHistoricoAjustes.setTipoajuste(tipoAjuste);
        regHistoricoAjustes.setUsuario(usuario);
        regHistoricoAjustes.setValor(valor);
        regHistoricoAjustes.setTalon(talon);
        regHistoricoAjustes.setDescripcionerror(descripcionError);
        try {
            super.adicionar(regHistoricoAjustes);
        }
        catch (IllegalArgumentException ex) {
            Logger.getLogger("globalApp").info("Error al grabar los datos en HistoricoAjustes para codigoCajero " + codigoCajero + " " + ex.getMessage());
        }
        catch (Exception ex2) {
            Logger.getLogger("globalApp").info("Error cargando en HistoricoAjustes registro datos codigoCajero  :" + codigoCajero + " descripcion Error : " + ex2.getMessage());
        }
    }
}
