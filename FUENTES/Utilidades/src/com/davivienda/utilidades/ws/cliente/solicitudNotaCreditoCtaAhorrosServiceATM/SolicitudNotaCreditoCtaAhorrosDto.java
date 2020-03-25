
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorrosServiceATM;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for solicitudNotaCreditoCtaAhorrosDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solicitudNotaCreditoCtaAhorrosDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://solicitudnotacreditoctaahorrosinterface.servicios.procesadortransacciones.davivienda.com/}parametrosDeTransaccionBaseDTO">
 *       &lt;sequence>
 *         &lt;element name="codigoBanco" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="concepto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cuentaChequera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaAplicacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="motivoDevoluncionCheque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroCuentaCheque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oficinaRecaudo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="origenFondos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referencia1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="talon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valorNotaCredito" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudNotaCreditoCtaAhorrosDto", propOrder = {
    "codigoBanco",
    "concepto",
    "cuentaChequera",
    "fechaAplicacion",
    "motivoDevoluncionCheque",
    "numeroCuentaCheque",
    "oficinaRecaudo",
    "origenFondos",
    "referencia1",
    "talon",
    "valorNotaCredito"
})
public class SolicitudNotaCreditoCtaAhorrosDto
    extends ParametrosDeTransaccionBaseDTO
{

    protected Short codigoBanco;
    protected String concepto;
    protected String cuentaChequera;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaAplicacion;
    protected String motivoDevoluncionCheque;
    protected String numeroCuentaCheque;
    protected String oficinaRecaudo;
    protected String origenFondos;
    protected String referencia1;
    protected String talon;
    protected BigDecimal valorNotaCredito;

    /**
     * Gets the value of the codigoBanco property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCodigoBanco() {
        return codigoBanco;
    }

    /**
     * Sets the value of the codigoBanco property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCodigoBanco(Short value) {
        this.codigoBanco = value;
    }

    /**
     * Gets the value of the concepto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * Sets the value of the concepto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConcepto(String value) {
        this.concepto = value;
    }

    /**
     * Gets the value of the cuentaChequera property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuentaChequera() {
        return cuentaChequera;
    }

    /**
     * Sets the value of the cuentaChequera property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuentaChequera(String value) {
        this.cuentaChequera = value;
    }

    /**
     * Gets the value of the fechaAplicacion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaAplicacion() {
        return fechaAplicacion;
    }

    /**
     * Sets the value of the fechaAplicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaAplicacion(XMLGregorianCalendar value) {
        this.fechaAplicacion = value;
    }

    /**
     * Gets the value of the motivoDevoluncionCheque property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoDevoluncionCheque() {
        return motivoDevoluncionCheque;
    }

    /**
     * Sets the value of the motivoDevoluncionCheque property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoDevoluncionCheque(String value) {
        this.motivoDevoluncionCheque = value;
    }

    /**
     * Gets the value of the numeroCuentaCheque property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCuentaCheque() {
        return numeroCuentaCheque;
    }

    /**
     * Sets the value of the numeroCuentaCheque property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCuentaCheque(String value) {
        this.numeroCuentaCheque = value;
    }

    /**
     * Gets the value of the oficinaRecaudo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOficinaRecaudo() {
        return oficinaRecaudo;
    }

    /**
     * Sets the value of the oficinaRecaudo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOficinaRecaudo(String value) {
        this.oficinaRecaudo = value;
    }

    /**
     * Gets the value of the origenFondos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigenFondos() {
        return origenFondos;
    }

    /**
     * Sets the value of the origenFondos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigenFondos(String value) {
        this.origenFondos = value;
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
     * Gets the value of the talon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTalon() {
        return talon;
    }

    /**
     * Sets the value of the talon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTalon(String value) {
        this.talon = value;
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

}
