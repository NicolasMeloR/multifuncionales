
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorrienteServiceATM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for solicitudNotaCreditoCtaCorriente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solicitudNotaCreditoCtaCorriente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dto" type="{http://solicitudnotacreditoctacorrienteinterface.servicios.procesadortransacciones.davivienda.com/}solicitudNotaCreditoCtaCorrienteDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudNotaCreditoCtaCorriente", propOrder = {
    "dto"
})
public class SolicitudNotaCreditoCtaCorriente {

    protected SolicitudNotaCreditoCtaCorrienteDto dto;

    /**
     * Gets the value of the dto property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudNotaCreditoCtaCorrienteDto }
     *     
     */
    public SolicitudNotaCreditoCtaCorrienteDto getDto() {
        return dto;
    }

    /**
     * Sets the value of the dto property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudNotaCreditoCtaCorrienteDto }
     *     
     */
    public void setDto(SolicitudNotaCreditoCtaCorrienteDto value) {
        this.dto = value;
    }

}
