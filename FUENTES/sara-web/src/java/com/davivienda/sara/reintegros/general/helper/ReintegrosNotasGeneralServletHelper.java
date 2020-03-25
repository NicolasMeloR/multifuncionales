package com.davivienda.sara.reintegros.general.helper;

import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.reintegros.general.ReintegrosNotasObjectContext;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;

import com.davivienda.utilidades.conversion.JSon;

import java.util.logging.Level;
import javax.ejb.EJBException;

import java.math.BigDecimal;
import com.davivienda.sara.constantes.TipoCuenta;
import com.davivienda.sara.entitys.Notasdebito;

import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.procesos.reintegros.notas.session.ReintegrosNotasProcesosSessionLocal;
import com.davivienda.sara.procesos.reintegros.session.ProcesoReintegrosSessionLocal;

import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SaraUtil;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Date;

;

/**
 * DiarioElectronicoGeneralDiarioElectronicoServletHelper - 27/08/2008
 * Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ReintegrosNotasGeneralServletHelper implements ReintegrosHelperInterface {

    private ReintegrosNotasProcesosSessionLocal session;
    private ReintegrosNotasObjectContext objectContext = null;
    private ProcesoReintegrosSessionLocal procesoReintegrosSessionLocal;
    private ConfModulosAplicacionLocal confModulosAplicacionSession;
    private boolean esNotaCredito;
    private Date fecha;

    public ReintegrosNotasGeneralServletHelper(ReintegrosNotasProcesosSessionLocal session, ReintegrosNotasObjectContext objectContext, ProcesoReintegrosSessionLocal procesoReintegrosSessionLocal, ConfModulosAplicacionLocal confModulosAplicacionSession, boolean esNotaCredito, Date fecha) {

        this.session = session;
        this.objectContext = objectContext;
        this.esNotaCredito = esNotaCredito;
        this.procesoReintegrosSessionLocal = procesoReintegrosSessionLocal;
        this.confModulosAplicacionSession = confModulosAplicacionSession;
        this.fecha = fecha;
    }

    public ReintegrosNotasGeneralServletHelper(ReintegrosNotasProcesosSessionLocal session, ReintegrosNotasObjectContext objectContext, ProcesoReintegrosSessionLocal procesoReintegrosSessionLocal, ConfModulosAplicacionLocal confModulosAplicacionSession, boolean esNotaCredito) {

        this.session = session;
        this.objectContext = objectContext;
        this.esNotaCredito = esNotaCredito;
        this.procesoReintegrosSessionLocal = procesoReintegrosSessionLocal;
        this.confModulosAplicacionSession = confModulosAplicacionSession;
        this.fecha = null;

    }

    public String obtenerDatos() {

        String respuesta = "";
        int intRegProcesados = -1;
        String strExepcion = "";
        Integer codigoCajero = 0;
        String cuenta = "0";
        String talon = "0";
        BigDecimal valor;
        String tipoNota = "";
        TipoCuenta tipoCuenta = TipoCuenta.CuentaAhorros;
        String usuario = "";

        try {

            codigoCajero = objectContext.getAtributoCodigoCajeroComboBox();
            valor = objectContext.getAtributoValor();
            tipoCuenta = objectContext.getTipoCuenta();
            usuario = objectContext.getUsuarioEnSesion().getUsuario();
            cuenta = SaraUtil.stripXSS(objectContext.getAtributoCuenta(), Constantes.REGEX_ACEPTAR_DEFAULT);
            talon = objectContext.getAtributoTalon();

            if (cuenta.length() < 4) {
                respuesta = JSon.getJSonRespuesta(0, "La Cuenta  tiene  Tamaño Errado");

            } else if (valor.intValue() <= ConsultarValMaxReintegro()) {
                System.err.println("El usuario : " + usuario
                        + " empezo a realizar una nota  al cajero: " + codigoCajero.toString()
                        + " por valor : " + valor.toString()
                        + " a la cuenta : " + cuenta
                        + " nota de el tipo: " + tipoCuenta);
                objectContext.getConfigApp().loggerApp.info("El usuario : " + usuario
                        + " empezo a realizar una nota  al cajero: " + codigoCajero.toString()
                        + " por valor : " + valor.toString()
                        + " a la cuenta : " + cuenta
                        + " nota de el tipo: " + tipoCuenta.nombre);

                if (esNotaCredito) {
                    //  respuesta=session.realizarNotaCredito(codigoCajero, valor, cuenta,tipoCuenta.codigo,usuario,talon);  
                    //    public void guardarReintegroCreado(Integer codigoCajero, Long valor,String cuenta,Integer tipoCuenta,String usuario,String talon) throws EntityServicioExcepcion
                    procesoReintegrosSessionLocal.guardarReintegroNuevo(codigoCajero, valor.longValue(), cuenta, tipoCuenta.codigo, usuario, talon);
                    respuesta = "Bien";
                    tipoNota = "Nota Credito ";
                } else {
                    procesoReintegrosSessionLocal.guardarNotaDebito(codigoCajero, valor.longValue(), cuenta, tipoCuenta.codigo, usuario);
                    respuesta = "Bien";
                    tipoNota = "Nota Debito ";
                }

                tipoNota = tipoNota + tipoCuenta.nombre;
                objectContext.getConfigApp().loggerApp.info("El usuario : " + usuario
                        + "  realizo una " + tipoNota + "  al cajero " + codigoCajero.toString()
                        + " por valor : " + valor.toString()
                        + " a la cuenta : " + cuenta
                        + " con respuesta:" + respuesta);
                if (respuesta != null) {
                    if (respuesta.length() > 0) {
                        if (respuesta.substring(0, 1).equals("B")) {
                            respuesta = tipoNota + " Realizada con Exito";
                        } else if (respuesta.substring(0, 1).equals("M")) {
                            respuesta = respuesta + "NO se pudo Realizar  la " + tipoNota;
                        } else if (respuesta.substring(0, 1).equals("F")) {
                            respuesta = respuesta + "Por favor verificar el  Estado de la " + tipoNota;
                        }
                    } else {
                        respuesta = respuesta + "Por favor verificar el  Estado de la " + tipoNota;
                    }
                } else {
                    respuesta = respuesta + "Por favor verificar el  Estado de la " + tipoNota;
                }

                respuesta = JSon.getJSonRespuesta(0, respuesta);
            } else {
                respuesta = JSon.getJSonRespuesta(0, "El valor de la nota credito es mayor al permitido");
            }

        } catch (EJBException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            objectContext.setError(strExepcion, CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
            //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            ex.printStackTrace();

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            objectContext.setError(ex.getMessage(), CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
            //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            ex.printStackTrace();
        }

//        } catch (ServletException ex) {
//            objectContext.setError( ex.getMessage(),CodigoError.PARAMETRO_INVALIDO.getCodigo());
//            respuesta = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
//        }
        return respuesta;
    }

    private int ConsultarValMaxReintegro() {
        int intValReintegro = 0;

        try {
            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.MAX_VALOR_REINTEGRO");
            registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            //ConfModulosAplicacion registroEntityConsulta=confModulosAplicacionSession.buscar(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.MAX_VALOR_REINTEGRO"));
            intValReintegro = com.davivienda.utilidades.conversion.Cadena.aInteger(registroEntityConsulta.getValor());

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error obteniendo el valor maximo de el reintegro : " + ex.getMessage());
            intValReintegro = 720000;
        }
        return intValReintegro;

    }

    public String generarAjustesXML() {

        throw new UnsupportedOperationException("Not supported yet.");

    }

}
