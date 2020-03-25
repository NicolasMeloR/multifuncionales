/*
 * ProcesoCuadreCifrasSessionBean.java
 *
 * Created on 25/09/2007, 09:32:24 AM
 * 
 * Babel Ver   :1.0
 */
package com.davivienda.sara.procesos.cargues.masivos.reintegros.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.CarmasReintegrosTemp;
import com.davivienda.sara.procesos.reintegros.servicio.CarguesMasivosReintegrosTempServicio;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author jjvargas
 */
@Stateless
@Local(value = CargarReintegrosMasivosSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class CargarReintegrosMasivosSessionBean implements CargarReintegrosMasivosSessionLocal {

    private static final String PR_CARGUES_MASIVOS_REINTEGROS = "BEGIN PR_CARGUES_MASIVOS_REINTEGROS(PE_USUARIO=>?, PS_ERROR=>?); END;";
    private static final String SEL_CARMAS_REINTEGROS_TEMP = "SELECT TEM.* FROM CARMAS_REINTEGROS_TEMP TEM";
    private static final String DEL_CARMAS_REINTEGROS_TEMP = "DELETE FROM CARMAS_REINTEGROS_TEMP";

    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;
    private CarguesMasivosReintegrosTempServicio servicio;

    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
        //servicio = new CarguesMasivosReintegrosTempServicio(em, list);
    }

    @Override
    public List<CarmasReintegrosTemp> ejecutarCargue(List<CarmasReintegrosTemp> reintegrosList, String usuarioEnSesion) {
        try {
            em.clear();
            servicio = new CarguesMasivosReintegrosTempServicio(em, CarmasReintegrosTemp.class);
            double bigDecimal = 1.0;
            for (CarmasReintegrosTemp carmasReintegrosTemp : reintegrosList) {
                BigDecimal valDouble = new BigDecimal(bigDecimal);
                carmasReintegrosTemp.setConsecutivo(valDouble);
                servicio.adicionar(carmasReintegrosTemp);
                bigDecimal++;
            }
            ejecutarProcedimiento(usuarioEnSesion);
            em.clear();
            return listaReintegros();
        } catch (EntityServicioExcepcion ex) {
            ex.printStackTrace();
            Logger.getLogger(CargarReintegrosMasivosSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void ejecutarProcedimiento(String usuarioEnSesion) {
        String error = "";
        Query query = em.createNativeQuery(PR_CARGUES_MASIVOS_REINTEGROS);
        query.setParameter(1, usuarioEnSesion);
        query.setParameter(2, error);
        query.executeUpdate();
    }

    private List<CarmasReintegrosTemp> listaReintegros() {
        List<CarmasReintegrosTemp> list = em.createNativeQuery(SEL_CARMAS_REINTEGROS_TEMP, CarmasReintegrosTemp.class).getResultList();

        em.createNativeQuery(DEL_CARMAS_REINTEGROS_TEMP).executeUpdate();
        return list;
    }
}
