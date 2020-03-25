// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.reintegros.session;

import java.util.Date;
import java.util.Calendar;
import javax.ejb.Local;

@Local
public interface ReintegrosMultiSessionLocal
{
    Integer calcularReintegros(final Calendar p0, final Date p1);
}
