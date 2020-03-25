
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorrosServiceATM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for solicitudNotaCreditoCtaAhorrosResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solicitudNotaCreditoCtaAhorrosResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://solicitudnotacreditoctaahorrosinterface.servicios.procesadortransacciones.davivienda.com/}respuestaNotaCreditoCtaAhorrosDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudNotaCreditoCtaAhorrosResponse", propOrder = {
    "_return"
})
public class SolicitudNotaCreditoCtaAhorrosResponse {

    @XmlElement(name = "return")
    protected RespuestaNotaCreditoCtaAhorrosDto _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaNotaCreditoCtaAhorrosDto }
     *     
     */
    public RespuestaNotaCreditoCtaAhorrosDto getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaNotaCreditoCtaAhorrosDto }
     *     
     */
    public void setReturn(RespuestaNotaCreditoCtaAhorrosDto value) {
        this._return = value;
    }

}
