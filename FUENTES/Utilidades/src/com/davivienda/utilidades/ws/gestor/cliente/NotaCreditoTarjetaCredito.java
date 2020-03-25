package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.multifuncional.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.notaCreditoTarjetaCredito.INotaCreditoTarjetaCreditoService;
import com.davivienda.utilidades.ws.cliente.notaCreditoTarjetaCredito.NotaCreditoTarjetaCreditoDto;
import com.davivienda.utilidades.ws.cliente.notaCreditoTarjetaCredito.NotaCreditoTarjetaCreditoServiceService;
import com.davivienda.utilidades.ws.cliente.notaCreditoTarjetaCredito.RespuestaNotaCreditoTarjetaCreditoDto;
import com.davivienda.utilidades.ws.cliente.notaCreditoTarjetaCredito.ServicioException_Exception;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;

/**
 * NotaCreditoTarjetaCredito Descripci�n : Clase cliente para invocar el
 * servicio NotaDebitoCtaCorriente Versi�n : 1.0
 *
 * @author mdruiz 2011
 */

public class NotaCreditoTarjetaCredito {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);

    private static NotaCreditoTarjetaCreditoServiceService servicioImplementacion = null;
    private static INotaCreditoTarjetaCreditoService servicioPort = null;

    private static URL NOTACREDITOTJCR_WSDL_LOCATION;

    static {

        String urlString = null;
        try {
            NOTACREDITOTJCR_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.NotaCreditoTarjetaCredito.class.getClassLoader().getResource("META-INF/wsdl/NotaCreditoTarjetaCredito/NotaCreditoTarjetaCreditoService_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/NotaCreditoTarjetaCreditoServiceService/NotaCreditoTarjetaCreditoService";
            if (NOTACREDITOTJCR_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/NotaCreditoTarjetaCredito/NotaCreditoTarjetaCreditoService_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new NotaCreditoTarjetaCreditoServiceService(NOTACREDITOTJCR_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getNotaCreditoTarjetaCreditoServicePort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementacion de " + urlString, e);
        }
    }

    public RespuestaNotaCreditoTarjetaCreditoDto realizarCreditoTarjetaCredito(NotaCreditoTarjetaCreditoDto datosRequerimiento) throws ServicioException_Exception {
        NotaCreditoTarjetaCreditoDto dtoRequerimiento = datosRequerimiento;
        RespuestaNotaCreditoTarjetaCreditoDto dtoRespuesta = null;
        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getNotaCreditoTarjetaCreditoServicePort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.generarNotaCreditoTarjetaCredito(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
