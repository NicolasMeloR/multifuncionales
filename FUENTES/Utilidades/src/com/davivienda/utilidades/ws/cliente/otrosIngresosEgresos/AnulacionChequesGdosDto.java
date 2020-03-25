
package com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anulacionChequesGdosDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="anulacionChequesGdosDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/}parametrosDeTransaccionBaseDTO">
 *       &lt;sequence>
 *         &lt;element name="beneficiario" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="chequera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoBanco" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="concepto" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="numeroCheque" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="referencia1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referencia2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referencia3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referencia4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referencia5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="talon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valorCheques" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="valorEfectivo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="valorTotal" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "anulacionChequesGdosDto", propOrder = {
    "beneficiario",
    "chequera",
    "codigoBanco",
    "concepto",
    "numeroCheque",
    "referencia1",
    "referencia2",
    "referencia3",
    "referencia4",
    "referencia5",
    "talon",
    "valorCheques",
    "valorEfectivo",
    "valorTotal"
})
public class AnulacionChequesGdosDto
    extends ParametrosDeTransaccionBaseDTO
{

    protected Long beneficiario;
    protected String chequera;
    protected Long codigoBanco;
    protected Short concepto;
    protected Long numeroCheque;
    protected String referencia1;
    protected String referencia2;
    protected String referencia3;
    protected String referencia4;
    protected String referencia5;
    protected String talon;
    protected BigDecimal valorCheques;
    protected BigDecimal valorEfectivo;
    protected BigDecimal valorTotal;

    /**
     * Gets the value of the beneficiario property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBeneficiario() {
        return beneficiario;
    }

    /**
     * Sets the value of the beneficiario property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBeneficiario(Long value) {
        this.beneficiario = value;
    }

    /**
     * Gets the value of the chequera property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChequera() {
        return chequera;
    }

    /**
     * Sets the value of the chequera property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChequera(String value) {
        this.chequera = value;
    }

    /**
     * Gets the value of the codigoBanco property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoBanco() {
        return codigoBanco;
    }

    /**
     * Sets the value of the codigoBanco property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoBanco(Long value) {
        this.codigoBanco = value;
    }

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
     * Gets the value of the numeroCheque property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumeroCheque() {
        return numeroCheque;
    }

    /**
     * Sets the value of the numeroCheque property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumeroCheque(Long value) {
        this.numeroCheque = value;
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

    /**
     * Gets the value of the referencia3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencia3() {
        return referencia3;
    }

    /**
     * Sets the value of the referencia3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencia3(String value) {
        this.referencia3 = value;
    }

    /**
     * Gets the value of the referencia4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencia4() {
        return referencia4;
    }

    /**
     * Sets the value of the referencia4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencia4(String value) {
        this.referencia4 = value;
    }

    /**
     * Gets the value of the referencia5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencia5() {
        return referencia5;
    }

    /**
     * Sets the value of the referencia5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencia5(String value) {
        this.referencia5 = value;
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
     * Gets the value of the valorCheques property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorCheques() {
        return valorCheques;
    }

    /**
     * Sets the value of the valorCheques property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorCheques(BigDecimal value) {
        this.valorCheques = value;
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

}
