// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.cuadrecifras.session;

import java.util.Calendar;
import javax.ejb.Local;

@Local
public interface CuadreCifrasCargasSessionLocal
{
    Integer cargarArchivoCorte(final Calendar p0, final boolean p1);
    
    Integer cargarArchivoTotalesEgresos(final Calendar p0, final boolean p1);
    
    Integer cargarArchivoProvisiones(final Calendar p0, final boolean p1);
}
