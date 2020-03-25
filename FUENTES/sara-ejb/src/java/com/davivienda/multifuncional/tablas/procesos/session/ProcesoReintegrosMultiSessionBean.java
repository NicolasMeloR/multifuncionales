package com.davivienda.multifuncional.tablas.procesos.session;

import com.davivienda.multifuncional.tablas.procesos.servicio.ReintegrosProcesosMultiServicio;
import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import com.davivienda.sara.procesos.reintegros.servicio.ReintegrosDiarioTEMPProcesosServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.procesos.reintegros.filtro.host.servicio.ProcesadorHostServicio;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;

/**
 *
 * @author mdruiz
 */
@Stateless
@Local(value = ProcesoReintegrosMultiSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class ProcesoReintegrosMultiSessionBean implements ProcesoReintegrosMultiSessionLocal {

    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    private ProcesadorHostServicio servicio;
    private ReintegrosProcesosMultiServicio reintegrosServicio;
    private ReintegrosDiarioTEMPProcesosServicio reintegrosDiarioTEMPProcesosServicio;

    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {

        servicio = new ProcesadorHostServicio(em);
        reintegrosServicio = new ReintegrosProcesosMultiServicio(em);
        reintegrosDiarioTEMPProcesosServicio = new ReintegrosDiarioTEMPProcesosServicio(em);
    }

    public void actualizarReintegro(Reintegrosmultiefectivo objetoModificado) throws EntityServicioExcepcion {
        reintegrosServicio.actualizar(objetoModificado);
    }

    public void guardarReintegro(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario, String talon, Integer codigoOficinaMulti) throws EntityServicioExcepcion {
        reintegrosServicio.guardarReintegro(codigoCajero, valor, cuenta, tipoCuenta, usuario, talon, codigoOficinaMulti);
    }

    public void actualizarNotaDebito(Notasdebitomultifuncional objetoModificado) throws EntityServicioExcepcion {
        reintegrosServicio.actualizarNotaDebito(objetoModificado);
    }

    public void guardarNotaDebito(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario) throws EntityServicioExcepcion {
        reintegrosServicio.guardarNotaDebito(codigoCajero, valor, cuenta, tipoCuenta, usuario);
    }

}
