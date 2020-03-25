package com.davivienda.multifuncional.tablas.historicoajustesmulti.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.dto.HistoricoAjustesMultiDTO;
import com.davivienda.sara.entitys.Historicoajustesmulti;
import com.davivienda.sara.entitys.HistoricoajustesmultiHisto;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;

import javax.persistence.Query;

import com.davivienda.utilidades.conversion.Fecha;
import java.math.BigDecimal;
import java.util.logging.Level;

/**
 * TipoCajeroServicio - 21/08/2008 Descripción : Servicio para la administración
 * de datos en la tabla TipoCajero Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class HistoricoAjustesMultiServicio extends BaseEntityServicio<Historicoajustesmulti> implements AdministracionTablasInterface<Historicoajustesmulti> {

    public HistoricoAjustesMultiServicio(EntityManager em) {
        super(em, Historicoajustesmulti.class);
    }

//    @Override
//    public Historicoajustesmulti actualizar(Historicoajustesmulti objeto) throws EntityServicioExcepcion {
//        Historicoajustesmulti objetoActual = super.buscar(objeto.getIdhistoricoajustes());
//        String accion = (objetoActual == null) ? "NUEVO" : "ACTUALIZAR";
//        if (accion.equals("NUEVO")) {
//            // Es nuevo y debo asociar las relaciones
//            super.adicionar(objeto);
//            objetoActual = super.buscar(objeto.getIdhistoricoajustes());
//        } else {
//            // Se actualizan solo datos
//            
//            objetoActual = objetoActual.actualizarEntity(objeto);
//            super.actualizar(objetoActual);
//        }
//        return objetoActual;
//    }
    public Collection<Historicoajustesmulti> getColeccionHistoricoAjustes(Integer codigoCajero, Integer codigoOficina, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion {
        return get_ColeccionHistoricoAjustes(codigoCajero, codigoOficina, fechaInicial, fechaFinal, fechaHisto);
    }

    public Collection<Historicoajustesmulti> get_ColeccionHistoricoAjustes(Integer codigoCajero, Integer codigoOficina, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion {

        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "HistoricoAjustesMultiServicio - get_ColeccionHistoricoAjustes fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal
                + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "HistoricoAjustesMultiServicio - Consultando tabla historico: " + HistoricoajustesmultiHisto.class.getSimpleName());
            nombreTabla = "HistoricoajustesmultiHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "HistoricoAjustesMultiServicio - Consultando tabla : " + Historicoajustesmulti.class.getSimpleName());
            nombreTabla = "Historicoajustesmulti";
        }

        Collection<Historicoajustesmulti> regs = null;

        Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;

        String strQuery = "select object(obj) from " + nombreTabla + " obj "
                + "        where obj.fecha between :fechaInicial and :fechaFinal ";

        String orderQuery = " order by obj.fecha";

        try {
            if (codigoOficina != null) {
                strQuery += "          and  obj.codigooficinamulti = :codigoOficina";
            }
            if (!cCajero.equals(9999)) {
                strQuery += "          and obj.codigocajero = :codigoCajero  ";
            }

            strQuery += orderQuery;

            Date fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getDateHoy();
            Date fFin = (fechaFinal != null) ? fechaFinal : com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fInicial);
            
            //Date fFin = (fechaFinal != null) ? com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaFinal) : com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fInicial);
                       

            // Date fFin = (fechaFinal != null) ? com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaFinal) : Fecha.getFechaFinDia(fInicial);
            // Date fFin = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fInicial);
            Query query = null;

            //createNamedQuery("Transaccion.CajeroRangoFecha");
            query = em.createQuery(strQuery);
            query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
            if (codigoOficina != null) {
                query.setParameter("codigoOficina", codigoOficina);
            }
            if (!cCajero.equals(9999)) {
                query.setParameter("codigoCajero", codigoCajero);

            }

            if (tablaHisto) {
                regs = new HistoricoAjustesMultiDTO(configApp.loggerApp).historicoAjustesMultiHistoAHistoricoAjusteMulti(query.getResultList());
            } else {
                regs = query.getResultList();
            }

            configApp.loggerApp.log(Level.INFO, "HistoricoAjustesMultiServicio - get_ColeccionHistoricoAjustes regs: " + regs.size());

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return regs;

    }

    public void guardarHistoricoAjustes(String usuario, Integer codigoCajero, Integer codigoOficina, String tipoAjuste, Date fecha, Long valor, String talon, String error, String descripcionError) throws EntityServicioExcepcion, IllegalArgumentException {

        Historicoajustesmulti regHistoricoAjustes = new Historicoajustesmulti();

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

        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos en HistoricoAjustes para codigoCajero " + codigoCajero + " " + ex.getMessage());

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error cargando en HistoricoAjustes registro datos codigoCajero  :" + codigoCajero + " descripcion Error : " + ex.getMessage());
        }

    }

}
