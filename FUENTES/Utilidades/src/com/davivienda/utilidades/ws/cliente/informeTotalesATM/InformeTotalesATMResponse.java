
package com.davivienda.utilidades.ws.cliente.informeTotalesATM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for informeTotalesATMResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="informeTotalesATMResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://InformeTotalesATM.servicios.impl.procesadortransacciones.davivienda.com/}respuestaInformeTotalesATMDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "informeTotalesATMResponse_", propOrder = {
    "_return"
})
public class InformeTotalesATMResponse {

    @XmlElement(name = "return")
    protected RespuestaInformeTotalesATMDto _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaInformeTotalesATMDto }
     *     
     */
    public RespuestaInformeTotalesATMDto getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaInformeTotalesATMDto }
     *     
     */
    public void setReturn(RespuestaInformeTotalesATMDto value) {
        this._return = value;
    }

}
