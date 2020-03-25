
package com.davivienda.utilidades.ws.cliente.notaCreditoFM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for generarNotaCreditoFMResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="generarNotaCreditoFMResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://notacreditofminterface.procesadortransacciones.davivienda.com/}respuestaNotaCreditoFMDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generarNotaCreditoFMResponse", propOrder = {
    "_return"
})
public class GenerarNotaCreditoFMResponse {

    @XmlElement(name = "return")
    protected RespuestaNotaCreditoFMDto _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaNotaCreditoFMDto }
     *     
     */
    public RespuestaNotaCreditoFMDto getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaNotaCreditoFMDto }
     *     
     */
    public void setReturn(RespuestaNotaCreditoFMDto value) {
        this._return = value;
    }

}
