package com.davivienda.sara.reintegros.general.helper;

import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.ReintegrosGeneralObjectContext;
import com.davivienda.sara.tablas.reintegros.session.ReintegrosSessionLocal;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.constantes.TipoCuentaReintegro;

import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.sara.constantes.RedCajero;
import com.davivienda.sara.procesos.reintegros.session.ProcesoReintegrosSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.Cadena;

import com.davivienda.utilidades.conversion.JSon;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.EJBException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * ReintegrosRevisarServletHelper - 27/08/2008 Descripción : Helper para el
 * manejo de los requerimientos de Reintegros Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ReintegrosGuadarRevisionServletHelper implements ReintegrosHelperInterface {

    private ReintegrosSessionLocal session = null;
    private ProcesoReintegrosSessionLocal procesoReintegrosSession;
    private ReintegrosGeneralObjectContext objectContext = null;
    private String respuestaJSon;
    boolean esCreado = false;

    public ReintegrosGuadarRevisionServletHelper(ReintegrosSessionLocal session, ProcesoReintegrosSessionLocal procesoReintegrosSession, ReintegrosGeneralObjectContext objectContext, boolean esCreado) {
        this.session = session;
        this.procesoReintegrosSession = procesoReintegrosSession;
        this.objectContext = objectContext;
        this.esCreado = esCreado;
    }

    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Reintegros> items = null;
        try {
            try {
                actualizarJsonSelecionados(objectContext.getAtributoJsonArray("events"));
            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {

                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_SIN_DATA);
                }
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            }

        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
        }

        if (items != null && this.respuestaJSon.length() <= 1) {
            respuesta = aJSon(items);  // paso los items a JSON
        } else {
            respuesta = this.respuestaJSon;
        }

        return respuesta;
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
                itemJSon.put("usuarioRevisa", item.getUsuariorevisa());
                itemJSon.put("redEnruta", RedCajero.getRedCajero(Cadena.aInteger(item.getHNumerocuenta().substring(0, 4))).toString());
                itemJSon.put("checkSeleccion", true);
                //se revisa si es una cuenta davivienda de ahorros o corriente
                if ((item.getTipoCuentaReintegro().equals(TipoCuentaReintegro.CuentaAhorros.codigo)) || (item.getTipoCuentaReintegro().equals(TipoCuentaReintegro.CuentaCorriente.codigo)) && item.getEstadoreintegro().equals(EstadoReintegro.INICIADO.codigo)) {
                    respItems.put(itemJSon);
                    actualizarReintegro(item.getReintegrosPK().getHCodigocajero(), item.getReintegrosPK().getHFechasistema(), item.getReintegrosPK().getHTalon(), item.getValorajustado(), item.getHNumerocuenta(), item.gethValor(), item.getEstadoreintegro());
                }
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

    private String actualizarReintegro(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, Long valorajustado, String numeroCuenta, Long valor, int estadoReintegro) {
        String usuario = "";
        String mensaje = "";
        mensaje = respuestaJSon;
        usuario = objectContext.getIdUsuarioEnSesion();
        Reintegros regReintegros = null;
        try {
            regReintegros = findByPrimayKey(codigoCajero, fechaProceso, numeroTransaccion);

            if (regReintegros != null) {
                if (esCreado) {
                    regReintegros.setEstadoreintegro(EstadoReintegro.INICIADO.codigo);
                } else {
                    regReintegros.setEstadoreintegro(estadoReintegro);
                }
                regReintegros.setUsuariorevisa(regReintegros.getEstadoreintegro() == EstadoReintegro.REVISADOAUTORIZADO.codigo ? usuario : "");
                regReintegros.setUsuarioautoriza(regReintegros.getEstadoreintegro() == EstadoReintegro.FINALIZADOEXITOSO.codigo ? usuario : "");
                regReintegros.setValorajustado(valorajustado);
                regReintegros.sethNumerocuenta(numeroCuenta);
                regReintegros.sethValor(valor);
                procesoReintegrosSession.actualizar(regReintegros);

                respuestaJSon = "Se guardaron los registros con Exito";
                respuestaJSon = JSon.getJSonRespuesta(0, respuestaJSon);
                objectContext.getConfigApp().loggerApp.info("El usuario : " + usuario + " reviso el reintegro del cajero : " + codigoCajero.toString() + " con  valor : " + valor.toString() + " y talon : " + numeroTransaccion.toString());
            }

        } catch (Exception ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
        }

        return mensaje;
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
        String strValorAjustado = "";
        Long valor = null;
        int estadoReintegro;
        String strValor = "";
        try {
            if (myArrayList != null) {
                for (int i = 0; i < myArrayList.length(); i++) {

                    itemJSon = new JSONObject(myArrayList.getString(i));
                    if (itemJSon.get("checkSeleccion").toString().equals("true")) {
                        strValorAjustado = itemJSon.get("valorAjustar").toString().replace(".", "").replace(",", "");
                        valorajustado = Cadena.aLong(strValorAjustado);
                        strValor = itemJSon.get("valor").toString().replace(".", "").replace(",", "");
                        valor = Cadena.aLong(strValor);
                        estadoReintegro = itemJSon.getInt("estadoReintegro");
                        fecha = com.davivienda.utilidades.conversion.Cadena.aDate(itemJSon.get("fecha").toString(), FormatoFecha.FECHA_HORA);
//                        fecha = new Date((Long) itemJSon.get("fechaSistema"));
                        actualizarReintegro(Cadena.aInteger(itemJSon.get("codigoCajero").toString()), fecha, Cadena.aInteger(itemJSon.get("numeroTransaccion").toString()), valorajustado, itemJSon.get("numeroCuenta").toString(), valor, estadoReintegro);

                    }

                }
            }
        } catch (org.json.JSONException ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_PARSEAR_REGISTRO.getCodigo(), "Error leyendo arrayJson Grid", ex);

        }
    }

}
