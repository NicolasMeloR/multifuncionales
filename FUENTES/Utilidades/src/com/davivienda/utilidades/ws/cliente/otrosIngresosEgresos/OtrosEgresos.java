
package com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for otrosEgresos complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="otrosEgresos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parameter" type="{http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/}anulacionChequesGdosDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "otrosEgresos", propOrder = {
    "parameter"
})
public class OtrosEgresos {

    protected AnulacionChequesGdosDto parameter;

    /**
     * Gets the value of the parameter property.
     * 
     * @return
     *     possible object is
     *     {@link AnulacionChequesGdosDto }
     *     
     */
    public AnulacionChequesGdosDto getParameter() {
        return parameter;
    }

    /**
     * Sets the value of the parameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnulacionChequesGdosDto }
     *     
     */
    public void setParameter(AnulacionChequesGdosDto value) {
        this.parameter = value;
    }

}
