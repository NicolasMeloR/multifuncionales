package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.multifuncional.clientews;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.SaraSOAPHandlerResolver;
import com.davivienda.utilidades.ws.cliente.autenticarUsuarioLdap.AutenticarUsuarioDTO;
import com.davivienda.utilidades.ws.cliente.autenticarUsuarioLdap.AutenticarUsuarioLdap;
import com.davivienda.utilidades.ws.cliente.autenticarUsuarioLdap.AutenticarUsuarioLdapService;
import com.davivienda.utilidades.ws.cliente.autenticarUsuarioLdap.RespuestaAutenticarUsuarioDTO;
import com.davivienda.utilidades.ws.cliente.autenticarUsuarioLdap.ServicioException_Exception;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.HandlerResolver;
/**
 * NotaDebitoCuentaCorriente - 10/08/2009
 * Descripciï¿½n : Clase cliente para invocar el servicio NotaDebitoCtaCorriente
 * Versiï¿½n : 1.0
 *
 * @author jjvargas
 * 2009
 */

public class AutenticacionLdap {
    
    private static final Logger logger = Logger.getLogger(Constantes.LOGGER_APP);

    private static AutenticarUsuarioLdapService servicioImplementacion = null;
    private static AutenticarUsuarioLdap servicioPort = null;
    private static URL AUTENTICACIONLDAP_WSDL_LOCATION;
    static {
        String urlString = null;
        try {
            AUTENTICACIONLDAP_WSDL_LOCATION = com.davivienda.utilidades.ws.gestor.cliente.AutenticacionLdap.class.getClassLoader().getResource("META-INF/wsdl/AutenticarUsuarioLdap/AutenticarUsuarioLdap_1.wsdl");
            String host = SaraConfigServicios.SERVIDOR_WS.trim();
            String puerto = SaraConfigServicios.PUERTO_SERVIDOR_WS.trim();
            urlString = "http://" + host + ":" + puerto + "/AutenticarUsuarioLdapService/AutenticarUsuarioLdap";
            if (AUTENTICACIONLDAP_WSDL_LOCATION == null) {
                logger.log(Level.SEVERE, "Cannot find 'META-INF/wsdl/AutenticarUsuarioLdap/AutenticarUsuarioLdap_1.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            servicioImplementacion = new AutenticarUsuarioLdapService(AUTENTICACIONLDAP_WSDL_LOCATION);
            HandlerResolver handlerResolver = new SaraSOAPHandlerResolver();
            servicioImplementacion.setHandlerResolver(handlerResolver);
            servicioPort=  servicioImplementacion.getAutenticarUsuarioLdapPort();
            BindingProvider bindingPort = (BindingProvider) servicioPort;
            bindingPort.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,urlString);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "No se puede crear la Implementación de " + urlString, e);
        }
    }

    public RespuestaAutenticarUsuarioDTO autenticarUsuario(AutenticarUsuarioDTO datosRequerimiento) throws ServicioException_Exception {
        AutenticarUsuarioDTO dtoRequerimiento = datosRequerimiento;
        RespuestaAutenticarUsuarioDTO dtoRespuesta = null;
        try {
            if (dtoRequerimiento != null) {
                if (servicioPort == null) {
                    if (servicioImplementacion != null) {
                        servicioPort =  servicioImplementacion.getAutenticarUsuarioLdapPort();
                    } else {
                        throw new Exception("No se puede acceder al puerto del servicio");
                    }
                }
                dtoRespuesta = servicioPort.autenticarUsuario(dtoRequerimiento);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE,ex.getMessage(), ex);
        }
        return dtoRespuesta;
    }

}
