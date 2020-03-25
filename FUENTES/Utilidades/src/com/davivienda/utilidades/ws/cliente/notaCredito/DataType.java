
package com.davivienda.utilidades.ws.cliente.notaCredito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProductoAhorro" type="{http://www.davivienda.com/xml/NotaCredito}ProductoAhorroCorrienteType" minOccurs="0"/>
 *         &lt;element name="ProductoCorriente" type="{http://www.davivienda.com/xml/NotaCredito}ProductoAhorroCorrienteType" minOccurs="0"/>
 *         &lt;element name="AbonoTC" type="{http://www.davivienda.com/xml/NotaCredito}AbonoTCType" minOccurs="0"/>
 *         &lt;element name="AbonoFM" type="{http://www.davivienda.com/xml/NotaCredito}AbonoFMType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataType", propOrder = {
    "productoAhorro",
    "productoCorriente",
    "abonoTC",
    "abonoFM"
})
public class DataType {

    @XmlElement(name = "ProductoAhorro")
    protected ProductoAhorroCorrienteType productoAhorro;
    @XmlElement(name = "ProductoCorriente")
    protected ProductoAhorroCorrienteType productoCorriente;
    @XmlElement(name = "AbonoTC")
    protected AbonoTCType abonoTC;
    @XmlElement(name = "AbonoFM")
    protected AbonoFMType abonoFM;

    /**
     * Gets the value of the productoAhorro property.
     * 
     * @return
     *     possible object is
     *     {@link ProductoAhorroCorrienteType }
     *     
     */
    public ProductoAhorroCorrienteType getProductoAhorro() {
        return productoAhorro;
    }

    /**
     * Sets the value of the productoAhorro property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductoAhorroCorrienteType }
     *     
     */
    public void setProductoAhorro(ProductoAhorroCorrienteType value) {
        this.productoAhorro = value;
    }

    /**
     * Gets the value of the productoCorriente property.
     * 
     * @return
     *     possible object is
     *     {@link ProductoAhorroCorrienteType }
     *     
     */
    public ProductoAhorroCorrienteType getProductoCorriente() {
        return productoCorriente;
    }

    /**
     * Sets the value of the productoCorriente property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductoAhorroCorrienteType }
     *     
     */
    public void setProductoCorriente(ProductoAhorroCorrienteType value) {
        this.productoCorriente = value;
    }

    /**
     * Gets the value of the abonoTC property.
     * 
     * @return
     *     possible object is
     *     {@link AbonoTCType }
     *     
     */
    public AbonoTCType getAbonoTC() {
        return abonoTC;
    }

    /**
     * Sets the value of the abonoTC property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbonoTCType }
     *     
     */
    public void setAbonoTC(AbonoTCType value) {
        this.abonoTC = value;
    }

    /**
     * Gets the value of the abonoFM property.
     * 
     * @return
     *     possible object is
     *     {@link AbonoFMType }
     *     
     */
    public AbonoFMType getAbonoFM() {
        return abonoFM;
    }

    /**
     * Sets the value of the abonoFM property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbonoFMType }
     *     
     */
    public void setAbonoFM(AbonoFMType value) {
        this.abonoFM = value;
    }

}
