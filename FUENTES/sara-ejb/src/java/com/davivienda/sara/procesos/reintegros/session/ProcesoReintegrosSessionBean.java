/*
 * ProcesoCuadreCifrasSessionBean.java
 *
 * Created on 25/09/2007, 09:32:24 AM
 * 
 * Babel Ver   :1.0
 */
package com.davivienda.sara.procesos.reintegros.session;

import com.davivienda.sara.procesos.reintegros.servicio.ReintegrosProcesosServicio;
import com.davivienda.sara.procesos.reintegros.servicio.ReintegrosDiarioTEMPProcesosServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.procesos.reintegros.filtro.host.servicio.ProcesadorHostServicio;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.Notasdebito;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author jjvargas
 */
@Stateless
@Local(value = ProcesoReintegrosSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class ProcesoReintegrosSessionBean implements ProcesoReintegrosSessionLocal {

    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    private ProcesadorHostServicio servicio;
    private ReintegrosProcesosServicio reintegrosServicio;
    private ReintegrosDiarioTEMPProcesosServicio reintegrosDiarioTEMPProcesosServicio;

    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {

        servicio = new ProcesadorHostServicio(em);
        reintegrosServicio = new ReintegrosProcesosServicio(em);
        reintegrosDiarioTEMPProcesosServicio = new ReintegrosDiarioTEMPProcesosServicio(em);
    }

    /**
     * Realiza la generación de los registros de Dia sgte Real y Inico Día Real
     * para la fecha pasada en el parámetro fecha
     *
     * @param fecha
     * @return
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
//    public Integer CargarArchivo(Calendar fecha) throws  EntityServicioExcepcion,FileNotFoundException, IllegalArgumentException {                
//        return servicio.cargarArchivoHost( fecha);
//    }
    /**
     * Realiza los procesos para calcular los reintegros en el parámetro fecha
     *
     * @param fecha
     * @return
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public Integer calcularReintegros(Calendar fecha) {
        return reintegrosServicio.calcularReintegros(fecha);
    }

    /**
     * Realiza los procesos para calcular los reintegros en el parámetro fecha
     *
     * @param fecha
     * @return
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
    public Integer calcularReintegrosDiarioTEMP(Calendar fecha) {
        return reintegrosDiarioTEMPProcesosServicio.calcularReintegros(fecha);
    }

    public void actualizar(Reintegros objetoModificado) throws EntityServicioExcepcion {
        try {
            String formatDate = "dd-MM-YY HH:mm:ss.SSS";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
            String nuevaFechaSistema = simpleDateFormat.format(objetoModificado.getReintegrosPK().getHFechasistema());
            em.createNativeQuery("update reintegros  set estadoreintegro=?, usuariorevisa=?, usuarioautoriza=?, fechareintegro=?, errorreintegro=?, tipocuentareintegro=?, h_fechareversado=? where h_codigocajero =? and to_char(h_fechasistema,'dd-mm-rr hh24:mi:ss.ff3')=? and h_talon = ?")
                    .setParameter(1, objetoModificado.getEstadoreintegro())
                    .setParameter(2, objetoModificado.getUsuariorevisa())
                    .setParameter(3, objetoModificado.getUsuarioautoriza())
                    .setParameter(4, objetoModificado.getFechareintegro())
                    .setParameter(5, objetoModificado.getErrorreintegro())
                    .setParameter(6, objetoModificado.getTipoCuentaReintegro())
                    .setParameter(7, objetoModificado.getFechaReversado())
                    .setParameter(8, objetoModificado.getReintegrosPK().getHCodigocajero())
                    .setParameter(9, nuevaFechaSistema)
                    .setParameter(10, objetoModificado.getReintegrosPK().getHTalon())
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new EntityServicioExcepcion(e);
        }
    }

    public void guardarReintegroNuevo(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario, String talon) throws EntityServicioExcepcion {
        reintegrosServicio.guardarReintegroNuevo(codigoCajero, valor, cuenta, tipoCuenta, usuario, talon);
    }

    public void guardarNotaDebito(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario) throws EntityServicioExcepcion {
        reintegrosServicio.guardarNotaDebito(codigoCajero, valor, cuenta, tipoCuenta, usuario);
    }

    public void actualizarNotaDebito(Notasdebito objetoModificado) throws EntityServicioExcepcion {
        reintegrosServicio.actualizarNotaDebito(objetoModificado);
    }

    public Notasdebito guardarNotaDebito(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario, Date fecha, int estado) throws EntityServicioExcepcion {
        return reintegrosServicio.guardarNotaDebito(codigoCajero, valor, cuenta, tipoCuenta, usuario, fecha, estado);
    }

    public void guardarNotaDebito(Notasdebito newNotaDebito) {
        try {
            reintegrosServicio.guardarNotaDebito(newNotaDebito);

        } catch (EntityServicioExcepcion ex) {
            Logger.getLogger(ProcesoReintegrosSessionBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reversar(String codigoCajero, String fecha, String talon, Date fechaReversado) {
//        reintegrosServicio.actualizar(objetoModificado);
    }

    private Object setParameter(String codigocajero, Integer hCodigocajero) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
