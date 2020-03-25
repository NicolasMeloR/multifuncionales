package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.multifuncional.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaCorrienteServiceATM.ISolictarNotaDebitoCuentaCorrienteServiceATM;
import com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaCorrienteServiceATM.RespuestaNotaDebitoCuentaCorrienteDto;
import com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaCorrienteServiceATM.ServicioException_Exception;
import com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaCorrienteServiceATM.SolicitudNotaDebitoCuentaCorrienteDto;
import com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaCorrienteServiceATM.SolictarNotaDebitoCuentaCorrienteServiceATMService;
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

public class SolicitarNotaDebitoCtaCorriente {

    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);

    private static SolictarNotaDebitoCuentaCorrienteServiceATMService servicioImplementacion = null;
    private static ISolictarNotaDebitoCuentaCorrienteServiceATM servicioPort = null;
    private static URL SOLNOTADEBCTACTE_WSDL_LOCATION;

    static {

        String urlString = null;
        try {
            SOLNOTADEBCTACTE_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.SolicitarNotaDebitoCtaCorriente.class.getClassLoader().getResource("META-INF/wsdl/SolictarNotaDebitoCuentaCorrienteServiceATM/SolictarNotaDebitoCuentaCorrienteServiceATM_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/SolictarNotaDebitoCuentaCorrienteServiceATMService/SolictarNotaDebitoCuentaCorrienteServiceATM";
            if (SOLNOTADEBCTACTE_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/SolictarNotaDebitoCuentaCorrienteServiceATM/SolictarNotaDebitoCuentaCorrienteServiceATM_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new SolictarNotaDebitoCuentaCorrienteServiceATMService(SOLNOTADEBCTACTE_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort = servicioImplementacion.getSolictarNotaDebitoCuentaCorrienteServiceATMPort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlString);
        } catch (Exception e) {
            Logger.global.log(Level.SEVERE, "No se puede crear la Implementacion de " + urlString, e);
        }
    }

    public RespuestaNotaDebitoCuentaCorrienteDto realizarNotaDebitoCuentaCorriente(SolicitudNotaDebitoCuentaCorrienteDto datosRequerimiento) throws ServicioException_Exception {
        SolicitudNotaDebitoCuentaCorrienteDto dtoRequerimiento = datosRequerimiento;
        RespuestaNotaDebitoCuentaCorrienteDto dtoRespuesta = null;
        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort = servicioImplementacion.getSolictarNotaDebitoCuentaCorrienteServiceATMPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.solictarNotaDebitoCuentaCorriente(dtoRequerimiento);
            }
        } catch (Exception ex) {
            Logger.global.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
