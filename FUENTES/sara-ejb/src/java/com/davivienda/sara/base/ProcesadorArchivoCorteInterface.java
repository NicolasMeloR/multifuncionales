package com.davivienda.sara.base;

import com.davivienda.sara.entitys.BilletajeCajero;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

/**
 * ProcesadorArchivoDiarioElectronicoInterface - 22/08/2008
 * Descripci�n : 
 * Versi�n : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public interface ProcesadorArchivoCorteInterface {

    public Collection<BilletajeCajero> getRegistrosBilletajeCajero() throws FileNotFoundException, IllegalArgumentException;

    public List<String> getErroresDelProceso();
}
