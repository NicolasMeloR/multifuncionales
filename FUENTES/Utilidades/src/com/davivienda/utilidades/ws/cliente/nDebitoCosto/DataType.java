
package com.davivienda.utilidades.ws.cliente.nDebitoCosto;

import java.math.BigDecimal;
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
 *         &lt;element name="tipoNotaDebito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="origen" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="cuenta" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="talon" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="motivoDebito" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="codigoDelBanco" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="motivoDevolucionDebito" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="fechaDebito" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="valorDelDebito" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="cuentaChequeDebito" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "DataType", propOrder = {
    "tipoNotaDebito",
    "origen",
    "cuenta",
    "talon",
    "motivoDebito",
    "codigoDelBanco",
    "motivoDevolucionDebito",
    "fechaDebito",
    "valorDelDebito",
    "cuentaChequeDebito",
    "referencia1",
    "referencia2"
})
public class DataType {

    @XmlElement(required = true)
    protected String tipoNotaDebito;
    protected short origen;
    protected long cuenta;
    protected int talon;
    protected short motivoDebito;
    protected short codigoDelBanco;
    protected short motivoDevolucionDebito;
    protected int fechaDebito;
    @XmlElement(required = true)
    protected BigDecimal valorDelDebito;
    @XmlElement(required = true)
    protected String cuentaChequeDebito;
    @XmlElement(required = true)
    protected String referencia1;
    @XmlElement(required = true)
    protected String referencia2;

    /**
     * Gets the value of the tipoNotaDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoNotaDebito() {
        return tipoNotaDebito;
    }

    /**
     * Sets the value of the tipoNotaDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoNotaDebito(String value) {
        this.tipoNotaDebito = value;
    }

    /**
     * Gets the value of the origen property.
     * 
     */
    public short getOrigen() {
        return origen;
    }

    /**
     * Sets the value of the origen property.
     * 
     */
    public void setOrigen(short value) {
        this.origen = value;
    }

    /**
     * Gets the value of the cuenta property.
     * 
     */
    public long getCuenta() {
        return cuenta;
    }

    /**
     * Sets the value of the cuenta property.
     * 
     */
    public void setCuenta(long value) {
        this.cuenta = value;
    }

    /**
     * Gets the value of the talon property.
     * 
     */
    public int getTalon() {
        return talon;
    }

    /**
     * Sets the value of the talon property.
     * 
     */
    public void setTalon(int value) {
        this.talon = value;
    }

    /**
     * Gets the value of the motivoDebito property.
     * 
     */
    public short getMotivoDebito() {
        return motivoDebito;
    }

    /**
     * Sets the value of the motivoDebito property.
     * 
     */
    public void setMotivoDebito(short value) {
        this.motivoDebito = value;
    }

    /**
     * Gets the value of the codigoDelBanco property.
     * 
     */
    public short getCodigoDelBanco() {
        return codigoDelBanco;
    }

    /**
     * Sets the value of the codigoDelBanco property.
     * 
     */
    public void setCodigoDelBanco(short value) {
        this.codigoDelBanco = value;
    }

    /**
     * Gets the value of the motivoDevolucionDebito property.
     * 
     */
    public short getMotivoDevolucionDebito() {
        return motivoDevolucionDebito;
    }

    /**
     * Sets the value of the motivoDevolucionDebito property.
     * 
     */
    public void setMotivoDevolucionDebito(short value) {
        this.motivoDevolucionDebito = value;
    }

    /**
     * Gets the value of the fechaDebito property.
     * 
     */
    public int getFechaDebito() {
        return fechaDebito;
    }

    /**
     * Sets the value of the fechaDebito property.
     * 
     */
    public void setFechaDebito(int value) {
        this.fechaDebito = value;
    }

    /**
     * Gets the value of the valorDelDebito property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorDelDebito() {
        return valorDelDebito;
    }

    /**
     * Sets the value of the valorDelDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorDelDebito(BigDecimal value) {
        this.valorDelDebito = value;
    }

    /**
     * Gets the value of the cuentaChequeDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuentaChequeDebito() {
        return cuentaChequeDebito;
    }

    /**
     * Sets the value of the cuentaChequeDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuentaChequeDebito(String value) {
        this.cuentaChequeDebito = value;
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
