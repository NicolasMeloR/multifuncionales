/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.sara.procesos.diarioelectronico.session;

import java.util.Calendar;
import javax.ejb.Local;


/**
 *
 * @author jjvargas
 */
@Local
public interface AdministradorProcesosSessionLocal {

    public Integer cargarArchivo( Integer codigoCajero,Calendar fecha,String nombreArchivo);

    public Integer cargarCiclo(Calendar fecha);
    
    public Integer cargarCicloTemp(Calendar fecha);
     
    public Integer cargarDiarioElectronicoTemp(Integer codigoCajero,Calendar fecha,String nombreArchivo);
    
     public Integer cargarArchivoTemp(Integer codigoCajero,Calendar fecha,String nombreArchivo);
     
     public Integer cargarArchivoSoloDiario(Integer codigoCajero,Calendar fecha,String nombreArchivo);

    
    
 
    
    }
