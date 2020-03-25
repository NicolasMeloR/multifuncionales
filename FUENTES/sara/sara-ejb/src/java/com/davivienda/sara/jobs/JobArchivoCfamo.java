// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.jobs;

import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.sara.constantes.EstadoProceso;
import javax.persistence.Query;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import java.io.FileNotFoundException;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Calendar;
import com.davivienda.utilidades.conversion.JSon;
import com.davivienda.sara.constantes.CodigoError;
import java.util.logging.Level;
import java.util.Date;
import org.quartz.CronExpression;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.config.SaraConfig;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.RemoveException;
import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.EJBException;
import java.util.Iterator;
import javax.ejb.TimerService;
import java.io.Serializable;
import javax.ejb.Timer;
import com.davivienda.utilidades.SchedulerInfo;
import java.util.logging.Logger;
import com.davivienda.sara.procesos.cuadrecifras.filtro.corte.servicio.ProcesadorCorteArchivoServicio;
import com.davivienda.sara.tablas.regcargue.session.RegCargueArchivoSessionLocal;
import com.davivienda.sara.tareas.regcargue.session.AdminTareasRegCargueArchivoSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import javax.ejb.EJB;
import com.davivienda.sara.procesos.cuadrecifras.session.ProcesoCuadreCifrasSessionLocal;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import javax.ejb.TimerHandle;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.RemoteHome;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TimedObject;
import com.davivienda.sara.base.BaseServicio;

@Stateless(mappedName = "JobArchivoCfamo")
@Remote({ JobArchivoCfamoRemote.class })
@RemoteHome(JobArchivoCfamoHome.class)
public class JobArchivoCfamo extends BaseServicio implements JobArchivoCfamoRemote, TimedObject
{
    @Resource
    SessionContext context;
    private TimerHandle timerHandler;
    SimpleDateFormat sdf;
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
    private ProcesadorCorteArchivoServicio corteServicio;
    private Logger loggerApp;
    
    public JobArchivoCfamo() {
        this.sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }
    
    @Override
    public TimerHandle createTimer(final SchedulerInfo schedulerInfo) throws EJBException {
        final TimerService timerService = this.context.getTimerService();
        if (null != timerService) {
            final Iterator iter = timerService.getTimers().iterator();
            while (iter.hasNext()) {
                try {
                    final Timer timer =(Timer) iter.next();
                    final SchedulerInfo tarea = (SchedulerInfo)timer.getInfo();
                    if (tarea.getTipoProceso().equals(schedulerInfo.getTipoProceso())) {
                        System.err.println("Tarea programada ya existe:  " + tarea.getTipoProceso());
                        return timer.getHandle();
                    }
                    continue;
                }
                catch (Exception exc) {
                    System.err.println("Error consultando tareas programadas: " + exc.getMessage());
                }
            }
        }
        final Timer timer2 = timerService.createTimer(schedulerInfo.getProximaEjecucion(), (Serializable)schedulerInfo);
        this.timerHandler = timer2.getHandle();
        System.out.println(System.getProperty("weblogic.Name") + " Timer Created ... prox eje: " + timer2.getNextTimeout());
        return this.timerHandler;
    }
    
    public EJBHome getEJBHome() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Object getPrimaryKey() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void remove() throws RemoteException, RemoveException {
        try {
            if (this.context != null) {
                final Iterator iter = this.context.getTimerService().getTimers().iterator();
                while (iter.hasNext()) {
                    try {
                        final Timer timer = (Timer) iter.next();
                        final SchedulerInfo tarea = (SchedulerInfo)timer.getInfo();
                        this.sdf.setLenient(false);
                        System.err.println("Eliminando tarea programada: " + tarea.getTipoProceso() + " con fecha proxima ejecucion: " + this.sdf.format(tarea.getProximaEjecucion()));
                        timer.cancel();
                    }
                    catch (Exception exc) {
                        System.err.println("Error eliminando tarea programada: " + exc.getMessage());
                    }
                }
            }
            else {
                System.err.println("No se pudo detener las tareas programadas");
            }
        }
        catch (Exception exc2) {
            System.out.println("No se pudo detener las tareas programadas");
            exc2.printStackTrace();
        }
    }
    
