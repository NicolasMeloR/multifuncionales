// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.jobs;

import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.constantes.EstadoProceso;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.entitys.Tipomovimientocuadremulti;
import com.davivienda.sara.entitys.Movimientocuadremulti;
import com.davivienda.multifuncional.constantes.TipoMovimientoCuadreMultifuncional;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Map;
import java.math.BigDecimal;
import com.davivienda.multifuncional.constantes.TipoAjusteMultifuncional;
import java.util.HashMap;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionAttribute;
import java.util.Calendar;
import com.davivienda.sara.entitys.Totalesestacionmultifuncional;
import com.davivienda.sara.entitys.Billetajemulti;
import java.util.Collection;
import java.util.Date;
import org.quartz.CronExpression;
import com.davivienda.utilidades.conversion.JSon;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.logging.Level;
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
import com.davivienda.sara.tareas.regcargue.session.AdminTareasRegCargueArchivoSessionLocal;
import com.davivienda.sara.tablas.regcargue.session.RegCargueArchivoSessionLocal;
import com.davivienda.multifuncional.tablas.totalesestacionmultifuncional.session.TotalesEstacionMultiSessionLocal;
import com.davivienda.multifuncional.tablas.biletajemulti.session.BilletajeMultiSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.multifuncional.cuadrecifrasmulti.session.CuadreCifrasMultiSessionLocal;
import javax.ejb.EJB;
import com.davivienda.multifuncional.ws.cuadrecifrasmulti.servicio.CuadreCifrasMultiWsSessionLocal;
import java.text.SimpleDateFormat;
import javax.ejb.TimerHandle;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.RemoteHome;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TimedObject;
import com.davivienda.sara.base.BaseServicio;

@Stateless(mappedName = "JobDaliAuto")
@Remote({ JobDaliAutoRemote.class })
@RemoteHome(JobDaliAutoHome.class)
public class JobDaliAuto extends BaseServicio implements JobDaliAutoRemote, TimedObject
{
    @Resource
    SessionContext context;
    private TimerHandle timerHandler;
    SimpleDateFormat sdf;
    @EJB
    CuadreCifrasMultiWsSessionLocal cuadreCifrasMultiWsSessionLocal;
    @EJB
    CuadreCifrasMultiSessionLocal cuadreCifrasMultiSessionLocal;
    @EJB
    CajeroSessionLocal cajeroSessionLocal;
    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionLocal;
    @EJB
    BilletajeMultiSessionLocal billetajeMultiSessionLocal;
    @EJB
    TotalesEstacionMultiSessionLocal totalesEstacionMultiSessionLocal;
    @EJB
    RegCargueArchivoSessionLocal regCargueArchivoSessionLocal;
    @EJB
    AdminTareasRegCargueArchivoSessionLocal adminTareasRegCargueArchivoSessionLocal;
    private Logger loggerApp;
    
