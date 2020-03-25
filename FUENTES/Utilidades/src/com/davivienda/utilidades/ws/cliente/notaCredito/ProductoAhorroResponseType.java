
package com.davivienda.utilidades.ws.cliente.notaCredito;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProductoAhorroResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductoAhorroResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oficinaCredito" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="talonCredito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indicadorPin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indicadorTalonario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valorSalida" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="cuentaCredito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaCredito" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="referencia1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="referencia2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductoAhorroResponseType", propOrder = {
    "oficinaCredito",
    "talonCredito",
    "indicadorPin",
    "indicadorTalonario",
    "valorSalida",
    "cuentaCredito",
    "fechaCredito",
    "referencia1",
    "referencia2"
})
public class ProductoAhorroResponseType {

    protected short oficinaCredito;
    @XmlElement(required = true)
    protected String talonCredito;
    @XmlElement(required = true)
    protected String indicadorPin;
    @XmlElement(required = true)
    protected String indicadorTalonario;
    @XmlElement(required = true)
    protected BigDecimal valorSalida;
    @XmlElement(required = true)
    protected String cuentaCredito;
    protected int fechaCredito;
    @XmlElement(required = true)
    protected String referencia1;
    @XmlElement(required = true)
    protected String referencia2;

    /**
     * Gets the value of the oficinaCredito property.
     * 
     */
    public short getOficinaCredito() {
        return oficinaCredito;
    }

    /**
     * Sets the value of the oficinaCredito property.
     * 
     */
    public void setOficinaCredito(short value) {
        this.oficinaCredito = value;
    }

    /**
     * Gets the value of the talonCredito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTalonCredito() {
        return talonCredito;
    }

    /**
     * Sets the value of the talonCredito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTalonCredito(String value) {
        this.talonCredito = value;
    }

    /**
     * Gets the value of the indicadorPin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndicadorPin() {
        return indicadorPin;
    }

    /**
     * Sets the value of the indicadorPin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndicadorPin(String value) {
        this.indicadorPin = value;
    }

    /**
     * Gets the value of the indicadorTalonario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndicadorTalonario() {
        return indicadorTalonario;
    }

    /**
     * Sets the value of the indicadorTalonario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndicadorTalonario(String value) {
        this.indicadorTalonario = value;
    }

    /**
     * Gets the value of the valorSalida property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorSalida() {
        return valorSalida;
    }

    /**
     * Sets the value of the valorSalida property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorSalida(BigDecimal value) {
        this.valorSalida = value;
    }

    /**
     * Gets the value of the cuentaCredito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuentaCredito() {
        return cuentaCredito;
    }

    /**
     * Sets the value of the cuentaCredito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuentaCredito(String value) {
        this.cuentaCredito = value;
    }

    /**
     * Gets the value of the fechaCredito property.
     * 
     */
    public int getFechaCredito() {
        return fechaCredito;
    }

    /**
     * Sets the value of the fechaCredito property.
     * 
     */
    public void setFechaCredito(int value) {
        this.fechaCredito = value;
    }

    /**
     * Gets the value of the referencia1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencia1() {
        return referencia1;
    }

    /**
     * Sets the value of the referencia1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencia1(String value) {
        this.referencia1 = value;
    }

    /**
     * Gets the value of the referencia2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencia2() {
        return referencia2;
    }

    /**
     * Sets the value of the referencia2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencia2(String value) {
        this.referencia2 = value;
    }

}
