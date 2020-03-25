
package com.davivienda.utilidades.ws.cliente.notaCredito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProductoAhorro" type="{http://www.davivienda.com/xml/NotaCredito}ProductoAhorroResponseType" minOccurs="0"/>
 *         &lt;element name="ProductoCorriente" type="{http://www.davivienda.com/xml/NotaCredito}ProductoCorrienteResponseType" minOccurs="0"/>
 *         &lt;element name="AbonoTC" type="{http://www.davivienda.com/xml/NotaCredito}AbonoTCResponseType" minOccurs="0"/>
 *         &lt;element name="AbonoFM" type="{http://www.davivienda.com/xml/NotaCredito}AbonoFMResponseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataResponseType", propOrder = {
    "productoAhorro",
    "productoCorriente",
    "abonoTC",
    "abonoFM"
})
public class DataResponseType {

    @XmlElement(name = "ProductoAhorro")
    protected ProductoAhorroResponseType productoAhorro;
    @XmlElement(name = "ProductoCorriente")
    protected ProductoCorrienteResponseType productoCorriente;
    @XmlElement(name = "AbonoTC")
    protected AbonoTCResponseType abonoTC;
    @XmlElement(name = "AbonoFM")
    protected AbonoFMResponseType abonoFM;

    /**
     * Gets the value of the productoAhorro property.
     * 
     * @return
     *     possible object is
     *     {@link ProductoAhorroResponseType }
     *     
     */
    public ProductoAhorroResponseType getProductoAhorro() {
        return productoAhorro;
    }

    /**
     * Sets the value of the productoAhorro property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductoAhorroResponseType }
     *     
     */
    public void setProductoAhorro(ProductoAhorroResponseType value) {
        this.productoAhorro = value;
    }

    /**
     * Gets the value of the productoCorriente property.
     * 
     * @return
     *     possible object is
     *     {@link ProductoCorrienteResponseType }
     *     
     */
    public ProductoCorrienteResponseType getProductoCorriente() {
        return productoCorriente;
    }

    /**
     * Sets the value of the productoCorriente property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductoCorrienteResponseType }
     *     
     */
    public void setProductoCorriente(ProductoCorrienteResponseType value) {
        this.productoCorriente = value;
    }

    /**
     * Gets the value of the abonoTC property.
     * 
     * @return
     *     possible object is
     *     {@link AbonoTCResponseType }
     *     
     */
    public AbonoTCResponseType getAbonoTC() {
        return abonoTC;
    }

    /**
     * Sets the value of the abonoTC property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbonoTCResponseType }
     *     
     */
    public void setAbonoTC(AbonoTCResponseType value) {
        this.abonoTC = value;
    }

    /**
     * Gets the value of the abonoFM property.
     * 
     * @return
     *     possible object is
     *     {@link AbonoFMResponseType }
     *     
     */
    public AbonoFMResponseType getAbonoFM() {
        return abonoFM;
    }

    /**
     * Sets the value of the abonoFM property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbonoFMResponseType }
     *     
     */
    public void setAbonoFM(AbonoFMResponseType value) {
        this.abonoFM = value;
    }

}