    public Handle getHandle() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean isIdentical(final EJBObject obj) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void ejbTimeout(Timer timer) {
        try {
            (this.loggerApp = SaraConfig.obtenerInstancia().loggerApp).info(System.getProperty("weblogic.Name") + " com.davivienda.sara.jobs.JobArchivoCfamo.ejbTimeout()");
            this.cargarObjetos();
            String respuesta = "";
            int intRegProcesadosCorte = -1;
            String strExepcion = "";
            Calendar fechaInicial = null;
            try {
                fechaInicial = Fecha.getCalendar(Fecha.getDateHoy());
                final String fechaConsulta = Fecha.aCadena(fechaInicial, "yyMMdd");
                if (this.ConsultarEstadoCargue().equals("0")) {
                    this.CambiarEstadoCargue("1");
                    intRegProcesadosCorte = this.procesarArchivoCfamo(fechaInicial, fechaConsulta);
                    respuesta = "Se Actualizaron con exito en la bd : " + intRegProcesadosCorte + " Registros de Cuadre diario ";
                    this.em.flush();
                }
                final TimerService timerService = this.context.getTimerService();
                final SchedulerInfo scheduler = (SchedulerInfo)timer.getInfo();
                timer = timerService.createTimer(new CronExpression(scheduler.getCronExpresion()).getNextValidTimeAfter(new Date()), (Serializable)scheduler);
                this.loggerApp.info(System.getProperty("weblogic.Name") + " EJB Timed Out ... prox eje: " + timer.getNextTimeout());
            }
            catch (EJBException ex) {
                this.loggerApp.log(Level.SEVERE, "EJBException:", (Throwable)ex);
                if (ex.getMessage() == null) {
                    strExepcion = ex.getCause().getMessage();
                }
                else {
                    strExepcion = ex.getMessage();
                }
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);
            }
            catch (Exception ex2) {
                this.loggerApp.log(Level.SEVERE, "Exception:", ex2);
                strExepcion = ex2.getMessage();
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);
            }
            finally {
                this.CambiarEstadoCargue("0");
            }
            this.loggerApp.info(System.getProperty("weblogic.Name") + " com.davivienda.sara.jobs.JobArchivoCfamo.ejbTimeout(): " + respuesta);
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    
    public void cargarObjetos() {
        this.corteServicio = new ProcesadorCorteArchivoServicio(this.em);
    }
    
    private int procesarArchivoCfamo(final Calendar fechaInicial, final String fechaConsulta) {
        int intRegProcesadosCorte = -1;
        final String strArchivoCarga = "cfamo001";
        final boolean cargueautomatico = true;
        try {
            this.loggerApp.log(Level.INFO, "Iniciando con archivo: {0}", strArchivoCarga);
            final Regcarguearchivo archivoCargado = this.adminTareasRegCargueArchivoSessionLocal.buscarPorArchivoFecha(strArchivoCarga, fechaConsulta, cargueautomatico);
            if (archivoCargado == null) {
                this.guardarRegistroTxArchivo(strArchivoCarga, cargueautomatico, fechaInicial, "Cuadre");
                intRegProcesadosCorte = this.cargarArchivoCorte(fechaInicial, cargueautomatico);
                this.actualizarRegCargueArchivo(strArchivoCarga, cargueautomatico, fechaInicial, "", intRegProcesadosCorte);
                this.loggerApp.log(Level.INFO, "Finalizando con archivo: {0}", strArchivoCarga);
            }
            else {
                this.loggerApp.log(Level.INFO, "No se creara el registro RegCargueArchivo para la tarea archivo: {0} con fecha: {1}  por que ya se encuentra en Base de Datos", new Object[] { strArchivoCarga, fechaConsulta });
            }
        }
        catch (Exception ex) {
            try {
                this.loggerApp.log(Level.SEVERE, "ProcesarArchivoCfamo Exception:", ex);
                this.loggerApp.log(Level.INFO, "Error al procesar tarea {0} : {1}", new Object[] { strArchivoCarga, ex.getMessage() });
                this.actualizarRegCargueArchivo(strArchivoCarga, cargueautomatico, fechaInicial, "", null);
            }
            catch (EntityServicioExcepcion ese) {
                this.loggerApp.log(Level.INFO, "Error al actualizar el RegCargueArchivo para {0} y fecha: {1} -->{2}", new Object[] { strArchivoCarga, fechaConsulta, ese.getMessage() });
                this.loggerApp.log(Level.SEVERE, "ProcesarArchivoCfamo EntityServicioExcepcion:", ese);
            }
        }
        return intRegProcesadosCorte;
    }
    
    public Integer cargarArchivoCorte(final Calendar fecha, final boolean cargueautomatico) throws Exception {
        this.loggerApp.log(Level.INFO, "Cargar archivo corte con fecha {0}", fecha.getTime());
        Integer registros = -1;
        String msgError = "";
        final String strFecha = Fecha.aCadena(fecha, FormatoFecha.FECHA_HORA);
        try {
            registros = this.corteServicio.cargarArchivoCorte(fecha);
        }
        catch (FileNotFoundException ex) {
            this.loggerApp.log(Level.SEVERE, "", ex);
            msgError = "No existe el archivo Corte para la fecha  " + strFecha + " " + ex.getMessage();
            this.loggerApp.info(msgError);
        }
        catch (Exception ex2) {
            this.loggerApp.log(Level.SEVERE, "", ex2);
            msgError = "Error al cargar archivo Corte para la fecha  " + strFecha + " " + ex2.getMessage();
            this.loggerApp.info(msgError);
        }
        return registros;
    }
    
