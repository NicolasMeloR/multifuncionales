
package com.davivienda.utilidades.ws.cliente.autenticarUsuarioLdap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for autenticarUsuario complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="autenticarUsuario">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parameter" type="{http://servicio.autenticacionldap.davivienda.com/}autenticarUsuarioDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autenticarUsuario_type", propOrder = {
    "parameter"
})
public class AutenticarUsuario {

    protected AutenticarUsuarioDTO parameter;

    /**
     * Gets the value of the parameter property.
     * 
     * @return
     *     possible object is
     *     {@link AutenticarUsuarioDTO }
     *     
     */
    public AutenticarUsuarioDTO getParameter() {
        return parameter;
    }

    /**
     * Sets the value of the parameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutenticarUsuarioDTO }
     *     
     */
    public void setParameter(AutenticarUsuarioDTO value) {
        this.parameter = value;
    }

}
