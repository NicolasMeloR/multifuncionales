package com.davivienda.utilidades.log;

import com.davivienda.utilidades.Constantes;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class SaraAccesoLogFilter implements Filter {

    @Override
    public boolean isLoggable(LogRecord lr) {
        return Constantes.LOGGER_ACCESO.equals(lr.getLoggerName());
    }
}
