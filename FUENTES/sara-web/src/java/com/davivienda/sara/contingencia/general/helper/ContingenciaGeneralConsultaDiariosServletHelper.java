package com.davivienda.sara.contingencia.general.helper;

import com.davivienda.multifuncional.tablas.edccargue.session.EDCCargueMultifuncionalLocal;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.TipoDiario;
import com.davivienda.sara.contingencia.general.ContingenciaManualGeneralObjectContext;
import com.davivienda.sara.contingencia.general.ContingenciaManualHelperInterface;
import com.davivienda.sara.diarioelectronico.general.DiarioElectronicoGeneralObjectContext;
import com.davivienda.sara.diarioelectronico.general.DiarioElectronicoHelperInterface;
import com.davivienda.sara.entitys.EdcCargue;
import com.davivienda.sara.tablas.edccargue.session.EdcCargueSessionLocal;
import com.davivienda.utilidades.conversion.JSon;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.EJBException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.davivienda.sara.diarioelectronico.general.bean.DiarioElectronicoBean;
import com.davivienda.sara.entitys.Edcarguemultifuncional;

/**
 * DiarioElectronicoGeneralTransaccionServletHelper - 27/08/2008 Descripcion :
 * Helper para el manejo de los requerimientos de Transaccion Version : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ContingenciaGeneralConsultaDiariosServletHelper implements ContingenciaManualHelperInterface {

    private EdcCargueSessionLocal session = null;
    private EDCCargueMultifuncionalLocal sessionMulti = null;
    private ContingenciaManualGeneralObjectContext objectContext = null;
    private String respuestaJSon;

    public ContingenciaGeneralConsultaDiariosServletHelper(EdcCargueSessionLocal session, EDCCargueMultifuncionalLocal sessionMulti, ContingenciaManualGeneralObjectContext objectContext) {

        this.session = session;
        this.sessionMulti = sessionMulti;
        this.objectContext = objectContext;
    }

    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<EdcCargue> items = null;
        Collection<Edcarguemultifuncional> itemsMulti = null;
        Integer intTipoDiario;
        TipoDiario tipoDiario = TipoDiario.Dispensador;

        try {

            Date fechaInicial = null;
            Date fechaFinal = null;

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
            fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);

            if (objectContext.getAtributoTipoDiarioString("tipoDiario") == null || objectContext.getAtributoTipoDiarioString("tipoDiario").equals("")) {
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), "Por favor seleccione un tipo de tarea");

            } else {
                intTipoDiario = com.davivienda.utilidades.conversion.Cadena.aInteger(objectContext.getAtributoTipoDiarioString("tipoDiario"));
                tipoDiario = TipoDiario.getTipoDiario(intTipoDiario);
            }

            try {
                // Consulta los registros segun los parametros tomados del request

                switch (tipoDiario) {

                    case Dispensador:
                        items = session.getEDCCarguePorFecha(fechaInicial, fechaFinal);
                        break;

                    case Efectivo:
                        itemsMulti = sessionMulti.getEDCCargueXFecha(fechaInicial, fechaFinal);
                        break;
                }

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
            } catch (Exception ex) {
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
            }

        } catch (IllegalArgumentException ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
        }
//        if (items == null || items.isEmpty() ) {
//            respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
//        }

        if (null != items  || this.respuestaJSon.length() <= 1 || (null != itemsMulti && itemsMulti.isEmpty())) {
            respuesta = aJSon(items, itemsMulti);  // paso los items a JSON
        } else {
            respuesta = this.respuestaJSon;
        }

        return respuesta;
    }

    private String aJSon(Collection<EdcCargue> items, Collection<Edcarguemultifuncional> itemsMulti) {
        String cadenaJSon = "";
        String descripcionError = "";
        try {
            Integer idRegistro = 0;
            JSONObject resp = new JSONObject();
            JSONArray respItems = new JSONArray();
            if (items != null) {
                for (EdcCargue item : items) {
                    JSONObject itemJSon = new JSONObject();
                    itemJSon.put("idRegistro", ++idRegistro);
                    itemJSon.put("codigoCajero", item.getCodigoCajero());
                    itemJSon.put("nombreArchivo", item.getNombrearchivo());
                    itemJSon.put("fecha", item.getFechaEdcCargue());
                    itemJSon.put("ciclo", item.getCiclo());
                    itemJSon.put("estadoProceso", com.davivienda.sara.constantes.EstadoProceso.getEstadoProceso(item.getEstadoproceso()));
                    itemJSon.put("error", com.davivienda.sara.constantes.CodigoError.getCodigoError(item.getError()));

                    respItems.put(itemJSon);
                }
            }
            if (itemsMulti != null) {
                for (Edcarguemultifuncional itemMulti : itemsMulti) {
                    JSONObject itemJSon = new JSONObject();
                    itemJSon.put("idRegistro", ++idRegistro);
                    itemJSon.put("codigoCajero", itemMulti.getCodigocajero());
                    itemJSon.put("nombreArchivo", itemMulti.getNombrearchivo());
                    itemJSon.put("fecha", itemMulti.getFechaedccargue());
                    itemJSon.put("ciclo", itemMulti.getCiclo());
                    itemJSon.put("estadoProceso", com.davivienda.sara.constantes.EstadoProceso.getEstadoProceso(itemMulti.getEstadoproceso()));
                    itemJSon.put("error", com.davivienda.sara.constantes.CodigoError.getCodigoError(itemMulti.getError()));
                    if (itemMulti.getDescripcionerror() != null) {
                        descripcionError = itemMulti.getDescripcionerror();
                    } else {
                        descripcionError = "";
                    }
                    itemJSon.put("descripcionError", descripcionError);

                    respItems.put(itemJSon);
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

    public String generarDiarioelectronicoXML() {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection<DiarioElectronicoBean> obtenerDatosCollectionDE() {

        throw new UnsupportedOperationException("Not supported yet.");
    }

}
