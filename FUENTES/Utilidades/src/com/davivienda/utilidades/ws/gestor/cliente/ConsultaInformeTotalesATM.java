package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.sara.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.informeTotalesATM.InformeTotalesATM;
import com.davivienda.utilidades.ws.cliente.informeTotalesATM.InformeTotalesATMDto;
import com.davivienda.utilidades.ws.cliente.informeTotalesATM.InformeTotalesATMService;
import com.davivienda.utilidades.ws.cliente.informeTotalesATM.RespuestaInformeTotalesATMDto;
import com.davivienda.utilidades.ws.cliente.informeTotalesATM.ServicioException_Exception;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;

/**
 * ConsultaInformeTotalesATM - 17/07/2009
 * Descripción : Clase cliente para invocar el servicio InformeTotalesATMService
 *               WSDL http://90.4.5.135/InformeTotalesATMService/ConsultaInformeTotalesATM?wsdl
 * Versión : 1.0 
 *
 * @author jjvargas
 * 2009
 */

public class ConsultaInformeTotalesATM {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);
    
    private static InformeTotalesATMService servicioImplementacion = null;
    private static InformeTotalesATM servicioPort = null;
    private static URL INFORMETOTALESATMSERVICE_WSDL_LOCATION;

    static {
        String urlString = null;
        try {
            INFORMETOTALESATMSERVICE_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.ConsultaInformeTotalesATM.class.getClassLoader().getResource("META-INF/wsdl/InformeTotalesATM/InformeTotalesATM_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/InformeTotalesATMService/InformeTotalesATM";
            if (INFORMETOTALESATMSERVICE_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/InformeTotalesATM/InformeTotalesATM_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new InformeTotalesATMService(INFORMETOTALESATMSERVICE_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort =  servicioImplementacion.getInformeTotalesATMPort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,urlString);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "No se puede crear la Implementación de " + urlString, e);
        }
    }

    public RespuestaInformeTotalesATMDto consultarInformeTotalesATM(InformeTotalesATMDto datosRequerimiento) throws ServicioException_Exception {
        InformeTotalesATMDto dtoRequerimiento = datosRequerimiento;
        RespuestaInformeTotalesATMDto dtoRespuesta = null;

        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort =  servicioImplementacion.getInformeTotalesATMPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.informeTotalesATM(dtoRequerimiento);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE,ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }
}
