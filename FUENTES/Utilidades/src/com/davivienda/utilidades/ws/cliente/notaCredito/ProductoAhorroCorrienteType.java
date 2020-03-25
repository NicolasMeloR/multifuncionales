package com.davivienda.utilidades.ws.cliente.notaCredito;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ProductoAhorroCorrienteType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="ProductoAhorroCorrienteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="origenCredito" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="cuentaCredito" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="talonCredito" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="motivoCredito" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="codigoDelBanco" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="motivoDevolucionCredito" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="fechaCredito" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="valorCredito" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="cuentaChequeCredito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="referencia1" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="oficinaRecaudo" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="indicadorValidacionNit" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="nitOrigenCredito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="comprobanteCredito" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="indicadorRemesaCredito" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductoAhorroCorrienteType", propOrder = {
    "origenCredito",
    "cuentaCredito",
    "talonCredito",
    "motivoCredito",
    "codigoDelBanco",
    "motivoDevolucionCredito",
    "fechaCredito",
    "valorCredito",
    "cuentaChequeCredito",
    "referencia1",
    "oficinaRecaudo",
    "indicadorValidacionNit",
    "nitOrigenCredito",
    "comprobanteCredito",
    "indicadorRemesaCredito"
})
public class ProductoAhorroCorrienteType {

    protected short origenCredito;
    protected long cuentaCredito;
    protected int talonCredito;
    protected short motivoCredito;
    protected short codigoDelBanco;
    protected short motivoDevolucionCredito;
    protected int fechaCredito;
    @XmlElement(required = true)
    protected BigDecimal valorCredito;
    @XmlElement(required = true)
    protected String cuentaChequeCredito;
    protected long referencia1;
    protected short oficinaRecaudo;
    protected short indicadorValidacionNit;
    @XmlElement(required = true)
    protected String nitOrigenCredito;
    protected int comprobanteCredito;
    protected short indicadorRemesaCredito;

    /**
     * Gets the value of the origenCredito property.
     *
     */
    public short getOrigenCredito() {
        return origenCredito;
    }

    /**
     * Sets the value of the origenCredito property.
     *
     */
    public void setOrigenCredito(short value) {
        this.origenCredito = value;
    }

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
     * Gets the value of the talonCredito property.
     *
     */
    public int getTalonCredito() {
        return talonCredito;
    }

    /**
     * Sets the value of the talonCredito property.
     *
     */
    public void setTalonCredito(int value) {
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
     * Gets the value of the motivoDevolucionCredito property.
     *
     */
    public short getMotivoDevolucionCredito() {
        return motivoDevolucionCredito;
    }

    /**
     * Sets the value of the motivoDevolucionCredito property.
     *
     */
    public void setMotivoDevolucionCredito(short value) {
        this.motivoDevolucionCredito = value;
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
     * Gets the value of the valorCredito property.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getValorCredito() {
        return valorCredito;
    }

    /**
     * Sets the value of the valorCredito property.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setValorCredito(BigDecimal value) {
        this.valorCredito = value;
    }

    /**
     * Gets the value of the cuentaChequeCredito property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCuentaChequeCredito() {
        return cuentaChequeCredito;
    }

    /**
     * Sets the value of the cuentaChequeCredito property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCuentaChequeCredito(String value) {
        this.cuentaChequeCredito = value;
    }

    /**
     * Gets the value of the referencia1 property.
     *
     */
    public long getReferencia1() {
        return referencia1;
    }

    /**
     * Sets the value of the referencia1 property.
     *
     */
    public void setReferencia1(long value) {
        this.referencia1 = value;
    }

    /**
     * Gets the value of the oficinaRecaudo property.
     *
     */
    public short getOficinaRecaudo() {
        return oficinaRecaudo;
    }

    /**
     * Sets the value of the oficinaRecaudo property.
     *
     */
    public void setOficinaRecaudo(short value) {
        this.oficinaRecaudo = value;
    }

    /**
     * Gets the value of the indicadorValidacionNit property.
     *
     */
    public short getIndicadorValidacionNit() {
        return indicadorValidacionNit;
    }

    /**
     * Sets the value of the indicadorValidacionNit property.
     *
     */
    public void setIndicadorValidacionNit(short value) {
        this.indicadorValidacionNit = value;
    }

    /**
     * Gets the value of the nitOrigenCredito property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getNitOrigenCredito() {
        return nitOrigenCredito;
    }

    /**
     * Sets the value of the nitOrigenCredito property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setNitOrigenCredito(String value) {
        this.nitOrigenCredito = value;
    }

    /**
     * Gets the value of the comprobanteCredito property.
     *
     */
    public int getComprobanteCredito() {
        return comprobanteCredito;
    }

    /**
     * Sets the value of the comprobanteCredito property.
     *
     */
    public void setComprobanteCredito(int value) {
        this.comprobanteCredito = value;
    }

    /**
     * Gets the value of the indicadorRemesaCredito property.
     *
     */
    public short getIndicadorRemesaCredito() {
        return indicadorRemesaCredito;
    }

    /**
     * Sets the value of the indicadorRemesaCredito property.
     *
     */
    public void setIndicadorRemesaCredito(short value) {
        this.indicadorRemesaCredito = value;
    }

}
