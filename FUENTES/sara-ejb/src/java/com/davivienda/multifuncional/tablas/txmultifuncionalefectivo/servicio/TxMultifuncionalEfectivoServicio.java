package com.davivienda.multifuncional.tablas.txmultifuncionalefectivo.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.dto.TxMultifuncionalEfeDTO;

import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import com.davivienda.sara.entitys.TxmultifuncionalefectivoHisto;
import com.davivienda.utilidades.conversion.Fecha;
import java.sql.CallableStatement;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * TxMultifuncionalEfectivoServicio Descripción : Versión : 1.0
 *
 * @author P-MDRUIZ Davivienda 2011
 */
public class TxMultifuncionalEfectivoServicio extends BaseEntityServicio<Txmultifuncionalefectivo> implements AdministracionTablasInterface<Txmultifuncionalefectivo> {

    public TxMultifuncionalEfectivoServicio(EntityManager em) {
        super(em, Txmultifuncionalefectivo.class);
    }

    @SuppressWarnings("unchecked")
    public Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion {
        return getColeccionTxEfectivo(codigoCajero, fechaInicial, fechaFinal, null, null, fechaHisto);
    }

    @SuppressWarnings(value = "unchecked")
    public Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(Integer codigoCajero, Date fecha, Date fechaHisto) throws EntityServicioExcepcion {
        return getColeccionTxEfectivo(codigoCajero, fecha, null, null, null, fechaHisto);
    }

    public Txmultifuncionalefectivo getTxEfectivo(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "TxMultifuncionalEfectivoServicio - getTxEfectivo fechaProceso: " + fechaProceso
                + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "TxMultifuncionalEfectivoServicio - Consultando tabla historico: " + TxmultifuncionalefectivoHisto.class.getSimpleName());
            nombreTabla = "TxmultifuncionalefectivoHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "TxMultifuncionalEfectivoServicio - Consultando tabla : " + Txmultifuncionalefectivo.class.getSimpleName());
            nombreTabla = "Txmultifuncionalefectivo";
        }

        Txmultifuncionalefectivo reg = null;
        try {
            Date fInicial = (fechaProceso != null) ? fechaProceso : Fecha.getDateHoy();
            Date fFin = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fInicial);
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0000;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery(nombreTabla + ".TransaccionBuscarReintegro");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
                query.setParameter("numeroTransaccion", nTran);
                if (tablaHisto) {
                    reg = new TxMultifuncionalEfeDTO(configApp.loggerApp).txMultiEfeHistoATxMultiEfe((TxmultifuncionalefectivoHisto) query.getSingleResult());
                } else {
                    reg = (Txmultifuncionalefectivo) query.getSingleResult();

                }
            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (Exception ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error no conocido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return reg;
    }

    @SuppressWarnings("unchecked")
    public Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer numeroTransaccion, String cuenta, Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - getTransaccionTalonUnico fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal
                + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla historico: " + TxmultifuncionalefectivoHisto.class.getSimpleName());
            nombreTabla = "TxmultifuncionalefectivoHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla : " + Txmultifuncionalefectivo.class.getSimpleName());
            nombreTabla = "Txmultifuncionalefectivo";
        }
        
        
        Collection<Txmultifuncionalefectivo> regs = null;
        String strQuery = "select object(obj) from "+nombreTabla+" obj "
                + "        where  obj.txmultifuncionalefectivoPK.fechacajero between :fechaInicial and :fechaFinal ";
        String orderQuery = "        order by obj.txmultifuncionalefectivoPK.fechacajero";

        try {
            if (codigoCajero != 0) {
                strQuery += "          and obj.txmultifuncionalefectivoPK.codigocajero =:codigoCajero";
            }
            if (numeroTransaccion != null) {
                strQuery += "          and obj.txmultifuncionalefectivoPK.codigotx =:numeroTransaccion";
            }

            if (cuenta != null && !cuenta.equals("")) {
                strQuery += "           and obj.numerocuentaconsignar =:cuenta";
            }

            strQuery += orderQuery;
            Date fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getDateHoy();
            Date fFin = (fechaFinal != null) ? com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaFinal) : com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fInicial);
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                //createNamedQuery("Transaccion.CajeroRangoFecha");
                query = em.createQuery(strQuery);

                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
                if (codigoCajero != 0) {
                    query.setParameter("codigoCajero", cCajero);
                }
                if (numeroTransaccion != null) {
                    query.setParameter("numeroTransaccion", numeroTransaccion);
                }
                if (cuenta != null && !cuenta.equals("")) {
                    query.setParameter("cuenta", cuenta);
                }
                
                if (tablaHisto) {
                regs = new TxMultifuncionalEfeDTO(configApp.loggerApp).txMultiEfeHistoATxMultiEfe(query.getResultList());
                } else {
                    regs = query.getResultList();
                }
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

    public void guardarArchivoMultifuncionalEfectivo(Integer codigoCajero) throws EntityServicioExcepcion {

        //colocar query AQUI
        String strQuery = "select object(obj) from Txmultifuncionalefectivo obj "
                + "        where  obj.txmultifuncionalefectivoPK.fechacajero between :fechaInicial and :fechaFinal ";
        String orderQuery = "        order by obj.txmultifuncionalefectivoPK.fechacajero";

        try {
            if (codigoCajero != 0) {
                strQuery += "          and obj.txmultifuncionalefectivoPK.codigocajero =:codigoCajero";
            }

            strQuery += orderQuery;

            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                //createNamedQuery("Transaccion.CajeroRangoFecha");
                query = em.createQuery(strQuery);

                if (codigoCajero != 0) {
                    query.setParameter("codigoCajero", cCajero);
                }

                query.executeUpdate();

            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void cargarDiariosMultiEfectivo() throws EntityServicioExcepcion {

        try {
//                Query query = em.createNativeQuery("BEGIN SP_CARGARDIARIOEFECTIVOMULTI; END;");
//                query.executeUpdate();
            ejecutarSpDesdeJob("SP_CARGARDIARIOEFECTIVOMULTI;");
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void cargarDiariosMultiCheque() throws EntityServicioExcepcion {

        try {
            Query query = em.createNativeQuery("BEGIN SP_CARGARDIARIOCHEQUEMULTI; END;");
            query.executeUpdate();
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void cargarLogEventos() throws EntityServicioExcepcion {

        try {
            Query query = em.createNativeQuery("BEGIN SP_CARGARLOGCAJEROSMULTI; END;");
            query.executeUpdate();
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

}
