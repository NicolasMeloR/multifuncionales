
package com.davivienda.utilidades.ws.cliente.notaCreditoTarjetaCredito;

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
@WebService(name = "INotaCreditoTarjetaCreditoService", targetNamespace = "http://notacreditotarjetacreditointerface.procesadortransacciones.davivienda.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface INotaCreditoTarjetaCreditoService {


    /**
     * 
     * @param dto
     * @return
     *     returns com.davivienda.sara.clientews.notaCreditoTarjetaCredito.RespuestaNotaCreditoTarjetaCreditoDto
     * @throws ServicioException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "generarNotaCreditoTarjetaCredito", targetNamespace = "http://notacreditotarjetacreditointerface.procesadortransacciones.davivienda.com/", className = "com.davivienda.sara.clientews.notaCreditoTarjetaCredito.GenerarNotaCreditoTarjetaCredito")
    @ResponseWrapper(localName = "generarNotaCreditoTarjetaCreditoResponse", targetNamespace = "http://notacreditotarjetacreditointerface.procesadortransacciones.davivienda.com/", className = "com.davivienda.sara.clientews.notaCreditoTarjetaCredito.GenerarNotaCreditoTarjetaCreditoResponse")
    public RespuestaNotaCreditoTarjetaCreditoDto generarNotaCreditoTarjetaCredito(
        @WebParam(name = "dto", targetNamespace = "")
        NotaCreditoTarjetaCreditoDto dto)
        throws ServicioException_Exception
    ;

}
