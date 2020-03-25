// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.diarioelectronico.session;

import java.util.Calendar;
import javax.ejb.Local;

@Local
public interface AdministradorProcesosSessionLocal
{
    Integer cargarArchivo(final Integer p0, final Calendar p1, final String p2);
    
    Integer cargarCiclo(final Calendar p0);
    
    Integer cargarCicloTemp(final Calendar p0);
    
    Integer cargarDiarioElectronicoTemp(final Integer p0, final Calendar p1, final String p2);
    
    Integer cargarArchivoTemp(final Integer p0, final Calendar p1, final String p2);
    
    Integer cargarArchivoSoloDiario(final Integer p0, final Calendar p1, final String p2);
}
