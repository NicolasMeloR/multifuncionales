package com.davivienda.sara.reintegros.general.helper;

import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.ReintegrosGeneralObjectContext;
import com.davivienda.sara.tablas.reintegros.session.ReintegrosSessionLocal;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.procesos.reintegros.session.ProcesoReintegrosSessionLocal;
import com.davivienda.sara.procesos.reintegros.notas.session.ReintegrosNotasProcesosSessionLocal;
import com.davivienda.sara.constantes.TipoCuenta;
import com.davivienda.sara.procesos.autenticacion.webservice.session.AutenticacionLdapWSProcesosSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.sara.constantes.RedCajero;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.utilidades.conversion.JSon;
import java.math.BigDecimal;
import java.util.Collection;
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
public class ReintegrosGuardarAutorizacionServletHelper implements ReintegrosHelperInterface {

    private ReintegrosSessionLocal session = null;
    private ReintegrosGeneralObjectContext objectContext = null;
    private ReintegrosNotasProcesosSessionLocal reintegrosNotasProcesosSessionLocal;
    private ProcesoReintegrosSessionLocal procesoReintegrosSession;
    private String respuestaJSon;
    private AutenticacionLdapWSProcesosSessionLocal autenticacionLdapWSProcesosSessionLocal;
    private ConfModulosAplicacionLocal confModulosAplicacionSession;

    public ReintegrosGuardarAutorizacionServletHelper(ReintegrosSessionLocal session, ProcesoReintegrosSessionLocal procesoReintegrosSession, ReintegrosNotasProcesosSessionLocal reintegrosNotasProcesosSessionLocal, AutenticacionLdapWSProcesosSessionLocal autenticacionLdapWSProcesosSessionLocal, ConfModulosAplicacionLocal confModulosAplicacionSession, ReintegrosGeneralObjectContext objectContext) {
        this.session = session;
        this.procesoReintegrosSession = procesoReintegrosSession;
        this.reintegrosNotasProcesosSessionLocal = reintegrosNotasProcesosSessionLocal;
        this.autenticacionLdapWSProcesosSessionLocal = autenticacionLdapWSProcesosSessionLocal;
        this.confModulosAplicacionSession = confModulosAplicacionSession;
        this.objectContext = objectContext;

    }

    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Reintegros> items = null;
        try {
            try {

                //DESCOMENTAREAR SOLO PARA PRUEBAS
                if (getAutenticacion()) {
                    actualizarJsonSelecionados(objectContext.getAtributoJsonArray("events"));
                } //actualizarJsonSelecionados(objectContext.getAtributoJsonArray("events"));
                else {
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_EN_AUTENTICACION.getCodigo(), "Datos de Autenticacion Errados");

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

    //obtengo el reintegro pro la llave formada por  codigoCajero , fechaProceso , fechaProceso
    private Reintegros getReintegroXLlave(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion) throws Exception {
        Reintegros regReintegros = null;
        try {
            Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
            regReintegros = session.getReintegroXLlave(codigoCajero, fechaProceso, numeroTransaccion, fechaHisto);
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

    private String aJSon(Collection<Reintegros> items) {

        String cadenaJSon = "";
        try {
            Integer idRegistro = 0;
            JSONObject resp = new JSONObject();
            JSONArray respItems = new JSONArray();
            for (Reintegros item : items) {
                JSONObject itemJSon = new JSONObject();
                itemJSon.put("idRegistro", ++idRegistro);
                itemJSon.put("codigoCajero", item.getReintegrosPK().getHCodigocajero().toString());
                itemJSon.put("codigoOcca", item.getHCodigoocca().toString());
                itemJSon.put("numeroTransaccion", item.getReintegrosPK().getHTalon().toString());
                itemJSon.put("numeroCuenta", item.getHNumerocuenta());
                itemJSon.put("numeroTarjeta", item.getTTarjeta());
                itemJSon.put("fecha", com.davivienda.utilidades.conversion.Fecha.aCadena(item.getReintegrosPK().getHFechasistema(), FormatoFecha.FECHA_HORA));
                itemJSon.put("valor", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getHValor()));
                itemJSon.put("valorAjustar", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getValorajustado()));
                itemJSon.put("statusTransaccion", item.getTCodigoterminaciontransaccion());
                itemJSon.put("valorAjustado", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getTValorentregado()));
                itemJSon.put("redEnruta", RedCajero.getRedCajero(Cadena.aInteger(item.getHNumerocuenta().substring(0, 4))).toString());
                itemJSon.put("usuarioRevisa", item.getUsuariorevisa());
                itemJSon.put("checkSeleccion", true);
            }
            resp.put("identifier", "idRegistro");
            resp.put("label", "codigoCajero");
            resp.put("items", respItems);
            cadenaJSon = resp.toString();

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.severe("No se puede pasar a JSON \n " + ex.getMessage());
            cadenaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_PARSEAR_REGISTRO.getCodigo(), ex.getMessage());
        }
        return cadenaJSon;
    }

