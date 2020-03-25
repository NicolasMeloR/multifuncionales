
package com.davivienda.utilidades.ws.cliente.notaCreditoFM;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "ServicioException", targetNamespace = "http://notacreditofminterface.procesadortransacciones.davivienda.com/")
public class ServicioException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ServicioException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public ServicioException_Exception(String message, ServicioException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public ServicioException_Exception(String message, ServicioException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.davivienda.sara.clientews.notaCreditoFM.ServicioException
     */
    public ServicioException getFaultInfo() {
        return faultInfo;
    }

}
