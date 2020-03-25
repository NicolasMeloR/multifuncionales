
package com.davivienda.utilidades.ws.cliente.autenticarUsuarioLdap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for grupoDaviviendaDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="grupoDaviviendaDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dc1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dc2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ou" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "grupoDaviviendaDTO", propOrder = {
    "cn",
    "dc1",
    "dc2",
    "ou"
})
public class GrupoDaviviendaDTO {

    protected String cn;
    protected String dc1;
    protected String dc2;
    protected String ou;

    /**
     * Gets the value of the cn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCn() {
        return cn;
    }

    /**
     * Sets the value of the cn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCn(String value) {
        this.cn = value;
    }

    /**
     * Gets the value of the dc1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDc1() {
        return dc1;
    }

    /**
     * Sets the value of the dc1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDc1(String value) {
        this.dc1 = value;
    }

    /**
     * Gets the value of the dc2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDc2() {
        return dc2;
    }

    /**
     * Sets the value of the dc2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDc2(String value) {
        this.dc2 = value;
    }

    /**
     * Gets the value of the ou property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOu() {
        return ou;
    }

    /**
     * Sets the value of the ou property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOu(String value) {
        this.ou = value;
    }

}
