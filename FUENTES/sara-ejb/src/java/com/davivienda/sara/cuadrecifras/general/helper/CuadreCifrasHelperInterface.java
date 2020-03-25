package com.davivienda.sara.cuadrecifras.general.helper;
import com.davivienda.sara.cuadrecifras.general.bean.MovimientoCuadreCifrasBean;
import java.util.Calendar;
import java.util.Collection;

/**
 * DiarioElectronicoHelperInterface - 27/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public interface CuadreCifrasHelperInterface {
    
    
    public Collection<MovimientoCuadreCifrasBean> obtenerDatosCollectionCC(Calendar fechaInicial, Integer codigoOcca) throws IllegalAccessException;
    //public String generarDiarioelectronicoXML();

    
    public void procesoInformeDescuadre(Calendar fechaInicial, Integer codigoOcca) throws IllegalAccessException;

}
