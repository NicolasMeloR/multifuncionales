
package com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaCorrienteServiceATM;

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
@WebServiceClient(name = "SolictarNotaDebitoCuentaCorrienteServiceATMService", targetNamespace = "http://solictarnotadebitocuentacorriente.servicios.procesadortransacciones.davivienda.com/")
public class SolictarNotaDebitoCuentaCorrienteServiceATMService
    extends Service
{

    private final static QName SOLICTARNOTADEBITOCUENTACORRIENTESERVICEATMSERVICE_QNAME = new QName("http://solictarnotadebitocuentacorriente.servicios.procesadortransacciones.davivienda.com/", "SolictarNotaDebitoCuentaCorrienteServiceATMService");

    public SolictarNotaDebitoCuentaCorrienteServiceATMService(URL wsdlLocation) {
        super(wsdlLocation, SOLICTARNOTADEBITOCUENTACORRIENTESERVICEATMSERVICE_QNAME);
    }

    @WebEndpoint(name = "SolictarNotaDebitoCuentaCorrienteServiceATMPort")
    public ISolictarNotaDebitoCuentaCorrienteServiceATM getSolictarNotaDebitoCuentaCorrienteServiceATMPort() {
        return super.getPort(new QName("http://solictarnotadebitocuentacorriente.servicios.procesadortransacciones.davivienda.com/", "SolictarNotaDebitoCuentaCorrienteServiceATMPort"), ISolictarNotaDebitoCuentaCorrienteServiceATM.class);
    }

}
