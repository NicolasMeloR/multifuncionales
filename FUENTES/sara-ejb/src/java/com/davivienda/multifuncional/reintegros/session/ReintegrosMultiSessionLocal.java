/*
 * ProcesoCuadreCifrasSessionLocal.java
 *
 * Created on 25/09/2007, 09:32:24 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.davivienda.multifuncional.reintegros.session;


import java.util.Calendar;
import java.util.Date;
import javax.ejb.Local;


/**
 *
 * @author jjvargas
 */
@Local
public interface ReintegrosMultiSessionLocal {
   
     public Integer calcularReintegros(Calendar fecha , Date fechaHisto)  ;
    
 
    
}
