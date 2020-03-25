
package com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for otrosIngresos complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="otrosIngresos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parameter" type="{http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/}ingresoGiroChqDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "otrosIngresos", propOrder = {
    "parameter"
})
public class OtrosIngresos {

    protected IngresoGiroChqDto parameter;

    /**
     * Gets the value of the parameter property.
     * 
     * @return
     *     possible object is
     *     {@link IngresoGiroChqDto }
     *     
     */
    public IngresoGiroChqDto getParameter() {
        return parameter;
    }

    /**
     * Sets the value of the parameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link IngresoGiroChqDto }
     *     
     */
    public void setParameter(IngresoGiroChqDto value) {
        this.parameter = value;
    }

}
