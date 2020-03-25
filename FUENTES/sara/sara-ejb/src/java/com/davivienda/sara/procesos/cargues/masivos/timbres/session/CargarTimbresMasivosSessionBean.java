/*
 * ProcesoCuadreCifrasSessionBean.java
 *
 * Created on 25/09/2007, 09:32:24 AM
 * 
 * Babel Ver   :1.0
 */
package com.davivienda.sara.procesos.cargues.masivos.timbres.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.CarmasTimbresTemp;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.exception.ServiceInExecutionException;
import com.davivienda.sara.procesos.reintegros.servicio.CargarTimbresMasivosServicio;
import com.davivienda.sara.tablas.confmodulosaplicacion.servicio.ConfModulosAplicacionServicio;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.management.InvalidApplicationException;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author jjvargas
 */
@Stateless
@Local(value = CargarTimbresMasivosSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class CargarTimbresMasivosSessionBean implements CargarTimbresMasivosSessionLocal {

    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    private CargarTimbresMasivosServicio cargarTimbresMasivosServicio;
    private final String NEXT_VALUE_SEQ_TIMBRES_TEMP = "SELECT SEQ_CARMAS_TIMBRES_LOG.NEXTVAL FROM DUAL";
    private String EJECUTAR_PRC_TIMBRES_MASIVOS;
    private String EJECUTAR_PRC_ACTUALIZAR_TIMBRES_MASIVOS;
    private final String TIMBRES_MASIVOS_PARA_APLICAR_STRATUS = "SELECT TMP.* FROM CARMAS_TIMBRES_TEMP TMP WHERE TMP.RESULTADO_CARGA IS NULL OR TRIM(TMP.RESULTADO_CARGA) =''";

    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
        cargarTimbresMasivosServicio = new CargarTimbresMasivosServicio(em);
    }

    @Override
    public boolean update(CarmasTimbresTemp timbre) {
        CarmasTimbresTemp t = null;
        try {
            cargarTimbresMasivosServicio.actualizar(timbre);
        } catch (Exception e) {
            Logger.getLogger("CargarTimbresMasivosServicio update error : " + e.getMessage());
        }
        return t != null;
    }

    @Override
    public Collection<CarmasTimbresTemp> getTodos() {
        return cargarTimbresMasivosServicio.getTodos();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<CarmasTimbresTemp> ejecutarCargue(Collection<CarmasTimbresTemp> timbres, String usuario) throws RuntimeException, ServiceInExecutionException {
        try {
            EJECUTAR_PRC_TIMBRES_MASIVOS = "SELECT FN_EJECUTAR_TIMBRES_MASIVOS('" + usuario + "') AS V_ERROR FROM DUAL";
            em.clear();
            for (CarmasTimbresTemp timbre : timbres) {
                timbre.setIdCargueMasivoTimbresTmp((BigDecimal) em.createNativeQuery(NEXT_VALUE_SEQ_TIMBRES_TEMP).getSingleResult());
                cargarTimbresMasivosServicio.adicionar(timbre);
            }
            Query query = em.createNativeQuery(EJECUTAR_PRC_TIMBRES_MASIVOS);
            String errorEjecucion = (String) query.getSingleResult();
            if (errorEjecucion != null && !errorEjecucion.trim().isEmpty()) {
                throw new RuntimeException("Error en la ejecución del procedimiento almacenado");
            }
            List<CarmasTimbresTemp> carmasTimbresTempList = em.createNativeQuery(TIMBRES_MASIVOS_PARA_APLICAR_STRATUS, CarmasTimbresTemp.class).getResultList();
            return carmasTimbresTempList;
        } catch (EntityServicioExcepcion ex) {
            Logger.getLogger(CargarTimbresMasivosSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String actualizarLog(String usuario) {
        try {
            EJECUTAR_PRC_ACTUALIZAR_TIMBRES_MASIVOS = "SELECT FN_ACTUALIZAR_TIMBRES_MASIVOS('" + usuario + "') FROM DUAL";
            Query nq = em.createNativeQuery(EJECUTAR_PRC_ACTUALIZAR_TIMBRES_MASIVOS);
            String errorEjecucion = (String) nq.getSingleResult();
            return errorEjecucion;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
