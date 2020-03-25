/*
 * ProcesoCuadreCifrasSessionBean.java
 *
 * Created on 25/09/2007, 09:32:24 AM
 * 
 * Babel Ver   :1.0
 */

package com.davivienda.multifuncional.reintegros.session;


import com.davivienda.multifuncional.reintegros.servicio.ReintegrosMultiServicio;

import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;

/**
 *
 * @author jjvargas
 */
@Stateless
@Local(value = ReintegrosMultiSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class ReintegrosMultiSessionBean implements ReintegrosMultiSessionLocal {
   @Resource private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    private ReintegrosMultiServicio servicio;


    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
       
        servicio = new ReintegrosMultiServicio(em);
       
    }
    


    
    

      /**
     * Realiza  los procesos para calcular los reintegros
     * en el parámetro fecha
     *
     * @param fecha 
     * @return 
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion 
     */
    public Integer calcularReintegros(Calendar fecha, Date fechaHisto){                
         return servicio.calcularReintegros(fecha, fechaHisto);
    }
    
    
}
