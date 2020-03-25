/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */

package com.davivienda.sara.administracion.programadortareas.session;

import com.davivienda.sara.config.SaraConfig;
import javax.ejb.Local;
/**
 *
 * @author jjvargas
 */
@Local
public interface ProgramadorTareasCargaDatosCorteCajerosSession {
    
        void configuraProgramador(SaraConfig appConfiguracion);
    
}
