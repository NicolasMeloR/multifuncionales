
package com.davivienda.utilidades.ws.cliente.consultaTotalesMultifuncional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consultarTotalesMultifuncional complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consultarTotalesMultifuncional">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dto" type="{http://ConsultaTotalesMultifuncional.servicios.procesadortransacciones.davivienda.com/}consultaTotalesMultifuncionalDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarTotalesMultifuncional", propOrder = {
    "dto"
})
public class ConsultarTotalesMultifuncional {

    protected ConsultaTotalesMultifuncionalDto dto;

    /**
     * Gets the value of the dto property.
     * 
     * @return
     *     possible object is
     *     {@link ConsultaTotalesMultifuncionalDto }
     *     
     */
    public ConsultaTotalesMultifuncionalDto getDto() {
        return dto;
    }

    /**
     * Sets the value of the dto property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsultaTotalesMultifuncionalDto }
     *     
     */
    public void setDto(ConsultaTotalesMultifuncionalDto value) {
        this.dto = value;
    }

}
