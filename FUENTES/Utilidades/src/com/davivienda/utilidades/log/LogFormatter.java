package com.davivienda.utilidades.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        
        StringBuilder log = new StringBuilder();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS");
        log.append(format.format(record.getMillis()).toUpperCase());
        log.append(" Thread:");
        log.append(record.getThreadID());
        log.append(" ");	
        log.append(record.getLevel());
        log.append(": ");
        log.append(record.getMessage());
        log.append(System.getProperty("line.separator"));

        if (record.getThrown() != null) {
            ByteArrayOutputStream baos = null;
            PrintStream ps = null;
            try {
                baos = new ByteArrayOutputStream();
                ps = new PrintStream(baos, true, "utf-8");
                record.getThrown().printStackTrace(ps);
                log.append(baos.toString("utf-8"));
            } catch (UnsupportedEncodingException e) {
                log.append("Cause: ");
                log.append(record.getThrown().getMessage());
            } finally {
                if (ps != null) {
                    ps.close();
                }
                if (baos != null) {
                    try {
                        baos.close();
                    } catch (IOException e) {
                    }
                }
            }
            log.append(System.getProperty("line.separator"));
        }

        return log.toString();

    }
}
