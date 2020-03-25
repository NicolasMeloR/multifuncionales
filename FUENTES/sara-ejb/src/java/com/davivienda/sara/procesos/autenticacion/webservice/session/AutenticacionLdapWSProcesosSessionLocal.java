/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.sara.procesos.autenticacion.webservice.session;


import javax.ejb.Local;
import java.math.BigDecimal;

/**
 *
 * @author jjvargas
 */
@Local
public interface AutenticacionLdapWSProcesosSessionLocal 
{


    public String autenticarLdap(String usuario,String clave);
    
}
