// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.reintegros.session;

import java.util.Date;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import com.davivienda.multifuncional.reintegros.servicio.ReintegrosMultiServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local({ ReintegrosMultiSessionLocal.class })
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReintegrosMultiSessionBean implements ReintegrosMultiSessionLocal
{
    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;
    private ReintegrosMultiServicio servicio;
    
    @PostConstruct
    public void postConstructor() {
        this.servicio = new ReintegrosMultiServicio(this.em);
    }
    
    @Override
    public Integer calcularReintegros(final Calendar fecha, final Date fechaHisto) {
        return this.servicio.calcularReintegros(fecha, fechaHisto);
    }
}
