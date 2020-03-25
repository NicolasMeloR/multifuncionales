
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorros;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for generarNotaCreditoCtaAhorrosResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="generarNotaCreditoCtaAhorrosResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="result" type="{http://servicios.davivienda.com/solicitudNotaCreditoCtaAhorrosServiceTypes}RespuestaNotaCreditoCtaAhorrosDto"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generarNotaCreditoCtaAhorrosResponse_", propOrder = {
    "result"
})
public class GenerarNotaCreditoCtaAhorrosResponse {

    @XmlElement(required = true, nillable = true)
    protected RespuestaNotaCreditoCtaAhorrosDto result;

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaNotaCreditoCtaAhorrosDto }
     *     
     */
    public RespuestaNotaCreditoCtaAhorrosDto getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaNotaCreditoCtaAhorrosDto }
     *     
     */
    public void setResult(RespuestaNotaCreditoCtaAhorrosDto value) {
        this.result = value;
    }

}
