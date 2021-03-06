/*
 * ProcesoCuadreCifrasSessionLocal.java
 *
 * Created on 25/09/2007, 09:32:24 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.davivienda.sara.procesos.reintegros.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.entitys.Reintegros;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author jjvargas
 */
@Local
public interface ProcesoReintegrosSessionLocal {

    /**
     * Realiza los procesos para calcular los reintegros en el parámetro fecha
     *
     * @param fecha
     * @return
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public Integer calcularReintegros(Calendar fecha);

    public Integer calcularReintegrosDiarioTEMP(Calendar fecha);

    public void actualizar(Reintegros objetoModificado) throws EntityServicioExcepcion;

    public void guardarReintegroNuevo(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario, String talon) throws EntityServicioExcepcion;

    public void guardarNotaDebito(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario) throws EntityServicioExcepcion;

    public void actualizarNotaDebito(Notasdebito objetoModificado) throws EntityServicioExcepcion;

    public Notasdebito guardarNotaDebito(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario, Date fecha, int estado) throws EntityServicioExcepcion;

    public void guardarNotaDebito(Notasdebito newNotaDebito);

    public void reversar(String codigoCajero, String fecha, String talon, Date fechaReversado);

}
