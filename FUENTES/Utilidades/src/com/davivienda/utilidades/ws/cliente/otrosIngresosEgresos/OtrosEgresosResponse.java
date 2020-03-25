
package com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for otrosEgresosResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="otrosEgresosResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/}respuestaAnulacionChequesGdosDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "otrosEgresosResponse", propOrder = {
    "_return"
})
public class OtrosEgresosResponse {

    @XmlElement(name = "return")
    protected RespuestaAnulacionChequesGdosDto _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaAnulacionChequesGdosDto }
     *     
     */
    public RespuestaAnulacionChequesGdosDto getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaAnulacionChequesGdosDto }
     *     
     */
    public void setReturn(RespuestaAnulacionChequesGdosDto value) {
        this._return = value;
    }

}
