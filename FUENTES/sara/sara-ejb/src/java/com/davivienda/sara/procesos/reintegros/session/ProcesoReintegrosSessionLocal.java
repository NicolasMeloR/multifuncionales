// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.reintegros.session;

import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Reintegros;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.Local;

@Local
public interface ProcesoReintegrosSessionLocal
{
    Integer calcularReintegros(final Calendar p0);
    
    Integer calcularReintegrosDiarioTEMP(final Calendar p0);
    
    void actualizar(final Reintegros p0) throws EntityServicioExcepcion;
    
    void guardarReintegroNuevo(final Integer p0, final Long p1, final String p2, final Integer p3, final String p4, final String p5) throws EntityServicioExcepcion;
    
    void guardarNotaDebito(final Integer p0, final Long p1, final String p2, final Integer p3, final String p4) throws EntityServicioExcepcion;
    
    void actualizarNotaDebito(final Notasdebito p0) throws EntityServicioExcepcion;
    
    public Notasdebito guardarNotaDebito(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario, Date fecha, int estado) throws EntityServicioExcepcion;

    public void guardarNotaDebito(Notasdebito newNotaDebito);

    public void reversar(String codigoCajero, String fecha, String talon, Date fechaReversado);
    
}
