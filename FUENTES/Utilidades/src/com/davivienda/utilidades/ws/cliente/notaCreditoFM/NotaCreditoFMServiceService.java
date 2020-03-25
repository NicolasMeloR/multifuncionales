
package com.davivienda.utilidades.ws.cliente.notaCreditoFM;

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
@WebServiceClient(name = "NotaCreditoFMServiceService", targetNamespace = "http://notacreditofm.procesadortransacciones.davivienda.com/")
public class NotaCreditoFMServiceService
    extends Service
{

    private final static QName NOTACREDITOFMSERVICESERVICE_QNAME = new QName("http://notacreditofm.procesadortransacciones.davivienda.com/", "NotaCreditoFMServiceService");

    public NotaCreditoFMServiceService(URL wsdlLocation) {
        super(wsdlLocation, NOTACREDITOFMSERVICESERVICE_QNAME);
    }

    @WebEndpoint(name = "NotaCreditoFMServicePort")
    public INotaCreditoFMService getNotaCreditoFMServicePort() {
        return super.getPort(new QName("http://notacreditofm.procesadortransacciones.davivienda.com/", "NotaCreditoFMServicePort"), INotaCreditoFMService.class);
    }

}