package com.davivienda.sara.diarioelectronico.general.helper;

import com.davivienda.sara.base.ProcesosArchivoObjectContextWeb;
import com.davivienda.sara.procesos.diarioelectronico.session.AdministradorProcesosSessionLocal;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoProceso;
import com.davivienda.sara.diarioelectronico.general.ProcesosArchivoHelperInterface;
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
import com.davivienda.utilidades.Constantes;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * DiarioElectronicoGeneralDiarioElectronicoServletHelper - 27/08/2008
 * Descripci�n : Versi�n : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ProcesosArchivoCargarArchivoServletHelper implements ProcesosArchivoHelperInterface {

    private AdministradorProcesosSessionLocal session;
    private ProcesosArchivoObjectContextWeb objectContext = null;
    private boolean blnObtenerCajero;
    private String directorioUpload;
    private AdministradorProcesosEdcCargueSessionLocal administradorProcesosEdcCargueSessionLocal;
    private ConfModulosAplicacionLocal confModulosAplicacionSession;

    public ProcesosArchivoCargarArchivoServletHelper(AdministradorProcesosSessionLocal session, ProcesosArchivoObjectContextWeb objectContext, AdministradorProcesosEdcCargueSessionLocal administradorProcesosEdcCargueSessionLocal, ConfModulosAplicacionLocal confModulosAplicacionSession, String directorioUpload, boolean blnObtenerCajero) {
        this.session = session;
        this.objectContext = objectContext;
        this.administradorProcesosEdcCargueSessionLocal = administradorProcesosEdcCargueSessionLocal;
        this.blnObtenerCajero = blnObtenerCajero;
        this.directorioUpload = directorioUpload;
        this.confModulosAplicacionSession = confModulosAplicacionSession;
    }

    public String obtenerDatos() {

        String respuesta = "";
        int intRegProcesados = -1;
        String strExepcion = "";
        ArrayList lstArchivos;
        String nombreArchivo = "";

        try {

            Calendar fechaInicial = null;
            Integer codigoCajero = null;
            try {
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(objectContext.getAtributoFechaInicial().getTime());
            } catch (IllegalArgumentException ex) {
                //fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
                throw new IllegalArgumentException(Constantes.MSJ_SELECCIONAR_FECHA);
            }
            if (blnObtenerCajero) {

                //OJOOO SOLO PARA PRUEBAS
                //codigoCajero = 433;
                codigoCajero = objectContext.getAtributoCodigoCajeroInteger();

                if (codigoCajero == 0) {
                    throw new IllegalArgumentException(Constantes.MSJ_SELECCIONAR_CAJERO);
                }
            }
            try {
                if (ConsultarEstadoCargue().equals("0")) {
                    CambiarEstadoCargue("1");
                    ConsultarEstadoCargue();

                    // Consulta los registros seg�n los par�metros tomados del request
                    if (blnObtenerCajero) {
                        nombreArchivo = Edc.obtenerNombreArchivo(codigoCajero, fechaInicial);
                        administradorProcesosEdcCargueSessionLocal.registrarArchivoEdcCargue(codigoCajero, nombreArchivo, 0, 0, EstadoProceso.INICIADO.getEstado(), false);
                        intRegProcesados = session.cargarArchivo(codigoCajero, fechaInicial, nombreArchivo);
                        session.cargarArchivoTemp(codigoCajero, fechaInicial, nombreArchivo);
                    } else {
                        nombreArchivo = com.davivienda.utilidades.edc.Edc.getNombreArchivoCiclosComprimido(fechaInicial);
                        lstArchivos = com.davivienda.utilidades.archivo.ProcesosArchivo.unzipArray(directorioUpload, nombreArchivo);

                        //administradorProcesosEdcCargueSessionLocal.registrarCicloEdcCargue(lstArchivos,nombreArchivo,com.davivienda.utilidades.conversion.Fecha.getCalendarHoy());
                        administradorProcesosEdcCargueSessionLocal.registrarCicloEdcCargue(lstArchivos, nombreArchivo, fechaInicial, EstadoProceso.INICIADO.getEstado(), false);;
                        intRegProcesados = session.cargarCiclo(fechaInicial);
                    }

                    if (intRegProcesados >= 0) {
                        respuesta = "Se Actualizaron con exito en la bd : " + intRegProcesados + " Registros a la fecha: "
                                + Fecha.aCadena(Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
                        objectContext.setError(respuesta, 0);

                    } else {

                        respuesta = "NO Se Actualizaron los registros debido a que no se pudo acceder al archivo o no se pudo guardar los registros en la bd  a la fecha: "
                                + Fecha.aCadena(Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);

                    }
                    respuesta = JSon.getJSonRespuesta(CodigoError.SIN_ERROR.getCodigo(), respuesta);

                } else {

                    respuesta = JSon.getJSonRespuesta(CodigoError.SIN_AUTORIZACION.getCodigo(), "el indicador de cargue esta en estado 1-procesando");
                }
            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
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
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                objectContext.setError(ex.getMessage(), CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
                //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(),Constantes.MSJ_QUERY_ERROR);
            }

        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            objectContext.setError(ex.getMessage(), CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
            //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(),Constantes.MSJ_QUERY_ERROR);
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
            java.util.logging.Logger.getLogger("globalApp").info("Error obteniendo estado cargue : " + ex.getMessage());
            strEstadoCargue = "0";
        }
        return strEstadoCargue;

    }

    //metodo cambia estado de  el proceso de cargue de tiras  0- no se esta ejecutando 1- se esta ejecutando
    private void CambiarEstadoCargue(String strEstado) {

        try {
//         ConfModulosAplicacion registroEntity =em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGADIARIO"));
//                  // new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
//         registroEntity.setValor(strEstado);
//         em.persist(registroEntity);
//         em.flush();

            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
            registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());

            //ConfModulosAplicacion registroEntityConsulta=confModulosAplicacionSession.buscar(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGADIARIO"));
            ConfModulosAplicacion registroEntity = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
            // new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
            registroEntity.setValor(strEstado);
            registroEntity.setDescripcion(registroEntityConsulta.getDescripcion());
//       utx.begin();
            confModulosAplicacionSession.actualizar(registroEntity);

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error cambiando estado cargue : " + ex.getMessage());

        }

    }

    public String diasTranscurridos(int dias) {

//                String ii=new String();
//                int diasTranscurridos=328;
//                ii=diasTranscurridos(diasTranscurridos);
        String fechaFinal = "";
        Calendar fecha = Calendar.getInstance();
        //Se instacia la fecha 
        //OJOO cambiara 2000 por el a�o presente comparando que es mayor
        fecha.set(2000, 00, 01);

        // se suma los dias o restan 
        fecha.add(Calendar.DAY_OF_YEAR, dias);
        // verifico fecha 
        Timestamp time = new Timestamp(fecha.getTimeInMillis());
        SimpleDateFormat Formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaString = Formato.format(time);
        System.out.println(fechaString);
        return fechaFinal;
    }

}
