// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tareas.contingencia.carguearchivos.session;

import com.davivienda.sara.entitys.Regcarguearchivo;
import java.util.Collection;
import java.util.Date;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tareas.contingencia.carguearchivos.servicio.ContingenciaCargueArchivosServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class ContingenciaCargueArchivosSessionBean extends BaseAdministracionTablas<Txmultifuncionalefectivo> implements ContingenciaCargueArchivosSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private ContingenciaCargueArchivosServicio contingenciaCargueArchivosServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.contingenciaCargueArchivosServicio = new ContingenciaCargueArchivosServicio(this.em);
        super.servicio = this.contingenciaCargueArchivosServicio;
    }
    
    @Override
    public void cargarArchivoCfamoMulti() throws EntityServicioExcepcion {
        this.contingenciaCargueArchivosServicio.cargarArchivoCfamoMulti();
    }
    
    @Override
    public void cargarArchivoGeatoMulti() throws EntityServicioExcepcion {
        this.contingenciaCargueArchivosServicio.cargarArchivoGeatoMulti();
    }
    
    @Override
    public void cargarArchivoOtbmoMulti() throws EntityServicioExcepcion {
        this.contingenciaCargueArchivosServicio.cargarArchivoOtbmoMulti();
    }
    
    @Override
    public void cargarArchivoHostDispensa() throws EntityServicioExcepcion {
        this.contingenciaCargueArchivosServicio.cargarArchivoHostDispensa();
    }
    
    @Override
    public void cargarArchivoHostMulti() throws EntityServicioExcepcion {
        this.contingenciaCargueArchivosServicio.cargarArchivoHostMulti();
    }
    
    @Override
    public void calcReintegrosDispensa() throws EntityServicioExcepcion {
        this.contingenciaCargueArchivosServicio.calcReintegrosDispensa();
    }
    
    @Override
    public Collection<Regcarguearchivo> getRegCargueArchivoPorFecha(final Date fechaInicial, final Date fechaFinal) throws EntityServicioExcepcion {
        return this.contingenciaCargueArchivosServicio.getRegCargueArchivoPorFecha(fechaInicial, fechaFinal);
    }
    
    @Override
    public void cargarDiarioEfectivoMulti() throws EntityServicioExcepcion {
        this.contingenciaCargueArchivosServicio.cargarDiarioEfectivoMulti();
    }
    
    @Override
    public void cargarDiarioChequeMulti() throws EntityServicioExcepcion {
        this.contingenciaCargueArchivosServicio.cargarDiarioChequeMulti();
    }
    
    @Override
    public void cargarLogMulti() throws EntityServicioExcepcion {
        this.contingenciaCargueArchivosServicio.cargarLogMulti();
    }
    
    @Override
    public void cargarReintegrosMultifuncional(final String fechaConsulta) throws EntityServicioExcepcion {
        this.contingenciaCargueArchivosServicio.cargarReintegrosMultifuncional(fechaConsulta);
    }
}
