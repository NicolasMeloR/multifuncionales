
package com.davivienda.utilidades.ws.cliente.notaCredito;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AbonoFMResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbonoFMResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cuentaCredito" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="tipoNit" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="nit" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="oficinaCredito" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="talonCredito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="valorTotal" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
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
@XmlType(name = "AbonoFMResponseType", propOrder = {
    "cuentaCredito",
    "tipoNit",
    "nit",
    "oficinaCredito",
    "talonCredito",
    "fecha",
    "valorTotal",
    "valorEfectivo",
    "valorCheque"
})
public class AbonoFMResponseType {

    protected long cuentaCredito;
    protected short tipoNit;
    protected long nit;
    protected int oficinaCredito;
    @XmlElement(required = true)
    protected String talonCredito;
    protected int fecha;
    @XmlElement(required = true)
    protected BigDecimal valorTotal;
    @XmlElement(required = true)
    protected BigDecimal valorEfectivo;
    @XmlElement(required = true)
    protected BigDecimal valorCheque;

    /**
     * Gets the value of the cuentaCredito property.
     * 
     */
    public long getCuentaCredito() {
        return cuentaCredito;
    }

    /**
     * Sets the value of the cuentaCredito property.
     * 
     */
    public void setCuentaCredito(long value) {
        this.cuentaCredito = value;
    }

    /**
     * Gets the value of the tipoNit property.
     * 
     */
    public short getTipoNit() {
        return tipoNit;
    }

    /**
     * Sets the value of the tipoNit property.
     * 
     */
    public void setTipoNit(short value) {
        this.tipoNit = value;
    }

    /**
     * Gets the value of the nit property.
     * 
     */
    public long getNit() {
        return nit;
    }

    /**
     * Sets the value of the nit property.
     * 
     */
    public void setNit(long value) {
        this.nit = value;
    }

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
     * Gets the value of the fecha property.
     * 
     */
    public int getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     * 
     */
    public void setFecha(int value) {
        this.fecha = value;
    }

    /**
     * Gets the value of the valorTotal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    /**
     * Sets the value of the valorTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorTotal(BigDecimal value) {
        this.valorTotal = value;
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
