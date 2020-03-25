package com.davivienda.multifuncional.reintegros.helper;

import com.davivienda.multifuncional.reintegros.general.ReintegrosMultifuncionalGeneralObjectContext;
import com.davivienda.multifuncional.reintegros.general.ReintegrosMultifuncionalHelperInterface;
import com.davivienda.multifuncional.tablas.notasdebitomulti.session.NotasDebitoMultiSessionLocal;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.ReintegrosGeneralObjectContext;

import com.davivienda.utilidades.conversion.JSon;
import java.util.Collection;
import java.util.Date;

import java.util.logging.Level;
import javax.ejb.EJBException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.sara.constantes.RedCajero;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.constantes.TipoCuentaReintegro;
import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import com.davivienda.utilidades.Constantes;

/**
 * DiarioElectronicoGeneralTransaccionServletHelper - 27/08/2008 Descripción :
 * Helper para el manejo de los requerimientos de Transaccion Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ReintegrosConsultaNotasDbServletHelper implements ReintegrosMultifuncionalHelperInterface {

    private NotasDebitoMultiSessionLocal session = null;
    private ReintegrosMultifuncionalGeneralObjectContext objectContext = null;
    private String respuestaJSon;

    public ReintegrosConsultaNotasDbServletHelper(NotasDebitoMultiSessionLocal session, ReintegrosMultifuncionalGeneralObjectContext objectContext) {
        this.session = session;
        this.objectContext = objectContext;

    }

    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Notasdebitomultifuncional> items = null;
        try {

            Date fechaInicial = null;
            Date fechaFinal = null;
            Integer codigoCajero = 0;
            JSONArray myArrayGrid = null;

//            try {
//           myArrayGrid=objectContext.getAtributoJsonArray("events");
//           JSONObject   itemJSon = new JSONObject(myArrayGrid.get(1).toString());
//           itemJSon.get("fecha");
//           } catch (org.json.JSONException ex) {
//             
//                      
//                respuestaJSon = "";
//            }
            try {
                codigoCajero = objectContext.getCodigoCajero();
            } catch (IllegalArgumentException ex) {
                codigoCajero = 0;
            }
            if (codigoCajero == null) {
                codigoCajero = 0;
            }

            try {
                fechaInicial = objectContext.getAtributoFechaHoraInicial().getTime();
            } catch (IllegalArgumentException ex) {
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
            }
            try {
                fechaFinal = objectContext.getAtributoFechaHoraFinal().getTime();
            } catch (IllegalArgumentException ex) {
                fechaFinal = fechaInicial;
            }
            //fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);

            try {
                // Consulta los registros según los parámetros tomados del request
                Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
                items = session.getNotasDebito(fechaInicial, fechaFinal, codigoCajero, fechaHisto);
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

    private String aJSon(Collection<Notasdebitomultifuncional> items) {
        String cadenaJSon = "";

        try {
            Integer idRegistro = 0;

            JSONObject resp = new JSONObject();
            JSONArray respItems = new JSONArray();
            for (Notasdebitomultifuncional item : items) {

                JSONObject itemJSon = new JSONObject();
                itemJSon.put("idRegistro", ++idRegistro);
                itemJSon.put("codigoCajero", item.getNotasdebitomultifuncionalPK().getCodigocajero().toString());
                itemJSon.put("codigoOficina", item.getCodigooficina().toString());
                itemJSon.put("numeroCuenta", item.getNumerocuenta());
                itemJSon.put("fecha", com.davivienda.utilidades.conversion.Fecha.aCadena(item.getNotasdebitomultifuncionalPK().getFecha(), FormatoFecha.FECHA_HORA));
                itemJSon.put("valor", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getValor()));
                itemJSon.put("valorAjustar", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getValorajustado()));
                itemJSon.put("usuarioCrea", item.getUsuariocrea());
                itemJSon.put("checkSeleccion", true);
                //se revisa si es una cuenta davivienda de ahorros o corriente 
                //if(((item.getTipoCuentaReintegro().equals(TipoCuentaReintegro.CuentaAhorros.codigo)) || (item.getTipoCuentaReintegro().equals(TipoCuentaReintegro.CuentaCorriente.codigo))) && item.getEstadoreintegro().equals(indAutoriza) && item.getReintegrosPK().getHTalon()==2189 )

                if (item.getEstado().equals(EstadoReintegro.INICIADO.codigo)) {

                    respItems.put(itemJSon);
                }

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

    public String generarDiarioElectronicoXML() {

        throw new UnsupportedOperationException("Not supported yet.");
    }

}
