package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.multifuncional.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.AnulacionChequesGdosDto;
import com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.IngresoGiroChqDto;
import com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.OtrosIngresosEgresos;
import com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.OtrosIngresosEgresosService;
import com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.RespuestaAnulacionChequesGdosDto;
import com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.RespuestaIngresoGiroChqDto;
import com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.ServicioException_Exception;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;

/**
 * AjusteOtrosIngresosEgresos - 17/07/2009 Descripci�n : Clase cliente para
 * invocar el servicio OtrosIngresosEgresosService Versi�n : 1.0
 *
 * @author jjvargas 2009
 */
public class Otros_IngresosEgresos {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);

    private static OtrosIngresosEgresosService servicioImplementacion = null;
    private static OtrosIngresosEgresos servicioPort = null;
    private static URL OTROSINGRESOSEGRESOSSERVICE_WSDL_LOCATION;

    static {
        String urlString = null;
        try {
            OTROSINGRESOSEGRESOSSERVICE_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.Otros_IngresosEgresos.class.getClassLoader().getResource("META-INF/wsdl/OtrosIngresosEgresos/OtrosIngresosEgresos_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/OtrosIngresosEgresosService/OtrosIngresosEgresos";
            if (OTROSINGRESOSEGRESOSSERVICE_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/OtrosIngresosEgresos/OtrosIngresosEgresos_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new OtrosIngresosEgresosService(OTROSINGRESOSEGRESOSSERVICE_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getOtrosIngresosEgresosPort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la ImplementaciOn de " + urlString, e);
        }
    }

    public RespuestaAnulacionChequesGdosDto ajustarEgreso(AnulacionChequesGdosDto datosRequerimiento) throws ServicioException_Exception {
        AnulacionChequesGdosDto dtoRequerimiento = datosRequerimiento;
        RespuestaAnulacionChequesGdosDto dtoRespuesta = null;
        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getOtrosIngresosEgresosPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.otrosEgresos(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

    public RespuestaIngresoGiroChqDto ajustarIngreso(IngresoGiroChqDto datosRequerimiento) throws ServicioException_Exception {
        IngresoGiroChqDto dtoRequerimiento = datosRequerimiento;
        RespuestaIngresoGiroChqDto dtoRespuesta = null;
        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getOtrosIngresosEgresosPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.otrosIngresos(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
