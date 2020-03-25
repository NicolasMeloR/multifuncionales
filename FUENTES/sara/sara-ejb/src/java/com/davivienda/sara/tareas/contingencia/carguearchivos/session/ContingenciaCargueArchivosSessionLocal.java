// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tareas.contingencia.carguearchivos.session;

import com.davivienda.sara.entitys.Regcarguearchivo;
import java.util.Collection;
import java.util.Date;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface ContingenciaCargueArchivosSessionLocal extends AdministracionTablasInterface<Txmultifuncionalefectivo>
{
    void cargarArchivoCfamoMulti() throws EntityServicioExcepcion;
    
    void cargarArchivoGeatoMulti() throws EntityServicioExcepcion;
    
    void cargarArchivoOtbmoMulti() throws EntityServicioExcepcion;
    
    void cargarArchivoHostDispensa() throws EntityServicioExcepcion;
    
    void cargarArchivoHostMulti() throws EntityServicioExcepcion;
    
    void calcReintegrosDispensa() throws EntityServicioExcepcion;
    
    Collection<Regcarguearchivo> getRegCargueArchivoPorFecha(final Date p0, final Date p1) throws EntityServicioExcepcion;
    
    void cargarDiarioEfectivoMulti() throws EntityServicioExcepcion;
    
    void cargarDiarioChequeMulti() throws EntityServicioExcepcion;
    
    void cargarLogMulti() throws EntityServicioExcepcion;
    
    void cargarReintegrosMultifuncional(final String p0) throws EntityServicioExcepcion;
}
