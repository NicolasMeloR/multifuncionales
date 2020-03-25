// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tareas.regcargue.session;

import java.util.Collection;
import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Date;
import javax.ejb.Local;

@Local
public interface AdminTareasRegCargueArchivoSessionLocal
{
    void guardarRegCargueArchivo(final String p0, final boolean p1, final String p2, final String p3, final Date p4, final String p5, final boolean p6, final String p7) throws EntityServicioExcepcion, IllegalArgumentException;
    
    void actualizar(final Regcarguearchivo p0) throws EntityServicioExcepcion;
    
    Regcarguearchivo buscarPorArchivoFecha(final String p0, final String p1, final boolean p2) throws EntityServicioExcepcion;
    
    Collection<Regcarguearchivo> getRegCargueArchivoPorFecha(final Date p0, final Date p1) throws EntityServicioExcepcion;
}
