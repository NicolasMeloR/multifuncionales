/*
 * ProcesoCuadreCifrasSessionLocal.java
 *
 * Created on 25/09/2007, 09:32:24 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.davivienda.sara.procesos.cargues.masivos.reintegros.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.CarmasReintegrosTemp;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.entitys.Reintegros;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;


/**
 *
 * @author jjvargas
 */
@Local
public interface CargarReintegrosMasivosSessionLocal  {

     /**
     * Realiza  los procesos para calcular los reintegros
     * en el parámetro fecha
     *
     * @param reintegrosList is the list of reintegros for filter and apply when this is a valid reintegro.
     * @return reintegrosList whit a response process data
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion 
     */
     public List<CarmasReintegrosTemp> ejecutarCargue(List<CarmasReintegrosTemp> reintegrosList, String usuarioEnSesion);
    
}
