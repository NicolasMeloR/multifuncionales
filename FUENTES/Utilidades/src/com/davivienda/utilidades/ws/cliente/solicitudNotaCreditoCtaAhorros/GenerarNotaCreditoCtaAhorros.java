
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorros;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for generarNotaCreditoCtaAhorros complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="generarNotaCreditoCtaAhorros">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SolicitudNotaCreditoCtaAhorrosDto_1" type="{http://servicios.davivienda.com/solicitudNotaCreditoCtaAhorrosServiceTypes}SolicitudNotaCreditoCtaAhorrosDto"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generarNotaCreditoCtaAhorros_", propOrder = {
    "solicitudNotaCreditoCtaAhorrosDto1"
})
public class GenerarNotaCreditoCtaAhorros {

    @XmlElement(name = "SolicitudNotaCreditoCtaAhorrosDto_1", required = true, nillable = true)
    protected SolicitudNotaCreditoCtaAhorrosDto solicitudNotaCreditoCtaAhorrosDto1;

    /**
     * Gets the value of the solicitudNotaCreditoCtaAhorrosDto1 property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudNotaCreditoCtaAhorrosDto }
     *     
     */
    public SolicitudNotaCreditoCtaAhorrosDto getSolicitudNotaCreditoCtaAhorrosDto1() {
        return solicitudNotaCreditoCtaAhorrosDto1;
    }

    /**
     * Sets the value of the solicitudNotaCreditoCtaAhorrosDto1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudNotaCreditoCtaAhorrosDto }
     *     
     */
    public void setSolicitudNotaCreditoCtaAhorrosDto1(SolicitudNotaCreditoCtaAhorrosDto value) {
        this.solicitudNotaCreditoCtaAhorrosDto1 = value;
    }

}
