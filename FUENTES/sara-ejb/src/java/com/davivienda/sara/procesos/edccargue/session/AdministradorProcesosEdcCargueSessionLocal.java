/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.sara.procesos.edccargue.session;

import java.util.Calendar;  
import javax.ejb.Local;
import java.util.ArrayList; 



/**
 *
 * @author jjvargas
 */
@Local
public interface AdministradorProcesosEdcCargueSessionLocal 
{

    public Integer registrarCicloEdcCargue(ArrayList nombreArchivos,String nombreZip,Calendar fecha,Integer estado,boolean actualiza);
    public Integer registrarArchivoEdcCargue(Integer codigoCajero,String nombreArchivo,Integer tamano,Integer ciclo,Integer estado,boolean actualiza);
    public Integer actualizarEdcCargue(ArrayList nombreArchivos,String nombreZip,Calendar fecha);
}
