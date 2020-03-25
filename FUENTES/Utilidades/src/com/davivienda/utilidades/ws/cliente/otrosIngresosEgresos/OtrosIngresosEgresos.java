
package com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos;

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
@WebService(name = "OtrosIngresosEgresos", targetNamespace = "http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface OtrosIngresosEgresos {


    /**
     * 
     * @param parameter
     * @return
     *     returns com.davivienda.sara.clientews.otrosIngresosEgresos.RespuestaIngresoGiroChqDto
     * @throws ServicioException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "otrosIngresos", targetNamespace = "http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/", className = "com.davivienda.sara.clientews.otrosIngresosEgresos.OtrosIngresos")
    @ResponseWrapper(localName = "otrosIngresosResponse", targetNamespace = "http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/", className = "com.davivienda.sara.clientews.otrosIngresosEgresos.OtrosIngresosResponse")
    public RespuestaIngresoGiroChqDto otrosIngresos(
        @WebParam(name = "parameter", targetNamespace = "")
        IngresoGiroChqDto parameter)
        throws ServicioException_Exception
    ;

    /**
     * 
     * @param parameter
     * @return
     *     returns com.davivienda.sara.clientews.otrosIngresosEgresos.RespuestaAnulacionChequesGdosDto
     * @throws ServicioException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "otrosEgresos", targetNamespace = "http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/", className = "com.davivienda.sara.clientews.otrosIngresosEgresos.OtrosEgresos")
    @ResponseWrapper(localName = "otrosEgresosResponse", targetNamespace = "http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/", className = "com.davivienda.sara.clientews.otrosIngresosEgresos.OtrosEgresosResponse")
    public RespuestaAnulacionChequesGdosDto otrosEgresos(
        @WebParam(name = "parameter", targetNamespace = "")
        AnulacionChequesGdosDto parameter)
        throws ServicioException_Exception
    ;

}
