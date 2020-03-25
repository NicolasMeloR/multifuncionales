// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.base;

import java.util.List;
import java.io.FileNotFoundException;
import com.davivienda.sara.entitys.HostAtm;
import java.util.Collection;

public interface ProcesadorArchivoHostInterface
{
    Collection<HostAtm> getRegistrosHost() throws FileNotFoundException, IllegalArgumentException;
    
    Collection<HostAtm> getRegistrosHostXFilas(final int p0, final int p1) throws FileNotFoundException, IllegalArgumentException;
    
    List<String> getErroresDelProceso();
}
