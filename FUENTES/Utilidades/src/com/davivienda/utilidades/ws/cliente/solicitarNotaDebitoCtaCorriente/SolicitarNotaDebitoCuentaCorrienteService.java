
package com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaCorriente;

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
@WebServiceClient(name = "SolicitarNotaDebitoCuentaCorrienteService", targetNamespace = "http://servicios.davivienda.com/solicitarNotaDebitoCuentaCorrienteService")
public class SolicitarNotaDebitoCuentaCorrienteService
    extends Service
{

    private final static QName SOLICITARNOTADEBITOCUENTACORRIENTESERVICE_QNAME = new QName("http://servicios.davivienda.com/solicitarNotaDebitoCuentaCorrienteService", "SolicitarNotaDebitoCuentaCorrienteService");

    public SolicitarNotaDebitoCuentaCorrienteService(URL wsdlLocation) {
        super(wsdlLocation, SOLICITARNOTADEBITOCUENTACORRIENTESERVICE_QNAME);
    }
   
    @WebEndpoint(name = "SolicitarNotaDebCtaCorrServiceInterfacePort")
    public SolicitarNotaDebCtaCorrServiceInterface getSolicitarNotaDebCtaCorrServiceInterfacePort() {
        return super.getPort(new QName("http://servicios.davivienda.com/solicitarNotaDebitoCuentaCorrienteService", "SolicitarNotaDebCtaCorrServiceInterfacePort"), SolicitarNotaDebCtaCorrServiceInterface.class);
    }

}