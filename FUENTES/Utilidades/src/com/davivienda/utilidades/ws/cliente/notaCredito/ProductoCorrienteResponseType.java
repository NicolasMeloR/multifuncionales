
package com.davivienda.utilidades.ws.cliente.notaCredito;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProductoCorrienteResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductoCorrienteResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oficinaCredito" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="talonCredito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="motivoCredito" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="fechaAplicacionCredito" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="valorNotaCredito" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="cuentaCorriente" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="referencia1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductoCorrienteResponseType", propOrder = {
    "oficinaCredito",
    "talonCredito",
    "motivoCredito",
    "fechaAplicacionCredito",
    "valorNotaCredito",
    "cuentaCorriente",
    "referencia1"
})
public class ProductoCorrienteResponseType {

    protected short oficinaCredito;
    @XmlElement(required = true)
    protected String talonCredito;
    protected short motivoCredito;
    protected int fechaAplicacionCredito;
    @XmlElement(required = true)
    protected BigDecimal valorNotaCredito;
    protected long cuentaCorriente;
    @XmlElement(required = true)
    protected String referencia1;

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
     * Gets the value of the motivoCredito property.
     * 
     */
    public short getMotivoCredito() {
        return motivoCredito;
    }

    /**
     * Sets the value of the motivoCredito property.
     * 
     */
    public void setMotivoCredito(short value) {
        this.motivoCredito = value;
    }

    /**
     * Gets the value of the fechaAplicacionCredito property.
     * 
     */
    public int getFechaAplicacionCredito() {
        return fechaAplicacionCredito;
    }

    /**
     * Sets the value of the fechaAplicacionCredito property.
     * 
     */
    public void setFechaAplicacionCredito(int value) {
        this.fechaAplicacionCredito = value;
    }

    /**
     * Gets the value of the valorNotaCredito property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorNotaCredito() {
        return valorNotaCredito;
    }

    /**
     * Sets the value of the valorNotaCredito property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorNotaCredito(BigDecimal value) {
        this.valorNotaCredito = value;
    }

    /**
     * Gets the value of the cuentaCorriente property.
     * 
     */
    public long getCuentaCorriente() {
        return cuentaCorriente;
    }

    /**
     * Sets the value of the cuentaCorriente property.
     * 
     */
    public void setCuentaCorriente(long value) {
        this.cuentaCorriente = value;
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

}
