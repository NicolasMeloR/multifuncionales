/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operaci�n Cajeros Autom�ticos
 * Versi�n  1.0
 */
package com.davivienda.sara.cuadrecifras.session;

import java.util.Calendar;
import javax.ejb.Local;

/**
 *
 * @author jjvargas
 */
@Local
public interface CuadreCifrasCargasSessionLocal 

{

    public Integer cargarArchivoCorte( Calendar fecha, boolean cargueautomatico);

    public Integer cargarArchivoTotalesEgresos(Calendar fecha, boolean cargueautomatico);

    public Integer cargarArchivoProvisiones( Calendar fecha, boolean cargueautomatico);
    
   
    
}
