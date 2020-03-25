package com.davivienda.sara.tareas.contingencia.carguearchivos.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;

import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import com.davivienda.utilidades.conversion.Fecha;
import java.sql.CallableStatement;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 * TxMultifuncionalEfectivoServicio Descripción : Versión : 1.0
 *
 * @author P-MDRUIZ Davivienda 2011
 */
public class ContingenciaCargueArchivosServicio extends BaseEntityServicio<Txmultifuncionalefectivo> implements AdministracionTablasInterface<Txmultifuncionalefectivo> {

    public ContingenciaCargueArchivosServicio(EntityManager em) {
        super(em, Txmultifuncionalefectivo.class);
    }

    public void cargarArchivoCfamoMulti() throws EntityServicioExcepcion {

        try {
//                Query query = em.createNativeQuery("BEGIN ADMINATM.SP_CARGAR_CFAMO002MULTI; END;");
//                query.executeUpdate();
            ejecutarSpDesdeJob("ADMINATM.SP_CARGAR_CFAMO002MULTI;");
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void cargarArchivoGeatoMulti() throws EntityServicioExcepcion {

        try {
//                Query query = em.createNativeQuery("BEGIN ADMINATM.SP_CARGARARCHIVOGEATO003; END;");
//                query.executeUpdate();
            ejecutarSpDesdeJob("ADMINATM.SP_CARGARARCHIVOGEATO003;");
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void cargarArchivoOtbmoMulti() throws EntityServicioExcepcion {

        try {
//                Query query = em.createNativeQuery("BEGIN ADMINATM.SP_CARGARARCHIVOOTBMO003; END;");
//                query.executeUpdate();
            ejecutarSpDesdeJob("ADMINATM.SP_CARGARARCHIVOOTBMO003;");
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void cargarArchivoHostDispensa() throws EntityServicioExcepcion {

        try {
//                Query query = em.createNativeQuery("BEGIN ADMINATM.SP_CARGARARCHIVOHOSTDISPENSA; END;");
//                query.executeUpdate();
            ejecutarSpDesdeJob("ADMINATM.SP_CARGARARCHIVOHOSTDISPENSA;");
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void cargarArchivoHostMulti() throws EntityServicioExcepcion {

        try {
//                Query query = em.createNativeQuery("BEGIN ADMINATM.SP_CARGARARCHIVOHOSTMULTI; END;");
//                query.executeUpdate();
            ejecutarSpDesdeJob("ADMINATM.SP_CARGARARCHIVOHOSTMULTI;");
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void calcReintegrosDispensa() throws EntityServicioExcepcion {

        try {
//                Query query = em.createNativeQuery("BEGIN ADMINATM.SP_REINTEGROS; END;");
//                query.executeUpdate();
            ejecutarSpDesdeJob("ADMINATM.SP_REINTEGROS;");
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public Collection<Regcarguearchivo> getRegCargueArchivoPorFecha(Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {

        Collection<Regcarguearchivo> items = null;
        if (fechaInicial == null) {
            fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
        }
        if (fechaFinal == null) {
            fechaFinal = fechaInicial;
        }
        if (fechaFinal.before(fechaInicial)) {
            throw new IllegalArgumentException("La fecha final debe ser mayor a la fecha Inicial");
        }
        try {
            Query query = null;
            //setHint("toplink.refresh" se utiliza para refrescar las consultas
            query = em.createNamedQuery("Regcarguearchivo.RangoFecha").setHint("toplink.refresh", "true");
            query.setParameter("fechaInicial", fechaInicial);
            query.setParameter("fechaFinal", fechaFinal);
            items = query.getResultList();

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return items;

    }

    public void cargarDiarioEfectivoMulti() throws EntityServicioExcepcion {

        try {
//                Query query = em.createNativeQuery("BEGIN ADMINATM.SP_CARGARDIARIOEFECTIVOMULTI; END;");
//                query.executeUpdate();
            ejecutarSpDesdeJob("ADMINATM.SP_CARGARDIARIOEFECTIVOMULTI;");
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void cargarDiarioChequeMulti() throws EntityServicioExcepcion {

        try {
            Query query = em.createNativeQuery("BEGIN ADMINATM.SP_CARGARDIARIOCHEQUEMULTI; END;");
            query.executeUpdate();
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void cargarLogMulti() throws EntityServicioExcepcion {

        try {
            Query query = em.createNativeQuery("BEGIN ADMINATM.SP_CARGARLOGCAJEROSMULTI; END;");
            query.executeUpdate();
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

    public void cargarReintegrosMultifuncional(String fechaConsulta) throws EntityServicioExcepcion {

        try {
            ejecutarSpDesdeJob("ADMINATM.CARGUETXREINTEGROSMULTI_INIT(''" + fechaConsulta + "'');");

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

}
