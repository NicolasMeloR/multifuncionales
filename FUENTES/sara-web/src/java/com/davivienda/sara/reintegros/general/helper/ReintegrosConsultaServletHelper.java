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
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.sara.constantes.RedCajero;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.constantes.TipoCuentaReintegro;
import com.davivienda.sara.entitys.ReintegrosHisto;
import com.davivienda.utilidades.Constantes;

/**
 * DiarioElectronicoGeneralTransaccionServletHelper - 27/08/2008 Descripción :
 * Helper para el manejo de los requerimientos de Transaccion Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ReintegrosConsultaServletHelper implements ReintegrosHelperInterface {

    private ReintegrosSessionLocal session = null;
    private ReintegrosGeneralObjectContext objectContext = null;
    private String respuestaJSon;
    Integer tipoReintegro;
    ;
    boolean esCreado = false;

    public ReintegrosConsultaServletHelper(ReintegrosSessionLocal session, ReintegrosGeneralObjectContext objectContext, Integer tipoReintegro, boolean esCreado) {
        this.session = session;
        this.objectContext = objectContext;
        this.tipoReintegro = tipoReintegro;
        this.esCreado = esCreado;
    }

    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Reintegros> items = null;
        Collection<ReintegrosHisto> itemsHisto = null;
        try {
            Date fechaInicial = null;
            Date fechaFinal = null;
            Integer codigoCajero = 0;
            JSONArray myArrayGrid = null;
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
                fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
            }
            try {
                Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
                items = session.getReintegros(fechaInicial, fechaFinal, codigoCajero, fechaHisto);
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
        objectContext.getConfigApp().loggerApp.severe("ReintegrosConsultaServletHelper aJSon  items -  " + items.size());
        String cadenaJSon = "";
        Integer indAutoriza = tipoReintegro;
        Integer indAutorizaError = tipoReintegro;
        try {
            Integer idRegistro = 0;
            if (tipoReintegro.equals(EstadoReintegro.REVISADOAUTORIZADO.codigo)) {
                indAutoriza = EstadoReintegro.REVISADOAUTORIZADO.codigo;
                indAutorizaError = EstadoReintegro.ERRORSTRATUS.codigo;
            }
            JSONObject resp = new JSONObject();
            JSONArray respItems = new JSONArray();
            for (Reintegros item : items) {
                if (null == item.getDifeDescuadre() || !item.getDifeDescuadre().equals(Constantes.PARAM_REINTEGROS_DESCUADRE_N)) {
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
                    itemJSon.put("valorEntregado", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getTValorentregado()));
                    itemJSon.put("redEnruta", RedCajero.getRedCajero(Cadena.aInteger(item.getHNumerocuenta().substring(0, 4))).toString());
                    itemJSon.put("usuarioRevisa", item.getUsuariorevisa());
                    itemJSon.put("estadoReintegro", item.getEstadoreintegro());
                    itemJSon.put("hDatostarjeta", item.gethDatostarjeta());
                    itemJSon.put("hTipotransaccion", item.gethTipotransaccion());
                    itemJSon.put("hIndices", item.gethIndices());
                    itemJSon.put("hTipotarjeta", item.gethTipotarjeta());
                    itemJSon.put("hFiller", item.gethFiller());
                    itemJSon.put("tCodigocajero", item.gettCodigocajero());
                    itemJSon.put("tNumerotransaccion", item.gettNumerotransaccion());
                    itemJSon.put("tFechatransaccion", item.gettFechatransaccion());
                    itemJSon.put("tTipotransaccion", item.gettTipotransaccion());
                    itemJSon.put("tCodigotransaccion", item.gettCodigotransaccion());
                    itemJSon.put("tErrortransaccion", item.gettErrortransaccion());
                    itemJSon.put("tValorsolicitado", item.gettValorsolicitado());
                    itemJSon.put("tValorentregado", item.gettValorentregado());
                    itemJSon.put("tTarjeta", item.getTTarjeta());
                    itemJSon.put("tCuenta", item.gettCuenta());
                    itemJSon.put("tCodigoterminaciontransaccion", item.getTCodigoterminaciontransaccion());
                    itemJSon.put("", item.gettCodigoterminaciontransaccion());
                    itemJSon.put("tReferencia", item.gettReferencia());
                    itemJSon.put("valorajustado", item.getValorajustado());
                    itemJSon.put("estadoreintegro", item.getEstadoreintegro());
                    itemJSon.put("usuariorevisa", item.getUsuariorevisa());
                    itemJSon.put("usuarioautoriza", item.getUsuarioautoriza());
                    itemJSon.put("fechareintegro", item.getFechareintegro());
                    itemJSon.put("errorreintegro", item.getErrorreintegro());
                    itemJSon.put("tipocuentareintegro", item.getTipocuentareintegro());
                    itemJSon.put("hOfiRadicacion", item.gethOfiRadicacion());
                    itemJSon.put("hNumerocuentaOrigen", item.gethNumerocuentaOrigen());
                    itemJSon.put("dispensed", item.getDispensed());
                    itemJSon.put("remaining", item.getRemaining());
                    itemJSon.put("difeDescuadre", item.getDifeDescuadre());
                    itemJSon.put("fechaReversado", item.getFechaReversado() != null && !item.getFechaReversado().toString().trim().equals("") ? com.davivienda.utilidades.conversion.Fecha.aCadena(item.getFechaReversado(), FormatoFecha.FECHA_HORA) : null);
                    itemJSon.put("concepto", item.getConcepto());
                    itemJSon.put("comision", item.getComision());
                    itemJSon.put("fechaSistema", item.getReintegrosPK().getHFechasistema().getTime());
                    if (esCreado) {
                        itemJSon.put("checkSeleccion", false);
                        if (item.getEstadoreintegro().equals(indAutoriza)) {

                            respItems.put(itemJSon);
                        }
                    } else {
                        itemJSon.put("checkSeleccion", true);
                        if (((item.getTipoCuentaReintegro().equals(TipoCuentaReintegro.CuentaAhorros.codigo))
                                || (item.getTipoCuentaReintegro().equals(TipoCuentaReintegro.CuentaCorriente.codigo))
                                || (item.getTipoCuentaReintegro().equals(TipoCuentaReintegro.SinMedio.codigo)))
                                && (item.getEstadoreintegro().equals(indAutoriza) || item.getEstadoreintegro().equals(indAutorizaError))) {

                            respItems.put(itemJSon);
                        }
                    }
                }
                resp.put("identifier", "idRegistro");
                resp.put("label", "codigoCajero");
                resp.put("items", respItems);
                cadenaJSon = resp.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            objectContext.getConfigApp().loggerApp.severe("No se puede pasar a JSON \n " + ex);
            cadenaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_PARSEAR_REGISTRO.getCodigo(), ex.getMessage());
        }
        return cadenaJSon;
    }
}
