/*
 * ProcesoCuadreCifrasSessionBean.java
 *
 * Created on 25/09/2007, 09:32:24 AM
 * 
 * Babel Ver   :1.0
 */
package com.davivienda.sara.procesos.cargues.masivos.notasdebito.session;

import com.davivienda.sara.procesos.reintegros.servicio.ReintegrosProcesosServicio;
import com.davivienda.sara.procesos.reintegros.servicio.ReintegrosDiarioTEMPProcesosServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.CarmasNotasDebitoTemp;
import com.davivienda.sara.procesos.reintegros.filtro.host.servicio.ProcesadorHostServicio;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.procesos.reintegros.servicio.CarguesMasivosNotasDebitoTempServicio;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author jjvargas
 */
@Stateless
@Local(value = CargarNotasDebitoMasivosSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class CargarNotasDebitoMasivosSessionBean implements CargarNotasDebitoMasivosSessionLocal {

    private static final String PR_CARGUES_MASIVOS_NOTDEB = "BEGIN PR_CARGUES_MASIVOS_NOTDEB(PE_USUARIO=>?, PS_ERROR=>?); END;";
    private static final String SEL_CARMAS_NOTDEB_TEMP = "SELECT TEM.* FROM CARMAS_NOTDEB_TEMP TEM";
    private static final String DEL_CARMAS_NOTDEB_TEMP = "DELETE FROM CARMAS_NOTDEB_TEMP";

    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    private CarguesMasivosNotasDebitoTempServicio serviciond;
    private ReintegrosProcesosServicio reintegrosServicio;
    private ReintegrosDiarioTEMPProcesosServicio reintegrosDiarioTEMPProcesosServicio;

    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
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

        reintegrosServicio.actualizar(objetoModificado);

    }

    public void guardarReintegroNuevo(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario, String talon) throws EntityServicioExcepcion {

        reintegrosServicio.guardarReintegroNuevo(codigoCajero, valor, cuenta, tipoCuenta, usuario, talon);
    }

    public void guardarNotaDebito(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario) throws EntityServicioExcepcion {
        reintegrosServicio.guardarNotaDebito(codigoCajero, valor, cuenta, tipoCuenta, usuario);
    }

    public void guardarNotaDebito(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario, Date fecha) throws EntityServicioExcepcion {
        reintegrosServicio.guardarNotaDebito(codigoCajero, valor, cuenta, tipoCuenta, usuario, fecha);
    }

    public void actualizarNotaDebito(Notasdebito objetoModificado) throws EntityServicioExcepcion {

        reintegrosServicio.actualizarNotaDebito(objetoModificado);

    }

    @Override
    public List<CarmasNotasDebitoTemp> ejecutarCargue(List<CarmasNotasDebitoTemp> notasDebitoList, String usuarioEnSesion) {
        try {
            em.clear();
            serviciond = new CarguesMasivosNotasDebitoTempServicio(em, CarmasNotasDebitoTemp.class);
            double bigDecimal = 1.0;
            for (CarmasNotasDebitoTemp carmasNotasDebitoTemp : notasDebitoList) {
                try {
                    BigDecimal valDouble = new BigDecimal(bigDecimal);
                    carmasNotasDebitoTemp.setConsecutivo(valDouble);
                    serviciond.adicionar(carmasNotasDebitoTemp);
                } catch (EntityExistsException ex) {
                    Logger.getLogger(CargarNotasDebitoMasivosSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                bigDecimal++;
            }

            ejecutarProcedimiento(usuarioEnSesion);
            em.clear();
            return listaNotasDebito();
        } catch (EntityServicioExcepcion ex) {
            Logger.getLogger(CargarNotasDebitoMasivosSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void ejecutarProcedimiento(String usuarioEnSesion) {
        String error = "";
        Query query = em.createNativeQuery(PR_CARGUES_MASIVOS_NOTDEB);
        query.setParameter(1, usuarioEnSesion);
        query.setParameter(2, error);
        query.executeUpdate();
        //query.getSingleResult();
    }

    private List<CarmasNotasDebitoTemp> listaNotasDebito() {
        List<CarmasNotasDebitoTemp> list = em.createNativeQuery(SEL_CARMAS_NOTDEB_TEMP, CarmasNotasDebitoTemp.class).getResultList();
        em.createNativeQuery(DEL_CARMAS_NOTDEB_TEMP).executeUpdate();
        return list;
    }
}
