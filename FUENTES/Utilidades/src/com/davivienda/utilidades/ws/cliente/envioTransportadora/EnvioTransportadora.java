
package com.davivienda.utilidades.ws.cliente.envioTransportadora;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for envioTransportadora complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="envioTransportadora">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://enviotransportadorainterface.servicios.procesadortransacciones.davivienda.com/}envioTransportadoraDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "envioTransportadora_", propOrder = {
    "arg0"
})
public class EnvioTransportadora {

    protected EnvioTransportadoraDto arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link EnvioTransportadoraDto }
     *     
     */
    public EnvioTransportadoraDto getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnvioTransportadoraDto }
     *     
     */
    public void setArg0(EnvioTransportadoraDto value) {
        this.arg0 = value;
    }

}
