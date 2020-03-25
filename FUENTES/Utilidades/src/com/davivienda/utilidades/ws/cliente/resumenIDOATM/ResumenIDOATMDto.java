
package com.davivienda.utilidades.ws.cliente.resumenIDOATM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resumenIDOATMDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resumenIDOATMDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ResumenIDOATM.servicios.impl.procesadortransacciones.davivienda.com/}parametrosDeTransaccionBaseDTO">
 *       &lt;sequence>
 *         &lt;element name="indicadorTotales" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resumenIDOATMDto", propOrder = {
    "indicadorTotales"
})
public class ResumenIDOATMDto
    extends ParametrosDeTransaccionBaseDTO
{

    protected Short indicadorTotales;

    /**
     * Gets the value of the indicadorTotales property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getIndicadorTotales() {
        return indicadorTotales;
    }

    /**
     * Sets the value of the indicadorTotales property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setIndicadorTotales(Short value) {
        this.indicadorTotales = value;
    }

}
