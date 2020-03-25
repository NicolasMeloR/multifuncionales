
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorrosServiceATM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for solicitudNotaCreditoCtaAhorros complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solicitudNotaCreditoCtaAhorros">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dto" type="{http://solicitudnotacreditoctaahorrosinterface.servicios.procesadortransacciones.davivienda.com/}solicitudNotaCreditoCtaAhorrosDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudNotaCreditoCtaAhorros", propOrder = {
    "dto"
})
public class SolicitudNotaCreditoCtaAhorros {

    protected SolicitudNotaCreditoCtaAhorrosDto dto;

    /**
     * Gets the value of the dto property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudNotaCreditoCtaAhorrosDto }
     *     
     */
    public SolicitudNotaCreditoCtaAhorrosDto getDto() {
        return dto;
    }

    /**
     * Sets the value of the dto property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudNotaCreditoCtaAhorrosDto }
     *     
     */
    public void setDto(SolicitudNotaCreditoCtaAhorrosDto value) {
        this.dto = value;
    }

}
