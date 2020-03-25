package com.davivienda.sara.contingencia.general.helper;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.constantes.CargueArchivoContingencia;
import com.davivienda.sara.procesos.diarioelectronico.session.AdministradorProcesosSessionLocal;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoProceso;
import com.davivienda.sara.constantes.TipoDiario;
import com.davivienda.sara.contingencia.general.ContingenciaManualGeneralObjectContext;
import com.davivienda.sara.contingencia.general.ContingenciaManualHelperInterface;

import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.utilidades.conversion.JSon;
import com.davivienda.utilidades.edc.Edc;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Calendar;
import java.util.logging.Level;
import javax.ejb.EJBException;
import com.davivienda.sara.procesos.edccargue.session.AdministradorProcesosEdcCargueSessionLocal;
import java.util.ArrayList;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;

import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.regcargue.session.RegCargueArchivoSessionLocal;
import com.davivienda.sara.tareas.contingencia.carguearchivos.session.ContingenciaCargueArchivosSessionLocal;
import com.davivienda.sara.tareas.regcargue.session.AdminTareasRegCargueArchivoSessionLocal;

import java.util.Date;
import java.util.logging.Logger;

/**
 * DiarioElectronicoGeneralDiarioElectronicoServletHelper - 27/08/2008
 * Descripcion : Version : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ContingenciaGeneralDiariosServletHelper implements ContingenciaManualHelperInterface {

    private AdministradorProcesosSessionLocal session;
    private ContingenciaManualGeneralObjectContext objectContext = null;
    private String directorioUpload;
    private AdministradorProcesosEdcCargueSessionLocal administradorProcesosEdcCargueSessionLocal;
    private ConfModulosAplicacionLocal confModulosAplicacionSession;
    private AdminTareasRegCargueArchivoSessionLocal adminTareasRegCargueArchivoSession;
    private RegCargueArchivoSessionLocal regCargueArchivoSessionLocal;
    private ContingenciaCargueArchivosSessionLocal contingenciaCargueArchivosSessionLocal;
    private Logger loggerApp;

    public ContingenciaGeneralDiariosServletHelper(AdministradorProcesosSessionLocal session, ContingenciaManualGeneralObjectContext objectContext, AdministradorProcesosEdcCargueSessionLocal administradorProcesosEdcCargueSessionLocal, ConfModulosAplicacionLocal confModulosAplicacionSession, String directorioUpload, AdminTareasRegCargueArchivoSessionLocal adminTareasRegCargueArchivoSession, RegCargueArchivoSessionLocal regCargueArchivoSessionLocal, ContingenciaCargueArchivosSessionLocal contingenciaCargueArchivosSessionLocal) {
        this.session = session;
        this.objectContext = objectContext;
        this.administradorProcesosEdcCargueSessionLocal = administradorProcesosEdcCargueSessionLocal;
        this.directorioUpload = directorioUpload;
        this.confModulosAplicacionSession = confModulosAplicacionSession;
        this.adminTareasRegCargueArchivoSession = adminTareasRegCargueArchivoSession;
        this.regCargueArchivoSessionLocal = regCargueArchivoSessionLocal;
        this.contingenciaCargueArchivosSessionLocal = contingenciaCargueArchivosSessionLocal;
        this.loggerApp = objectContext.getConfigApp().loggerApp;
    }

    public String obtenerDatos() {

        String respuesta = "";
        int intRegProcesados = -1;
        String strExepcion = "";
        ArrayList lstArchivos;
        String nombreArchivo = "";

        Integer intTipoDiario = 3;
        //PENDIENTE OBTENER TIPO DIARIO DE LA INTERFAZ

        try {

            Calendar fechaInicial = null;
            Integer codigoCajero = null;
            try {
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(objectContext.getAtributoFechaInicial().getTime());
            } catch (IllegalArgumentException ex) {
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
            }

            codigoCajero = objectContext.getAtributoCodigo_Cajero();
            if (objectContext.getAtributoTipoDiarioString("tipoDiario") == null || objectContext.getAtributoTipoDiarioString("tipoDiario").equals("")) {
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), "Por favor seleccione un tipo de diario");

            } else {
                intTipoDiario = com.davivienda.utilidades.conversion.Cadena.aInteger(objectContext.getAtributoTipoDiarioString("tipoDiario"));
            }

            if (codigoCajero == 0) {
                throw new IllegalArgumentException("Debe seleccionar un cajero");
            }

            try {

                intRegProcesados = clasificarCarguediario(intTipoDiario, fechaInicial, codigoCajero);

                if (intRegProcesados >= 0) {

                    intRegProcesados = intRegProcesados + 1;
                    respuesta = "Se Actualizaron con exito en la bd : " + intRegProcesados + " Registros a la fecha: "
                            + Fecha.aCadena(Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
                    objectContext.setError(respuesta, 0);

                } else {

                    respuesta = "NO Se Actualizaron los registros debido a que no se pudo acceder al archivo o no se pudo guardar los registros en la bd  a la fecha: "
                            + Fecha.aCadena(Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);

                }
                respuesta = JSon.getJSonRespuesta(CodigoError.SIN_ERROR.getCodigo(), respuesta);

            } catch (EJBException ex) {
                loggerApp.log(Level.SEVERE, "", ex);
                if (ex.getMessage() == null) {
                    strExepcion = ex.getCause().getMessage();
                } else {
                    strExepcion = ex.getMessage();
                }
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, strExepcion);
                objectContext.setError(strExepcion, CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);

            } catch (Exception ex) {
                loggerApp.log(Level.SEVERE, "", ex);
                objectContext.setError(ex.getMessage(), CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            }

        } catch (IllegalArgumentException ex) {
            loggerApp.log(Level.SEVERE, "", ex);
            objectContext.setError(ex.getMessage(), CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
        } finally {

            CambiarEstadoCargue("0");
        }

        return respuesta;
    }

    //metodo consulta estado de  el proceso de cargue de tiras  0- no se esta ejecutando 1- se esta ejecutando
    private String ConsultarEstadoCargue() {
        String strEstadoCargue = "0";

        try {
            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
            registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            //ConfModulosAplicacion registroEntityConsulta=confModulosAplicacionSession.buscar(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGADIARIO"));
            strEstadoCargue = registroEntityConsulta.getValor();

        } catch (Exception ex) {
            loggerApp.log(Level.SEVERE, "", ex);
            strEstadoCargue = "0";
        }
        return strEstadoCargue;

    }

    //metodo cambia estado de  el proceso de cargue de tiras  0- no se esta ejecutando 1- se esta ejecutando
    private void CambiarEstadoCargue(String strEstado) {

        try {

            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
            registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());

            ConfModulosAplicacion registroEntity = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
            // new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
            registroEntity.setValor(strEstado);
            registroEntity.setDescripcion(registroEntityConsulta.getDescripcion());
//       utx.begin();
            confModulosAplicacionSession.actualizar(registroEntity);

        } catch (Exception ex) {
            loggerApp.log(Level.SEVERE, "", ex);

        }

    }

    public Integer clasificarCarguediario(Integer intTipoDiario, Calendar fechaInicial, Integer codigoCajero) throws EntityServicioExcepcion {

        Integer regActualizados = 0;
        String nombreArchivo = "";

        TipoDiario tipoDiario = TipoDiario.Efectivo;
        tipoDiario = TipoDiario.getTipoDiario(intTipoDiario);

        switch (tipoDiario) {

            case Dispensador:
                if (ConsultarEstadoCargue().equals("0")) {
                    CambiarEstadoCargue("1");
                    ConsultarEstadoCargue();

                    nombreArchivo = Edc.obtenerNombreArchivo(codigoCajero, fechaInicial);
                    loggerApp.info("clasificarCarguediario: " + nombreArchivo);
                    administradorProcesosEdcCargueSessionLocal.registrarArchivoEdcCargue(codigoCajero, nombreArchivo, 0, 0, EstadoProceso.CARGUEMANUAL.getEstado(), true);
                    regActualizados = session.cargarArchivoSoloDiario(codigoCajero, fechaInicial, nombreArchivo);
                    session.cargarArchivoTemp(codigoCajero, fechaInicial, nombreArchivo);

                }

                //REVISAR COMO NO CARGAR EN TRANSACCION
                break;

            case Efectivo:
                guardarRegistroTxArchivo(CargueArchivoContingencia.DIARIOSEFECTIVOMULTIFUNCIONAL.nombre, false, fechaInicial, "DiariosEfectivoMulti");
                contingenciaCargueArchivosSessionLocal.cargarDiarioEfectivoMulti();
                break;

            case Cheque:
                guardarRegistroTxArchivo(CargueArchivoContingencia.DIARIOSREINTEGROS.nombre, false, fechaInicial, "DiariosReintegros");
                contingenciaCargueArchivosSessionLocal.cargarDiarioChequeMulti();
                break;

            case Log:
                guardarRegistroTxArchivo(CargueArchivoContingencia.LOGCAJEROSMULTIFUNCIONAL.nombre, false, fechaInicial, "LogCajerosMultifuncional");
                contingenciaCargueArchivosSessionLocal.cargarLogMulti();
                break;

        }

        return regActualizados;
    }

//}
    private void guardarRegistroTxArchivo(String archivoTarea, boolean IndAuto, Calendar fechaTarea, String tarea) {

        Date fechaCarga = new Date();
        String strFechaTarea = "";

        try {

            fechaCarga = com.davivienda.utilidades.conversion.Fecha.getDateHoy();
            strFechaTarea = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaTarea, "yyMMdd");
            adminTareasRegCargueArchivoSession.guardarRegCargueArchivo(archivoTarea, IndAuto, strFechaTarea, tarea, fechaCarga, objectContext.getIdUsuarioEnSesion(), false, "");

        } catch (IllegalArgumentException ex) {
            loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos en RegCargueArchivo para el archivo " + archivoTarea + fechaTarea + " " + ex.getMessage());
        } catch (Exception ex) {
            loggerApp.log(Level.SEVERE, "", ex);
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
}
