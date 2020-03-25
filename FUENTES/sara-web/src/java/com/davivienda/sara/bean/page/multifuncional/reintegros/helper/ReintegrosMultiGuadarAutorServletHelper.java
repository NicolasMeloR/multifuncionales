/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.multifuncional.reintegros.helper;

import com.davivienda.multifuncional.tablas.notasdebitomulti.session.NotasDebitoMultiSessionLocal;
import com.davivienda.multifuncional.tablas.procesos.session.ProcesoReintegrosMultiSessionLocal;
import com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.session.ReintegrosMultiEfectivoSessionLocal;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.constantes.RedCajero;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.procesos.autenticacion.webservice.session.AutenticacionLdapWSProcesosSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.conversion.JSon;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.EJBException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.davivienda.multifuncional.constantes.TipoCuentaMultifuncional;
import com.davivienda.multifuncional.ws.reintegrosmulti.session.ReintegrosNotasWsSessionLocal;
import com.davivienda.utilidades.Constantes;

/**
 *
 * @author p-cchapa
 */
public class ReintegrosMultiGuadarAutorServletHelper implements ReintegrosMultifuncionalHelperInterface {

    private ReintegrosNotasWsSessionLocal reintegrosNotasWsSessionLocal;
    private ReintegrosMultiEfectivoSessionLocal session = null;
    private ReintegrosMultifuncionalGeneralObjectContext objectContext = null;
    private NotasDebitoMultiSessionLocal notasDebitoMultiSessionLocal;
    private ProcesoReintegrosMultiSessionLocal procesoReintegrosMultiSessionLocal;
    private String respuestaJSon;
    private AutenticacionLdapWSProcesosSessionLocal autenticacionLdapWSProcesosSessionLocal;
    private ConfModulosAplicacionLocal confModulosAplicacionSession;

    public ReintegrosMultiGuadarAutorServletHelper(ReintegrosNotasWsSessionLocal reintegrosNotasWsSessionLocal, ReintegrosMultiEfectivoSessionLocal session, ProcesoReintegrosMultiSessionLocal procesoReintegrosSession, NotasDebitoMultiSessionLocal reintegrosMultifuncionalNotasProcesosSessionLocal, AutenticacionLdapWSProcesosSessionLocal autenticacionLdapWSProcesosSessionLocal, ConfModulosAplicacionLocal confModulosAplicacionSession, ReintegrosMultifuncionalGeneralObjectContext objectContext) {
        this.reintegrosNotasWsSessionLocal = reintegrosNotasWsSessionLocal;
        this.session = session;
        this.procesoReintegrosMultiSessionLocal = procesoReintegrosSession;
        this.notasDebitoMultiSessionLocal = reintegrosMultifuncionalNotasProcesosSessionLocal;
        this.autenticacionLdapWSProcesosSessionLocal = autenticacionLdapWSProcesosSessionLocal;
        this.confModulosAplicacionSession = confModulosAplicacionSession;
        this.objectContext = objectContext;

    }

    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Reintegrosmultiefectivo> items = null;
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

        if (items != null && this.respuestaJSon.length() <= 1) {
            respuesta = aJSon(items);  // paso los items a JSON
        } else {
            respuesta = this.respuestaJSon;
        }

