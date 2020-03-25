
package com.davivienda.utilidades.ws.cliente.consultaTotalesMultifuncional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consultarTotalesMultifuncionalResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consultarTotalesMultifuncionalResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://ConsultaTotalesMultifuncional.servicios.procesadortransacciones.davivienda.com/}respuestaConsultaTotalesMultifuncionalDto" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarTotalesMultifuncionalResponse", propOrder = {
    "_return"
})
public class ConsultarTotalesMultifuncionalResponse {

    @XmlElement(name = "return", namespace = "http://ConsultaTotalesMultifuncional.servicios.procesadortransacciones.davivienda.com/")
    protected RespuestaConsultaTotalesMultifuncionalDto _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaConsultaTotalesMultifuncionalDto }
     *     
     */
    public RespuestaConsultaTotalesMultifuncionalDto getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaConsultaTotalesMultifuncionalDto }
     *     
     */
    public void setReturn(RespuestaConsultaTotalesMultifuncionalDto value) {
        this._return = value;
    }

}
