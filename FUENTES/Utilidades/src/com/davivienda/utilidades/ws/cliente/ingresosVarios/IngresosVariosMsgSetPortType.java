
package com.davivienda.utilidades.ws.cliente.ingresosVarios;

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
@WebService(name = "IngresosVarios_MsgSetPortType", targetNamespace = "http://www.davivienda.com/xml/IngresosVarios")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IngresosVariosMsgSetPortType {


    /**
     * 
     * @param request
     * @return
     *     returns com.davivienda.sara.clientews.ingresosVarios.ResponseType
     */
    @WebMethod(operationName = "IngresosVarios")
    @WebResult(name = "Response", targetNamespace = "")
    @RequestWrapper(localName = "IngresosVarios", targetNamespace = "http://www.davivienda.com/xml/IngresosVarios", className = "com.davivienda.sara.clientews.ingresosVarios.IngresosVariosType")
    @ResponseWrapper(localName = "IngresosVariosResponse", targetNamespace = "http://www.davivienda.com/xml/IngresosVarios", className = "com.davivienda.sara.clientews.ingresosVarios.IngresosVariosRespType")
    public ResponseType ingresosVarios(
        @WebParam(name = "Request", targetNamespace = "")
        RequestType request);

}
