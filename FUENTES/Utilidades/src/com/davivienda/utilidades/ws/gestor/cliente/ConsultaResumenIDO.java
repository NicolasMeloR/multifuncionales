package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.multifuncional.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.resumenIDOATM.RespuestaResumenIDOATMDto;
import com.davivienda.utilidades.ws.cliente.resumenIDOATM.ResumenIDOATM;
import com.davivienda.utilidades.ws.cliente.resumenIDOATM.ResumenIDOATMDto;
import com.davivienda.utilidades.ws.cliente.resumenIDOATM.ResumenIDOATMService;
import com.davivienda.utilidades.ws.cliente.resumenIDOATM.ServicioException_Exception;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;

/**
 * ConsultaResumenIDO - 17/07/2009 Descripci�n : Clase cliente para invocar el
 * servicio ResumenIDOATMService Versi�n : 1.0
 *
 * @author jjvargas 2009
 */
public class ConsultaResumenIDO {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);
    private static ResumenIDOATMService servicioImplementacion = null;
    private static ResumenIDOATM servicioPort = null;
    private static URL RESUMENIDOATMSERVICE_WSDL_LOCATION;

    static {
        String urlString = null;
        try {
            RESUMENIDOATMSERVICE_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.ConsultaResumenIDO.class.getClassLoader().getResource("META-INF/wsdl/ResumenIDOATM/ResumenIDOATM_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/ResumenIDOATMService/ResumenIDOATM";
            if (RESUMENIDOATMSERVICE_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/ResumenIDOATM/ResumenIDOATM_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new ResumenIDOATMService(RESUMENIDOATMSERVICE_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getResumenIDOATMPort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementaci�n de " + urlString, e);
        }
    }

    public RespuestaResumenIDOATMDto consultarResumenIDO(ResumenIDOATMDto datosRequerimiento) throws ServicioException_Exception {
        ResumenIDOATMDto dtoRequerimiento = datosRequerimiento;
        RespuestaResumenIDOATMDto dtoRespuesta = null;

        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getResumenIDOATMPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.resumenIDOATM(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
