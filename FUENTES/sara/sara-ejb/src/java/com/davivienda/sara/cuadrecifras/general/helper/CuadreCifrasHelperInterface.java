// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.cuadrecifras.general.helper;

import com.davivienda.sara.cuadrecifras.general.bean.MovimientoCuadreCifrasBean;
import java.util.Collection;
import java.util.Calendar;

public interface CuadreCifrasHelperInterface
{
    Collection<MovimientoCuadreCifrasBean> obtenerDatosCollectionCC(final Calendar p0, final Integer p1) throws IllegalAccessException;
    
    void procesoInformeDescuadre(final Calendar p0, final Integer p1) throws IllegalAccessException;
}
