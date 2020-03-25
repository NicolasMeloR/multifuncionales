
package com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaIngresoGiroChqDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaIngresoGiroChqDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/}respuestaDeTransaccionBaseDTO">
 *       &lt;sequence>
 *         &lt;element name="concepto" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="oficina" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="referencia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="talon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "respuestaIngresoGiroChqDto", propOrder = {
    "concepto",
    "oficina",
    "referencia",
    "talon",
    "valor"
})
public class RespuestaIngresoGiroChqDto
    extends RespuestaDeTransaccionBaseDTO
{

    protected Short concepto;
    protected Short oficina;
    protected Long referencia;
    protected String talon;
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
     * Gets the value of the oficina property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getOficina() {
        return oficina;
    }

    /**
     * Sets the value of the oficina property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setOficina(Short value) {
        this.oficina = value;
    }

    /**
     * Gets the value of the referencia property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getReferencia() {
        return referencia;
    }

    /**
     * Sets the value of the referencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setReferencia(Long value) {
        this.referencia = value;
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