    public JobDaliAuto() {
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
                    if (!tarea.getTipoProceso().equals(schedulerInfo.getTipoProceso())) {
                        continue;
                    }
                    System.err.println("Tarea programada ya existe:  " + tarea.getTipoProceso() + " con fecha de ejecucion " + timer.getNextTimeout());
                    timer.cancel();
                    System.err.println("Tarea eliminada " + tarea.getTipoProceso());
                }
                catch (Exception exc) {
                    System.err.println("Error consultando tareas programadas: " + exc.getMessage());
                }
            }
        }
        final Timer timer2 = timerService.createTimer(schedulerInfo.getProximaEjecucion(), (Serializable)schedulerInfo);
        this.timerHandler = timer2.getHandle();
        System.out.println("JobDaliAuto: ---> " + System.getProperty("weblogic.Name") + " Timer Created ... prox eje: " + timer2.getNextTimeout());
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
                        final Timer timer =(Timer) iter.next();
                        final SchedulerInfo tarea = (SchedulerInfo)timer.getInfo();
                        if (!tarea.getTipoProceso().equals("DaliAuto")) {
                            continue;
                        }
                        this.sdf.setLenient(false);
                        System.err.println("JobDaliAuto: ---> Eliminando tarea programada: " + tarea.getTipoProceso() + " con fecha proxima ejecucion: " + this.sdf.format(tarea.getProximaEjecucion()));
                        timer.cancel();
                    }
                    catch (Exception exc) {
                        System.err.println("JobDaliAuto: ---> Error eliminando tarea programada: " + exc.getMessage());
                    }
                }
            }
            else {
                System.err.println("JobDaliAuto: ---> No se pudo detener las tareas programadas");
            }
        }
        catch (Exception exc2) {
            System.out.println("JobDaliAuto: ---> No se pudo detener las tareas programadas" + exc2.getMessage());
            exc2.printStackTrace();
        }
    }
    
    public Handle getHandle() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean isIdentical(final EJBObject obj) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ejbTimeout(Timer timer) {
        try {
            (this.loggerApp = SaraConfig.obtenerInstancia().loggerApp).info("com.davivienda.sara.jobs.JobDaliAuto.ejbTimeout()");
            this.loggerApp.info("Inicio - " + this.getClass().getSimpleName());
            this.loggerApp.log(Level.INFO, "Empieza el proceso de JobDaliAuto para la fecha: {0} y nombre nodo: {1}", new Object[] { Fecha.aCadena(Fecha.getCalendarHoy(), "yyyy/MM/dd HH:mm:ss"), System.getProperty("weblogic.Name") });
            String respuesta = "";
            Collection<Billetajemulti> items = null;
            Collection<Totalesestacionmultifuncional> itemsTotalesestacion = null;
            Integer regActualizados = 0;
            String strExepcion = "";
            String strRespuestaCargue = "";
            try {
                Calendar fechaInicial = null;
                fechaInicial = Fecha.getFechaInicioDia(Fecha.getCalendarAyer());
                this.loggerApp.info("com.davivienda.sara.jobs.JobDaliAuto.ejbTimeout(): fechaInicial" + fechaInicial.getTime());
                try {
                    items = this.billetajeMultiSessionLocal.getBilletajeMultiRangoFecha(fechaInicial, fechaInicial);
                    itemsTotalesestacion = this.totalesEstacionMultiSessionLocal.getTotalesEstacionMultiRangoFecha(fechaInicial, fechaInicial);
                    this.guardarRegistroTxArchivo("CuadreCifrasMultifuncional", true, fechaInicial, "Cuadre");
                    if (items == null || itemsTotalesestacion == null) {
                        respuesta = (strRespuestaCargue = respuesta + "   " + "No se encontraron datos de cajeros multifuncionales para calculo de faltantes o sobrantes automaticos  ");
                        respuesta = JSon.getJSonRespuesta(CodigoError.SIN_ERROR.getCodigo(), respuesta);
                    }
                    else if (items.isEmpty() || itemsTotalesestacion.isEmpty()) {
                        respuesta = (strRespuestaCargue = respuesta + "   " + "No se encontraron datos de cajeros multifuncionales para calculo de faltantes o sobrantes automaticos  ");
                        respuesta = JSon.getJSonRespuesta(CodigoError.SIN_ERROR.getCodigo(), respuesta);
                    }
                    else {
                        respuesta = this.setAjustesDali40LS(items, itemsTotalesestacion);
                        regActualizados = 1;
                    }
                    this.actualizarRegCargueArchivo("CuadreCifrasMultifuncional", true, fechaInicial, strRespuestaCargue, regActualizados);
                    this.loggerApp.info(System.getProperty("weblogic.Name") + " EJB Timed Out ... prox eje: " + timer.getNextTimeout());
                }
                catch (EJBException ex) {
                    this.loggerApp.log(Level.SEVERE, "JobDaliAuto-EJBException: ", (Throwable)ex);
                    if (ex.getMessage() == null) {
                        strExepcion = ex.getCause().getMessage();
                    }
                    else {
                        strExepcion = ex.getMessage();
                    }
                    respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);
                }
                catch (Exception ex2) {
                    this.loggerApp.log(Level.SEVERE, "JobDaliAuto-Exception Interno: ", ex2);
                    respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex2.getMessage());
                }
            }
            catch (IllegalArgumentException ex3) {
                this.loggerApp.log(Level.SEVERE, "JobDaliAuto-IllegalArgumentException: ", ex3);
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex3.getMessage());
            }
            System.out.println("com.davivienda.sara.jobs.JobDaliAuto.ejbTimeout():" + respuesta);
            final TimerService timerService = this.context.getTimerService();
            final SchedulerInfo scheduler = (SchedulerInfo)timer.getInfo();
            timer = timerService.createTimer(new CronExpression(scheduler.getCronExpresion()).getNextValidTimeAfter(new Date()), (Serializable)scheduler);
        }
        catch (Exception exc) {
            this.loggerApp.log(Level.SEVERE, "JobDaliAuto-Exception: ", exc);
        }
    }
    
    public String setAjustesDali40LS(final Collection<Billetajemulti> regs, final Collection<Totalesestacionmultifuncional> regsTotalesestacion) throws EntityServicioExcepcion {
        String respuesta = "";
        final Map<Integer, Long> mapTotalesEstacion = new HashMap<Integer, Long>();
        Long recTotalesEstacion = new Long(0L);
        Long totalATM = new Long(0L);
        Long totalLinea = new Long(0L);
        Long diferencia = new Long(0L);
        Integer codCajero = 0;
        TipoAjusteMultifuncional tipoAjusteMultifuncional = TipoAjusteMultifuncional.Inicio;
        if (regsTotalesestacion != null) {
            for (final Totalesestacionmultifuncional totalesestacionmulti : regsTotalesestacion) {
                if (codCajero.compareTo(new Integer(0)) == 0) {
                    codCajero = totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigocajero();
                }
                if (codCajero.compareTo(totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigocajero()) != 0) {
                    mapTotalesEstacion.put(codCajero, recTotalesEstacion / 100L);
                    recTotalesEstacion = new Long(0L);
                    codCajero = totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigocajero();
                }
                if (totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(475) || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(476) || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(477) || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(483) || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(481) || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(95) || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(361) || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(485)) {
                    recTotalesEstacion += totalesestacionmulti.getValorevento();
                }
            }
            mapTotalesEstacion.put(codCajero, recTotalesEstacion / 100L);
        }
        if (regs != null) {
            for (final Billetajemulti billetajemulti : regs) {
                billetajemulti.getBilletajemultiPK().getCodigocajero();
                if (!mapTotalesEstacion.isEmpty() && mapTotalesEstacion.containsKey(billetajemulti.getBilletajemultiPK().getCodigocajero())) {
                    totalLinea = mapTotalesEstacion.get(billetajemulti.getBilletajemultiPK().getCodigocajero());
                    totalATM = billetajemulti.getValordepefectivoctaaho() + billetajemulti.getValordepefectivoctacte() + billetajemulti.getValorpagoefectivofm() + billetajemulti.getValorpagoefectivotc();
                    if (totalLinea.equals(new Long(0L)) || totalATM.equals(new Long(0L))) {
                        continue;
                    }
                    if (totalATM < totalLinea) {
                        diferencia = totalLinea - totalATM;
                        tipoAjusteMultifuncional = TipoAjusteMultifuncional.Faltante;
                    }
                    else if (totalATM > totalLinea) {
                        diferencia = totalATM - totalLinea;
                        tipoAjusteMultifuncional = TipoAjusteMultifuncional.Sobrante;
                    }
                    if (diferencia != 0L) {
                        respuesta = respuesta + " Ajuste: " + tipoAjusteMultifuncional.nombre + " cajero : " + billetajemulti.getBilletajemultiPK().getCodigocajero() + "por: " + diferencia.toString();
                    }
                    else {
                        respuesta = respuesta + "  Sin diferencia :  cajero : " + billetajemulti.getBilletajemultiPK().getCodigocajero();
                    }
                    if (diferencia <= this.ConsultarValMaxAjuste()) {
                        if (diferencia != 0L) {
                            respuesta += this.guardarAjusteStratus(billetajemulti.getBilletajemultiPK().getCodigocajero(), new BigDecimal(diferencia.toString()), "Dali40Ajuste", tipoAjusteMultifuncional);
                        }
                    }
                    else {
                        respuesta = respuesta + " Valor mayor al permitido  maximo de:" + this.ConsultarValMaxAjuste().toString();
                    }
                    totalATM = new Long(0L);
                    totalLinea = new Long(0L);
                    diferencia = new Long(0L);
                }
            }
        }
        return respuesta;
    }
    
    private String guardarAjusteStratus(final Integer codigo_Cajero, final BigDecimal valorAjuste, final String usuario, final TipoAjusteMultifuncional tipoAjusteMultifuncional) throws EntityServicioExcepcion {
        Integer tipoMovCuadre = -1;
        Integer codigoOficina = 0;
        String respuesta = "";
        Cajero cajero = null;
        try {
            cajero = this.cajeroSessionLocal.buscar(codigo_Cajero);
            codigoOficina = cajero.getOficinaMulti().getCodigooficinamulti();
        }
        catch (EntityServicioExcepcion ex) {
            this.loggerApp.log(Level.SEVERE, "", ex);
        }
        try {
            switch (tipoAjusteMultifuncional) {
                case Sobrante: {
                    respuesta = this.cuadreCifrasMultiWsSessionLocal.realizarAjustePorSobrante(usuario, codigo_Cajero, codigoOficina, valorAjuste);
                    respuesta = "B";
                    tipoMovCuadre = TipoMovimientoCuadreMultifuncional.Sobrante.codigo;
                    break;
                }
                case Faltante: {
                    respuesta = this.cuadreCifrasMultiWsSessionLocal.realizarAjustePorFaltante(usuario, codigo_Cajero, codigoOficina, valorAjuste);
                    respuesta = "B";
                    tipoMovCuadre = TipoMovimientoCuadreMultifuncional.Faltante.codigo;
                    break;
                }
            }
            try {
                if (tipoMovCuadre > 0) {
                    this.guardarAjusteCuadre(codigo_Cajero, valorAjuste, usuario, tipoMovCuadre);
                }
            }
            catch (EntityServicioExcepcion ex) {
                this.loggerApp.log(Level.SEVERE, "", ex);
            }
            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("B")) {
                        respuesta = "Ajuste Realizado con Exito";
                    }
                    else if (respuesta.substring(0, 1).equals("M")) {
                        respuesta += "NO se pudo Realizar el Ajuste: ";
                    }
                    else if (respuesta.substring(0, 1).equals("F")) {
                        respuesta += "Por favor verificar el  Estado de el Ajuste";
                    }
                }
                else {
                    respuesta += "Por favor verificar el  Estado de el Ajuste";
                }
            }
            else {
                respuesta += "Por favor verificar el  Estado de el Ajuste";
            }
            respuesta = JSon.getJSonRespuesta(Integer.valueOf(0), respuesta);
        }
        catch (EJBException ex2) {
            this.loggerApp.log(Level.SEVERE, "", (Throwable)ex2);
            if (ex2.getMessage() == null) {
                respuesta = ex2.getCause().getMessage();
            }
            else {
                respuesta = ex2.getMessage();
            }
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), respuesta);
        }
        return respuesta;
    }
    
    private void guardarAjusteCuadre(final Integer codigo_Cajero, final BigDecimal valorAjuste, final String usuario, final Integer tipoMovCuadre) throws EntityServicioExcepcion {
        final Movimientocuadremulti mc = new Movimientocuadremulti();
        Cajero cajero = null;
        cajero = this.cajeroSessionLocal.buscar(codigo_Cajero);
        final Tipomovimientocuadremulti tmc = null;
        if (cajero != null) {
            mc.setCajero((Cajero)this.cajeroSessionLocal.buscar(codigo_Cajero));
            mc.setValormovimiento(Long.valueOf(valorAjuste.longValue()));
            mc.setIdusuario(usuario);
            mc.setFecha(Fecha.getDateAyer());
            mc.setFechaajsute(Fecha.getDateHoy());
            mc.setCodtipomovimientocuadremuti((long)tipoMovCuadre);
            this.cuadreCifrasMultiSessionLocal.grabarMovimientoCuadre(mc, false);
        }
    }
    
    private Long ConsultarValMaxAjuste() {
        Long longValReintegro = new Long("0");
        try {
            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("MULTIFUNCIONAL", "MULTIFUNCIONAL.MAX_VALOR_DALI40");
            registroEntityConsulta = this.confModulosAplicacionLocal.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            longValReintegro = Cadena.aLong(registroEntityConsulta.getValor());
        }
        catch (Exception ex) {
            this.loggerApp.log(Level.SEVERE, "", ex);
            this.loggerApp.info("Error obteniendo el valor maximo de Dali40 : " + ex.getMessage());
            longValReintegro = new Long("4999999");
        }
        return longValReintegro;
    }
    
    private void guardarRegistroTxArchivo(final String archivoTarea, final boolean IndAuto, final Calendar fechaTarea, final String tarea) {
        Date fechaCarga = new Date();
        String strFechaTarea = "";
        try {
            fechaCarga = Fecha.getDateHoy();
            strFechaTarea = Fecha.aCadena(fechaTarea, "yyMMdd");
            this.adminTareasRegCargueArchivoSessionLocal.guardarRegCargueArchivo(archivoTarea, IndAuto, strFechaTarea, tarea, fechaCarga, "usuarioSara", false, "");
        }
        catch (IllegalArgumentException ex) {
            this.loggerApp.info("Error al grabar los datos en RegCargueArchivo para el archivo " + archivoTarea + fechaTarea + " " + ex.getMessage());
        }
        catch (Exception ex2) {
            this.loggerApp.info("Error al grabar los datos en RegCargueArchivo  para el archivo :" + archivoTarea + fechaTarea + " descripcion Error : " + ex2.getMessage());
        }
    }
    
    private void actualizarRegCargueArchivo(final String archivoTarea, final boolean IndAuto, final Calendar fechaTarea, final String msgError, final Integer numRegistros) throws EntityServicioExcepcion {
        String strFechaTarea = "";
        Long lngNumRegistros = new Long(0L);
        lngNumRegistros = Cadena.aLong(Integer.toString(numRegistros));
        strFechaTarea = Fecha.aCadena(fechaTarea, "yyMMdd");
        final Regcarguearchivo edcCargue = this.adminTareasRegCargueArchivoSessionLocal.buscarPorArchivoFecha(archivoTarea, strFechaTarea, IndAuto);
        if (edcCargue != null) {
            if (msgError.equals("")) {
                edcCargue.setEstadocarga(EstadoProceso.FINALIZADO.getEstado());
                edcCargue.setNumregistros(lngNumRegistros);
            }
            else {
                edcCargue.setEstadocarga(EstadoProceso.ERROR.getEstado());
            }
            edcCargue.setDescripcionerror(msgError);
            edcCargue.setFecha(Fecha.getDateHoy());
            this.regCargueArchivoSessionLocal.actualizar(edcCargue);
        }
    }
}
