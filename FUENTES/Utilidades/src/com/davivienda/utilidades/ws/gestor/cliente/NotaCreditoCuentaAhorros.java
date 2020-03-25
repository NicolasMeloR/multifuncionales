package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.sara.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorros.RespuestaNotaCreditoCtaAhorrosDto;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorros.ServicioException_Exception;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorros.SoliNotaCredCtaAhorrosServiceInterface;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorros.SolicitudNotaCreditoCtaAhorrosDto;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorros.SolicitudNotaCreditoCtaAhorrosService;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;

/**
 * NotaCreditoCuentaAhorros - 10/08/2009 Descripción : Clase cliente para
 * invocar el servicio NotaCreditoCtaAhorros Versión : 1.0
 *
 * @author jjvargas 2009
 */

public class NotaCreditoCuentaAhorros {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);

    private static SolicitudNotaCreditoCtaAhorrosService servicioImplementacion = null;
    private static SoliNotaCredCtaAhorrosServiceInterface servicioPort = null;
    private static URL NOTADEBITOCTAAHORROS_WSDL_LOCATION;

    static {
        String urlString = null;
        try {
            NOTADEBITOCTAAHORROS_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.NotaCreditoCuentaAhorros.class.getClassLoader().getResource("META-INF/wsdl/SolicitudNotaCreditoCtaAhorros/SolicitudNotaCreditoCtaAhorrosService_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/servicios/SolicitudNotaCreditoCtaAhorrosService";
            if (NOTADEBITOCTAAHORROS_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/SolicitudNotaCreditoCtaAhorros/SolicitudNotaCreditoCtaAhorrosService_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new SolicitudNotaCreditoCtaAhorrosService(NOTADEBITOCTAAHORROS_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getSoliNotaCredCtaAhorrosServiceInterfacePort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementación de " + urlString, e);
        }
    }

    public RespuestaNotaCreditoCtaAhorrosDto realizarNotaDebito(SolicitudNotaCreditoCtaAhorrosDto datosRequerimiento) throws ServicioException_Exception {
        SolicitudNotaCreditoCtaAhorrosDto dtoRequerimiento = datosRequerimiento;
        RespuestaNotaCreditoCtaAhorrosDto dtoRespuesta = null;

        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getSoliNotaCredCtaAhorrosServiceInterfacePort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.generarNotaCreditoCtaAhorros(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
