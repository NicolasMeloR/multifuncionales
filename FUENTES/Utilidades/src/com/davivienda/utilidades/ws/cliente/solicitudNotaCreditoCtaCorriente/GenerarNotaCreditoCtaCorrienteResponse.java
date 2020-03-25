
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorriente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for generarNotaCreditoCtaCorrienteResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="generarNotaCreditoCtaCorrienteResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="result" type="{http://servicios.davivienda.com/solicitudNotaCreditoCtaCorrienteServiceTypes}RespuestaNotaCreditoCtaCorrienteDto"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generarNotaCreditoCtaCorrienteResponse_", propOrder = {
    "result"
})
public class GenerarNotaCreditoCtaCorrienteResponse {

    @XmlElement(required = true, nillable = true)
    protected RespuestaNotaCreditoCtaCorrienteDto result;

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaNotaCreditoCtaCorrienteDto }
     *     
     */
    public RespuestaNotaCreditoCtaCorrienteDto getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaNotaCreditoCtaCorrienteDto }
     *     
     */
    public void setResult(RespuestaNotaCreditoCtaCorrienteDto value) {
        this.result = value;
    }

}
