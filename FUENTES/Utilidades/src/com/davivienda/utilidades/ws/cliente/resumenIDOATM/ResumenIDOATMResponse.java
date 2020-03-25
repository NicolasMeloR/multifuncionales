
package com.davivienda.utilidades.ws.cliente.resumenIDOATM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resumenIDOATMResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resumenIDOATMResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://ResumenIDOATM.servicios.impl.procesadortransacciones.davivienda.com/}respuestaResumenIDOATMDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resumenIDOATMResponse_", propOrder = {
    "_return"
})
public class ResumenIDOATMResponse {

    @XmlElement(name = "return")
    protected RespuestaResumenIDOATMDto _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaResumenIDOATMDto }
     *     
     */
    public RespuestaResumenIDOATMDto getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaResumenIDOATMDto }
     *     
     */
    public void setReturn(RespuestaResumenIDOATMDto value) {
        this._return = value;
    }

}
