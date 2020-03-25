
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorrienteServiceATM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for solicitudNotaCreditoCtaCorrienteResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solicitudNotaCreditoCtaCorrienteResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://solicitudnotacreditoctacorrienteinterface.servicios.procesadortransacciones.davivienda.com/}respuestaNotaCreditoCtaCorrienteDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudNotaCreditoCtaCorrienteResponse", propOrder = {
    "_return"
})
public class SolicitudNotaCreditoCtaCorrienteResponse {

    @XmlElement(name = "return")
    protected RespuestaNotaCreditoCtaCorrienteDto _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaNotaCreditoCtaCorrienteDto }
     *     
     */
    public RespuestaNotaCreditoCtaCorrienteDto getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaNotaCreditoCtaCorrienteDto }
     *     
     */
    public void setReturn(RespuestaNotaCreditoCtaCorrienteDto value) {
        this._return = value;
    }

}
