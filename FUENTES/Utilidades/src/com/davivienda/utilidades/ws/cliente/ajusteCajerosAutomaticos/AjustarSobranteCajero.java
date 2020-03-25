
package com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ajustarSobranteCajero complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ajustarSobranteCajero">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parameter" type="{http://servicios.impl.procesadortransacciones.davivienda.com/}ajustarSobranteCajeroDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ajustarSobranteCajero", propOrder = {
    "parameter"
})
public class AjustarSobranteCajero {

    protected AjustarSobranteCajeroDto parameter;

    /**
     * Gets the value of the parameter property.
     * 
     * @return
     *     possible object is
     *     {@link AjustarSobranteCajeroDto }
     *     
     */
    public AjustarSobranteCajeroDto getParameter() {
        return parameter;
    }

    /**
     * Sets the value of the parameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link AjustarSobranteCajeroDto }
     *     
     */
    public void setParameter(AjustarSobranteCajeroDto value) {
        this.parameter = value;
    }

}
