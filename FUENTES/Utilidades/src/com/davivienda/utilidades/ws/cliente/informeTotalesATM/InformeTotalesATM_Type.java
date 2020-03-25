
package com.davivienda.utilidades.ws.cliente.informeTotalesATM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for informeTotalesATM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="informeTotalesATM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parameter" type="{http://InformeTotalesATM.servicios.impl.procesadortransacciones.davivienda.com/}informeTotalesATMDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "informeTotalesATM_Type", propOrder = {
    "parameter"
})
public class InformeTotalesATM_Type {

    protected InformeTotalesATMDto parameter;

    /**
     * Gets the value of the parameter property.
     * 
     * @return
     *     possible object is
     *     {@link InformeTotalesATMDto }
     *     
     */
    public InformeTotalesATMDto getParameter() {
        return parameter;
    }

    /**
     * Sets the value of the parameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformeTotalesATMDto }
     *     
     */
    public void setParameter(InformeTotalesATMDto value) {
        this.parameter = value;
    }

}
