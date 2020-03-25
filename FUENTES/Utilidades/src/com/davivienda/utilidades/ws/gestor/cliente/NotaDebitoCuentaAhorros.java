package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.sara.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaAhorros.RespuestaNotaDebitoCuentaAhorrosDto;
import com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaAhorros.ServicioException_Exception;
import com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaAhorros.SolicitarNotaDebitoCuentaAhorrosService;
import com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaAhorros.SolicitarNotaDebitoCuentaAhorrosServiceInterface;
import com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaAhorros.SolicitudNotaDebitoCuentaAhorrosDto;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;

/**
 * NotaDebitoCuentaAhorros - 10/08/2009 Descripción : Clase cliente para invocar
 * el servicio NotaDebitoCtaAhorros Versión : 1.0
 *
 * @author jjvargas 2009
 */

public class NotaDebitoCuentaAhorros {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);

    private static SolicitarNotaDebitoCuentaAhorrosService servicioImplementacion = null;
    private static SolicitarNotaDebitoCuentaAhorrosServiceInterface servicioPort = null;
    private static URL NOTADEBITOCTAAHORROS_WSDL_LOCATION;

    static {

        String urlString = null;
        try {
            NOTADEBITOCTAAHORROS_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.NotaDebitoCuentaAhorros.class.getClassLoader().getResource("META-INF/wsdl/SolicitarNotaDebitoCuentaAhorros/SolicitarNotaDebitoCuentaAhorrosService_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/servicios/SolicitarNotaDebitoCuentaAhorrosService";
            if (NOTADEBITOCTAAHORROS_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/SolicitarNotaDebitoCuentaAhorros/SolicitarNotaDebitoCuentaAhorrosService_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new SolicitarNotaDebitoCuentaAhorrosService(NOTADEBITOCTAAHORROS_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getSolicitarNotaDebitoCuentaAhorrosServiceInterfacePort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementación de " + urlString, e);
        }
    }

    public RespuestaNotaDebitoCuentaAhorrosDto realizarNotaDebito(SolicitudNotaDebitoCuentaAhorrosDto datosRequerimiento) throws ServicioException_Exception {
        SolicitudNotaDebitoCuentaAhorrosDto dtoRequerimiento = datosRequerimiento;
        RespuestaNotaDebitoCuentaAhorrosDto dtoRespuesta = null;

        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getSolicitarNotaDebitoCuentaAhorrosServiceInterfacePort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.solicitarNotaDebitoCuentaAhorros(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
