// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.administracion.programadortareas.session;

import com.davivienda.sara.config.SaraConfig;
import javax.ejb.Local;

@Local
public interface ProgramadorTareasDiarioElectronicoSession
{
    void configuraProgramador(final SaraConfig p0);
}
