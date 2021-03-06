
package com.davivienda.utilidades.ws.cliente.envioTransportadora;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "EnvioTransportadoraService", targetNamespace = "http://enviotransportadora.servicios.procesadortransacciones.davivienda.com/")
public class EnvioTransportadoraService
        extends Service {

    private final static QName ENVIOTRANSPORTADORASERVICE_QNAME = new QName("http://enviotransportadora.servicios.procesadortransacciones.davivienda.com/", "EnvioTransportadoraService");

    public EnvioTransportadoraService(URL wsdlLocation) {
        super(wsdlLocation, ENVIOTRANSPORTADORASERVICE_QNAME);
    }

    @WebEndpoint(name = "EnvioTransportadoraPort")
    public IEnvioTransportadora getEnvioTransportadoraPort() {
        return super.getPort(new QName("http://enviotransportadora.servicios.procesadortransacciones.davivienda.com/", "EnvioTransportadoraPort"), IEnvioTransportadora.class);
    }

}
