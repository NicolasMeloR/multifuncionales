/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.multifuncional.reintegros.helper;

import com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.session.ReintegrosMultiEfectivoSessionLocal;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.multifuncional.constantes.TipoCuentaMultifuncional;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import com.davivienda.utilidades.Constantes;
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
 * @author p-cchapa
 */
public class ReintegrosMultiConsultaServletHelper implements ReintegrosMultifuncionalHelperInterface {

    private ReintegrosMultiEfectivoSessionLocal session = null;
    private ReintegrosMultifuncionalGeneralObjectContext objectContext = null;
    private String respuestaJSon;
    Integer tipoReintegro;

    public ReintegrosMultiConsultaServletHelper(ReintegrosMultiEfectivoSessionLocal session, ReintegrosMultifuncionalGeneralObjectContext objectContext, Integer tipoReintegro) {
        this.session = session;
        this.objectContext = objectContext;
        this.tipoReintegro = tipoReintegro;
    }

    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Reintegrosmultiefectivo> items = null;
        try {

            Date fechaInicial = null;
            Date fechaFinal = null;
            String accionReintegro = "";
            JSONArray myArrayGrid = null;
            Integer accion = 0;
            Integer codigoCajero = 0;

//            try {
//                accionReintegro = objectContext.getAccionReintegrosMulti();
//                accion = Integer.parseInt(accionReintegro);
//            } catch (IllegalArgumentException ex) {
//                accionReintegro = "";
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
                //fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaFinal);
            } catch (IllegalArgumentException ex) {
                fechaFinal = fechaInicial;
                fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
            }
            try {
                // Se envia el tipo de reintegro del ejb
                Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
                items = session.getReintegros(fechaInicial, fechaFinal, codigoCajero, fechaHisto);

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
        Integer indAutoriza = tipoReintegro;
        Integer indAutorizaError = tipoReintegro;
        try {
            Integer idRegistro = 0;
            //OJO SE USA SI ES AUTORIZA O NO para filtrar los registros correspondientes
            if (tipoReintegro.equals(EstadoReintegro.REVISADOAUTORIZADO.codigo)) {
                indAutoriza = EstadoReintegro.REVISADOAUTORIZADO.codigo;
                indAutorizaError = EstadoReintegro.ERRORSTRATUS.codigo;
            }

            JSONObject resp = new JSONObject();
            JSONArray respItems = new JSONArray();
            for (Reintegrosmultiefectivo item : items) {

                JSONObject itemJSon = new JSONObject();
                itemJSon.put("idRegistro", ++idRegistro);
                itemJSon.put("codigoCajero", item.getTCodigocajero());
                itemJSon.put("numeroTransaccion", item.getReintegrosmultiefectivoPK().getHTalon().toString());
                itemJSon.put("fechaCajero", com.davivienda.utilidades.conversion.Fecha.aCadena(item.getTFechacajero(), FormatoFecha.FECHA_HORA));
                itemJSon.put("numeroCuenta", item.getHNumerocuenta());
                itemJSon.put("tipoCuenta", TipoCuentaMultifuncional.getTipoCuentaMultifuncional(item.getTTipocuenta()).toString());
                itemJSon.put("fecha", com.davivienda.utilidades.conversion.Fecha.aCadena(item.getReintegrosmultiefectivoPK().getHFechasistema(), FormatoFecha.FECHA_HORA));
                itemJSon.put("valor", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getHValor()));
                itemJSon.put("totalBilletesConsignados", item.getTTotalbilletesconsig());
                itemJSon.put("valorAjustar", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getValorajustado()));
                itemJSon.put("usuarioRevisa", item.getUsuariosrevisa());
                itemJSon.put("checkSeleccion", true);

                if (item.getEstadoreintegro().equals(indAutoriza) || item.getEstadoreintegro().equals(indAutorizaError)) {

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