        return respuesta;
    }

    private String aJSon(Collection<Reintegrosmultiefectivo> items) {
        String cadenaJSon = "";

        try {
            Integer idRegistro = 0;

            JSONObject resp = new JSONObject();
            JSONArray respItems = new JSONArray();
            for (Reintegrosmultiefectivo item : items) {
                JSONObject itemJSon = new JSONObject();
                itemJSon.put("idRegistro", ++idRegistro);
                itemJSon.put("codigoCajero", item.getReintegrosmultiefectivoPK().getHCodigocajero().toString());
                itemJSon.put("numeroTransaccion", item.getReintegrosmultiefectivoPK().getHTalon().toString());
                itemJSon.put("numeroCuenta", item.getHNumerocuenta());
                itemJSon.put("fecha", com.davivienda.utilidades.conversion.Fecha.aCadena(item.getReintegrosmultiefectivoPK().getHFechasistema(), FormatoFecha.FECHA_HORA));
                itemJSon.put("valor", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getHValor()));
                itemJSon.put("valorAjustar", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getValorajustado()));
                itemJSon.put("statusTransaccion", item.getTEstado());
                itemJSon.put("valorAjustado", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getValorajustado()));
                itemJSon.put("redEnruta", RedCajero.getRedCajero(Cadena.aInteger(item.getHNumerocuenta().substring(0, 4))).toString());
                itemJSon.put("usuarioRevisa", item.getUsuariosrevisa());
                itemJSon.put("checkSeleccion", true);
            }
            resp.put("identifier", "idRegistro");
            resp.put("label", "codigoCajero");
            resp.put("items", respItems);
            cadenaJSon = resp.toString();

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            objectContext.getConfigApp().loggerApp.severe("No se puede pasar a JSON \n " + ex.getMessage());
            cadenaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_PARSEAR_REGISTRO.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
        }
        return cadenaJSon;
    }

    private boolean actualizarReintegro(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, Long valorajustado, String numeroCuenta, Long valor) {
        String usuario = "";
        String mensajeError = "";
        usuario = objectContext.getIdUsuarioEnSesion();
        Reintegrosmultiefectivo regReintegrosMulti = null;
        boolean estadoTransaccion = false;
        try {
            regReintegrosMulti = getReintegroXCuentaValor(codigoCajero, fechaProceso, numeroTransaccion, numeroCuenta, valor);
            if (regReintegrosMulti != null) {
                mensajeError = GuardarStratusNotaCredito(codigoCajero, new BigDecimal(valorajustado), regReintegrosMulti.getHNumerocuenta(), regReintegrosMulti.getTipocuentareintegro(), numeroTransaccion.toString());
                if (mensajeError.substring(0, 1).equals("B")) {
                    regReintegrosMulti.setEstadoreintegro(EstadoReintegro.FINALIZADOEXITOSO.codigo);
                    regReintegrosMulti.setFechareintegro(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
                    estadoTransaccion = true;
                } else {
                    //OJO VALIDAR ESTE ESTADO
                    regReintegrosMulti.setEstadoreintegro(EstadoReintegro.ERRORSTRATUS.codigo);
                }
                regReintegrosMulti.setErrorreintegro(mensajeError);
                //OJO DESCOMENTAREAR EN PRUEBAS 
                regReintegrosMulti.setUsuarioautoriza(usuario);
                //guardar registro
                procesoReintegrosMultiSessionLocal.actualizarReintegro(regReintegrosMulti);
            }
        } catch (Exception ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
        }
        return estadoTransaccion;
    }

    //obtengo el reintegro pro la llave formada por  codigoCajero , fechaProceso , fechaProceso
    private Reintegrosmultiefectivo getReintegroXCuentaValor(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, String numeroCuenta, Long valor) throws Exception {
        Reintegrosmultiefectivo regReintegrosMulti = null;
        try {
            Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
            regReintegrosMulti = session.getReintegroXCuentaValor(codigoCajero, fechaProceso, numeroTransaccion, numeroCuenta, valor, fechaHisto);
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

        return regReintegrosMulti;
    }

    public String GuardarStratusNotaCredito(Integer codigoCajero, BigDecimal valor, String cuenta, Integer tipoCuenta, String talon) {

        String respuesta = "";
        String tipoNota = "Nota Credito ";
        String strExepcion = "";

        try {
            String usuario = objectContext.getUsuarioEnSesion().getUsuario();
            respuesta = reintegrosNotasWsSessionLocal.realizarNotaCredito(codigoCajero, valor, cuenta, tipoCuenta, usuario, talon);

            tipoNota = tipoNota + TipoCuentaMultifuncional.getTipoCuentaMultifuncional(tipoCuenta).nombre;
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

    private void actualizarJsonSelecionados(JSONArray myArrayList) {
        JSONObject itemJSon = null;
        Date fecha = null;
        Long valorajustado = null;
        Long valor = null;
        String strValor = "";
        String strValorAjustado = "";
        respuestaJSon = "el estado de las Notas Credito es:";
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
                        estadoTransaccion = actualizarReintegro(Cadena.aInteger(itemJSon.get("codigoCajero").toString()), fecha, Cadena.aInteger(itemJSon.get("numeroTransaccion").toString()), valorajustado, itemJSon.get("numeroCuenta").toString(), valor);
                        respuestaJSon = respuestaJSon + " para codigo cajero: " + itemJSon.get("codigoCajero").toString() + " ,Talon: " + itemJSon.get("numeroTransaccion").toString() + " ,Valor: " + strValorAjustado + " la Transacion fue ";
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

    public String generarDiarioElectronicoXML() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
