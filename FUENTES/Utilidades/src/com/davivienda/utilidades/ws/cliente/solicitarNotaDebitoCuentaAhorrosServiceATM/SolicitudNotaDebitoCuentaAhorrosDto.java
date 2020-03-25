
package com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaAhorrosServiceATM;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for solicitudNotaDebitoCuentaAhorrosDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solicitudNotaDebitoCuentaAhorrosDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://solictarnotadebitocuentaahorrosserviceinteface.servicios.procesadortransacciones.davivienda.com/}parametrosDeTransaccionBaseDTO">
 *       &lt;sequence>
 *         &lt;element name="concepto" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="fechaMovimiento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="numeroCuentaAhorros" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="origenFondos" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="referenciaDos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenciaUno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secuencialRegistro" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudNotaDebitoCuentaAhorrosDto", propOrder = {
    "concepto",
    "fechaMovimiento",
    "numeroCuentaAhorros",
    "origenFondos",
    "referenciaDos",
    "referenciaUno",
    "secuencialRegistro",
    "valor"
})
public class SolicitudNotaDebitoCuentaAhorrosDto
    extends ParametrosDeTransaccionBaseDTO
{

    protected Short concepto;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaMovimiento;
    protected String numeroCuentaAhorros;
    protected Short origenFondos;
    protected String referenciaDos;
    protected String referenciaUno;
    protected Integer secuencialRegistro;
    protected BigDecimal valor;

    /**
     * Gets the value of the concepto property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getConcepto() {
        return concepto;
    }

    /**
     * Sets the value of the concepto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setConcepto(Short value) {
        this.concepto = value;
    }

    /**
     * Gets the value of the fechaMovimiento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaMovimiento() {
        return fechaMovimiento;
    }

    /**
     * Sets the value of the fechaMovimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaMovimiento(XMLGregorianCalendar value) {
        this.fechaMovimiento = value;
    }

    /**
     * Gets the value of the numeroCuentaAhorros property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCuentaAhorros() {
        return numeroCuentaAhorros;
    }

    /**
     * Sets the value of the numeroCuentaAhorros property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCuentaAhorros(String value) {
        this.numeroCuentaAhorros = value;
    }

    /**
     * Gets the value of the origenFondos property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getOrigenFondos() {
        return origenFondos;
    }

    /**
     * Sets the value of the origenFondos property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setOrigenFondos(Short value) {
        this.origenFondos = value;
    }

    /**
     * Gets the value of the referenciaDos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenciaDos() {
        return referenciaDos;
    }

    /**
     * Sets the value of the referenciaDos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenciaDos(String value) {
        this.referenciaDos = value;
    }

    /**
     * Gets the value of the referenciaUno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenciaUno() {
        return referenciaUno;
    }

    /**
     * Sets the value of the referenciaUno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenciaUno(String value) {
        this.referenciaUno = value;
    }

    /**
     * Gets the value of the secuencialRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSecuencialRegistro() {
        return secuencialRegistro;
    }

    /**
     * Sets the value of the secuencialRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSecuencialRegistro(Integer value) {
        this.secuencialRegistro = value;
    }

    /**
     * Gets the value of the valor property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Sets the value of the valor property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValor(BigDecimal value) {
        this.valor = value;
    }

}
