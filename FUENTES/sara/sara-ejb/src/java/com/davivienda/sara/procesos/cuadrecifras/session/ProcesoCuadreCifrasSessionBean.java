// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cuadrecifras.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import com.davivienda.sara.procesos.cuadrecifras.servicio.ProcesoCuadreCifrasServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local({ ProcesoCuadreCifrasSessionLocal.class })
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProcesoCuadreCifrasSessionBean implements ProcesoCuadreCifrasSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private ProcesoCuadreCifrasServicio servicio;
    
    @PostConstruct
    public void postConstructor() {
        this.servicio = new ProcesoCuadreCifrasServicio(this.em);
    }
    
    @Override
    public Integer procesarCuadreCifras(final Calendar fecha) throws EntityServicioExcepcion {
        return this.servicio.cudrarCajero(fecha);
    }
}
