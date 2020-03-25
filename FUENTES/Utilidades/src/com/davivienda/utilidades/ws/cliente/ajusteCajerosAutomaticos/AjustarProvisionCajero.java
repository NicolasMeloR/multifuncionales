
package com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ajustarProvisionCajero complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ajustarProvisionCajero">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parameter" type="{http://servicios.impl.procesadortransacciones.davivienda.com/}consultaManejoEfectivoATMDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ajustarProvisionCajero", propOrder = {
    "parameter"
})
public class AjustarProvisionCajero {

    protected ConsultaManejoEfectivoATMDto parameter;

    /**
     * Gets the value of the parameter property.
     * 
     * @return
     *     possible object is
     *     {@link ConsultaManejoEfectivoATMDto }
     *     
     */
    public ConsultaManejoEfectivoATMDto getParameter() {
        return parameter;
    }

    /**
     * Sets the value of the parameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsultaManejoEfectivoATMDto }
     *     
     */
    public void setParameter(ConsultaManejoEfectivoATMDto value) {
        this.parameter = value;
    }

}
