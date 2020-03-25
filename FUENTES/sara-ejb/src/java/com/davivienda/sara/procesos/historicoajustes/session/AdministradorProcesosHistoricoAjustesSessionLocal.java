/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.sara.procesos.historicoajustes.session;

import java.util.Calendar;  
import javax.ejb.Local;
import java.util.ArrayList; 
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author jjvargas
 */
@Local
public interface AdministradorProcesosHistoricoAjustesSessionLocal {

//    public void guardarHistoricoAjustes(String usuario, Integer codigoCajero, Integer codigoOcca, String tipoAjuste,String cuenta, Date fecha, BigDecimal valor,String talon, String error, String descripcionError);
      public void guardarHistoricoAjustes(String usuario, Integer codigoCajero, Integer codigoOcca, String tipoAjuste, Date fecha, BigDecimal valor,String talon, String error, String descripcionError);
   

 
    
    }
