
package com.davivienda.utilidades.ws.cliente.ingresosVarios;

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
 *         &lt;element name="talon" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="oficina" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="concepto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="referencia" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="dataTrans" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "talon",
    "oficina",
    "concepto",
    "valor",
    "referencia",
    "dataTrans"
})
public class DataResponseType {

    protected int talon;
    protected int oficina;
    protected int concepto;
    @XmlElement(required = true)
    protected BigDecimal valor;
    protected long referencia;
    @XmlElement(required = true)
    protected String dataTrans;

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
     * Gets the value of the oficina property.
     * 
     */
    public int getOficina() {
        return oficina;
    }

    /**
     * Sets the value of the oficina property.
     * 
     */
    public void setOficina(int value) {
        this.oficina = value;
    }

    /**
     * Gets the value of the concepto property.
     * 
     */
    public int getConcepto() {
        return concepto;
    }

    /**
     * Sets the value of the concepto property.
     * 
     */
    public void setConcepto(int value) {
        this.concepto = value;
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

    /**
     * Gets the value of the referencia property.
     * 
     */
    public long getReferencia() {
        return referencia;
    }

    /**
     * Sets the value of the referencia property.
     * 
     */
    public void setReferencia(long value) {
        this.referencia = value;
    }

    /**
     * Gets the value of the dataTrans property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataTrans() {
        return dataTrans;
    }

    /**
     * Sets the value of the dataTrans property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataTrans(String value) {
        this.dataTrans = value;
    }

}
