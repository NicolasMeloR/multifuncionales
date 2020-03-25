// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.ajustes.session;

import com.davivienda.utilidades.ws.gestor.cliente.ResumenAjustes;
import java.math.BigDecimal;
import javax.ejb.Local;

@Local
public interface AjustesProcesosSessionLocal
{
    String realizarAjustePorSobrante(final String p0, final Integer p1, final Integer p2, final BigDecimal p3);
    
    String realizarAjustePorFaltante(final String p0, final Integer p1, final Integer p2, final BigDecimal p3);
    
    String realizarAjusteAumentoProvision(final String p0, final Integer p1, final Integer p2, final BigDecimal p3);
    
    String realizarAjusteDisminucionProvision(final String p0, final Integer p1, final Integer p2, final BigDecimal p3);
    
    String ajustarIngreso(final String p0, final Integer p1, final Integer p2, final BigDecimal p3, final short p4);
    
    String ajustarEgreso(final String p0, final Integer p1, final Integer p2, final BigDecimal p3, final short p4);
    
    ResumenAjustes[] consultarInformeTotalesATM(final Integer p0, final Integer p1);
    
    ResumenAjustes[] consultarResumenIDOTerminal(final Integer p0);
    
    ResumenAjustes[] consultarResumenIDOOficina(final Integer p0);
}
