
package com.davivienda.utilidades.ws.cliente.resumenIDOATM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resumenIDOATM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resumenIDOATM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parameter" type="{http://ResumenIDOATM.servicios.impl.procesadortransacciones.davivienda.com/}resumenIDOATMDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resumenIDOATM_Type", propOrder = {
    "parameter"
})
public class ResumenIDOATM_Type {

    protected ResumenIDOATMDto parameter;

    /**
     * Gets the value of the parameter property.
     * 
     * @return
     *     possible object is
     *     {@link ResumenIDOATMDto }
     *     
     */
    public ResumenIDOATMDto getParameter() {
        return parameter;
    }

    /**
     * Sets the value of the parameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResumenIDOATMDto }
     *     
     */
    public void setParameter(ResumenIDOATMDto value) {
        this.parameter = value;
    }

}
