package com.davivienda.sara.tablas.binentidad.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.BinEntidad;
import javax.ejb.Local;



@Local
public interface BinEntidadSessionLocal extends AdministracionTablasInterface<BinEntidad>{
    public BinEntidad getEntidadXBin(String bin) throws Exception;
    
}
