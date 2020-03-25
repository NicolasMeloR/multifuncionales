package com.davivienda.sara.base;

import com.davivienda.sara.entitys.TotalesEstacion;
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
public interface ProcesadorArchivoTotalesEstacionInterface {

    public Collection<TotalesEstacion> getRegistrosTotalesEstacion() throws FileNotFoundException, IllegalArgumentException;

    public List<String> getErroresDelProceso();
}
