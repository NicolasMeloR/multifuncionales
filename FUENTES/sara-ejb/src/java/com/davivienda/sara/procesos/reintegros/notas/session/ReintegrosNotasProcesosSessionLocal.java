/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.sara.procesos.reintegros.notas.session;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author jjvargas
 */
@Local
public interface ReintegrosNotasProcesosSessionLocal {

    public String realizarNotaDebito(Integer codigoCajero, BigDecimal valor, String cuenta, Integer tipoCuenta, String usuario);

    public String realizarNotaDebito(Integer codigoCajero, BigDecimal valor, String cuenta, Integer tipoCuenta, String usuario,Date fecha);
    
    public String realizarNotaCredito(Integer codigoCajero, BigDecimal valor, String cuenta, Integer tipoCuenta, String usuario, String talon,String concepto);

}
