
package com.davivienda.utilidades.ws.cliente.notaCreditoFM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for generarNotaCreditoFM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="generarNotaCreditoFM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dto" type="{http://notacreditofminterface.procesadortransacciones.davivienda.com/}notaCreditoFMDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generarNotaCreditoFM", propOrder = {
    "dto"
})
public class GenerarNotaCreditoFM {

    protected NotaCreditoFMDto dto;

    /**
     * Gets the value of the dto property.
     * 
     * @return
     *     possible object is
     *     {@link NotaCreditoFMDto }
     *     
     */
    public NotaCreditoFMDto getDto() {
        return dto;
    }

    /**
     * Sets the value of the dto property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotaCreditoFMDto }
     *     
     */
    public void setDto(NotaCreditoFMDto value) {
        this.dto = value;
    }

}
