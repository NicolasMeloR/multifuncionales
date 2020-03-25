/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.jobs;

import com.davivienda.multifuncional.constantes.TipoAjusteMultifuncional;
import com.davivienda.multifuncional.cuadrecifrasmulti.session.CuadreCifrasMultiSessionLocal;
import com.davivienda.multifuncional.tablas.biletajemulti.session.BilletajeMultiSessionLocal;
import com.davivienda.multifuncional.tablas.totalesestacionmultifuncional.session.TotalesEstacionMultiSessionLocal;
import com.davivienda.multifuncional.ws.cuadrecifrasmulti.servicio.CuadreCifrasMultiWsSessionLocal;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.Movimientocuadremulti;
import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.entitys.Tipomovimientocuadremulti;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.base.BaseServicio;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoProceso;
import com.davivienda.sara.entitys.Billetajemulti;
import com.davivienda.sara.entitys.Totalesestacionmultifuncional;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.regcargue.session.RegCargueArchivoSessionLocal;
import com.davivienda.sara.tareas.regcargue.session.AdminTareasRegCargueArchivoSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SchedulerInfo;
import com.davivienda.utilidades.conversion.JSon;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.quartz.CronExpression;

/**
 *
 * @author jediazs
 */
@Stateless(mappedName = "JobDaliAuto")
@Remote({JobDaliAutoRemote.class})
@RemoteHome(JobDaliAutoHome.class)
public class JobDaliAuto extends BaseServicio implements JobDaliAutoRemote, TimedObject {

    @Resource
    SessionContext context;
    private TimerHandle timerHandler;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

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

