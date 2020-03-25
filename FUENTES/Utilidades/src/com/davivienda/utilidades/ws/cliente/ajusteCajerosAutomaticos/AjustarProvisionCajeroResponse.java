
package com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ajustarProvisionCajeroResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ajustarProvisionCajeroResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://servicios.impl.procesadortransacciones.davivienda.com/}respuestaConsultaManejoEfectivoATMDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ajustarProvisionCajeroResponse", propOrder = {
    "_return"
})
public class AjustarProvisionCajeroResponse {

    @XmlElement(name = "return")
    protected RespuestaConsultaManejoEfectivoATMDto _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaConsultaManejoEfectivoATMDto }
     *     
     */
    public RespuestaConsultaManejoEfectivoATMDto getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaConsultaManejoEfectivoATMDto }
     *     
     */
    public void setReturn(RespuestaConsultaManejoEfectivoATMDto value) {
        this._return = value;
    }

}
