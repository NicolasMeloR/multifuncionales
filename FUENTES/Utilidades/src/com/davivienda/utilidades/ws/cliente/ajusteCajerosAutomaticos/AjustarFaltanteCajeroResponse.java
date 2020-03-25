
package com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ajustarFaltanteCajeroResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ajustarFaltanteCajeroResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://servicios.impl.procesadortransacciones.davivienda.com/}respAjustarSobranteCajeroDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ajustarFaltanteCajeroResponse", propOrder = {
    "_return"
})
public class AjustarFaltanteCajeroResponse {

    @XmlElement(name = "return")
    protected RespAjustarSobranteCajeroDto _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespAjustarSobranteCajeroDto }
     *     
     */
    public RespAjustarSobranteCajeroDto getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespAjustarSobranteCajeroDto }
     *     
     */
    public void setReturn(RespAjustarSobranteCajeroDto value) {
        this._return = value;
    }

}
