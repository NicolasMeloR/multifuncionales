
package com.davivienda.utilidades.ws.cliente.informeTotalesATM;

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
@WebService(name = "InformeTotalesATM", targetNamespace = "http://InformeTotalesATM.servicios.impl.procesadortransacciones.davivienda.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface InformeTotalesATM {


    /**
     * 
     * @param parameter
     * @return
     *     returns com.davivienda.sara.clientews.informeTotalesATM.RespuestaInformeTotalesATMDto
     * @throws ServicioException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "informeTotalesATM", targetNamespace = "http://InformeTotalesATM.servicios.impl.procesadortransacciones.davivienda.com/", className = "com.davivienda.sara.clientews.informeTotalesATM.InformeTotalesATM_Type")
    @ResponseWrapper(localName = "informeTotalesATMResponse", targetNamespace = "http://InformeTotalesATM.servicios.impl.procesadortransacciones.davivienda.com/", className = "com.davivienda.sara.clientews.informeTotalesATM.InformeTotalesATMResponse")
    public RespuestaInformeTotalesATMDto informeTotalesATM(
        @WebParam(name = "parameter", targetNamespace = "")
        InformeTotalesATMDto parameter)
        throws ServicioException_Exception
    ;

}
