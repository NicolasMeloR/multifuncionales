
package com.davivienda.utilidades.ws.cliente.autenticarUsuarioLdap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for autenticarUsuarioResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="autenticarUsuarioResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://servicio.autenticacionldap.davivienda.com/}respuestaAutenticarUsuarioDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autenticarUsuarioResponse_type", propOrder = {
    "_return"
})
public class AutenticarUsuarioResponse {

    @XmlElement(name = "return")
    protected RespuestaAutenticarUsuarioDTO _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaAutenticarUsuarioDTO }
     *     
     */
    public RespuestaAutenticarUsuarioDTO getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaAutenticarUsuarioDTO }
     *     
     */
    public void setReturn(RespuestaAutenticarUsuarioDTO value) {
        this._return = value;
    }

}
