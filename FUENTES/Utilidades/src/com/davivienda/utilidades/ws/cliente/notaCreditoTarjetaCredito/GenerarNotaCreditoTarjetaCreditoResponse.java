
package com.davivienda.utilidades.ws.cliente.notaCreditoTarjetaCredito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for generarNotaCreditoTarjetaCreditoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="generarNotaCreditoTarjetaCreditoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://notacreditotarjetacreditointerface.procesadortransacciones.davivienda.com/}respuestaNotaCreditoTarjetaCreditoDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generarNotaCreditoTarjetaCreditoResponse_", propOrder = {
    "_return"
})
public class GenerarNotaCreditoTarjetaCreditoResponse {

    @XmlElement(name = "return")
    protected RespuestaNotaCreditoTarjetaCreditoDto _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaNotaCreditoTarjetaCreditoDto }
     *     
     */
    public RespuestaNotaCreditoTarjetaCreditoDto getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaNotaCreditoTarjetaCreditoDto }
     *     
     */
    public void setReturn(RespuestaNotaCreditoTarjetaCreditoDto value) {
        this._return = value;
    }

}
