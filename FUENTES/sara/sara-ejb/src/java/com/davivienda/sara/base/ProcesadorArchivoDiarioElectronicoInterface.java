// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.base;

import java.util.List;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import java.io.FileNotFoundException;
import com.davivienda.sara.entitys.DiarioElectronico;
import java.util.Collection;

public interface ProcesadorArchivoDiarioElectronicoInterface
{
    Collection<DiarioElectronico> getRegistrosDiarioElectronico() throws FileNotFoundException, IllegalArgumentException;
    
    Collection<Transaccion> getRegistrosTipoTransaccion() throws FileNotFoundException, IllegalArgumentException;
    
    List<String> getErroresDelProceso();
}