    @Override
    public TimerHandle createTimer(SchedulerInfo schedulerInfo) throws EJBException {
        TimerService timerService = this.context.getTimerService();

        if (null != timerService) {
            for (java.util.Iterator iter = timerService.getTimers().iterator(); iter.hasNext();) {
                try {
                    Timer timer = (Timer) iter.next();
                    SchedulerInfo tarea = (SchedulerInfo) timer.getInfo();
                    if (tarea.getTipoProceso().equals(schedulerInfo.getTipoProceso())) {
                        System.err.println("Tarea programada ya existe:  " + tarea.getTipoProceso() + " con fecha de ejecucion " + timer.getNextTimeout());
                        timer.cancel();
                        System.err.println("Tarea eliminada " + tarea.getTipoProceso());
                    }
                } catch (Exception exc) {
                    System.err.println("Error consultando tareas programadas: " + exc.getMessage());
                }
            }
        }

        Timer timer = timerService.createTimer(schedulerInfo.getProximaEjecucion(), schedulerInfo);
        this.timerHandler = timer.getHandle();
        System.out.println("JobDaliAuto: ---> " + System.getProperty("weblogic.Name") + " Timer Created ... prox eje: " + timer.getNextTimeout());
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
                        if (tarea.getTipoProceso().equals(Constantes.JOB_DALI_AUTO)) {
                            sdf.setLenient(false);
                            System.err.println("JobDaliAuto: ---> Eliminando tarea programada: " + tarea.getTipoProceso() + " con fecha proxima ejecucion: " + sdf.format(tarea.getProximaEjecucion()));
                            timer.cancel();
                        }
                    } catch (Exception exc) {
                        System.err.println("JobDaliAuto: ---> Error eliminando tarea programada: " + exc.getMessage());
                    }
                }
            } else {
                System.err.println("JobDaliAuto: ---> No se pudo detener las tareas programadas");
            }

        } catch (Exception exc) {
            System.out.println("JobDaliAuto: ---> No se pudo detener las tareas programadas" + exc.getMessage());
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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ejbTimeout(Timer timer) {
        try {
            loggerApp = SaraConfig.obtenerInstancia().loggerApp;
            loggerApp.info("com.davivienda.sara.jobs.JobDaliAuto.ejbTimeout()");

            loggerApp.info("Inicio - " + this.getClass().getSimpleName());
            loggerApp.log(Level.INFO, "Empieza el proceso de JobDaliAuto para la fecha: {0} y nombre nodo: {1}",
                    new Object[]{com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(),
                                "yyyy/MM/dd HH:mm:ss"), System.getProperty("weblogic.Name")});
            String respuesta = "";

            Collection<Billetajemulti> items = null;
            Collection<Totalesestacionmultifuncional> itemsTotalesestacion = null;
            Integer regActualizados = 0;

            String strExepcion = "";
            String strRespuestaCargue = "";

            try {

                Calendar fechaInicial = null;

                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getFechaInicioDia(com.davivienda.utilidades.conversion.Fecha.getCalendarAyer());
                //fechaInicial =  com.davivienda.utilidades.conversion.Fecha.getFechaInicioDia( com.davivienda.utilidades.conversion.Fecha.getCalendar(-7));                                
                loggerApp.info("com.davivienda.sara.jobs.JobDaliAuto.ejbTimeout(): fechaInicial" + fechaInicial.getTime());

                try {

                    items = billetajeMultiSessionLocal.getBilletajeMultiRangoFecha(fechaInicial, fechaInicial);
                    itemsTotalesestacion = totalesEstacionMultiSessionLocal.getTotalesEstacionMultiRangoFecha(fechaInicial, fechaInicial);
                    guardarRegistroTxArchivo("CuadreCifrasMultifuncional", true, fechaInicial, "Cuadre");

                    if (items == null || itemsTotalesestacion == null) {

                        respuesta = respuesta + "   " + "No se encontraron datos de cajeros multifuncionales para calculo de faltantes o sobrantes automaticos  ";
                        strRespuestaCargue = respuesta;
                        respuesta = JSon.getJSonRespuesta(CodigoError.SIN_ERROR.getCodigo(), respuesta);

                    } else if (items.isEmpty() || itemsTotalesestacion.isEmpty()) {

                        respuesta = respuesta + "   " + "No se encontraron datos de cajeros multifuncionales para calculo de faltantes o sobrantes automaticos  ";
                        strRespuestaCargue = respuesta;

                        respuesta = JSon.getJSonRespuesta(CodigoError.SIN_ERROR.getCodigo(), respuesta);
                    } else {
                        respuesta = setAjustesDali40LS(items, itemsTotalesestacion);
                        regActualizados = 1;

                    }
                    actualizarRegCargueArchivo("CuadreCifrasMultifuncional", true, fechaInicial, strRespuestaCargue, regActualizados);

                    loggerApp.info(System.getProperty("weblogic.Name") + " EJB Timed Out ... prox eje: " + timer.getNextTimeout());

                } catch (EJBException ex) {
                    loggerApp.log(Level.SEVERE, "JobDaliAuto-EJBException: ", ex);
                    if (ex.getMessage() == null) {
                        strExepcion = ex.getCause().getMessage();
                    } else {
                        strExepcion = ex.getMessage();
                    }
                    respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);

                } catch (Exception ex) {
                    loggerApp.log(Level.SEVERE, "JobDaliAuto-Exception Interno: ", ex);
                    respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                }

            } catch (IllegalArgumentException ex) {
                loggerApp.log(Level.SEVERE, "JobDaliAuto-IllegalArgumentException: ", ex);
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            }

            System.out.println("com.davivienda.sara.jobs.JobDaliAuto.ejbTimeout():" + respuesta);

            TimerService timerService = this.context.getTimerService();
            SchedulerInfo scheduler = (SchedulerInfo) timer.getInfo();
            timer = timerService.createTimer(new CronExpression(scheduler.getCronExpresion()).getNextValidTimeAfter(new Date()), scheduler);

        } catch (Exception exc) {
            loggerApp.log(Level.SEVERE, "JobDaliAuto-Exception: ", exc);
        }
    }

    public String setAjustesDali40LS(Collection<Billetajemulti> regs, Collection<Totalesestacionmultifuncional> regsTotalesestacion) throws EntityServicioExcepcion {

        //VERIFICAR SI ES MEJOR IR REALIZANDO LA SUMATORIA POR CAJERO Y GUARDANDO DATOS EN LA TABLA MOVIMIENTOCUADRE
        String respuesta = "";

        Map<Integer, Long> mapTotalesEstacion = new HashMap<Integer, Long>();

        Long recTotalesEstacion = new Long(0);

        Long totalATM = new Long(0);

        Long totalLinea = new Long(0);

        Long diferencia = new Long(0);

        Integer codCajero = 0;

        TipoAjusteMultifuncional tipoAjusteMultifuncional = TipoAjusteMultifuncional.Inicio;

        //revisar cuando halla mas de un cajero
        if (regsTotalesestacion != null) {

            // creo cada uno de los informes asociados al cajero
            for (Totalesestacionmultifuncional totalesestacionmulti : regsTotalesestacion) {

                if (codCajero.compareTo(new Integer(0)) == 0) {

                    codCajero = totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigocajero();

                }

                if (codCajero.compareTo(totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigocajero()) != 0) {

                    mapTotalesEstacion.put(codCajero, recTotalesEstacion / 100);

                    recTotalesEstacion = new Long(0);

                    codCajero = totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigocajero();

                }

                if (totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(475)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(476)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(477)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(483)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(481)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(95)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(361)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(485)) {

                    recTotalesEstacion = recTotalesEstacion + totalesestacionmulti.getValorevento();

                }

            }

            //lleno el ultimo cajero
            mapTotalesEstacion.put(codCajero, recTotalesEstacion / 100);

        }

        if (regs != null) {

            for (Billetajemulti billetajemulti : regs) {

                billetajemulti.getBilletajemultiPK().getCodigocajero();

                if (!mapTotalesEstacion.isEmpty()) {

                    //revisar que el cajero tenga datos
                    if (mapTotalesEstacion.containsKey(billetajemulti.getBilletajemultiPK().getCodigocajero())) {

                        totalLinea = mapTotalesEstacion.get(billetajemulti.getBilletajemultiPK().getCodigocajero());

                        totalATM = billetajemulti.getValordepefectivoctaaho() + billetajemulti.getValordepefectivoctacte()
                                + billetajemulti.getValorpagoefectivofm() + billetajemulti.getValorpagoefectivotc();

                        if ((!totalLinea.equals(new Long(0))) && (!totalATM.equals(new Long(0)))) {

                            if (totalATM < totalLinea) {

                                diferencia = totalLinea - totalATM;

                                tipoAjusteMultifuncional = TipoAjusteMultifuncional.Faltante;

                            } else if (totalATM > totalLinea) {

                                diferencia = totalATM - totalLinea;

                                tipoAjusteMultifuncional = TipoAjusteMultifuncional.Sobrante;

                            }

                            if (diferencia != 0) {

                                respuesta = respuesta + " Ajuste: " + tipoAjusteMultifuncional.nombre + " cajero : " + billetajemulti.getBilletajemultiPK().getCodigocajero() + "por: " + diferencia.toString();

                            } else {

                                respuesta = respuesta + "  Sin diferencia :  cajero : " + billetajemulti.getBilletajemultiPK().getCodigocajero();

                            }

                            if (diferencia <= ConsultarValMaxAjuste()) {

                                if (diferencia != 0) {

                                    respuesta = respuesta + guardarAjusteStratus(billetajemulti.getBilletajemultiPK().getCodigocajero(), new BigDecimal(diferencia.toString()), "Dali40Ajuste", tipoAjusteMultifuncional);

                                }

                            } else {

                                respuesta = respuesta + " Valor mayor al permitido  maximo de:" + ConsultarValMaxAjuste().toString();

                            }

                            totalATM = new Long(0);

                            totalLinea = new Long(0);

                            diferencia = new Long(0);

                        }

                    }

                }

            }

        }

        return respuesta;

    }

    private String guardarAjusteStratus(Integer codigo_Cajero, BigDecimal valorAjuste, String usuario, TipoAjusteMultifuncional tipoAjusteMultifuncional) throws EntityServicioExcepcion {

        Integer tipoMovCuadre = -1;
        Integer codigoOficina = 0;

        String respuesta = "";
        Cajero cajero = null;

        try {
            cajero = cajeroSessionLocal.buscar(codigo_Cajero);
            codigoOficina = cajero.getOficinaMulti().getCodigooficinamulti();
        } catch (EntityServicioExcepcion ex) {
            loggerApp.log(Level.SEVERE, "", ex);
        }

        try {

            switch (tipoAjusteMultifuncional) {
                case Inicio:
                    // Se asocia un object context particular 
//                objectContext = diarioElectronicoGeneralObjectContext;

                    break;
                case Sobrante:
                    respuesta = cuadreCifrasMultiWsSessionLocal.realizarAjustePorSobrante(usuario, codigo_Cajero, codigoOficina, valorAjuste);
                    respuesta = "B";
                    tipoMovCuadre = com.davivienda.multifuncional.constantes.TipoMovimientoCuadreMultifuncional.Sobrante.codigo;
                    break;

                case Faltante:
                    respuesta = cuadreCifrasMultiWsSessionLocal.realizarAjustePorFaltante(usuario, codigo_Cajero, codigoOficina, valorAjuste);
                    respuesta = "B";
                    tipoMovCuadre = com.davivienda.multifuncional.constantes.TipoMovimientoCuadreMultifuncional.Faltante.codigo;
                    break;

                default:
                    break;
            }
            try {
                //PENDIENTE GUARADAR HISTORICO
                if (tipoMovCuadre > 0) {
                    guardarAjusteCuadre(codigo_Cajero, valorAjuste, usuario, tipoMovCuadre);
                }
            } catch (EntityServicioExcepcion ex) {
                loggerApp.log(Level.SEVERE, "", ex);
            }
            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("B")) {
                        respuesta = "Ajuste Realizado con Exito";
//                        try {
//                            cuadreCifrasMultiSessionLocal.grabarMovimientoCuadre(mc, actualizar);
//                        } catch (EntityServicioExcepcion ex) {
//                            Logger.getLogger(CuadreCifrasMultiGuardarAjusteServletHelper.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                    } else if (respuesta.substring(0, 1).equals("M")) {
                        respuesta = respuesta + "NO se pudo Realizar el Ajuste: ";
                    } else if (respuesta.substring(0, 1).equals("F")) {
                        respuesta = respuesta + "Por favor verificar el  Estado de el Ajuste";
                    }
                } else {
                    respuesta = respuesta + "Por favor verificar el  Estado de el Ajuste";
                }
            } else {
                respuesta = respuesta + "Por favor verificar el  Estado de el Ajuste";
            }
            respuesta = JSon.getJSonRespuesta(0, respuesta);
        } catch (EJBException ex) {
            loggerApp.log(Level.SEVERE, "", ex);
            if (ex.getMessage() == null) {
                respuesta = ex.getCause().getMessage();
            } else {
                respuesta = ex.getMessage();
            }
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), respuesta);
        }
        return respuesta;

    }

    private void guardarAjusteCuadre(Integer codigo_Cajero, BigDecimal valorAjuste, String usuario, Integer tipoMovCuadre) throws EntityServicioExcepcion {
        Movimientocuadremulti mc;
        mc = new Movimientocuadremulti();

        Cajero cajero = null;
        cajero = cajeroSessionLocal.buscar(codigo_Cajero);
        Tipomovimientocuadremulti tmc = null;
        //VALIDARA QUE EL CAJERO NO ESTE CREADO
        if (cajero != null) {
            mc.setCajero(cajeroSessionLocal.buscar(codigo_Cajero));
            mc.setValormovimiento(valorAjuste.longValue());
            mc.setIdusuario(usuario);
            mc.setFecha(com.davivienda.utilidades.conversion.Fecha.getDateAyer());
            mc.setFechaajsute(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
            //tmc = cuadreCifrasMultiSessionLocal.buscarTipoMovimientoCuadre(tipoMovCuadre);
            mc.setCodtipomovimientocuadremuti(tipoMovCuadre.longValue());
            cuadreCifrasMultiSessionLocal.grabarMovimientoCuadre(mc, false);

        }

    }

    private Long ConsultarValMaxAjuste() {
        Long longValReintegro = new Long("0");

        try {
            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("MULTIFUNCIONAL", "MULTIFUNCIONAL.MAX_VALOR_DALI40");
            registroEntityConsulta = confModulosAplicacionLocal.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            //ConfModulosAplicacion registroEntityConsulta=confModulosAplicacionSession.buscar(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.MAX_VALOR_REINTEGRO"));
            longValReintegro = com.davivienda.utilidades.conversion.Cadena.aLong(registroEntityConsulta.getValor());

        } catch (Exception ex) {
            loggerApp.log(Level.SEVERE, "", ex);
            loggerApp.info("Error obteniendo el valor maximo de Dali40 : " + ex.getMessage());
            longValReintegro = new Long("4999999");
        }
        return longValReintegro;

    }

    private void guardarRegistroTxArchivo(String archivoTarea, boolean IndAuto, Calendar fechaTarea, String tarea) {

        Date fechaCarga = new Date();
        String strFechaTarea = "";

        try {

            fechaCarga = com.davivienda.utilidades.conversion.Fecha.getDateHoy();
            strFechaTarea = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaTarea, "yyMMdd");
            adminTareasRegCargueArchivoSessionLocal.guardarRegCargueArchivo(archivoTarea, IndAuto, strFechaTarea, tarea, fechaCarga, "usuarioSara", false, "");

        } catch (IllegalArgumentException ex) {
            loggerApp.info("Error al grabar los datos en RegCargueArchivo para el archivo " + archivoTarea + fechaTarea + " " + ex.getMessage());
        } catch (Exception ex) {
            loggerApp.info("Error al grabar los datos en RegCargueArchivo  para el archivo :" + archivoTarea + fechaTarea + " descripcion Error : " + ex.getMessage());
        }

    }

    private void actualizarRegCargueArchivo(String archivoTarea, boolean IndAuto, Calendar fechaTarea, String msgError, Integer numRegistros) throws EntityServicioExcepcion {

        String strFechaTarea = "";
        Long lngNumRegistros = new Long(0);

        lngNumRegistros = com.davivienda.utilidades.conversion.Cadena.aLong(Integer.toString(numRegistros));
        strFechaTarea = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaTarea, "yyMMdd");

        Regcarguearchivo edcCargue = adminTareasRegCargueArchivoSessionLocal.buscarPorArchivoFecha(archivoTarea, strFechaTarea, IndAuto);
        if (edcCargue != null) {
            if (msgError.equals("")) {
                edcCargue.setEstadocarga(EstadoProceso.FINALIZADO.getEstado());
                edcCargue.setNumregistros(lngNumRegistros);
            } else {
                edcCargue.setEstadocarga(EstadoProceso.ERROR.getEstado());
            }
            edcCargue.setDescripcionerror(msgError);
            edcCargue.setFecha(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
            regCargueArchivoSessionLocal.actualizar(edcCargue);
        }
    }
}
