// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.reintegros.filtro.host.session;

import java.io.FileNotFoundException;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Calendar;
import javax.ejb.Local;

@Local
public interface ProcesoHostSessionLocal
{
    Integer CargarArchivo(final Calendar p0) throws EntityServicioExcepcion, FileNotFoundException, IllegalArgumentException;
}
