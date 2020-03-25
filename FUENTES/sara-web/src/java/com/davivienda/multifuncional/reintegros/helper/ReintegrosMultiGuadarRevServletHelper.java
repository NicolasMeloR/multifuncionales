/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.multifuncional.reintegros.helper;

import com.davivienda.multifuncional.reintegros.general.ReintegrosMultifuncionalGeneralObjectContext;
import com.davivienda.multifuncional.reintegros.general.ReintegrosMultifuncionalHelperInterface;
import com.davivienda.multifuncional.tablas.procesos.session.ProcesoReintegrosMultiSessionLocal;
import com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.session.ReintegrosMultiEfectivoSessionLocal;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.constantes.RedCajero;
import com.davivienda.multifuncional.reintegros.general.ReintegrosMultifuncionalHelperInterface;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.conversion.JSon;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.EJBException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author jediazs
 */
public class ReintegrosMultiGuadarRevServletHelper implements ReintegrosMultifuncionalHelperInterface {

    private ReintegrosMultiEfectivoSessionLocal session = null;
    private ProcesoReintegrosMultiSessionLocal procesoReintegrosMultiSessionLocal;
    private ReintegrosMultifuncionalGeneralObjectContext objectContext;
    private String respuestaJSon;
    boolean esCreado = false;

    public ReintegrosMultiGuadarRevServletHelper(ReintegrosMultiEfectivoSessionLocal session, ProcesoReintegrosMultiSessionLocal procesoReintegrosMultiSessionLocal, ReintegrosMultifuncionalGeneralObjectContext objectContext, boolean esCreado) {
        this.session = session;
        this.procesoReintegrosMultiSessionLocal = procesoReintegrosMultiSessionLocal;
        this.objectContext = objectContext;
        this.esCreado = esCreado;
    }

    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Reintegrosmultiefectivo> items = null;
        try {

            Date fechaInicial = null;
            Date fechaFinal = null;
            Integer codigoCajero = 0;
            try {
                actualizarJsonSelecionados(objectContext.getAtributoJsonArray("events"));
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
//        if (items == null || items.isEmpty()) {
//            respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
//        }

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
                itemJSon.put("codigoCajero", item.getTCodigocajero());
                itemJSon.put("numeroTransaccion", item.getReintegrosmultiefectivoPK().getHTalon().toString());
                itemJSon.put("numeroCuenta", item.getHNumerocuenta());
                //itemJSon.put("numeroTarjeta", item.getTTarjeta());
                itemJSon.put("fecha", com.davivienda.utilidades.conversion.Fecha.aCadena(item.getReintegrosmultiefectivoPK().getHFechasistema(), FormatoFecha.FECHA_HORA));
                itemJSon.put("valor", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getHValor()));
                itemJSon.put("valorAjustar", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getValorajustado()));
                itemJSon.put("statusTransaccion", item.getTEstado());
                itemJSon.put("valorAjustado", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getValorajustado()));
                itemJSon.put("usuarioRevisa", item.getUsuariosrevisa());
                itemJSon.put("redEnruta", RedCajero.getRedCajero(Cadena.aInteger(item.getHNumerocuenta().substring(0, 4))).toString());
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

    private void actualizarJsonSelecionados(JSONArray myArrayList) {
        objectContext.getConfigApp().loggerApp.log(Level.INFO, "ReintegrosMultiGuadarRevServletHelper - actualizarJsonSelecionados");
        JSONObject itemJSon = null;
        Date fecha = null;
        Long valorajustado = null;
        String strValorAjustado = "";
        Long valor = null;
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
                        fecha = com.davivienda.utilidades.conversion.Cadena.aDate(itemJSon.get("fecha").toString(), FormatoFecha.FECHA_HORA);
                        actualizarReintegro(Cadena.aInteger(itemJSon.get("codigoCajero").toString()), fecha, Cadena.aInteger(itemJSon.get("numeroTransaccion").toString()), valorajustado, itemJSon.get("numeroCuenta").toString(), valor);

                    }

                }
            }
        } catch (org.json.JSONException ex) {
             objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_PARSEAR_REGISTRO.getCodigo(), "Error leyendo arrayJson Grid", ex);

        }
    }

    private String actualizarReintegro(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, Long valorajustado, String numeroCuenta, Long valor) {
        objectContext.getConfigApp().loggerApp.log(Level.INFO, "ReintegrosMultiGuadarRevServletHelper - actualizarReintegro");
        String usuario = "";
        String mensaje = "";
        mensaje = respuestaJSon;
        usuario = objectContext.getIdUsuarioEnSesion();
        Reintegrosmultiefectivo regReintegrosMultifuncional = null;
        try {
            regReintegrosMultifuncional = getReintegroXCuentaValor(codigoCajero, fechaProceso, numeroTransaccion, numeroCuenta, valor);
            objectContext.getConfigApp().loggerApp.log(Level.INFO, "ReintegrosMultiGuadarRevServletHelper - actualizarReintegro regReintegrosMultifuncional: " + regReintegrosMultifuncional);
            if (regReintegrosMultifuncional != null) {
                if (esCreado) {
                    regReintegrosMultifuncional.setEstadoreintegro(EstadoReintegro.INICIADO.codigo);
                } else {
                    regReintegrosMultifuncional.setEstadoreintegro(EstadoReintegro.REVISADOAUTORIZADO.codigo);
                }
                regReintegrosMultifuncional.setValorajustado(valorajustado);
                //OJO DESCOMENTAREAR EN PRUEBAS
                regReintegrosMultifuncional.setUsuariosrevisa(usuario);
                //guardar registro
                procesoReintegrosMultiSessionLocal.actualizarReintegro(regReintegrosMultifuncional);
                respuestaJSon = "Se guardaron los registros con Exito";
                respuestaJSon = JSon.getJSonRespuesta(0, respuestaJSon);
                objectContext.getConfigApp().loggerApp.log(Level.INFO, respuestaJSon);

            }
        } catch (Exception ex) {
             objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
        }
        return mensaje;
    }

    private Reintegrosmultiefectivo getReintegroXCuentaValor(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, String numeroCuenta, Long valor) throws Exception {
        objectContext.getConfigApp().loggerApp.log(Level.INFO, "ReintegrosMultiGuadarRevServletHelper - getReintegroXCuentaValor");
        Reintegrosmultiefectivo regReintegrosMulti = null;
        try {
            Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
            regReintegrosMulti = session.getReintegroXCuentaValor(codigoCajero, fechaProceso, numeroTransaccion, numeroCuenta, valor, fechaHisto);
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
        }

        return regReintegrosMulti;
    }

    public String generarDiarioElectronicoXML() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
