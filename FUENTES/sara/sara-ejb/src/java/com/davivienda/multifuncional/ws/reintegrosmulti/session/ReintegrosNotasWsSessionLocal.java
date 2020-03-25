// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.ws.reintegrosmulti.session;

import java.math.BigDecimal;
import javax.ejb.Local;

@Local
public interface ReintegrosNotasWsSessionLocal
{
    String realizarNotaDebito(final Integer p0, final BigDecimal p1, final String p2, final Integer p3, final String p4);
    
    String realizarNotaCredito(final Integer p0, final BigDecimal p1, final String p2, final Integer p3, final String p4, final String p5);
}
