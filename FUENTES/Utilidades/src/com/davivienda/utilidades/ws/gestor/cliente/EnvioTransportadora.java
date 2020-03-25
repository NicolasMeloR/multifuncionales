package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.multifuncional.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.envioTransportadora.EnvioTransportadoraDto;
import com.davivienda.utilidades.ws.cliente.envioTransportadora.EnvioTransportadoraService;
import com.davivienda.utilidades.ws.cliente.envioTransportadora.IEnvioTransportadora;
import com.davivienda.utilidades.ws.cliente.envioTransportadora.RespuestaDisminucionProvisionDto;
import com.davivienda.utilidades.ws.cliente.envioTransportadora.ServicioException_Exception;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;

/**
 * EnvioTransportadora
 *
 *
 * @author mdruiz 2011
 */

public class EnvioTransportadora {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);
    private static EnvioTransportadoraService servicioImplementacion = null;
    private static IEnvioTransportadora servicioPort = null;
    private static URL ENVIOTRANSPORTADORA_WSDL_LOCATION;

    static {
        String urlString = null;
        try {
            ENVIOTRANSPORTADORA_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.EnvioTransportadora.class.getClassLoader().getResource("META-INF/wsdl/EnvioTransportadora/EnvioTransportadora_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/EnvioTransportadoraService/EnvioTransportadora";
            if (ENVIOTRANSPORTADORA_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/EnvioTransportadora/EnvioTransportadora_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new EnvioTransportadoraService(ENVIOTRANSPORTADORA_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getEnvioTransportadoraPort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementacion de " + urlString, e);
        }
    }

    public RespuestaDisminucionProvisionDto realizarEnvioTransportadora(EnvioTransportadoraDto datosRequerimiento) throws ServicioException_Exception {
        EnvioTransportadoraDto dtoRequerimiento = datosRequerimiento;
        RespuestaDisminucionProvisionDto dtoRespuesta = null;
        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getEnvioTransportadoraPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.envioTransportadora(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
