// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.ws.cuadrecifrasmulti.servicio;

import com.davivienda.utilidades.ws.gestor.cliente.ResumenAjustesMulti;
import java.math.BigDecimal;
import javax.ejb.Local;

@Local
public interface CuadreCifrasMultiWsSessionLocal
{
    String realizarAjustePorSobrante(final String p0, final Integer p1, final Integer p2, final BigDecimal p3);
    
    String realizarAjustePorFaltante(final String p0, final Integer p1, final Integer p2, final BigDecimal p3);
    
    String realizarAjustePorSobranteArqueo(final String p0, final Integer p1, final Integer p2, final BigDecimal p3, final String p4);
    
    String realizarAjustePorFaltanteArqueo(final String p0, final Integer p1, final Integer p2, final BigDecimal p3, final String p4);
    
    String realizarDisminucionDeposito(final String p0, final long p1, final Integer p2, final BigDecimal p3, final Integer p4);
    
    ResumenAjustesMulti[] consultarInformeTotalesMultifuncional(final Integer p0, final Integer p1, final Integer p2, final Integer p3);
}
