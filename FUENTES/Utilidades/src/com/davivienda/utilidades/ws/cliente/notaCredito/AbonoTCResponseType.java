
package com.davivienda.utilidades.ws.cliente.notaCredito;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AbonoTCResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbonoTCResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oficinaCredito" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="talonCredito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indicadorPin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indicadorC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="titulo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valorEfectivo" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="valorCheque" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbonoTCResponseType", propOrder = {
    "oficinaCredito",
    "talonCredito",
    "indicadorPin",
    "indicadorC",
    "titulo",
    "valorEfectivo",
    "valorCheque"
})
public class AbonoTCResponseType {

    protected int oficinaCredito;
    @XmlElement(required = true)
    protected String talonCredito;
    @XmlElement(required = true)
    protected String indicadorPin;
    @XmlElement(required = true)
    protected String indicadorC;
    @XmlElement(required = true)
    protected String titulo;
    @XmlElement(required = true)
    protected BigDecimal valorEfectivo;
    @XmlElement(required = true)
    protected BigDecimal valorCheque;

    /**
     * Gets the value of the oficinaCredito property.
     * 
     */
    public int getOficinaCredito() {
        return oficinaCredito;
    }

    /**
     * Sets the value of the oficinaCredito property.
     * 
     */
    public void setOficinaCredito(int value) {
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
     * Gets the value of the indicadorC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndicadorC() {
        return indicadorC;
    }

    /**
     * Sets the value of the indicadorC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndicadorC(String value) {
        this.indicadorC = value;
    }

    /**
     * Gets the value of the titulo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Sets the value of the titulo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitulo(String value) {
        this.titulo = value;
    }

    /**
     * Gets the value of the valorEfectivo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorEfectivo() {
        return valorEfectivo;
    }

    /**
     * Sets the value of the valorEfectivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorEfectivo(BigDecimal value) {
        this.valorEfectivo = value;
    }

    /**
     * Gets the value of the valorCheque property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorCheque() {
        return valorCheque;
    }

    /**
     * Sets the value of the valorCheque property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorCheque(BigDecimal value) {
        this.valorCheque = value;
    }

}
