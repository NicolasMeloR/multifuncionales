
package com.davivienda.utilidades.ws.cliente.nDebitoCosto;

import java.math.BigDecimal;
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
 *         &lt;element name="oficinaDebito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="talonDebito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indicadorPin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indicadorTalonario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tituloSalida" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valorSalida" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="cuentaDebito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaDebito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mensajeReferencia" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "DataResponseType", propOrder = {
    "oficinaDebito",
    "talonDebito",
    "indicadorPin",
    "indicadorTalonario",
    "tituloSalida",
    "valorSalida",
    "cuentaDebito",
    "fechaDebito",
    "mensajeReferencia",
    "referencia1",
    "referencia2"
})
public class DataResponseType {

    @XmlElement(required = true)
    protected String oficinaDebito;
    @XmlElement(required = true)
    protected String talonDebito;
    @XmlElement(required = true)
    protected String indicadorPin;
    @XmlElement(required = true)
    protected String indicadorTalonario;
    @XmlElement(required = true, nillable = true)
    protected String tituloSalida;
    @XmlElement(required = true)
    protected BigDecimal valorSalida;
    @XmlElement(required = true, nillable = true)
    protected String cuentaDebito;
    @XmlElement(required = true, nillable = true)
    protected String fechaDebito;
    @XmlElement(required = true, nillable = true)
    protected String mensajeReferencia;
    @XmlElement(required = true, nillable = true)
    protected String referencia1;
    @XmlElement(required = true, nillable = true)
    protected String referencia2;

    /**
     * Gets the value of the oficinaDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOficinaDebito() {
        return oficinaDebito;
    }

    /**
     * Sets the value of the oficinaDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOficinaDebito(String value) {
        this.oficinaDebito = value;
    }

    /**
     * Gets the value of the talonDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTalonDebito() {
        return talonDebito;
    }

    /**
     * Sets the value of the talonDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTalonDebito(String value) {
        this.talonDebito = value;
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
     * Gets the value of the tituloSalida property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTituloSalida() {
        return tituloSalida;
    }

    /**
     * Sets the value of the tituloSalida property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTituloSalida(String value) {
        this.tituloSalida = value;
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
     * Gets the value of the cuentaDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuentaDebito() {
        return cuentaDebito;
    }

    /**
     * Sets the value of the cuentaDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuentaDebito(String value) {
        this.cuentaDebito = value;
    }

    /**
     * Gets the value of the fechaDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaDebito() {
        return fechaDebito;
    }

    /**
     * Sets the value of the fechaDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaDebito(String value) {
        this.fechaDebito = value;
    }

    /**
     * Gets the value of the mensajeReferencia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensajeReferencia() {
        return mensajeReferencia;
    }

    /**
     * Sets the value of the mensajeReferencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensajeReferencia(String value) {
        this.mensajeReferencia = value;
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
