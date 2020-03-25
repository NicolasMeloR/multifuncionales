/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.administracion.session;

import com.davivienda.sara.administracion.tareas.programadas.excepcion.TimerServiceExcepcion;
import com.davivienda.sara.config.SaraConfig;
import java.io.IOException;
import java.text.ParseException;
import javax.ejb.Local;
import org.quartz.SchedulerException;

/**
 * SaraConfiguracionSession - 16/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Local
public interface SaraConfiguracionSession {
    
    void iniciarConfiguracion(String dirConfiguracion);

    public void cambiarPropiedadLevel(String nuevoNivel);

    public void finalizarAplicacion();

    public SaraConfig getAppConfig();
    
    
}
