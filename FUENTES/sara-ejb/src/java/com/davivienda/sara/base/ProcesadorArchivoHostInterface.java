package com.davivienda.sara.base;

import com.davivienda.sara.entitys.HostAtm;
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
public interface ProcesadorArchivoHostInterface {

    public Collection<HostAtm> getRegistrosHost() throws FileNotFoundException, IllegalArgumentException;
    public Collection<HostAtm> getRegistrosHostXFilas(int regInicial,int cantidadRegistros) throws FileNotFoundException, IllegalArgumentException;
    

    public List<String> getErroresDelProceso();
}
