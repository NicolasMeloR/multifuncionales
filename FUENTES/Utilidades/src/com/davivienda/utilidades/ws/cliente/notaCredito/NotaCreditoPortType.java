
package com.davivienda.utilidades.ws.cliente.notaCredito;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "NotaCreditoPortType", targetNamespace = "http://www.davivienda.com/xml/NotaCredito")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface NotaCreditoPortType {


    /**
     * 
     * @param request
     * @return
     *     returns com.davivienda.sara.clientews.notaCredito.ResponseType
     */
    @WebMethod(operationName = "NotaCredito")
    @WebResult(name = "Response", targetNamespace = "")
    @RequestWrapper(localName = "NotaCredito", targetNamespace = "http://www.davivienda.com/xml/NotaCredito", className = "com.davivienda.sara.clientews.notaCredito.NotaCreditoType")
    @ResponseWrapper(localName = "NotaCreditoResponse", targetNamespace = "http://www.davivienda.com/xml/NotaCredito", className = "com.davivienda.sara.clientews.notaCredito.NotaCreditoResponseType")
    public ResponseType notaCredito(
        @WebParam(name = "Request", targetNamespace = "")
        RequestType request);

}