package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.wsbus.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.ingresosVarios.IngresosVariosMsgSetPortType;
import com.davivienda.utilidades.ws.cliente.ingresosVarios.IngresosVariosRespType;
import com.davivienda.utilidades.ws.cliente.ingresosVarios.IngresosVariosType;
import com.davivienda.utilidades.ws.cliente.ingresosVarios.Operaciones02HTTPService;
import com.sun.xml.ws.streaming.XMLStreamReaderException;
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
public class IngresosVarios {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);
    private static Operaciones02HTTPService servicioImplementacion = null;
    private static IngresosVariosMsgSetPortType servicioPort = null;
    private static URL NDEBITO_WSDL_LOCATION;

    static {
        String urlString = null;
        try {
            NDEBITO_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.IngresosVarios.class.getClassLoader().getResource("META-INF/wsdl/IngresosVarios/IngresosVarios_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/ESBService/IngresosVarios";
            if (NDEBITO_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/IngresosVarios/IngresosVarios_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new Operaciones02HTTPService(NDEBITO_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getOperaciones02HTTPPort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementacion de " + urlString, e);
        }
    }

    public IngresosVariosRespType realizarIngresosVarios(IngresosVariosType datosRequerimiento) {
        IngresosVariosType dtoRequerimiento = datosRequerimiento;
        IngresosVariosRespType dtoRespuesta = new IngresosVariosRespType();

        try {
            System.out.println("dtoRequerimiento=" + dtoRequerimiento);
            System.out.println("servicioPort1=" + servicioPort);
            System.out.println("servicioImplementacion=" + servicioImplementacion);
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getOperaciones02HTTPPort();
                        System.out.println("servicioPort2=" + servicioPort);
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta.setResponse(servicioPort.ingresosVarios(dtoRequerimiento.getRequest()));
            }
        } catch (XMLStreamReaderException e) {

        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }
}
