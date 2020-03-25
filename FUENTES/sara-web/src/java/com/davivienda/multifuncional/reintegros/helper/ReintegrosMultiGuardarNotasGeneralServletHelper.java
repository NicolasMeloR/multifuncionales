/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.multifuncional.reintegros.helper;

import com.davivienda.multifuncional.reintegros.general.ReintegrosMultifuncionalGeneralObjectContext;
import com.davivienda.multifuncional.reintegros.general.ReintegrosMultifuncionalHelperInterface;
import com.davivienda.multifuncional.tablas.notasdebitomulti.session.NotasDebitoMultiSessionLocal;
import com.davivienda.multifuncional.tablas.procesos.session.ProcesoReintegrosMultiSessionLocal;
import com.davivienda.multifuncional.ws.reintegrosmulti.session.ReintegrosNotasWsSessionLocal;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.constantes.TipoCuenta;
import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.procesos.autenticacion.webservice.session.AutenticacionLdapWSProcesosSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.conversion.JSon;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.EJBException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author P-CCHAPA
 */
public class ReintegrosMultiGuardarNotasGeneralServletHelper implements ReintegrosMultifuncionalHelperInterface {

    private NotasDebitoMultiSessionLocal session;
    private ReintegrosMultifuncionalGeneralObjectContext objectContext;
    private ProcesoReintegrosMultiSessionLocal procesoReintegrosMultiSessionLocal;
    private ConfModulosAplicacionLocal confModulosAplicacionSession;
    private AutenticacionLdapWSProcesosSessionLocal autenticacionLdapWSProcesosSessionLocal;
    private ReintegrosNotasWsSessionLocal reintegrosNotasWsSessionLocal;
    private boolean esNotaCredito;
    private String respuestaJSon;

    public ReintegrosMultiGuardarNotasGeneralServletHelper(NotasDebitoMultiSessionLocal session, ReintegrosMultifuncionalGeneralObjectContext objectContext, ProcesoReintegrosMultiSessionLocal procesoReintegrosSessionLocal, AutenticacionLdapWSProcesosSessionLocal autenticacionLdapWSProcesosSessionLocal, ConfModulosAplicacionLocal confModulosAplicacionSession, ReintegrosNotasWsSessionLocal reintegrosNotasWsSessionLocal, boolean esNotaCredito) {

        this.session = session;
        this.objectContext = objectContext;
        this.esNotaCredito = esNotaCredito;
        this.procesoReintegrosMultiSessionLocal = procesoReintegrosSessionLocal;
        this.confModulosAplicacionSession = confModulosAplicacionSession;
        this.autenticacionLdapWSProcesosSessionLocal = autenticacionLdapWSProcesosSessionLocal;
        this.reintegrosNotasWsSessionLocal = reintegrosNotasWsSessionLocal;

    }

    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        try {

            try {
                // Consulta los registros según los parámetros tomados del request
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

    private boolean ActualizarNotaDebito(Integer codigoCajero, Date fechaProceso, Long valorAjustado, String numeroCuenta, Long valor) {

        String usuario = "";
        String mensajeError = "";
        usuario = objectContext.getIdUsuarioEnSesion();
        Notasdebitomultifuncional regNotasdebitoMulti = null;
        boolean estadoTransaccion = false;
        try {
            regNotasdebitoMulti = getNotaDebitoXCuentaValor(codigoCajero, fechaProceso, numeroCuenta, valor);
            if (regNotasdebitoMulti != null) {
                mensajeError = GuardarStratusNotaDebito(codigoCajero, new BigDecimal(valorAjustado), regNotasdebitoMulti.getNumerocuenta(), regNotasdebitoMulti.getTipocuenta());
                if (mensajeError.substring(0, 1).equals("B")) {
                    regNotasdebitoMulti.setEstado(EstadoReintegro.FINALIZADOEXITOSO.codigo);
                    regNotasdebitoMulti.setFechaaplica(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
                    estadoTransaccion = true;
                } else {
                    regNotasdebitoMulti.setEstado(EstadoReintegro.ERRORSTRATUS.codigo);
                }
                regNotasdebitoMulti.setError(mensajeError);
                regNotasdebitoMulti.setUsuarioautoriza(usuario);
                procesoReintegrosMultiSessionLocal.actualizarNotaDebito(regNotasdebitoMulti);
            }
        } catch (Exception ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
        }
        return estadoTransaccion;
    }

    private Notasdebitomultifuncional getNotaDebitoXCuentaValor(Integer codigoCajero, Date fechaProceso, String numeroCuenta, Long valor) throws Exception {
        Notasdebitomultifuncional regReintegros = null;
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

    public String GuardarStratusNotaDebito(Integer codigoCajero, BigDecimal valor, String cuenta, Integer tipoCuenta) {

        String respuesta = "";
        String tipoNota = "Nota Debito ";
        String strExepcion = "";
        try {
            String usuario = objectContext.getUsuarioEnSesion().getUsuario();
            respuesta = reintegrosNotasWsSessionLocal.realizarNotaDebito(codigoCajero, valor, cuenta, tipoCuenta, usuario);
            tipoNota = tipoNota + TipoCuenta.getTipoCuenta(tipoCuenta).nombre;
            objectContext.getConfigApp().loggerApp.info("El usuario : " + usuario + "  realizo una " + tipoNota + "  al cajero " + codigoCajero.toString() + " por valor : " + valor.toString() + " a la cuenta : " + cuenta + " con respuesta:" + respuesta);
            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("B")) {
                        respuesta = respuesta + " " + tipoNota + " Realizada con Exito";
                    } else if (respuesta.substring(0, 1).equals("M")) {
                        respuesta = respuesta + " NO se pudo Realizar  la " + tipoNota;
                    } else if (respuesta.substring(0, 1).equals("F")) {
                        respuesta = respuesta + " Por favor verificar el  Estado de la " + tipoNota;
                    }
                } else {
                    respuesta = respuesta + " Por favor verificar el  Estado de la " + tipoNota;
                }
            } else {
                respuesta = respuesta + " Por favor verificar el  Estado de la " + tipoNota;
            }
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
                        estadoTransaccion = ActualizarNotaDebito(Cadena.aInteger(itemJSon.get("codigoCajero").toString()), fecha, valorajustado, itemJSon.get("numeroCuenta").toString(), valor);
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

    private boolean getAutenticacion() {
        boolean blnAutorizado = false;
        String respuesta = "";
        String grupoSaraNotaCredito = "";
        String[] gruposUsuario;
        try {
            //VALIDAR QUE EL USUARIO EN SESSION SEA EL MISMO INGRESADO EN LA AUTENTICACION
            grupoSaraNotaCredito = getParametro("SARA.GRUPO_NOTA_CREDITO", "");
            respuesta = autenticacionLdapWSProcesosSessionLocal.autenticarLdap(objectContext.getUsuarioLdap(), objectContext.getClaveLdap());
            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("B")) {
                        gruposUsuario = respuesta.split(";");
                        for (int i = 0; i < gruposUsuario.length; i++) {
                            if (grupoSaraNotaCredito.equals(gruposUsuario[i])) {
                                blnAutorizado = true;
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

    public String generarDiarioElectronicoXML() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
