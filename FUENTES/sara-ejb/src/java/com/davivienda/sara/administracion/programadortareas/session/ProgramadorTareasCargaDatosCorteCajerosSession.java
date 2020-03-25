/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operaci�n Cajeros Autom�ticos
 * Versi�n  1.0
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
