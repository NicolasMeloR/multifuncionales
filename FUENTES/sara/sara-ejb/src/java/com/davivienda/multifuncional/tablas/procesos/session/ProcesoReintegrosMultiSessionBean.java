// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.procesos.session;

import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import javax.annotation.PostConstruct;
import com.davivienda.sara.procesos.reintegros.servicio.ReintegrosDiarioTEMPProcesosServicio;
import com.davivienda.multifuncional.tablas.procesos.servicio.ReintegrosProcesosMultiServicio;
import com.davivienda.sara.procesos.reintegros.filtro.host.servicio.ProcesadorHostServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local({ ProcesoReintegrosMultiSessionLocal.class })
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProcesoReintegrosMultiSessionBean implements ProcesoReintegrosMultiSessionLocal
{
    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;
    private ProcesadorHostServicio servicio;
    private ReintegrosProcesosMultiServicio reintegrosServicio;
    private ReintegrosDiarioTEMPProcesosServicio reintegrosDiarioTEMPProcesosServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.servicio = new ProcesadorHostServicio(this.em);
        this.reintegrosServicio = new ReintegrosProcesosMultiServicio(this.em);
        this.reintegrosDiarioTEMPProcesosServicio = new ReintegrosDiarioTEMPProcesosServicio(this.em);
    }
    
    @Override
    public void actualizarReintegro(final Reintegrosmultiefectivo objetoModificado) throws EntityServicioExcepcion {
        this.reintegrosServicio.actualizar(objetoModificado);
    }
    
    @Override
    public void guardarReintegro(final Integer codigoCajero, final Long valor, final String cuenta, final Integer tipoCuenta, final String usuario, final String talon, final Integer codigoOficinaMulti) throws EntityServicioExcepcion {
        this.reintegrosServicio.guardarReintegro(codigoCajero, valor, cuenta, tipoCuenta, usuario, talon, codigoOficinaMulti);
    }
    
    @Override
    public void actualizarNotaDebito(final Notasdebitomultifuncional objetoModificado) throws EntityServicioExcepcion {
        this.reintegrosServicio.actualizarNotaDebito(objetoModificado);
    }
    
    @Override
    public void guardarNotaDebito(final Integer codigoCajero, final Long valor, final String cuenta, final Integer tipoCuenta, final String usuario) throws EntityServicioExcepcion {
        this.reintegrosServicio.guardarNotaDebito(codigoCajero, valor, cuenta, tipoCuenta, usuario);
    }
}
