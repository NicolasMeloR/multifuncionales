package com.davivienda.sara.reintegros.general.helper;

import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.ReintegrosGeneralObjectContext;
import com.davivienda.sara.tablas.reintegros.session.ReintegrosSessionLocal;
import com.davivienda.utilidades.conversion.JSon;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.EJBException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.utilidades.Constantes;
import java.util.ArrayList;

/**
 * DiarioElectronicoGeneralTransaccionServletHelper - 27/08/2008 Descripción :
 * Helper para el manejo de los requerimientos de Transaccion Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ReintegrosIniciarCreadosServletHelper implements ReintegrosHelperInterface {

    private ReintegrosSessionLocal session = null;
    private ReintegrosGeneralObjectContext objectContext = null;
    private String respuestaJSon;
    Integer tipoReintegro;
    Integer actualiza;

    public ReintegrosIniciarCreadosServletHelper(ReintegrosSessionLocal session, ReintegrosGeneralObjectContext objectContext, Integer tipoReintegro, Integer actualiza) {
        this.session = session;
        this.objectContext = objectContext;
        this.tipoReintegro = tipoReintegro;
        this.actualiza = actualiza;
    }

    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Object> items = null;
        try {

            Date fechaInicial = null;
            Date fechaFinal = null;
            Integer codigoCajero = 0;

            if (actualiza == 0) {
                try {
                    fechaInicial = objectContext.getAtributoFechaHoraInicial().getTime();
                } catch (IllegalArgumentException ex) {
                    fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
                }

                try {
                    codigoCajero = objectContext.getCodigoCajero();
                } catch (IllegalArgumentException ex) {
                    codigoCajero = 0;
                }
                if (codigoCajero == null) {
                    codigoCajero = 0;
                }

                try {
                    fechaFinal = objectContext.getAtributoFechaHoraFinal().getTime();
                    //fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaFinal);
                } catch (IllegalArgumentException ex) {
                    fechaFinal = fechaInicial;
                    fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
                }

            }

            try {
                Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
                // Consulta los registros según los parámetros tomados del request
                if (actualiza == 0) {
                    //OJO DEBEN SER SOLO LOS REINTEGROS en estao 9
                    items = session.getReintegrosXCajero(fechaInicial, fechaFinal,fechaHisto);
                } else {
                    try {
                        fechaInicial = objectContext.getFechaCalendar().getTime();
                    } catch (IllegalArgumentException ex) {
                        fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
                    }

                    actualizarJsonSelecionados(objectContext.getAtributoJsonArray("events"), fechaInicial);
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

    private String aJSon(Collection<Object> items) {
        String cadenaJSon = "";

        try {
            Integer idRegistro = 0;
            //OJO SE USA SI ES AUTORIZA O NO para filtrar los registros correspondientes
            if (tipoReintegro.equals(EstadoReintegro.REVISADOAUTORIZADO.codigo)) {

            }
            ArrayList<Object> intemsArray = new ArrayList<Object>(items);
            JSONObject resp = new JSONObject();
            JSONArray respItems = new JSONArray();

            for (Object item : intemsArray) {

                JSONObject itemJSon = new JSONObject();
                itemJSon.put("idRegistro", ++idRegistro);
                itemJSon.put("codigoCajero", item);
                itemJSon.put("checkSeleccion", false);
                respItems.put(itemJSon);

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

    private void actualizarJsonSelecionados(JSONArray myArrayList, Date fechaInicial) {
        JSONObject itemJSon = null;
        Date fecha = null;

        try {
            if (actualiza == 1) {
                if (myArrayList != null) {
                    for (int i = 0; i < myArrayList.length(); i++) {

                        itemJSon = new JSONObject(myArrayList.getString(i));
                        if (itemJSon.get("checkSeleccion").toString().equals("true")) {
                            actualizarReintegro(fechaInicial, Cadena.aInteger(itemJSon.get("codigoCajero").toString()));

                        }

                    }
                }
            } //actualiza todos los del dia
            else {
                actualizarReintegro(fechaInicial, 0);
            }
        } catch (org.json.JSONException ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_PARSEAR_REGISTRO.getCodigo(), "Error leyendo arrayJson Grid", ex);

        }
    }

    private String actualizarReintegro(Date fechaInicial, Integer codigoCajero) {
        String usuario = "";
        String mensaje = "";
        mensaje = respuestaJSon;
        usuario = objectContext.getIdUsuarioEnSesion();
        Reintegros regReintegros = null;
        try {
            //OJO REVISAR
            // regReintegros=getReintegroXCuentaValor(codigoCajero,fechaProceso,numeroTransaccion,numeroCuenta,valor);

//            if(regReintegros!=null)
//            {
            Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
            session.actualizarEstadoReintegros(fechaInicial, codigoCajero,fechaHisto);
            // regReintegros.setEstadoreintegro(EstadoReintegro.REINTEGROTIPOCREADO.codigo);

            ///se actualizan los reintegros en estado 9  con la fecha del dia de los cajeros seleccionados
            //OJO REVISAR
            //procesoReintegrosSession.actualizar(regReintegros);
            respuestaJSon = "Se guardaron los registros con Exito";
            respuestaJSon = JSon.getJSonRespuesta(0, respuestaJSon);

//            }
        } catch (Exception ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
        }

        return mensaje;
    }

    public String generarDiarioelectronicoXML() {

        throw new UnsupportedOperationException("Not supported yet.");
    }

}
