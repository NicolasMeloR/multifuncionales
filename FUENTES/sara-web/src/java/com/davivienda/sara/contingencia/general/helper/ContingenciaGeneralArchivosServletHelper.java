package com.davivienda.sara.contingencia.general.helper;

import com.davivienda.multifuncional.reintegros.session.ReintegrosMultiSessionLocal;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.constantes.CargueArchivoContingencia;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoProceso;

import com.davivienda.sara.constantes.TipoCajeroMulti;
import com.davivienda.sara.contingencia.general.ContingenciaManualGeneralObjectContext;
import com.davivienda.sara.contingencia.general.ContingenciaManualHelperInterface;
import com.davivienda.utilidades.conversion.JSon;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import com.davivienda.sara.procesos.cuadrecifras.session.ProcesoCuadreCifrasSessionLocal;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasCargasSessionLocal;
import java.util.Collection;
import com.davivienda.sara.cuadrecifras.general.bean.MovimientoCuadreCifrasBean;
import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.procesos.edccargue.session.AdministradorProcesosEdcCargueSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.regcargue.session.RegCargueArchivoSessionLocal;

import com.davivienda.sara.tablas.transacciontemp.session.TransaccionTempSessionLocal;
import com.davivienda.sara.tareas.contingencia.carguearchivos.session.ContingenciaCargueArchivosSessionLocal;
import com.davivienda.sara.tareas.regcargue.session.AdminTareasRegCargueArchivoSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.util.ArrayList;
import java.util.Date;

