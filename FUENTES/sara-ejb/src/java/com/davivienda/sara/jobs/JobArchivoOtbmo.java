/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.jobs;

import com.davivienda.sara.base.BaseServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoProceso;
import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.servicio.ProcesadorProvisionesArchivoServicio;
import com.davivienda.sara.procesos.cuadrecifras.session.ProcesoCuadreCifrasSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.regcargue.servicio.RegCargueArchivoServicio;
import com.davivienda.sara.tablas.regcargue.session.RegCargueArchivoSessionLocal;
import com.davivienda.sara.tareas.regcargue.session.AdminTareasRegCargueArchivoSessionLocal;
import com.davivienda.utilidades.SchedulerInfo;
import com.davivienda.utilidades.conversion.JSon;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Handle;
import javax.ejb.Remote;
import javax.ejb.RemoteHome;
import javax.ejb.RemoveException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TimedObject;
import javax.ejb.Timer;
import javax.ejb.TimerHandle;
import javax.ejb.TimerService;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import org.quartz.CronExpression;

/**
 *
 * @author jediazs
 */
@Stateless(mappedName = "JobArchivoOtbmo")
@Remote({JobArchivoOtbmoRemote.class})
@RemoteHome(JobArchivoOtbmoHome.class)
public class JobArchivoOtbmo extends BaseServicio implements JobArchivoOtbmoRemote, TimedObject {

    @Resource
    SessionContext context;
    private TimerHandle timerHandler;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    @PersistenceContext
    private EntityManager em;
    @EJB
    ProcesoCuadreCifrasSessionLocal procesoCuadreCifrasSessionLocal;
    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionSession;
    @EJB
    AdminTareasRegCargueArchivoSessionLocal adminTareasRegCargueArchivoSessionLocal;
    @EJB
    RegCargueArchivoSessionLocal regCargueArchivoSessionLocal;
    private ProcesadorProvisionesArchivoServicio provisionesServicio;
    private RegCargueArchivoServicio regCargueArchivoServicio;
    private Logger loggerApp;
    
    @Override
    public TimerHandle createTimer(SchedulerInfo schedulerInfo) throws EJBException {
        TimerService timerService = this.context.getTimerService();

        if (null != timerService) {
            for (java.util.Iterator iter = timerService.getTimers().iterator(); iter.hasNext();) {
                try {
                    Timer timer = (Timer) iter.next();
                    SchedulerInfo tarea = (SchedulerInfo) timer.getInfo();
                    if (tarea.getTipoProceso().equals(schedulerInfo.getTipoProceso())) {
                        System.err.println("Tarea programada ya existe:  " + tarea.getTipoProceso());
                        return timer.getHandle();
                    }
                } catch (Exception exc) {
                    System.err.println("Error consultando tareas programadas: " + exc.getMessage());
                }
            }
        }

        Timer timer = timerService.createTimer(schedulerInfo.getProximaEjecucion(), schedulerInfo);
        this.timerHandler = timer.getHandle();
        System.out.println(System.getProperty("weblogic.Name") + " Timer Created ... prox eje: " + timer.getNextTimeout());
        return this.timerHandler;
    }

    @Override
    public EJBHome getEJBHome() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getPrimaryKey() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove() throws RemoteException, RemoveException {
        try {

            if (context != null) {

                for (java.util.Iterator iter = context.getTimerService().getTimers().iterator(); iter.hasNext();) {
                    try {
                        Timer timer = (Timer) iter.next();
                        SchedulerInfo tarea = (SchedulerInfo) timer.getInfo();
                        sdf.setLenient(false);
                        System.err.println("Eliminando tarea programada: " + tarea.getTipoProceso() + " con fecha proxima ejecucion: " + sdf.format(tarea.getProximaEjecucion()));
                        timer.cancel();
                    } catch (Exception exc) {
                        System.err.println("Error eliminando tarea programada: " + exc.getMessage());
                    }
                }
            } else {
                System.err.println("No se pudo detener las tareas programadas");
            }

        } catch (Exception exc) {
            System.out.println("No se pudo detener las tareas programadas");
            exc.printStackTrace();
        }
    }

