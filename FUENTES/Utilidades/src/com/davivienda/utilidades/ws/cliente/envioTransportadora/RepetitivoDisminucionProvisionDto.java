
package com.davivienda.utilidades.ws.cliente.envioTransportadora;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for repetitivoDisminucionProvisionDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="repetitivoDisminucionProvisionDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="calidadBilletes" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="denominacion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="tipoMoneda" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="tipoUnidad" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "repetitivoDisminucionProvisionDto", propOrder = {
    "calidadBilletes",
    "cantidad",
    "denominacion",
    "tipoMoneda",
    "tipoUnidad"
})
public class RepetitivoDisminucionProvisionDto {

    protected Short calidadBilletes;
    protected Integer cantidad;
    protected Long denominacion;
    protected Short tipoMoneda;
    protected Short tipoUnidad;

    /**
     * Gets the value of the calidadBilletes property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCalidadBilletes() {
        return calidadBilletes;
    }

    /**
     * Sets the value of the calidadBilletes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCalidadBilletes(Short value) {
        this.calidadBilletes = value;
    }

    /**
     * Gets the value of the cantidad property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * Sets the value of the cantidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCantidad(Integer value) {
        this.cantidad = value;
    }

    /**
     * Gets the value of the denominacion property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDenominacion() {
        return denominacion;
    }

    /**
     * Sets the value of the denominacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDenominacion(Long value) {
        this.denominacion = value;
    }

    /**
     * Gets the value of the tipoMoneda property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getTipoMoneda() {
        return tipoMoneda;
    }

    /**
     * Sets the value of the tipoMoneda property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setTipoMoneda(Short value) {
        this.tipoMoneda = value;
    }

    /**
     * Gets the value of the tipoUnidad property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getTipoUnidad() {
        return tipoUnidad;
    }

    /**
     * Sets the value of the tipoUnidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setTipoUnidad(Short value) {
        this.tipoUnidad = value;
    }

}
