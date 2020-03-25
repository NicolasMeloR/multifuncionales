package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.multifuncional.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorrienteServiceATM.ISolicitudNotaCreditoCtaCorrienteServiceATM;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorrienteServiceATM.RespuestaNotaCreditoCtaCorrienteDto;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorrienteServiceATM.ServicioException_Exception;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorrienteServiceATM.SolicitudNotaCreditoCtaCorrienteDto;
import com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorrienteServiceATM.SolicitudNotaCreditoCtaCorrienteServiceATMService;
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

public class SolicitudNotaCreditoCtaCorriente {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);

    private static SolicitudNotaCreditoCtaCorrienteServiceATMService servicioImplementacion = null;
    private static ISolicitudNotaCreditoCtaCorrienteServiceATM servicioPort = null;
    private static URL SOLNOTACTACTE_WSDL_LOCATION;

    static {

        String urlString = null;
        try {
            SOLNOTACTACTE_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.SolicitudNotaCreditoCtaCorriente.class.getClassLoader().getResource("META-INF/wsdl/SolicitudNotaCreditoCtaCorrienteServiceATM/SolicitudNotaCreditoCtaCorrienteServiceATM_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/SolicitudNotaCreditoCtaCorrienteServiceATMService/SolicitudNotaCreditoCtaCorrienteServiceATM";
            if (SOLNOTACTACTE_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/SolicitudNotaCreditoCtaCorrienteServiceATM/SolicitudNotaCreditoCtaCorrienteServiceATM_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new SolicitudNotaCreditoCtaCorrienteServiceATMService(SOLNOTACTACTE_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getSolicitudNotaCreditoCtaCorrienteServiceATMPort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementacion de " + urlString, e);
        }
    }

    public RespuestaNotaCreditoCtaCorrienteDto realizarNotaCreditoCtaCorrienteDto(SolicitudNotaCreditoCtaCorrienteDto datosRequerimiento) throws ServicioException_Exception {
        SolicitudNotaCreditoCtaCorrienteDto dtoRequerimiento = datosRequerimiento;
        RespuestaNotaCreditoCtaCorrienteDto dtoRespuesta = null;
        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getSolicitudNotaCreditoCtaCorrienteServiceATMPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.solicitudNotaCreditoCtaCorriente(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
