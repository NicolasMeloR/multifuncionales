package com.davivienda.sara.base;

import com.davivienda.sara.entitys.DiarioelectronicoTemp;
import com.davivienda.sara.entitys.TransaccionTemp;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

/**
 * ProcesadorArchivoDiarioElectronicoInterface - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public interface ProcesadorArchivoDiarioElectronicoTEMPInterface {

    public Collection<DiarioelectronicoTemp> getRegistrosDiarioElectronicoTemp() throws FileNotFoundException, IllegalArgumentException;

    public Collection<TransaccionTemp> getRegistrosTipoTransaccionTemp() throws FileNotFoundException, IllegalArgumentException;

    public List<String> getErroresDelProceso();
}
