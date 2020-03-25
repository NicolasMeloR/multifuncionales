/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operaci�n Cajeros Autom�ticos
 * Versi�n  1.0
 */
package com.davivienda.multifuncional.ws.reintegrosmulti.session;


import javax.ejb.Local;
import java.math.BigDecimal;

/**
 *
 * @author jjvargas
 */
@Local
public interface ReintegrosNotasWsSessionLocal {
    public String realizarNotaDebito(Integer codigoCajero, BigDecimal valor,String cuenta,Integer tipoCuenta,String usuario);
    public String realizarNotaCredito(Integer codigoCajero, BigDecimal valor,String cuenta,Integer tipoCuenta,String usuario,String talon);
}