    private String ConsultarEstadoCargue() {
        String strEstadoCargue = "0";
        try {
            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGACUADRE");
            registroEntityConsulta = this.confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            strEstadoCargue = registroEntityConsulta.getValor();
        }
        catch (Exception ex) {
            this.loggerApp.info("Error obteniendo estado cargue : " + ex.getMessage());
            strEstadoCargue = "0";
        }
        return strEstadoCargue;
    }
    
    private void CambiarEstadoCargue(final String strEstado) {
        try {
            final Query query = this.em.createQuery("UPDATE ConfModulosAplicacion c SET c.valor = :valor  WHERE c.confModulosAplicacionPK.modulo = :modulo and c.confModulosAplicacionPK.atributo = :atributo");
            query.setParameter("valor", (Object)strEstado);
            query.setParameter("modulo", (Object)"SARA");
            query.setParameter("atributo", (Object)"SARA.ESTADOCARGACUADRE");
            final int act = query.executeUpdate();
            this.loggerApp.info("Se actualiza ConfModulosAplicacion " + act);
        }
        catch (Exception ex) {
            this.loggerApp.info("Error cambiando estado cargue : " + ex.getMessage());
        }
    }
    
    private void guardarRegistroTxArchivo(final String archivoTarea, final boolean IndAuto, final Calendar fechaTarea, final String tarea) {
        this.loggerApp.log(Level.INFO, "Creando entidad RegCargueArchivo con nombre: {0} fecha: {1} tarea: {2}", new Object[] { archivoTarea, fechaTarea.getTime(), tarea });
        Date fechaCarga = new Date();
        String strFechaTarea = "";
        try {
            fechaCarga = Fecha.getDateHoy();
            strFechaTarea = Fecha.aCadena(fechaTarea, "yyMMdd");
            this.adminTareasRegCargueArchivoSessionLocal.guardarRegCargueArchivo(archivoTarea, IndAuto, strFechaTarea, tarea, fechaCarga, "SARA", false, "");
        }
        catch (IllegalArgumentException ex) {
            this.loggerApp.log(Level.SEVERE, "IllegalArgumentException ", ex);
            this.loggerApp.log(Level.INFO, "Error al grabar los datos en RegCargueArchivo para el archivo {0}{1} {2}", new Object[] { archivoTarea, fechaTarea, ex.getMessage() });
        }
        catch (Exception ex2) {
            this.loggerApp.log(Level.SEVERE, "Exception", ex2);
            this.loggerApp.log(Level.INFO, "Error al grabar los datos en RegCargueArchivo  para el archivo :{0}{1} descripcion Error : {2}", new Object[] { archivoTarea, fechaTarea, ex2.getMessage() });
        }
    }
    
    private void actualizarRegCargueArchivo(final String archivoTarea, final boolean IndAuto, final Calendar fechaTarea, final String msgError, final Integer numRegistros) throws EntityServicioExcepcion {
        this.loggerApp.log(Level.INFO, "Actualizando entidad RegCargueArchivo con nombre: {0} fecha: {1} tarea: {2} numRegistros: {3} error: {4}", new Object[] { archivoTarea, fechaTarea.getTime(), numRegistros, msgError });
        String strFechaTarea = "";
        Long lngNumRegistros = new Long(0L);
        strFechaTarea = Fecha.aCadena(fechaTarea, "yyMMdd");
        final Regcarguearchivo edcCargue = this.adminTareasRegCargueArchivoSessionLocal.buscarPorArchivoFecha(archivoTarea, strFechaTarea, IndAuto);
        if (edcCargue != null) {
            if (msgError.equals("")) {
                edcCargue.setEstadocarga(EstadoProceso.FINALIZADO.getEstado());
                if (null != numRegistros) {
                    lngNumRegistros = Cadena.aLong(Integer.toString(numRegistros));
                    edcCargue.setNumregistros(lngNumRegistros);
                }
                else {
                    edcCargue.setNumregistros(edcCargue.getNumregistros());
                }
            }
            else {
                edcCargue.setEstadocarga(EstadoProceso.ERROR.getEstado());
            }
            edcCargue.setDescripcionerror(msgError);
            edcCargue.setFechafinal(Fecha.getDateHoy());
            this.regCargueArchivoSessionLocal.actualizar(edcCargue);
        }
    }
}
