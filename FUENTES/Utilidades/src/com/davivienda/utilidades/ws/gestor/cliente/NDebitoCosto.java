package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.wsbus.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.nDebitoCosto.Kioskos01HTTPService;
import com.davivienda.utilidades.ws.cliente.nDebitoCosto.NotaDebitoCostoMsgSetPortType;
import com.davivienda.utilidades.ws.cliente.nDebitoCosto.NotaDebitoCostoResponseType;
import com.davivienda.utilidades.ws.cliente.nDebitoCosto.NotaDebitoCostoType;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;

/**
 * EnvioTransportadora
 *
 *
 * @author mdruiz 2011
 */

public class NDebitoCosto {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);

    private static Kioskos01HTTPService servicioImplementacion = null;
    private static NotaDebitoCostoMsgSetPortType servicioPort = null;
    private static URL NDEBITO_WSDL_LOCATION;

    static {
        String urlString = null;
        try {
            NDEBITO_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.NDebitoCosto.class.getClassLoader().getResource("META-INF/wsdl/NDebitoCosto/NDebitoCosto_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/ESBService/NDebitoCosto";
            if (NDEBITO_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/NDebitoCosto/NDebitoCosto_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new Kioskos01HTTPService(NDEBITO_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getKioskos01HTTPPort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementacion de " + urlString, e);
        }
    }

    public NotaDebitoCostoResponseType realizarNotaDebito(NotaDebitoCostoType datosRequerimiento) {
        NotaDebitoCostoType dtoRequerimiento = datosRequerimiento;
        NotaDebitoCostoResponseType dtoRespuesta = null;

        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getKioskos01HTTPPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.notaDebitoCosto(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
