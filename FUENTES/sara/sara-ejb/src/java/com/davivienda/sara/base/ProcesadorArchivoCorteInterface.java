// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.base;

import java.util.List;
import java.io.FileNotFoundException;
import com.davivienda.sara.entitys.BilletajeCajero;
import java.util.Collection;

public interface ProcesadorArchivoCorteInterface
{
    Collection<BilletajeCajero> getRegistrosBilletajeCajero() throws FileNotFoundException, IllegalArgumentException;
    
    List<String> getErroresDelProceso();
}