    private boolean actualizarReintegro(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, Long valorajustado, String numeroCuenta, Long valor, int estadoReintegro, String concepto) {
        String usuario = "";
        String mensajeError = "";
        usuario = objectContext.getIdUsuarioEnSesion();
        Reintegros regReintegros = null;
        boolean estadoTransaccion = false;
        try {
            regReintegros = findByPrimayKey(codigoCajero, fechaProceso, numeroTransaccion);
            if (regReintegros != null) {
                if (estadoReintegro == EstadoReintegro.REINTEGROCANCELADO.codigo) {
                    regReintegros.setEstadoreintegro(estadoReintegro);
                    procesoReintegrosSession.actualizar(regReintegros);
                    estadoTransaccion = true;
                } else {
                    mensajeError = GuardarStratusNotaCredito(codigoCajero, new BigDecimal(valorajustado), regReintegros.getHNumerocuenta(), regReintegros.getTipoCuentaReintegro(), numeroTransaccion.toString(), concepto);
                    if (mensajeError.substring(0, 1).equals("B")) {
                        regReintegros.setEstadoreintegro(EstadoReintegro.FINALIZADOEXITOSO.codigo);
                        regReintegros.setFechareintegro(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
                        estadoTransaccion = true;
                    } else {
                        regReintegros.setEstadoreintegro(EstadoReintegro.ERRORSTRATUS.codigo);
                    }
                    regReintegros.setErrorreintegro(mensajeError);
                    regReintegros.setUsuarioautoriza(usuario);
                    procesoReintegrosSession.actualizar(regReintegros);
                }
            }
        } catch (Exception ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
        }
        return estadoTransaccion;
    }

    //obtengo el reintegro pro la llave formada por  codigoCajero , fechaProceso , fechaProceso
    private Reintegros findByPrimayKey(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion) throws Exception {
        Reintegros regReintegros = null;
        try {
            Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
            regReintegros = session.findByPrimayKey(codigoCajero, fechaProceso, numeroTransaccion, fechaHisto);
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

    public String GuardarStratusNotaCredito(Integer codigoCajero, BigDecimal valor, String cuenta, Integer tipoCuenta, String talon, String concepto) {

        String respuesta = "";
        String tipoNota = "Nota Credito ";
        String strExepcion = "";
        try {
            String usuario = objectContext.getUsuarioEnSesion().getUsuario();
            respuesta = reintegrosNotasProcesosSessionLocal.realizarNotaCredito(codigoCajero, valor, cuenta, tipoCuenta, usuario, talon, concepto);
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

    //obtengo el reintegro pro la llave formada por  codigoCajero , fechaProceso , fechaProceso
    private Reintegros getReintegroXCuentaValor(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, String numeroCuenta, Long valor) throws Exception {
        Reintegros regReintegros = null;
        try {
            Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
            regReintegros = session.getReintegroXCuentaValor(codigoCajero, fechaProceso, numeroTransaccion, numeroCuenta, valor, fechaHisto);
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
                    String concepto, comision = "";
                    if (itemJSon.get("checkSeleccion").toString().equals("true")) {
                        strValorAjustado = itemJSon.get("valorAjustar").toString().replace(".", "").replace(",", "");
                        valorajustado = Cadena.aLong(strValorAjustado);
                        strValor = itemJSon.get("valor").toString().replace(".", "").replace(",", "");
                        int estadoReintegro = (int) itemJSon.get("estadoReintegro");
                        concepto = itemJSon.getString("concepto");
                        if (concepto.equals("479") || concepto.equals("104")) {//Reintegro por valor de comision
                            comision = itemJSon.getString("comision").toString().replace(".", "").replace(",", "");
                            strValor = comision;
                            valorajustado = Cadena.aLong(comision);
                        }
                        valor = Cadena.aLong(strValor);
                        fecha = com.davivienda.utilidades.conversion.Cadena.aDate(itemJSon.get("fecha").toString(), FormatoFecha.FECHA_HORA);
                        estadoTransaccion = actualizarReintegro(Cadena.aInteger(itemJSon.get("codigoCajero").toString()), fecha, Cadena.aInteger(itemJSon.get("numeroTransaccion").toString()), valorajustado, itemJSon.get("numeroCuenta").toString(), valor, estadoReintegro, concepto);
                        if (estadoReintegro == EstadoReintegro.REINTEGROCANCELADO.codigo) {
                            respuestaJSon = respuestaJSon + " para codigo cajero: " + itemJSon.get("codigoCajero").toString() + " ,Talon: " + itemJSon.get("numeroTransaccion").toString() + " ,Valor: " + strValorAjustado + " la solicitud de rechazo del registro fué ";
                        } else {
                            respuestaJSon = respuestaJSon + " para codigo cajero: " + itemJSon.get("codigoCajero").toString() + " ,Talon: " + itemJSon.get("numeroTransaccion").toString() + " ,Valor: " + strValorAjustado + " la Transacion fué ";
                        }
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
