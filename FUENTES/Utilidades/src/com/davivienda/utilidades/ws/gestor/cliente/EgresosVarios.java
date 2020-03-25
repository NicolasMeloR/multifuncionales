package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.wsbus.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.egresosVarios.EgresosVariosPortType;
import com.davivienda.utilidades.ws.cliente.egresosVarios.EgresosVariosRespType;
import com.davivienda.utilidades.ws.cliente.egresosVarios.EgresosVariosType;
import com.davivienda.utilidades.ws.cliente.egresosVarios.Operaciones09HTTPService;
import com.davivienda.utilidades.ws.cliente.egresosVarios.ResponseType;
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
public class EgresosVarios {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);
    private static Operaciones09HTTPService servicioImplementacion = null;
    private static EgresosVariosPortType servicioPort = null;
    private static URL NDEBITO_WSDL_LOCATION;

    static {

        String urlString = null;
        try {
            NDEBITO_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.AjusteCajeroAutomatico.class.getClassLoader().getResource("META-INF/wsdl/EgresosVarios/EgresosVarios_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/ESBService/EgresosVarios";
            if (NDEBITO_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/EgresosVarios/EgresosVarios_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new Operaciones09HTTPService(NDEBITO_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getOperaciones02HTTPPort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementacion de " + urlString, e);
        }
    }

    public EgresosVariosRespType realizarEgresosVarios(EgresosVariosType datosRequerimiento) {
        EgresosVariosType dtoRequerimiento = datosRequerimiento;
        EgresosVariosRespType dtoRespuesta = new EgresosVariosRespType();

        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getOperaciones02HTTPPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                ResponseType respuesta = servicioPort.egresosVarios(dtoRequerimiento.getRequest());
                System.out.println("respuesta=" + respuesta);
                dtoRespuesta.setResponse(respuesta);
            }
        } catch (XMLStreamReaderException e) {

        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