/**
 * DiarioElectronicoGeneralDiarioElectronicoServletHelper - 27/08/2008
 * Descripci�n : Versi�n : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ContingenciaGeneralArchivosServletHelper implements ContingenciaManualHelperInterface {

    private ProcesoCuadreCifrasSessionLocal session;
    private ContingenciaManualGeneralObjectContext objectContext = null;
    private CuadreCifrasCargasSessionLocal cuadreCifrasCargasSessionLocal;
    private ConfModulosAplicacionLocal confModulosAplicacionSession;
    private AdminTareasRegCargueArchivoSessionLocal adminTareasRegCargueArchivoSession;
    private RegCargueArchivoSessionLocal regCargueArchivoSessionLocal;
    private ContingenciaCargueArchivosSessionLocal contingenciaCargueArchivosSessionLocal;
    private ReintegrosMultiSessionLocal reintegrosMultiSessionLocal;
    private String directorioUpload;
    private TransaccionTempSessionLocal transaccionTempSessionLocal;
    private AdministradorProcesosEdcCargueSessionLocal administradorProcesosEdcCargueSessionLocal;
    private String strTarea;

    public ContingenciaGeneralArchivosServletHelper(ProcesoCuadreCifrasSessionLocal session, ContingenciaManualGeneralObjectContext objectContext, CuadreCifrasCargasSessionLocal cuadreCifrasCargasSessionLocal, ConfModulosAplicacionLocal confModulosAplicacionSession, AdminTareasRegCargueArchivoSessionLocal adminTareasRegCargueArchivoSession, RegCargueArchivoSessionLocal regCargueArchivoSessionLocal, ContingenciaCargueArchivosSessionLocal contingenciaCargueArchivosSessionLocal, ReintegrosMultiSessionLocal reintegrosMultiSessionLocal, String directorioUpload, TransaccionTempSessionLocal transaccionTempSessionLocal, AdministradorProcesosEdcCargueSessionLocal administradorProcesosEdcCargueSessionLocal) {
        this.session = session;
        this.objectContext = objectContext;
        this.cuadreCifrasCargasSessionLocal = cuadreCifrasCargasSessionLocal;
        this.confModulosAplicacionSession = confModulosAplicacionSession;
        this.adminTareasRegCargueArchivoSession = adminTareasRegCargueArchivoSession;
        this.regCargueArchivoSessionLocal = regCargueArchivoSessionLocal;
        this.contingenciaCargueArchivosSessionLocal = contingenciaCargueArchivosSessionLocal;
        this.reintegrosMultiSessionLocal = reintegrosMultiSessionLocal;
        this.directorioUpload = directorioUpload;
        this.transaccionTempSessionLocal = transaccionTempSessionLocal;
        this.administradorProcesosEdcCargueSessionLocal = administradorProcesosEdcCargueSessionLocal;

        strTarea = "";

    }

    public String obtenerDatos() {

        String respuesta = "";
        int intRegActualizados = -1;
        int tipoTareaArchivoCajero = -1;
        String strExepcion = "";
        boolean cargueautomatico = false;
        String strArchivoCarga = "";
        Calendar fechaInicial = null;
        CargueArchivoContingencia tipoArchivo = CargueArchivoContingencia.TODOS;

        try {

            try {
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(objectContext.getAtributoFechaInicial().getTime());
            } catch (IllegalArgumentException ex) {

                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), "Por favor seleccione una fecha");

            }
            if (objectContext.getAtributoTipoCajeroString("tipoCajero") == null || objectContext.getAtributoTipoCajeroString("tipoCajero").equals("")) {

                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), "Por favor seleccione un tipo de cajero");

            }
            if (objectContext.getAtributoTipoTareaString("tipoTarea") == null || objectContext.getAtributoTipoTareaString("tipoTarea").equals("")) {
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), "Por favor seleccione un tipo de tarea");

            }
            //

            try {
                // Consulta los registros seg�n los par�metros tomados del request

                if (ConsultarEstadoCargue().equals("0") && respuesta.equals("")) {
                    Integer tipoCajero = com.davivienda.utilidades.conversion.Cadena.aInteger(objectContext.getAtributoTipoCajeroString("tipoCajero"));
                    CambiarEstadoCargue("1");
                    tipoTareaArchivoCajero = clasificarTarea(tipoCajero);
                    tipoArchivo = CargueArchivoContingencia.getCarguearchivo(tipoTareaArchivoCajero);
                    strArchivoCarga = tipoArchivo.nombre;
                    intRegActualizados = realizarTarea(fechaInicial, tipoTareaArchivoCajero);
                    
                    if(intRegActualizados != -1){
                        //respuesta = "Se Actualizaron con exito en la bd : " + intRegActualizados + " Registros ";
                        respuesta = "Se Actualizaron con exito en la bd los  Registros ";
                        objectContext.setError(respuesta, 0);
                        respuesta = JSon.getJSonRespuesta(CodigoError.SIN_ERROR.getCodigo(), respuesta);
                    }
                }

            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);

                if (ex.getMessage() == null) {
                    strExepcion = ex.getCause().getMessage();
                } else {
                    strExepcion = ex.getMessage();
                }

                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, strExepcion);
                objectContext.setError(strExepcion, CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
                //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(),Constantes.MSJ_QUERY_ERROR);
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
                strExepcion = ex.getMessage();
                objectContext.setError(ex.getMessage(), CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
                //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(),Constantes.MSJ_QUERY_ERROR);
            }

        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
            strExepcion = ex.getMessage();
            objectContext.setError(ex.getMessage(), CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
            //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(),Constantes.MSJ_QUERY_ERROR);
        } finally {

            CambiarEstadoCargue("0");
            if (!strExepcion.equals("")) {
                try {
                    strExepcion = "Error al grabar archivo no se pudo grabar el archivo o no existe " + strExepcion;
                    actualizarRegCargueArchivo(strArchivoCarga, cargueautomatico, fechaInicial, strExepcion, 0);
                } catch (EntityServicioExcepcion ex) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
                    Logger.getLogger(ContingenciaGeneralArchivosServletHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        return respuesta;
    }

    public Collection<MovimientoCuadreCifrasBean> obtenerDatosCollectionCC() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //metodo consulta estado de  el proceso de cargue de tiras  0- no se esta ejecutando 1- se esta ejecutando
    private String ConsultarEstadoCargue() {
        String strEstadoCargue = "0";

        try {
            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGACUADRE");
            registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            //ConfModulosAplicacion registroEntityConsulta=confModulosAplicacionSession.buscar(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGADIARIO"));
            strEstadoCargue = registroEntityConsulta.getValor();

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error obteniendo estado cargue : " + ex.getMessage());
            strEstadoCargue = "0";
        }
        return strEstadoCargue;

    }

    //metodo cambia estado de  el proceso de cargue de tiras  0- no se esta ejecutando 1- se esta ejecutando
    private void CambiarEstadoCargue(String strEstado) {

        try {

            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGACUADRE");
            registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());

            //ConfModulosAplicacion registroEntityConsulta=confModulosAplicacionSession.buscar(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGADIARIO"));
            ConfModulosAplicacion registroEntity = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGACUADRE");
            // new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
            registroEntity.setValor(strEstado);
            registroEntity.setDescripcion(registroEntityConsulta.getDescripcion());
//       utx.begin();
            confModulosAplicacionSession.actualizar(registroEntity);

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error cambiando estado cargue : " + ex.getMessage());

        }

    }

    private void guardarRegistroTxArchivo(String archivoTarea, boolean IndAuto, Calendar fechaTarea, String tarea) {

        Date fechaCarga = new Date();
        String strFechaTarea = "";
        String usuario = "";
        usuario = objectContext.getIdUsuarioEnSesion();
        boolean esCargueDipensador = false;

        try {

            if (tarea.equals("DiariosDispensador")) {
                esCargueDipensador = true;
            }

            fechaCarga = com.davivienda.utilidades.conversion.Fecha.getDateHoy();
            strFechaTarea = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaTarea, "yyMMdd");
            adminTareasRegCargueArchivoSession.guardarRegCargueArchivo(archivoTarea, IndAuto, strFechaTarea, tarea, fechaCarga, usuario, esCargueDipensador, "");

        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos en RegCargueArchivo para el archivo " + archivoTarea + fechaTarea + " " + ex.getMessage());
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos en RegCargueArchivo  para el archivo :" + archivoTarea + fechaTarea + " descripcion Error : " + ex.getMessage());
        }

    }

    private void actualizarRegCargueArchivo(String archivoTarea, boolean IndAuto, Calendar fechaTarea, String msgError, Integer numRegistros) throws EntityServicioExcepcion {

        String strFechaTarea = "";
        Long lngNumRegistros = new Long(0);

        lngNumRegistros = com.davivienda.utilidades.conversion.Cadena.aLong(Integer.toString(numRegistros));
        strFechaTarea = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaTarea, "yyMMdd");

        Regcarguearchivo edcCargue = adminTareasRegCargueArchivoSession.buscarPorArchivoFecha(archivoTarea, strFechaTarea, IndAuto);
        if (edcCargue != null) {
            if (msgError.equals("")) {
                edcCargue.setEstadocarga(EstadoProceso.FINALIZADO.getEstado());
                edcCargue.setNumregistros(lngNumRegistros);
            } else {
                edcCargue.setEstadocarga(EstadoProceso.ERROR.getEstado());
            }
            edcCargue.setDescripcionerror(msgError);
            edcCargue.setFechafinal(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
            regCargueArchivoSessionLocal.actualizar(edcCargue);

        }

    }

    private Integer clasificarTarea(Integer tipoCajero) {

        Integer tipoTareaArchivoCajero = 0;

        if (objectContext.getAtributoTipoTareaString("tipoTarea").length() > 0) {

            Integer tipoTareaArchivo = com.davivienda.utilidades.conversion.Cadena.aInteger(objectContext.getAtributoTipoTareaString("tipoTarea"));
            CargueArchivoContingencia tipoArchivo = CargueArchivoContingencia.TODOS;
            tipoArchivo = CargueArchivoContingencia.getCarguearchivo(tipoTareaArchivo);

            switch (tipoArchivo) {

                case CFAMO001:

                    if (TipoCajeroMulti.getTipoCajeroMulti(tipoCajero).equals(TipoCajeroMulti.DISPENSADOR)) {
                        tipoTareaArchivoCajero = CargueArchivoContingencia.CFAMO001.codigo;
                        strTarea = "CuadreDispensador";
                    } else {
                        tipoTareaArchivoCajero = CargueArchivoContingencia.CFAMO003.codigo;
                        strTarea = "CuadreMultifuncional";
                    }

                    break;

                case GEATO002:

                    if (TipoCajeroMulti.getTipoCajeroMulti(tipoCajero).equals(TipoCajeroMulti.DISPENSADOR)) {
                        tipoTareaArchivoCajero = CargueArchivoContingencia.GEATO002.codigo;
                        strTarea = "CuadreDispensador";
                    } else {
                        tipoTareaArchivoCajero = CargueArchivoContingencia.GEATO003.codigo;
                        strTarea = "CuadreMultifuncional";
                    }

                    break;

                case OTBMO001:

                    if (TipoCajeroMulti.getTipoCajeroMulti(tipoCajero).equals(TipoCajeroMulti.DISPENSADOR)) {
                        tipoTareaArchivoCajero = CargueArchivoContingencia.OTBMO001.codigo;
                        strTarea = "CuadreDispensador";
                    } else {
                        tipoTareaArchivoCajero = CargueArchivoContingencia.OTBMO003.codigo;
                        strTarea = "CuadreMultifuncional";
                    }

                    break;

                case CUADREDISPENSADOR:

                    if (TipoCajeroMulti.getTipoCajeroMulti(tipoCajero).equals(TipoCajeroMulti.DISPENSADOR)) {
                        tipoTareaArchivoCajero = CargueArchivoContingencia.CUADREDISPENSADOR.codigo;
                        strTarea = "CuadreDispensador";
                    } else {
                        tipoTareaArchivoCajero = CargueArchivoContingencia.CUADREMULTIFUNCIONAL.codigo;
                        strTarea = "CuadreMultifuncional";
                    }

                    break;

                case MVTOATM01:

                    if (TipoCajeroMulti.getTipoCajeroMulti(tipoCajero).equals(TipoCajeroMulti.DISPENSADOR)) {
                        tipoTareaArchivoCajero = CargueArchivoContingencia.MVTOATM01.codigo;
                        strTarea = "CuadreDispensador";
                    } else {
                        tipoTareaArchivoCajero = CargueArchivoContingencia.MVTODEP03.codigo;
                        strTarea = "CuadreMultifuncional";
                    }

                    break;

                case REINTEGROSDISPENSADOR:

                    if (TipoCajeroMulti.getTipoCajeroMulti(tipoCajero).equals(TipoCajeroMulti.DISPENSADOR)) {
                        tipoTareaArchivoCajero = CargueArchivoContingencia.REINTEGROSDISPENSADOR.codigo;
                        strTarea = "ReintegrosDispensador";
                    } else {
                        tipoTareaArchivoCajero = CargueArchivoContingencia.REINTEGROSMULTI.codigo;
                        strTarea = "ReintegrosMultifuncional";
                    }

                    break;

                case DIARIOSDISPENSADOR:

                    if (TipoCajeroMulti.getTipoCajeroMulti(tipoCajero).equals(TipoCajeroMulti.DISPENSADOR)) {
                        tipoTareaArchivoCajero = CargueArchivoContingencia.DIARIOSDISPENSADOR.codigo;
                        strTarea = "DiariosDispensador";
                    } else {
                        tipoTareaArchivoCajero = CargueArchivoContingencia.DIARIOSEFECTIVOMULTIFUNCIONAL.codigo;
                        strTarea = "DiariosEfectivoMulti";
                    }

                    break;

                case DIARIOSREINTEGROS:

                    tipoTareaArchivoCajero = CargueArchivoContingencia.DIARIOSREINTEGROS.codigo;
                    strTarea = "DiariosReintegros";

                    break;

                case LOGCAJEROSMULTIFUNCIONAL:

                    tipoTareaArchivoCajero = CargueArchivoContingencia.LOGCAJEROSMULTIFUNCIONAL.codigo;
                    strTarea = "LogCajerosMultifuncional";

                    break;

            }

        }

        return tipoTareaArchivoCajero;

    }

    private Integer realizarTarea(Calendar fechaInicial, Integer tipoTareaArchivo) throws Exception {

        Logger logger = objectContext.getConfigApp().loggerApp;

        logger.info("ContingenciaGeneralArchivosServletHelper -- realizarTarea");
        CargueArchivoContingencia tipoArchivo = CargueArchivoContingencia.TODOS;
        tipoArchivo = CargueArchivoContingencia.getCarguearchivo(tipoTareaArchivo);
        Integer intRegActualizados = -1;
        boolean ActRegistro = false;
        String strExepcion = "";
        Calendar fechaProceso = null;
        fechaProceso = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaInicial, -1);
        Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
        logger.info("ContingenciaGeneralArchivosServletHelper -- " + tipoArchivo);

        if (tipoTareaArchivo != CargueArchivoContingencia.TODOS.codigo) {

            guardarRegistroTxArchivo(tipoArchivo.nombre, false, fechaInicial, strTarea);
            try {

                switch (tipoArchivo) {

                    case CFAMO001:

                        intRegActualizados = cuadreCifrasCargasSessionLocal.cargarArchivoCorte(fechaInicial, false);
                        ActRegistro = true;

                        break;

                    case CFAMO003:

                        contingenciaCargueArchivosSessionLocal.cargarArchivoCfamoMulti();
                        intRegActualizados = 0;

                        break;

                    case GEATO002:

                        intRegActualizados = cuadreCifrasCargasSessionLocal.cargarArchivoTotalesEgresos(fechaInicial, false);
                        ActRegistro = true;

                        break;

                    case GEATO003:

                        contingenciaCargueArchivosSessionLocal.cargarArchivoGeatoMulti();
                        intRegActualizados = 0;
                        break;

                    case OTBMO001:

                        intRegActualizados = cuadreCifrasCargasSessionLocal.cargarArchivoProvisiones(fechaInicial, false);
                        ActRegistro = true;

                        break;

                    case OTBMO003:

                        contingenciaCargueArchivosSessionLocal.cargarArchivoOtbmoMulti();
                        intRegActualizados = 0;
                        break;

                    case CUADREDISPENSADOR:

                        intRegActualizados = session.procesarCuadreCifras(fechaProceso);
                        ActRegistro = true;

                        break;

                    case CUADREMULTIFUNCIONAL:

                        //PENDIENTE 
                        break;

                    case MVTOATM01:

                        contingenciaCargueArchivosSessionLocal.cargarArchivoHostDispensa();
                        intRegActualizados = 0;
                        break;

                    case MVTODEP03:

                        contingenciaCargueArchivosSessionLocal.cargarArchivoHostMulti();
                        intRegActualizados = 0;
                        break;

                    case REINTEGROSDISPENSADOR:
                        //SE DEBE ENVIAR LA FECHA DE LAS TRANSACCIONES   
                        contingenciaCargueArchivosSessionLocal.calcReintegrosDispensa();
                        intRegActualizados = 0;
                        break;

                    case REINTEGROSMULTI:

                        //SE DEBE ENVIAR LA FECHA DE LAS TRANSACCIONES   
                        /*
                        intRegActualizados = reintegrosMultiSessionLocal.calcularReintegros(fechaProceso, fechaHisto);
                        ActRegistro = true;*/
                        logger.info("ContingenciaGeneralArchivosServletHelper -- realizarTarea contingenciaCargueArchivosSessionLocal.cargarReintegrosMultifuncional");
                        String fechaConsulta = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicial, FormatoFecha.CICLO_EDC);
                        contingenciaCargueArchivosSessionLocal.cargarReintegrosMultifuncional(fechaConsulta);
                        intRegActualizados = 0;
                        break;

                    case DIARIOSDISPENSADOR:

                        intRegActualizados = cargarDiariosDispensador(true, fechaInicial);

                        break;

                    case DIARIOSEFECTIVOMULTIFUNCIONAL:

                        //SE DEBE ENVIAR LA FECHA DE LAS TRANSACCIONES   
                        contingenciaCargueArchivosSessionLocal.cargarDiarioEfectivoMulti();
                        intRegActualizados = 0;
                        break;

                    case DIARIOSREINTEGROS:

                        intRegActualizados = cargarDiariosDispensador(false, fechaInicial);

                        break;

                    case LOGCAJEROSMULTIFUNCIONAL:

                        //SE DEBE ENVIAR LA FECHA DE LAS TRANSACCIONES   
                        contingenciaCargueArchivosSessionLocal.cargarLogMulti();
                        intRegActualizados = 0;

                        break;

                }

                if (ActRegistro) {

                    actualizarRegCargueArchivo(tipoArchivo.nombre, false, fechaInicial, "", intRegActualizados);
                }

            } catch (EntityServicioExcepcion ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
                strExepcion = ex.getMessage();
                objectContext.setError(ex.getMessage(), CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());

            } finally {
                CambiarEstadoCargue("0");
                try {
                    if (!strExepcion.equals("")) {
                        strExepcion = "Error al grabar archivo no se pudo grabar el archivo o no existe " + strExepcion;
                        actualizarRegCargueArchivo(tipoArchivo.nombre, false, fechaInicial, strExepcion, 0);
                    }
                } catch (EntityServicioExcepcion ex) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
                }
            }
        }
        return intRegActualizados;

    }

    private Integer cargarDiariosDispensador(boolean tipoCarga, Calendar fechaInicial) throws Exception {
        String nombreArchivo = "";
        ArrayList lstArchivos;
        Integer intArchivosDescomprimidos = -1;

        nombreArchivo = com.davivienda.utilidades.edc.Edc.getNombreArchivoCiclosComprimido(fechaInicial);
        lstArchivos = com.davivienda.utilidades.archivo.ProcesosArchivo.unzipArray(directorioUpload, nombreArchivo);
        intArchivosDescomprimidos = administradorProcesosEdcCargueSessionLocal.registrarCicloEdcCargue(lstArchivos, nombreArchivo, fechaInicial, EstadoProceso.CARGUEMANUAL.getEstado(), false);
        //llamar a Store Procedures
        try {
            //transaccionTempSessionLocal.mantenimientoDiariosStoreP();
            transaccionTempSessionLocal.mantenimientoDiariosBorraStoreP();
        } catch (EntityServicioExcepcion ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
        } finally {
            transaccionTempSessionLocal.mantenimientoDiariosBorraStoreP();
        }

        transaccionTempSessionLocal.cargarCicloTempXStoreP();
        if (tipoCarga) {
            transaccionTempSessionLocal.cargarDiariosElectronicosXStoreP();
        }
        return intArchivosDescomprimidos;
    }

}
