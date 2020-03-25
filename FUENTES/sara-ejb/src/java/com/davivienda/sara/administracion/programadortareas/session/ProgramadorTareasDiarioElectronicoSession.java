/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.administracion.programadortareas.session;

import com.davivienda.sara.config.SaraConfig;
import javax.ejb.Local;

/**
 * ProgramadorTareasDiarioElectronicoSession - 16/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface ProgramadorTareasDiarioElectronicoSession {

    void configuraProgramador(SaraConfig appConfiguracion);
}