    @Override
    public Handle getHandle() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIdentical(EJBObject obj) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ejbTimeout(Timer timer) {
        try {
            loggerApp = SaraConfig.obtenerInstancia().loggerApp;
            loggerApp.info(System.getProperty("weblogic.Name")+" com.davivienda.sara.jobs.JobArchivoOtbmo.ejbTimeout()");
            cargarObjetos();
            String respuesta = "";
            int intRegProcesadosProvision = -1;
            String strExepcion = "";
            Calendar fechaInicial = null;

            try {

                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
                String fechaConsulta = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicial, "yyMMdd");

                if (ConsultarEstadoCargue().equals("0")) {

                    CambiarEstadoCargue("1");
                    intRegProcesadosProvision = procesarArchivoOtbmo(fechaInicial, fechaConsulta);
                    respuesta = "Se Actualizaron con exito en la bd : " + intRegProcesadosProvision + " Registros de Provisiones ";
                    em.flush();
                }

                TimerService timerService = this.context.getTimerService();
                SchedulerInfo scheduler = (SchedulerInfo) timer.getInfo();
                timer = timerService.createTimer(new CronExpression(scheduler.getCronExpresion()).getNextValidTimeAfter(new Date()), scheduler);

                loggerApp.info(System.getProperty("weblogic.Name") + " EJB Timed Out ... prox eje: " + timer.getNextTimeout());
            } catch (EJBException ex) {
                loggerApp.log(Level.SEVERE, "EJBException:", ex);
                if (ex.getMessage() == null) {
                    strExepcion = ex.getCause().getMessage();
                } else {
                    strExepcion = ex.getMessage();
                }
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);
            } catch (Exception ex) {
                loggerApp.log(Level.SEVERE, "Exception:", ex);
                strExepcion = ex.getMessage();
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);
            } finally {
                CambiarEstadoCargue("0");
            }
            loggerApp.info(System.getProperty("weblogic.Name") +" com.davivienda.sara.jobs.JobArchivoOtbmo.ejbTimeout():" + respuesta);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void cargarObjetos() {
        provisionesServicio = new ProcesadorProvisionesArchivoServicio(em);
        regCargueArchivoServicio = new RegCargueArchivoServicio(em);
    }

    private int procesarArchivoOtbmo(Calendar fechaInicial, String fechaConsulta) {
        int intRegProcesadosProvision = -1;
        int intRegCuadreCifras = -1;
        String strArchivoCarga = "otbmo001";
        boolean cargueautomatico = true;
        Calendar fechaProceso = null;
        try {
            loggerApp.log(Level.INFO, "Iniciando con archivo: {0}", strArchivoCarga);
            Regcarguearchivo archivoCargado = adminTareasRegCargueArchivoSessionLocal.buscarPorArchivoFecha(strArchivoCarga, fechaConsulta, cargueautomatico);

            if (archivoCargado == null) {
                guardarRegistroTxArchivo(strArchivoCarga, cargueautomatico, fechaInicial, "Cuadre");
                intRegProcesadosProvision = cargarArchivoProvisiones(fechaInicial, cargueautomatico);
                actualizarRegCargueArchivo(strArchivoCarga, cargueautomatico, fechaInicial, "", intRegProcesadosProvision);
                fechaProceso = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaInicial, -1);
                guardarRegistroTxArchivo("CuadreCifrasDispensador", cargueautomatico, fechaInicial, "Cuadre");
                intRegCuadreCifras = procesoCuadreCifrasSessionLocal.procesarCuadreCifras(fechaProceso);
                actualizarRegCargueArchivo("CuadreCifrasDispensador", cargueautomatico, fechaInicial, "", intRegCuadreCifras);
            } else {
                loggerApp.log(Level.INFO, "No se creara el registro RegCargueArchivo para la tarea archivo: {0} con fecha: {1}"
                        + "  por que ya se encuentra en Base de Datos", new Object[]{strArchivoCarga, fechaConsulta});
            }
        } catch (Exception ex) {
            try {
                loggerApp.log(Level.SEVERE, "ProcesarArchivoOtbmo Exception:", ex);
                loggerApp.log(Level.INFO, "Error al procesar tarea {0} : {1}", new Object[]{strArchivoCarga, ex.getMessage()});
                actualizarRegCargueArchivo(strArchivoCarga, cargueautomatico, fechaInicial, "", null);
            } catch (EntityServicioExcepcion ese) {
                loggerApp.log(Level.INFO, "Error al actualizar el RegCargueArchivo para {0} y fecha: {1} -->{2}",
                        new Object[]{strArchivoCarga, fechaConsulta, ese.getMessage()});
                loggerApp.log(Level.SEVERE, "ProcesarArchivoOtbmo EntityServicioExcepcion:", ese);
            }
        }

        return intRegProcesadosProvision;
    }

    /**
     * Carga un archivo de provisiones en la tabla ProvisionHost y
     * MovimientoCuadre
     *
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
    public Integer cargarArchivoProvisiones(Calendar fecha, boolean cargueautomatico) {
        loggerApp.log(Level.INFO, "Cargar archivo provisiones fecha {0}", fecha.getTime());

        Integer registros = -1;
        String msgError = "";
        try {
            registros = provisionesServicio.cargarArchivoProvisiones(fecha);
        } catch (FileNotFoundException ex) {
            msgError = "No existe el archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            loggerApp.info(msgError);
        } catch (EntityServicioExcepcion ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            loggerApp.info(msgError);
        } catch (IllegalArgumentException ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            loggerApp.info(msgError);
        } catch (EJBTransactionRolledbackException ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            loggerApp.info(msgError);
        } catch (EJBException ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            loggerApp.info(msgError);
        } catch (Exception ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            loggerApp.info(msgError);
        } finally {

            try {

                try {
                    actualizarRegCargueArchivo("cfamo001", cargueautomatico, fecha, msgError);
                } catch (RollbackException ex) {
                    msgError = "Error al actualizar registro RegCargueArchivo para la fecha  " + fecha.toString() + " " + ex.getMessage();
                    loggerApp.info(msgError);
                    throw new EntityExistsException(ex);
                } catch (SecurityException ex) {
                    msgError = "Error al actualizar registro RegCargueArchivo para la fecha  " + fecha.toString() + " " + ex.getMessage();
                    loggerApp.info(msgError);
                } catch (IllegalStateException ex) {
                    msgError = "Error al actualizar registro RegCargueArchivo para la fecha  " + fecha.toString() + " " + ex.getMessage();
                    loggerApp.info(msgError);
                } catch (Exception ex) {
                    msgError = "Error al actualizar registro RegCargueArchivo para la fecha  " + fecha.toString() + " " + ex.getMessage();
                    loggerApp.info(msgError);
                }

            } catch (IllegalStateException ex1) {
                loggerApp.info(ex1.getMessage());
            } catch (SecurityException ex1) {
                loggerApp.info(ex1.getMessage());
            } catch (EntityExistsException ex1) {
                loggerApp.info(ex1.getMessage());
            }
        }
        return registros;

    }

    private String ConsultarEstadoCargue() {
        String strEstadoCargue = "0";
        try {
            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGACUADRE");
            registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            strEstadoCargue = registroEntityConsulta.getValor();
        } catch (Exception ex) {
            loggerApp.info("Error obteniendo estado cargue : " + ex.getMessage());
            strEstadoCargue = "0";
        }
        return strEstadoCargue;

    }

    private void CambiarEstadoCargue(String strEstado) {
        try {
            Query query = em.createQuery(
                    "UPDATE ConfModulosAplicacion c SET c.valor = :valor "
                    + " WHERE c.confModulosAplicacionPK.modulo = :modulo and c.confModulosAplicacionPK.atributo = :atributo");
            query.setParameter("valor", strEstado);
            query.setParameter("modulo", "SARA");
            query.setParameter("atributo", "SARA.ESTADOCARGACUADRE");

            int act = query.executeUpdate();
            loggerApp.info("Se actualiza ConfModulosAplicacion " + act);

        } catch (Exception ex) {
            loggerApp.info("Error cambiando estado cargue : " + ex.getMessage());
        }
    }

    private void guardarRegistroTxArchivo(String archivoTarea, boolean IndAuto, Calendar fechaTarea, String tarea) {

        loggerApp.log(Level.INFO, "Creando entidad RegCargueArchivo con nombre: {0} fecha: {1} tarea: {2}",
                new Object[]{archivoTarea, fechaTarea.getTime(), tarea});
        Date fechaCarga = new Date();
        String strFechaTarea = "";

        try {

            fechaCarga = com.davivienda.utilidades.conversion.Fecha.getDateHoy();
            strFechaTarea = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaTarea, "yyMMdd");
            adminTareasRegCargueArchivoSessionLocal.guardarRegCargueArchivo(archivoTarea, IndAuto, strFechaTarea, tarea, fechaCarga, "SARA", false, "");

        } catch (IllegalArgumentException ex) {
            loggerApp.log(Level.SEVERE, "IllegalArgumentException ", ex);
            loggerApp.log(Level.INFO, "Error al grabar los datos en RegCargueArchivo para el archivo {0}{1} {2}", new Object[]{archivoTarea, fechaTarea, ex.getMessage()});
        } catch (Exception ex) {
            loggerApp.log(Level.SEVERE, "Exception", ex);
            loggerApp.log(Level.INFO, "Error al grabar los datos en RegCargueArchivo  para el archivo :{0}{1} descripcion Error : {2}", new Object[]{archivoTarea, fechaTarea, ex.getMessage()});
        }

    }

    private void actualizarRegCargueArchivo(String archivoTarea, boolean IndAuto, Calendar fechaTarea, String msgError, Integer numRegistros) throws EntityServicioExcepcion {
        loggerApp.log(Level.INFO, "Actualizando entidad RegCargueArchivo con nombre: {0} fecha: {1} tarea: {2} numRegistros: {3} error: {4}",
                new Object[]{archivoTarea, fechaTarea.getTime(), numRegistros, msgError});

        String strFechaTarea = "";
        Long lngNumRegistros = new Long(0);

        strFechaTarea = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaTarea, "yyMMdd");

        Regcarguearchivo edcCargue = adminTareasRegCargueArchivoSessionLocal.buscarPorArchivoFecha(archivoTarea, strFechaTarea, IndAuto);
        if (edcCargue != null) {
            if (msgError.equals("")) {
                edcCargue.setEstadocarga(EstadoProceso.FINALIZADO.getEstado());
                if (null != numRegistros) {
                    lngNumRegistros = com.davivienda.utilidades.conversion.Cadena.aLong(Integer.toString(numRegistros));
                    edcCargue.setNumregistros(lngNumRegistros);
                } else {
                    edcCargue.setNumregistros(edcCargue.getNumregistros());
                }
            } else {
                edcCargue.setEstadocarga(EstadoProceso.ERROR.getEstado());
            }
            edcCargue.setDescripcionerror(msgError);
            edcCargue.setFechafinal(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
            regCargueArchivoSessionLocal.actualizar(edcCargue);
        }

    }

    private void actualizarRegCargueArchivo(String archivoTarea, boolean IndAuto, Calendar fechaTarea, String msgError) throws EntityServicioExcepcion {

        String strFechaTarea = "";
        strFechaTarea = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaTarea, "yyMMdd");

        Regcarguearchivo edcCargue = regCargueArchivoServicio.buscarPorArchivoFecha(archivoTarea, strFechaTarea, IndAuto);
        if (edcCargue != null) {
            if (msgError.equals("")) {
                edcCargue.setEstadocarga(EstadoProceso.FINALIZADO.getEstado());
            } else {
                edcCargue.setEstadocarga(3);
                edcCargue.setDescripcionerror(msgError);
            }

            edcCargue.setFecha(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
            regCargueArchivoServicio.actualizar(edcCargue);
            regCargueArchivoServicio.actualizarPersistencia();
        }
    }

}
