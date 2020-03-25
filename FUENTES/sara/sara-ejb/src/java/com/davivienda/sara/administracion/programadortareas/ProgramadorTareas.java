// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.administracion.programadortareas;

import java.util.Calendar;
import java.util.Locale;
import java.util.Date;
import java.util.GregorianCalendar;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.config.SaraConfig;

public class ProgramadorTareas
{
    protected SaraConfig appConfiguracion;
    protected static final long DIA_EN_MILISEGUNDOS = 86400000L;
    protected static final long HORA_EN_MILESEGUNDOS = 3600000L;
    protected static final long MINUTO_EN_MILESEGUNDOS = 60000L;
    
    protected long getHoraInicioProgramador(final int horaProgramacionTimer) {
        final Locale loc = Fecha.ZONA_FECHA;
        final Calendar cal = new GregorianCalendar(loc);
        cal.setTime(new Date());
        final long horaActualMili = cal.get(11) * 3600000L + cal.get(12) * 60000L + cal.get(13) * 1000;
        long horaInicio = 0L;
        if (horaActualMili < horaProgramacionTimer) {
            horaInicio = horaProgramacionTimer - horaActualMili;
        }
        else {
            horaInicio = 86400000L - horaActualMili + horaProgramacionTimer;
        }
        return horaInicio;
    }
}
