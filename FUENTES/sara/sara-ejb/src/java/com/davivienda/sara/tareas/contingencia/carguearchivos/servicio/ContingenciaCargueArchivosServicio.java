// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tareas.contingencia.carguearchivos.servicio;

import javax.persistence.Query;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.entitys.Regcarguearchivo;
import java.util.Collection;
import java.util.Date;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import com.davivienda.sara.base.BaseEntityServicio;

public class ContingenciaCargueArchivosServicio extends BaseEntityServicio<Txmultifuncionalefectivo> implements AdministracionTablasInterface<Txmultifuncionalefectivo>
{
    public ContingenciaCargueArchivosServicio(final EntityManager em) {
        super(em, Txmultifuncionalefectivo.class);
    }
    
    public void cargarArchivoCfamoMulti() throws EntityServicioExcepcion {
        try {
            this.ejecutarSpDesdeJob("ADMINATM.SP_CARGAR_CFAMO002MULTI;");
        }
        catch (IllegalStateException ex) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void cargarArchivoGeatoMulti() throws EntityServicioExcepcion {
        try {
            this.ejecutarSpDesdeJob("ADMINATM.SP_CARGARARCHIVOGEATO003;");
        }
        catch (IllegalStateException ex) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void cargarArchivoOtbmoMulti() throws EntityServicioExcepcion {
        try {
            this.ejecutarSpDesdeJob("ADMINATM.SP_CARGARARCHIVOOTBMO003;");
        }
        catch (IllegalStateException ex) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void cargarArchivoHostDispensa() throws EntityServicioExcepcion {
        try {
            this.ejecutarSpDesdeJob("ADMINATM.SP_CARGARARCHIVOHOSTDISPENSA;");
        }
        catch (IllegalStateException ex) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void cargarArchivoHostMulti() throws EntityServicioExcepcion {
        try {
            this.ejecutarSpDesdeJob("ADMINATM.SP_CARGARARCHIVOHOSTMULTI;");
        }
        catch (IllegalStateException ex) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void calcReintegrosDispensa() throws EntityServicioExcepcion {
        try {
            this.ejecutarSpDesdeJob("ADMINATM.SP_REINTEGROS;");
        }
        catch (IllegalStateException ex) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public Collection<Regcarguearchivo> getRegCargueArchivoPorFecha(Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {
        Collection<Regcarguearchivo> items = null;
        if (fechaInicial == null) {
            fechaInicial = Fecha.getDateAyer();
        }
        if (fechaFinal == null) {
            fechaFinal = fechaInicial;
        }
        if (fechaFinal.before(fechaInicial)) {
            throw new IllegalArgumentException("La fecha final debe ser mayor a la fecha Inicial");
        }
        try {
            Query query = null;
            query = this.em.createNamedQuery("Regcarguearchivo.RangoFecha").setHint("toplink.refresh", (Object)"true");
            query.setParameter("fechaInicial", (Object)fechaInicial);
            query.setParameter("fechaFinal", (Object)fechaFinal);
            items = (Collection<Regcarguearchivo>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
    
    public void cargarDiarioEfectivoMulti() throws EntityServicioExcepcion {
        try {
            this.ejecutarSpDesdeJob("ADMINATM.SP_CARGARDIARIOEFECTIVOMULTI;");
        }
        catch (IllegalStateException ex) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void cargarDiarioChequeMulti() throws EntityServicioExcepcion {
        try {
            final Query query = this.em.createNativeQuery("BEGIN ADMINATM.SP_CARGARDIARIOCHEQUEMULTI; END;");
            query.executeUpdate();
        }
        catch (IllegalStateException ex) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void cargarLogMulti() throws EntityServicioExcepcion {
        try {
            final Query query = this.em.createNativeQuery("BEGIN ADMINATM.SP_CARGARLOGCAJEROSMULTI; END;");
            query.executeUpdate();
        }
        catch (IllegalStateException ex) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void cargarReintegrosMultifuncional(final String fechaConsulta) throws EntityServicioExcepcion {
        try {
            this.ejecutarSpDesdeJob("ADMINATM.CARGUETXREINTEGROSMULTI_INIT(''" + fechaConsulta + "'');");
        }
        catch (IllegalStateException ex) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ContingenciaCargueArchivosServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
}
