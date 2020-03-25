// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.base;

import java.util.List;
import com.davivienda.sara.entitys.TransaccionTemp;
import java.io.FileNotFoundException;
import com.davivienda.sara.entitys.DiarioelectronicoTemp;
import java.util.Collection;

public interface ProcesadorArchivoDiarioElectronicoTEMPInterface
{
    Collection<DiarioelectronicoTemp> getRegistrosDiarioElectronicoTemp() throws FileNotFoundException, IllegalArgumentException;
    
    Collection<TransaccionTemp> getRegistrosTipoTransaccionTemp() throws FileNotFoundException, IllegalArgumentException;
    
    List<String> getErroresDelProceso();
}
