package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.sara.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorriente.RespuestaNotaCreditoCtaCorrienteDto;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorriente.ServicioException_Exception;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorriente.SoliNotaCredCtaCteServiceInterface;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorriente.SolicitudNotaCreditoCtaCorrienteDto;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorriente.SolicitudNotaCreditoCtaCorrienteService;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;

/**
 * NotaCreditoCuentaCorriente - 10/08/2009 Descripción : Clase cliente para
 * invocar el servicio NotaCreditoCtaCorriente Versión : 1.0
 *
 * @author jjvargas 2009
 */

public class NotaCreditoCuentaCorriente {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);

    private static SolicitudNotaCreditoCtaCorrienteService servicioImplementacion = null;
    private static SoliNotaCredCtaCteServiceInterface servicioPort = null;
    private static URL NOTADEBITOCTAAHORROS_WSDL_LOCATION;

    static {

        String urlString = null;
        try {
            NOTADEBITOCTAAHORROS_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.NotaCreditoCuentaCorriente.class.getClassLoader().getResource("META-INF/wsdl/SolicitudNotaCreditoCtaCorriente/SolicitudNotaCreditoCtaCorrienteService_1.wsdl");

            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/servicios/SolicitudNotaCreditoCtaCorrienteService";
            if (NOTADEBITOCTAAHORROS_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/SolicitudNotaCreditoCtaCorriente/SolicitudNotaCreditoCtaCorrienteService_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new SolicitudNotaCreditoCtaCorrienteService(NOTADEBITOCTAAHORROS_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getSoliNotaCredCtaCteServiceInterfacePort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementación de " + urlString, e);
        }
    }

    public RespuestaNotaCreditoCtaCorrienteDto realizarNotaDebito(SolicitudNotaCreditoCtaCorrienteDto datosRequerimiento) throws ServicioException_Exception {
        SolicitudNotaCreditoCtaCorrienteDto dtoRequerimiento = datosRequerimiento;
        RespuestaNotaCreditoCtaCorrienteDto dtoRespuesta = null;

        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getSoliNotaCredCtaCteServiceInterfacePort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.generarNotaCreditoCtaCorriente(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
