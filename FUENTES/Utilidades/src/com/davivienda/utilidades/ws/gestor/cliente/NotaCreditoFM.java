package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.multifuncional.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.notaCreditoFM.INotaCreditoFMService;
import com.davivienda.utilidades.ws.cliente.notaCreditoFM.NotaCreditoFMDto;
import com.davivienda.utilidades.ws.cliente.notaCreditoFM.NotaCreditoFMServiceService;
import com.davivienda.utilidades.ws.cliente.notaCreditoFM.RespuestaNotaCreditoFMDto;
import com.davivienda.utilidades.ws.cliente.notaCreditoFM.ServicioException_Exception;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;

/**
 * NotaDebitoCuentaCorriente - 10/08/2009 Descripci�n : Clase cliente para
 * invocar el servicio NotaDebitoCtaCorriente Versi�n : 1.0
 *
 * @author jjvargas 2009
 */

public class NotaCreditoFM {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);

    private static NotaCreditoFMServiceService servicioImplementacion = null;
    private static INotaCreditoFMService servicioPort = null;
    private static URL NOTACREDITOFM_WSDL_LOCATION;

    static {

        String urlString = null;
        try {
            NOTACREDITOFM_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.NotaCreditoFM.class.getClassLoader().getResource("META-INF/wsdl/NotaCreditoFM/NotaCreditoFMService_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/NotaCreditoFMServiceService/NotaCreditoFMService";
            if (NOTACREDITOFM_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/NotaCreditoFM/NotaCreditoFMService_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new NotaCreditoFMServiceService(NOTACREDITOFM_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getNotaCreditoFMServicePort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementacion de " + urlString, e);
        }
    }

    public RespuestaNotaCreditoFMDto realizarCreditoFM(NotaCreditoFMDto datosRequerimiento) throws ServicioException_Exception {
        NotaCreditoFMDto dtoRequerimiento = datosRequerimiento;
        RespuestaNotaCreditoFMDto dtoRespuesta = null;
        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getNotaCreditoFMServicePort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.generarNotaCreditoFM(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
