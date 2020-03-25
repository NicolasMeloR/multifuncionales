
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorros;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParametrosSeguridadDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParametrosSeguridadDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="debeRevalidarToken" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="debeValidarSegundaClave" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="idSesionToken" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="usaToken" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParametrosSeguridadDTO", propOrder = {
    "debeRevalidarToken",
    "debeValidarSegundaClave",
    "idSesionToken",
    "usaToken"
})
public class ParametrosSeguridadDTO {

    protected boolean debeRevalidarToken;
    protected boolean debeValidarSegundaClave;
    @XmlElement(required = true, nillable = true)
    protected String idSesionToken;
    protected boolean usaToken;

    /**
     * Gets the value of the debeRevalidarToken property.
     * 
     */
    public boolean isDebeRevalidarToken() {
        return debeRevalidarToken;
    }

    /**
     * Sets the value of the debeRevalidarToken property.
     * 
     */
    public void setDebeRevalidarToken(boolean value) {
        this.debeRevalidarToken = value;
    }

    /**
     * Gets the value of the debeValidarSegundaClave property.
     * 
     */
    public boolean isDebeValidarSegundaClave() {
        return debeValidarSegundaClave;
    }

    /**
     * Sets the value of the debeValidarSegundaClave property.
     * 
     */
    public void setDebeValidarSegundaClave(boolean value) {
        this.debeValidarSegundaClave = value;
    }

    /**
     * Gets the value of the idSesionToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSesionToken() {
        return idSesionToken;
    }

    /**
     * Sets the value of the idSesionToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSesionToken(String value) {
        this.idSesionToken = value;
    }

    /**
     * Gets the value of the usaToken property.
     * 
     */
    public boolean isUsaToken() {
        return usaToken;
    }

    /**
     * Sets the value of the usaToken property.
     * 
     */
    public void setUsaToken(boolean value) {
        this.usaToken = value;
    }

}
