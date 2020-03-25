package com.davivienda.sara.reintegros.general.helper;

import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.ReintegrosGeneralObjectContext;
import com.davivienda.sara.tablas.notasdebito.session.NotasDebitoSessionLocal;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.sara.constantes.TipoCuenta;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.procesos.autenticacion.webservice.session.AutenticacionLdapWSProcesosSessionLocal;
import com.davivienda.sara.procesos.reintegros.notas.session.ReintegrosNotasProcesosSessionLocal;
import com.davivienda.sara.procesos.reintegros.session.ProcesoReintegrosSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.utilidades.conversion.JSon;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.EJBException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * ReintegrosRevisarServletHelper - 27/08/2008 Descripción : Helper para el
 * manejo de los requerimientos de Reintegros Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ReintegrosGuadarNotasDebitoServletHelper implements ReintegrosHelperInterface {

    private NotasDebitoSessionLocal session = null;
    private ProcesoReintegrosSessionLocal procesoReintegrosSession;
    private ReintegrosGeneralObjectContext objectContext = null;
    private ConfModulosAplicacionLocal confModulosAplicacionSession;
    private AutenticacionLdapWSProcesosSessionLocal autenticacionLdapWSProcesosSessionLocal;
    private ReintegrosNotasProcesosSessionLocal reintegrosNotasProcesosSessionLocal;
    private String respuestaJSon;

    public ReintegrosGuadarNotasDebitoServletHelper(NotasDebitoSessionLocal session, ProcesoReintegrosSessionLocal procesoReintegrosSession, ConfModulosAplicacionLocal confModulosAplicacionSession, AutenticacionLdapWSProcesosSessionLocal autenticacionLdapWSProcesosSessionLocal, ReintegrosNotasProcesosSessionLocal reintegrosNotasProcesosSessionLocal, ReintegrosGeneralObjectContext objectContext) {
        this.session = session;
        this.procesoReintegrosSession = procesoReintegrosSession;
        this.confModulosAplicacionSession = confModulosAplicacionSession;
        this.autenticacionLdapWSProcesosSessionLocal = autenticacionLdapWSProcesosSessionLocal;
        this.reintegrosNotasProcesosSessionLocal = reintegrosNotasProcesosSessionLocal;
        this.objectContext = objectContext;
    }

    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        try {
            try {

                //DESCOMENTAREAR SOLO PARA PRUEBAS
                if (getAutenticacion()) {
                    actualizarJsonSelecionados(objectContext.getAtributoJsonArray("events"));
                }

            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_SIN_DATA);
                }
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            }

        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
        }
        respuesta = this.respuestaJSon;
        return respuesta;
    }

    private String getParametro(String strParametro, String strValorDefault) {
        String strValParametro = "";
        try {
            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", strParametro);
            registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            strValParametro = registroEntityConsulta.getValor();

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error obteniendo el valor del parametro: " + strParametro + "  " + ex.getMessage());
            strValParametro = strValorDefault;
        }
        return strValParametro;
    }

    private boolean getAutenticacion() {
        boolean blnAutorizado = false;
        String respuesta = "";
        String grupoSaraNotaCredito = "";
        String[] gruposUsuario;
        try {
            grupoSaraNotaCredito = getParametro("SARA.GRUPO_NOTA_CREDITO", "");
            respuesta = autenticacionLdapWSProcesosSessionLocal.autenticarLdap(objectContext.getUsuarioLdap(), objectContext.getClaveLdap());
            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("B")) {
                        gruposUsuario = respuesta.split(";");
                        for (int i = 0; i < gruposUsuario.length; i++) {
                            if (grupoSaraNotaCredito.equals(gruposUsuario[i])) {
                                blnAutorizado = true;
                                // respuestaJSon= JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.codigo, gruposUsuario[i]);
                            }
                        }
                    } else {
                        respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.codigo, respuesta);

                    }
                }
            }
        } catch (Exception ex) {
            objectContext.setError(ex.getMessage(), CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
        }

        return blnAutorizado;

    }

    private boolean actualizarNotaDebito(Integer codigoCajero, Date fechaProceso, Long valorajustado, String numeroCuenta, Long valor) {
        String usuario = "";
        String mensaje = "";
        String mensajeError = "";
        mensaje = respuestaJSon;
        usuario = objectContext.getIdUsuarioEnSesion();
        Notasdebito regNotasdebito = null;
        boolean estadoTransaccion = false;
        try {
            regNotasdebito = getNotaDebitoXCuentaValor(codigoCajero, fechaProceso, numeroCuenta, valor);
            if (regNotasdebito != null) {
                if (regNotasdebito.getConcepto() == null || !regNotasdebito.getConcepto().trim().equals("16")) {
                    valorajustado = new Long(regNotasdebito.getComision());
                }
                mensajeError = GuardarStratusNotaDebito(codigoCajero, new BigDecimal(valorajustado), regNotasdebito.getNumerocuenta(), regNotasdebito.getTipocuenta(), regNotasdebito.getConcepto());
                if (mensajeError.substring(0, 1).equals("B")) {
                    regNotasdebito.setEstado(EstadoReintegro.FINALIZADOEXITOSO.codigo);
                    regNotasdebito.setFechaaplica(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
                    estadoTransaccion = true;
                } else {
                    //OJO VALIDAR ESTE ESTADO
                    regNotasdebito.setEstado(EstadoReintegro.ERRORSTRATUS.codigo);
                }
                regNotasdebito.setError(mensajeError);
                //OJO DESCOMENTAREAR EN PRUEBAS 
                regNotasdebito.setUsuarioautoriza(usuario);
                //guardar registro
                procesoReintegrosSession.actualizarNotaDebito(regNotasdebito);
            }

        } catch (Exception ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
        }

        return estadoTransaccion;
    }

    //obtengo el reintegro pro la llave formada por  codigoCajero , fechaProceso , fechaProceso
    private Notasdebito getReintegroXLlave(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion) throws Exception {
        Notasdebito regReintegros = null;
        try {
            Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
            regReintegros = session.getNotasDebitoXLlave(codigoCajero, fechaProceso, fechaHisto);
        } catch (EJBException ex) {
            if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            }
            if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            }
            if (ex.getLocalizedMessage().contains("NoResultException")) {
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
            }
        }

        return regReintegros;
    }

    //obtengo el reintegro pro la llave formada por  codigoCajero , fechaProceso , fechaProceso
    private Notasdebito getNotaDebitoXCuentaValor(Integer codigoCajero, Date fechaProceso, String numeroCuenta, Long valor) throws Exception {
        Notasdebito regReintegros = null;
        try {
            Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
            regReintegros = session.getNotasDebitoXCuentaValor(codigoCajero, fechaProceso, numeroCuenta, valor, fechaHisto);
        } catch (EJBException ex) {
            if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            }
            if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            }
            if (ex.getLocalizedMessage().contains("NoResultException")) {
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
            }
        }

        return regReintegros;
    }

    public String GuardarStratusNotaDebito(Integer codigoCajero, BigDecimal valor, String cuenta, Integer tipoCuenta, String concepto) {

        String respuesta = "";
        String tipoNota = "Nota Debito ";
        String strExepcion = "";

        try {

            String usuario = objectContext.getUsuarioEnSesion().getUsuario();
            respuesta = reintegrosNotasProcesosSessionLocal.realizarNotaDebito(codigoCajero, valor, cuenta, tipoCuenta, usuario, concepto);

            //  respuesta="B0;4599  0525200016      $             0.00 Nota Credito Cuenta Ahorros Realizada con Exito";
            //norespuesta=reintegrosNotasProcesosSessionLocal.realizarNotaCredito(codigoCajero, new BigDecimal(0) , "099201003375",tipoCuenta);   
            tipoNota = tipoNota + TipoCuenta.getTipoCuenta(tipoCuenta).nombre;
            objectContext.getConfigApp().loggerApp.info("El usuario : " + usuario
                    + "  realizo una " + tipoNota + "  al cajero " + codigoCajero.toString()
                    + " por valor : " + valor.toString()
                    + " a la cuenta : " + cuenta
                    + " con respuesta:" + respuesta);
            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("B")) {
                        respuesta = respuesta + " " + tipoNota + " Realizada con Exito";

                    } else if (respuesta.substring(0, 1).equals("M")) {
                        respuesta = respuesta + " NO se pudo Realizar  la " + tipoNota;
                    } else {
                        if (respuesta.substring(0, 1).equals("F")) {
                            respuesta = respuesta + " Por favor verificar el  Estado de la " + tipoNota;
                        }
                    }
                } else {
                    respuesta = respuesta + " Por favor verificar el  Estado de la " + tipoNota;
                }
            } else {
                respuesta = respuesta + " Por favor verificar el  Estado de la " + tipoNota;
            }

            //respuesta=  JSon.getJSonRespuesta(0, respuesta);
        } catch (EJBException ex) {

            if (ex.getMessage() == null) {
                strExepcion = ex.getCause().getMessage();
            } else {
                strExepcion = ex.getMessage();
            }
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, strExepcion);
            objectContext.setError(strExepcion, CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);

        } catch (Exception ex) {
            objectContext.setError(ex.getMessage(), CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
        }

//        } catch (ServletException ex) {
//            objectContext.setError( ex.getMessage(),CodigoError.PARAMETRO_INVALIDO.getCodigo());
//            respuesta = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
//        }
        return respuesta;
    }

    private void actualizarJsonSelecionados(JSONArray myArrayList) {
        JSONObject itemJSon = null;
        Date fecha = null;
        Long valorajustado = null;
        String strValorAjustado = "";
        Long valor = null;
        String strValor = "";
        boolean estadoTransaccion = false;
        try {
            if (myArrayList != null) {
                for (int i = 0; i < myArrayList.length(); i++) {

                    itemJSon = new JSONObject(myArrayList.getString(i));
                    if (itemJSon.get("checkSeleccion").toString().equals("true")) {
                        strValorAjustado = itemJSon.get("valorAjustar").toString().replace(".", "").replace(",", "");
                        valorajustado = Cadena.aLong(strValorAjustado);
                        strValor = itemJSon.get("valor").toString().replace(".", "").replace(",", "");
                        valor = Cadena.aLong(strValor);
                        fecha = com.davivienda.utilidades.conversion.Cadena.aDate(itemJSon.get("fecha").toString(), FormatoFecha.FECHA_HORA);
                        estadoTransaccion = actualizarNotaDebito(Cadena.aInteger(itemJSon.get("codigoCajero").toString()), fecha, valorajustado, itemJSon.get("numeroCuenta").toString(), valor);
                        respuestaJSon = respuestaJSon + " para codigo cajero: " + itemJSon.get("codigoCajero").toString() + " ,Valor: " + strValorAjustado + " la Transacion fue ";
                        if (estadoTransaccion) {
                            respuestaJSon = respuestaJSon + " Exitosa; ";
                        } else {
                            respuestaJSon = respuestaJSon + " Errada;  ";

                        }
                    }

                }
            }
            respuestaJSon = JSon.getJSonRespuesta(0, respuestaJSon);
        } catch (org.json.JSONException ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_PARSEAR_REGISTRO.getCodigo(), "Error leyendo arrayJson Grid", ex);

        }
    }
}
