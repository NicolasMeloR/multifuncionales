package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.sara.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.AjustarSobranteCajeroDto;
import com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.AjusteCajerosAutomaticos;
import com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.AjusteCajerosAutomaticosService;
import com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.ConsultaManejoEfectivoATMDto;
import com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.RespAjustarSobranteCajeroDto;
import com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.RespuestaConsultaManejoEfectivoATMDto;
import com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.ServicioException_Exception;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;

/**
 * AjusteCajeroAutomatico - 17/07/2009 Descripción : Clase cliente para invocar
 * el servicio AjusteCajerosAutomaticosService Versión : 1.0
 *
 * @author jjvargas 2009
 */
public class AjusteCajeroAutomatico {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);
    private static AjusteCajerosAutomaticosService servicioImplementacion = null;
    private static AjusteCajerosAutomaticos servicioPort = null;
    private static URL AJUSTECAJEROSAUTOMATICOSSERVICE_WSDL_LOCATION;

    static {
        URL url = null;
        String urlString = null;
        try {
            AJUSTECAJEROSAUTOMATICOSSERVICE_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.AjusteCajeroAutomatico.class.getClassLoader().getResource("META-INF/wsdl/AjusteCajerosAutomaticos/AjusteCajerosAutomaticos_1.wsdl");

            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/AjusteCajerosAutomaticosService/AjusteCajerosAutomaticos";
            if (AJUSTECAJEROSAUTOMATICOSSERVICE_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/AjusteCajerosAutomaticos/AjusteCajerosAutomaticos_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }            
            servicioImplementacion = new AjusteCajerosAutomaticosService(AJUSTECAJEROSAUTOMATICOSSERVICE_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getAjusteCajerosAutomaticosPort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementación de " + urlString, e);
        }
    }

    public RespAjustarSobranteCajeroDto realizarAjustePorSobrante(AjustarSobranteCajeroDto datosRequerimiento) throws ServicioException_Exception {
        AjustarSobranteCajeroDto dtoRequerimiento = datosRequerimiento;
        RespAjustarSobranteCajeroDto dtoRespuesta = null;

        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getAjusteCajerosAutomaticosPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.ajustarSobranteCajero(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

    public RespAjustarSobranteCajeroDto realizarAjustePorFaltante(AjustarSobranteCajeroDto datosRequerimiento) throws ServicioException_Exception {
        AjustarSobranteCajeroDto dtoRequerimiento = datosRequerimiento;
        RespAjustarSobranteCajeroDto dtoRespuesta = null;

        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getAjusteCajerosAutomaticosPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.ajustarFaltanteCajero(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

    public RespuestaConsultaManejoEfectivoATMDto realizarAjustePorProvision(ConsultaManejoEfectivoATMDto datosRequerimiento) throws ServicioException_Exception {

        ConsultaManejoEfectivoATMDto dtoRequerimiento = datosRequerimiento;
        RespuestaConsultaManejoEfectivoATMDto dtoRespuesta = null;
        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getAjusteCajerosAutomaticosPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.ajustarProvisionCajero(datosRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
