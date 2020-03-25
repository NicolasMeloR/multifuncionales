package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.multifuncional.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorrosServiceATM.ISolicitudNotaCreditoCtaAhorrosServiceATM;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorrosServiceATM.RespuestaNotaCreditoCtaAhorrosDto;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorrosServiceATM.ServicioException_Exception;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorrosServiceATM.SolicitudNotaCreditoCtaAhorrosDto;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorrosServiceATM.SolicitudNotaCreditoCtaAhorrosServiceATMService;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;

/**
 * NotaDebitoCuentaCorriente - 10/08/2009 Descripci�n : Clase cliente para
 * invocar el servicio NotaDebitoCtaCorriente Versi�n : 1.0
 *
 * @author jjvargas 2009
 */

public class SolicitudNotaCreditoCtaAhorros {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);

    private static SolicitudNotaCreditoCtaAhorrosServiceATMService servicioImplementacion = null;
    private static ISolicitudNotaCreditoCtaAhorrosServiceATM servicioPort = null;
    private static URL SOLNOTACRCTAAHO_WSDL_LOCATION;

    static {
        String urlString = null;
        try {
            SOLNOTACRCTAAHO_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.SolicitudNotaCreditoCtaAhorros.class.getClassLoader().getResource("META-INF/wsdl/SolicitudNotaCreditoCtaAhorros/SolicitudNotaCreditoCtaAhorrosService_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/SolicitudNotaCreditoCtaAhorrosServiceATMService/SolicitudNotaCreditoCtaAhorrosServiceATM";
            if (SOLNOTACRCTAAHO_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/SolicitudNotaCreditoCtaAhorros/SolicitudNotaCreditoCtaAhorrosService_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new SolicitudNotaCreditoCtaAhorrosServiceATMService(SOLNOTACRCTAAHO_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getSolicitudNotaCreditoCtaAhorrosServiceATMPort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementacion de " + urlString, e);
        }
    }

    public RespuestaNotaCreditoCtaAhorrosDto realizarNotaCreditoCtaAhorros(SolicitudNotaCreditoCtaAhorrosDto datosRequerimiento) throws ServicioException_Exception {
        SolicitudNotaCreditoCtaAhorrosDto dtoRequerimiento = datosRequerimiento;
        RespuestaNotaCreditoCtaAhorrosDto dtoRespuesta = null;

        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getSolicitudNotaCreditoCtaAhorrosServiceATMPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.solicitudNotaCreditoCtaAhorros(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
