
package com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for otrosIngresosResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="otrosIngresosResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/}respuestaIngresoGiroChqDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "otrosIngresosResponse", propOrder = {
    "_return"
})
public class OtrosIngresosResponse {

    @XmlElement(name = "return")
    protected RespuestaIngresoGiroChqDto _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaIngresoGiroChqDto }
     *     
     */
    public RespuestaIngresoGiroChqDto getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaIngresoGiroChqDto }
     *     
     */
    public void setReturn(RespuestaIngresoGiroChqDto value) {
        this._return = value;
    }

}
