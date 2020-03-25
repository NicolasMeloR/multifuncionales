// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.administracion.session;

import com.davivienda.sara.config.SaraConfig;
import javax.ejb.Local;

@Local
public interface SaraConfiguracionSession
{
    void iniciarConfiguracion(final String p0);
    
    void cambiarPropiedadLevel(final String p0);
    
    void finalizarAplicacion();
    
    SaraConfig getAppConfig();
}
