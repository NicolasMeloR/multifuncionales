package com.davivienda.sara.diarioelectronico.general;
import com.davivienda.sara.diarioelectronico.general.bean.DiarioElectronicoBean;
import java.util.Collection;

/**
 * DiarioElectronicoHelperInterface - 27/08/2008
 * Descripcion : 
 * Version : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public interface DiarioElectronicoHelperInterface {
    
    
    public String obtenerDatos();
   // public Collection<DiarioElectronicoBean> obtenerDatosCollectionDE();
    public String generarDiarioelectronicoXML();


}
