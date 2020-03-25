package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.multifuncional.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.consultaTotalesMultifuncional.ConsultaTotalesMultifuncionalDto;
import com.davivienda.utilidades.ws.cliente.consultaTotalesMultifuncional.ConsultaTotalesMultifuncionalServiceService;
import com.davivienda.utilidades.ws.cliente.consultaTotalesMultifuncional.IConsultaTotalesMultifuncionalService;
import com.davivienda.utilidades.ws.cliente.consultaTotalesMultifuncional.RespuestaConsultaTotalesMultifuncionalDto;
import com.davivienda.utilidades.ws.cliente.consultaTotalesMultifuncional.ServicioException_Exception;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;

/**
 * NotaDebitoCuentaCorriente - 10/08/2009 Descripcin : Clase cliente para
 * invocar el servicio NotaDebitoCtaCorriente Versi n : 1.0
 *
 * @author jjvargas 2009
 */
public class ConsultaTotalesMultifuncional {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);
    private static ConsultaTotalesMultifuncionalServiceService servicioImplementacion = null;
    private static IConsultaTotalesMultifuncionalService servicioPort = null;
    private static URL CONSTOTMULTI_WSDL_LOCATION;

    static {
        String urlString = null;
        try {
            CONSTOTMULTI_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.ConsultaTotalesMultifuncional.class.getClassLoader().getResource("META-INF/wsdl/ConsultaTotalesMultifuncional/ConsultaTotalesMultifuncionalService_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/ConsultaTotalesMultifuncionalServiceService/ConsultaTotalesMultifuncionalService";
            if (CONSTOTMULTI_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/ConsultaTotalesMultifuncional/ConsultaTotalesMultifuncionalService_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new ConsultaTotalesMultifuncionalServiceService(CONSTOTMULTI_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getConsultaTotalesMultifuncionalServicePort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementacion de " + urlString, e);
        }
    }

    public RespuestaConsultaTotalesMultifuncionalDto realizarConsultaTotalesMultifuncional(ConsultaTotalesMultifuncionalDto datosRequerimiento) throws ServicioException_Exception {
        ConsultaTotalesMultifuncionalDto dtoRequerimiento = datosRequerimiento;
        RespuestaConsultaTotalesMultifuncionalDto dtoRespuesta = null;
        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getConsultaTotalesMultifuncionalServicePort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.consultarTotalesMultifuncional(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
