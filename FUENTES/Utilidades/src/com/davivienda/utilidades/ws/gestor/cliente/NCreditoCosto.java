package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.wsbus.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.notaCredito.BankTrade07HTTPService;
import com.davivienda.utilidades.ws.cliente.notaCredito.NotaCreditoPortType;
import com.davivienda.utilidades.ws.cliente.notaCredito.NotaCreditoResponseType;
import com.davivienda.utilidades.ws.cliente.notaCredito.NotaCreditoType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;

/**
 *
 * @author epadilla
 */
public class NCreditoCosto {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);
    private static BankTrade07HTTPService servicioImplementacion = null;
    private static NotaCreditoPortType servicioPort = null;
    private static URL NCREDITO_WSDL_LOCATION;

    static {
        String urlString = null;
        try {
            NCREDITO_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.NCreditoCosto.class.getClassLoader().getResource("META-INF/wsdl/NotaCredito/NotaCredito_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/ESBService/NotaCredito";
            if (NCREDITO_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/NotaCredito/NotaCredito_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new BankTrade07HTTPService(NCREDITO_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getBankTrade07HTTPPort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementacion de " + urlString, e);
        }
    }

    public NotaCreditoResponseType realizarNotaCredito(NotaCreditoType datosRequerimiento) {
        NotaCreditoType dtoRequerimiento = datosRequerimiento;
        NotaCreditoResponseType dtoRespuesta = new NotaCreditoResponseType();

        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getBankTrade07HTTPPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta.setResponse(servicioPort.notaCredito(dtoRequerimiento.getRequest()));
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
