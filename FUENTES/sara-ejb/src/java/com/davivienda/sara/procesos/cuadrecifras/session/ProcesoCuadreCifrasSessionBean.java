/*
 * ProcesoCuadreCifrasSessionBean.java
 *
 * Created on 25/09/2007, 09:32:24 AM
 * 
 * Babel Ver   :1.0
 */

package com.davivienda.sara.procesos.cuadrecifras.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.procesos.cuadrecifras.servicio.ProcesoCuadreCifrasServicio;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jjvargas
 */
@Stateless
@Local(value = ProcesoCuadreCifrasSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class ProcesoCuadreCifrasSessionBean implements ProcesoCuadreCifrasSessionLocal {
    
    @PersistenceContext
    private EntityManager em;

    private ProcesoCuadreCifrasServicio servicio;

    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
        servicio = new ProcesoCuadreCifrasServicio(em);
    }
    
    
    /**
     * Realiza la generación de los registros de Dia sgte Real y Inico Día Real para la fecha pasada
     * en el parámetro fecha
     *
     * @param fecha
     * @return
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public Integer procesarCuadreCifras(Calendar fecha) throws EntityServicioExcepcion {                
        return servicio.cudrarCajero(fecha);
    }
       

}
