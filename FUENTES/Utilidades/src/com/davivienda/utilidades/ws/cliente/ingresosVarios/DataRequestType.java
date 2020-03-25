
package com.davivienda.utilidades.ws.cliente.ingresosVarios;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="concepto" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="talon" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="efectivo" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="cheques" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="valorTotal" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="referencia1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referencia2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referencia3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referencia4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referencia5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beneficiario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoBanco" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="numeroDeCheque" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="chequera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataRequestType", propOrder = {
    "concepto",
    "talon",
    "efectivo",
    "cheques",
    "valorTotal",
    "referencia1",
    "referencia2",
    "referencia3",
    "referencia4",
    "referencia5",
    "beneficiario",
    "codigoBanco",
    "numeroDeCheque",
    "chequera"
})
public class DataRequestType {

    protected short concepto;
    protected Integer talon;
    @XmlElement(required = true)
    protected BigDecimal efectivo;
    @XmlElement(required = true)
    protected BigDecimal cheques;
    @XmlElement(required = true)
    protected BigDecimal valorTotal;
    protected String referencia1;
    protected String referencia2;
    protected String referencia3;
    protected String referencia4;
    protected String referencia5;
    protected String beneficiario;
    protected short codigoBanco;
    protected int numeroDeCheque;
    protected String chequera;

    /**
     * Gets the value of the concepto property.
     * 
     */
    public short getConcepto() {
        return concepto;
    }

    /**
     * Sets the value of the concepto property.
     * 
     */
    public void setConcepto(short value) {
        this.concepto = value;
    }

    /**
     * Gets the value of the talon property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTalon() {
        return talon;
    }

    /**
     * Sets the value of the talon property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTalon(Integer value) {
        this.talon = value;
    }

    /**
     * Gets the value of the efectivo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEfectivo() {
        return efectivo;
    }

    /**
     * Sets the value of the efectivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEfectivo(BigDecimal value) {
        this.efectivo = value;
    }

    /**
     * Gets the value of the cheques property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCheques() {
        return cheques;
    }

    /**
     * Sets the value of the cheques property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCheques(BigDecimal value) {
        this.cheques = value;
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
     * Gets the value of the beneficiario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneficiario() {
        return beneficiario;
    }

    /**
     * Sets the value of the beneficiario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneficiario(String value) {
        this.beneficiario = value;
    }

    /**
     * Gets the value of the codigoBanco property.
     * 
     */
    public short getCodigoBanco() {
        return codigoBanco;
    }

    /**
     * Sets the value of the codigoBanco property.
     * 
     */
    public void setCodigoBanco(short value) {
        this.codigoBanco = value;
    }

    /**
     * Gets the value of the numeroDeCheque property.
     * 
     */
    public int getNumeroDeCheque() {
        return numeroDeCheque;
    }

    /**
     * Sets the value of the numeroDeCheque property.
     * 
     */
    public void setNumeroDeCheque(int value) {
        this.numeroDeCheque = value;
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

}
