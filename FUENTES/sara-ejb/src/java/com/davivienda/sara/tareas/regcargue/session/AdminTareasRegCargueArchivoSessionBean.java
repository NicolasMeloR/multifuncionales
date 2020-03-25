package com.davivienda.sara.tareas.regcargue.session;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ejb.Local;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.PersistenceContext;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.constantes.EstadoProceso;
import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.tareas.regcargue.servicio.AdminTareasRegCargueArchivoServicio;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.SystemException;

/**
 * ProcesadorDiarioElectronicoServicio - 22/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
@Stateless
@Local(value = AdminTareasRegCargueArchivoSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.BEAN)
//@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class AdminTareasRegCargueArchivoSessionBean implements AdminTareasRegCargueArchivoSessionLocal {

    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;

    private AdminTareasRegCargueArchivoServicio adminTareasRegCargueArchivoServicio;

    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
        adminTareasRegCargueArchivoServicio = new AdminTareasRegCargueArchivoServicio(em);

    }

    public void actualizar(Regcarguearchivo objetoModificado) throws EntityServicioExcepcion {

        adminTareasRegCargueArchivoServicio.actualizar(objetoModificado);

    }

    public Regcarguearchivo buscarPorArchivoFecha(String archivoTarea, String fecha, boolean IndAuto) throws EntityServicioExcepcion {
        return adminTareasRegCargueArchivoServicio.buscarPorArchivoFecha(archivoTarea, fecha, IndAuto);
    }

    public void guardarRegCargueArchivo(String archivoTarea, boolean IndAuto, String fechaTarea, String tarea, Date fecha, String usuario, boolean esCargueDipensador, String descrpcionError) throws EntityServicioExcepcion, IllegalArgumentException {
        boolean creaRegNuevo = false;
        Regcarguearchivo edcCargue = buscarPorArchivoFecha(archivoTarea, fechaTarea, IndAuto);

        if (esCargueDipensador) {
            if (IndAuto == false && edcCargue == null)//&& edcCargue.getEstadocarga().equals(EstadoProceso.INICIADO.getEstado()))
            {
                creaRegNuevo = true;
            }
        } else {
            creaRegNuevo = true;
        }

        // creaRegNuevo=true; 
        if (creaRegNuevo) {
            Logger loggerApp = adminTareasRegCargueArchivoServicio.getConfigApp().loggerApp;

            try {
                utx.begin();
                adminTareasRegCargueArchivoServicio.guardarRegCargueArchivo(archivoTarea, IndAuto, fechaTarea, tarea, fecha, usuario, descrpcionError);
                utx.commit();
            } catch (IllegalArgumentException ex) {
                loggerApp.log(Level.SEVERE, "", ex);
                loggerApp.log(Level.INFO, "Error al grabar los datos en RegCargueArchivo para el archivo {0}{1} {2}",
                        new Object[]{archivoTarea, fechaTarea, ex.getMessage()});
            } catch (Exception ex) {
                loggerApp.log(Level.SEVERE, "", ex);
                loggerApp.log(Level.INFO, "Error al grabar los datos en RegCargueArchivo  para el archivo :{0}{1} "
                        + "descripcion Error : {2}", new Object[]{archivoTarea, fechaTarea, ex.getMessage()});
            } finally {
                try {
                    //Status.STATUS_COMMITTED tiene valor 6 
                    if (utx.getStatus() != 6) {
                        utx.rollback();
                    }
                } catch (IllegalStateException ex1) {
                    loggerApp.info(ex1.getMessage());
                } catch (SecurityException ex1) {
                    loggerApp.info(ex1.getMessage());
                } catch (SystemException ex1) {
                    //Logger.getLogger(AdministradorProcesosSessionBean.class.getName()).log(Level.SEVERE, null, ex1);
                    loggerApp.info(ex1.getMessage());
                }
            }
        }

    }

    public Collection<Regcarguearchivo> getRegCargueArchivoPorFecha(Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {

        return adminTareasRegCargueArchivoServicio.getRegCargueArchivoPorFecha(fechaInicial, fechaFinal);

    }

}
