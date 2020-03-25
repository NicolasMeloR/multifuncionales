// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.reintegros.notas.session;

import java.math.BigDecimal;
import javax.ejb.Local;

@Local
public interface ReintegrosNotasProcesosSessionLocal
{
    String realizarNotaDebito(final Integer p0, final BigDecimal p1, final String p2, final Integer p3, final String p4,String p5);
    
    String realizarNotaCredito(final Integer p0, final BigDecimal p1, final String p2, final Integer p3, final String p4, final String p5);
    
    public String realizarNotaCredito(Integer codigoCajero, BigDecimal valor, String cuenta, Integer tipoCuenta, String usuario, String talon,String concepto);
}
