
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorrienteServiceATM;

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
@WebService(name = "ISolicitudNotaCreditoCtaCorrienteServiceATM", targetNamespace = "http://solicitudnotacreditoctacorrienteinterface.servicios.procesadortransacciones.davivienda.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ISolicitudNotaCreditoCtaCorrienteServiceATM {


    /**
     * 
     * @param dto
     * @return
     *     returns com.davivienda.sara.clientews.solicitudNotaCreditoCtaCorrienteServiceATM.RespuestaNotaCreditoCtaCorrienteDto
     * @throws ServicioException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "solicitudNotaCreditoCtaCorriente", targetNamespace = "http://solicitudnotacreditoctacorrienteinterface.servicios.procesadortransacciones.davivienda.com/", className = "com.davivienda.sara.clientews.solicitudNotaCreditoCtaCorrienteServiceATM.SolicitudNotaCreditoCtaCorriente")
    @ResponseWrapper(localName = "solicitudNotaCreditoCtaCorrienteResponse", targetNamespace = "http://solicitudnotacreditoctacorrienteinterface.servicios.procesadortransacciones.davivienda.com/", className = "com.davivienda.sara.clientews.solicitudNotaCreditoCtaCorrienteServiceATM.SolicitudNotaCreditoCtaCorrienteResponse")
    public RespuestaNotaCreditoCtaCorrienteDto solicitudNotaCreditoCtaCorriente(
        @WebParam(name = "dto", targetNamespace = "")
        SolicitudNotaCreditoCtaCorrienteDto dto)
        throws ServicioException_Exception
    ;

}