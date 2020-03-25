package com.davivienda.utilidades.log;

import com.davivienda.utilidades.Constantes;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class SaraAppLogFilter implements Filter {

    @Override
    public boolean isLoggable(LogRecord lr) {
        return Constantes.LOGGER_APP.equals(lr.getLoggerName());
    }
}
