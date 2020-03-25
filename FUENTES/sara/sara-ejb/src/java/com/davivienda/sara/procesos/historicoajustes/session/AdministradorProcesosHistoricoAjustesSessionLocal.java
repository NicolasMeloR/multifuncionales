// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.historicoajustes.session;

import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Local;

@Local
public interface AdministradorProcesosHistoricoAjustesSessionLocal
{
    void guardarHistoricoAjustes(final String p0, final Integer p1, final Integer p2, final String p3, final Date p4, final BigDecimal p5, final String p6, final String p7, final String p8);
}
