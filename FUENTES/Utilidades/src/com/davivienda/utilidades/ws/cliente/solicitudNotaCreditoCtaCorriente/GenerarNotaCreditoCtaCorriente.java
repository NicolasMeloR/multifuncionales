
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorriente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for generarNotaCreditoCtaCorriente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="generarNotaCreditoCtaCorriente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SolicitudNotaCreditoCtaCorrienteDto_1" type="{http://servicios.davivienda.com/solicitudNotaCreditoCtaCorrienteServiceTypes}SolicitudNotaCreditoCtaCorrienteDto"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generarNotaCreditoCtaCorriente_", propOrder = {
    "solicitudNotaCreditoCtaCorrienteDto1"
})
public class GenerarNotaCreditoCtaCorriente {

    @XmlElement(name = "SolicitudNotaCreditoCtaCorrienteDto_1", required = true, nillable = true)
    protected SolicitudNotaCreditoCtaCorrienteDto solicitudNotaCreditoCtaCorrienteDto1;

    /**
     * Gets the value of the solicitudNotaCreditoCtaCorrienteDto1 property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudNotaCreditoCtaCorrienteDto }
     *     
     */
    public SolicitudNotaCreditoCtaCorrienteDto getSolicitudNotaCreditoCtaCorrienteDto1() {
        return solicitudNotaCreditoCtaCorrienteDto1;
    }

    /**
     * Sets the value of the solicitudNotaCreditoCtaCorrienteDto1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudNotaCreditoCtaCorrienteDto }
     *     
     */
    public void setSolicitudNotaCreditoCtaCorrienteDto1(SolicitudNotaCreditoCtaCorrienteDto value) {
        this.solicitudNotaCreditoCtaCorrienteDto1 = value;
    }

}
