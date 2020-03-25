package com.davivienda.sara.base;

import com.davivienda.sara.entitys.DiarioElectronico;
import com.davivienda.sara.entitys.transaccion.Transaccion;
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
public interface ProcesadorArchivoDiarioElectronicoInterface {

    public Collection<DiarioElectronico> getRegistrosDiarioElectronico() throws FileNotFoundException, IllegalArgumentException;

    public Collection<Transaccion> getRegistrosTipoTransaccion() throws FileNotFoundException, IllegalArgumentException;

    public List<String> getErroresDelProceso();
}
