
package com.davivienda.utilidades.ws.cliente.notaCreditoTarjetaCredito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for generarNotaCreditoTarjetaCredito complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="generarNotaCreditoTarjetaCredito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dto" type="{http://notacreditotarjetacreditointerface.procesadortransacciones.davivienda.com/}notaCreditoTarjetaCreditoDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generarNotaCreditoTarjetaCredito_", propOrder = {
    "dto"
})
public class GenerarNotaCreditoTarjetaCredito {

    protected NotaCreditoTarjetaCreditoDto dto;

    /**
     * Gets the value of the dto property.
     * 
     * @return
     *     possible object is
     *     {@link NotaCreditoTarjetaCreditoDto }
     *     
     */
    public NotaCreditoTarjetaCreditoDto getDto() {
        return dto;
    }

    /**
     * Sets the value of the dto property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotaCreditoTarjetaCreditoDto }
     *     
     */
    public void setDto(NotaCreditoTarjetaCreditoDto value) {
        this.dto = value;
    }

}
