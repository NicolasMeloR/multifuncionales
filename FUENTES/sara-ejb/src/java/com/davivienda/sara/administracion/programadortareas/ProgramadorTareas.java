/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */

package com.davivienda.sara.administracion.programadortareas;


import com.davivienda.sara.config.SaraConfig;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * ProgramadorTareas
 * Descripción : Clase base para los programadores de tareas
 * Fecha       : 9/02/2008 12:24:44 PM
 * @author     : jjvargas
 **/
public class ProgramadorTareas {
    
    protected SaraConfig appConfiguracion;
    protected static final long DIA_EN_MILISEGUNDOS = 86400000;            
    protected static final long HORA_EN_MILESEGUNDOS = 60 * 60 * 1000;
    protected static final long MINUTO_EN_MILESEGUNDOS = 60 * 1000;
    
    
    /**
     * Retorna los milisegundos que faltan para la hora programada del timer, calculada a partir del momento en que se inicia Servidor
     * @param horaProgramacionTimer Hora en que debe iniciar el timer
     * @return
     */
    protected long getHoraInicioProgramador(int horaProgramacionTimer){
        // Creo el objeto calendario actual para la zona ES,CO
        Locale loc = com.davivienda.utilidades.conversion.Fecha.ZONA_FECHA;
        Calendar cal = new GregorianCalendar(loc);
        cal.setTime(new Date());

        // calculo el tiempo de experiación del timer de acuerdo con la hora en que se inicia la aplicación
        long horaActualMili = (cal.get(Calendar.HOUR_OF_DAY) * HORA_EN_MILESEGUNDOS) + (cal.get(Calendar.MINUTE) * MINUTO_EN_MILESEGUNDOS) + (cal.get(Calendar.SECOND) * 1000);
        long horaInicio = 0;

        // Calculo el tiempo que falta para que se cumpla la hora normal del proceso 
        if (horaActualMili < horaProgramacionTimer) {
            horaInicio = horaProgramacionTimer - horaActualMili;
        } else {
            horaInicio = (24 * HORA_EN_MILESEGUNDOS) - horaActualMili + horaProgramacionTimer;
        }
        
        return horaInicio;
        
    }
    

}
