// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.edccargue.session;

import java.util.Calendar;
import java.util.ArrayList;
import javax.ejb.Local;

@Local
public interface AdministradorProcesosEdcCargueSessionLocal
{
    Integer registrarCicloEdcCargue(final ArrayList p0, final String p1, final Calendar p2, final Integer p3, final boolean p4);
    
    Integer registrarArchivoEdcCargue(final Integer p0, final String p1, final Integer p2, final Integer p3, final Integer p4, final boolean p5);
    
    Integer actualizarEdcCargue(final ArrayList p0, final String p1, final Calendar p2);
}
