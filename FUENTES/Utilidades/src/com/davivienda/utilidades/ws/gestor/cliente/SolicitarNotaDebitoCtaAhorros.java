package com.davivienda.utilidades.ws.gestor.cliente;
//old: package com.davivienda.multifuncional.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaAhorrosServiceATM.ISolictarNotaDebitoCuentaAhorrosServiceATM;
import com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaAhorrosServiceATM.RespuestaNotaDebitoCuentaAhorrosDto;
import com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaAhorrosServiceATM.ServicioException_Exception;
import com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaAhorrosServiceATM.SolicitudNotaDebitoCuentaAhorrosDto;
import com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaAhorrosServiceATM.SolictarNotaDebitoCuentaAhorrosServiceATMService;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;

/**
 * NotaDebitoCuentaCorriente - 10/08/2009 Descripci�n : Clase cliente para
 * invocar el servicio NotaDebitoCtaCorriente Versi�n : 1.0
 *
 * @author jjvargas 2009
 */

public class SolicitarNotaDebitoCtaAhorros {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);

    private static SolictarNotaDebitoCuentaAhorrosServiceATMService servicioImplementacion = null;
    private static ISolictarNotaDebitoCuentaAhorrosServiceATM servicioPort = null;
    private static URL SOLNOTADEBCTAAHO_WSDL_LOCATION;

    static {

        String urlString = null;
        try {
            SOLNOTADEBCTAAHO_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.SolicitarNotaDebitoCtaAhorros.class.getClassLoader().getResource("META-INF/wsdl/SolictarNotaDebitoCuentaAhorrosServiceATM/SolictarNotaDebitoCuentaAhorrosServiceATM_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/SolictarNotaDebitoCuentaAhorrosServiceATMService/SolictarNotaDebitoCuentaAhorrosServiceATM";
            if (SOLNOTADEBCTAAHO_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/SolictarNotaDebitoCuentaAhorrosServiceATM/SolictarNotaDebitoCuentaAhorrosServiceATM_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new SolictarNotaDebitoCuentaAhorrosServiceATMService(SOLNOTADEBCTAAHO_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getSolictarNotaDebitoCuentaAhorrosServiceATMPort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementacion de " + urlString, e);
        }
    }

    public RespuestaNotaDebitoCuentaAhorrosDto realizarNotaDebitoCuentaAhorros(SolicitudNotaDebitoCuentaAhorrosDto datosRequerimiento) throws ServicioException_Exception {
        SolicitudNotaDebitoCuentaAhorrosDto dtoRequerimiento = datosRequerimiento;
        RespuestaNotaDebitoCuentaAhorrosDto dtoRespuesta = null;
        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getSolictarNotaDebitoCuentaAhorrosServiceATMPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.solictarNotaDebitoCuentaAhorros(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
