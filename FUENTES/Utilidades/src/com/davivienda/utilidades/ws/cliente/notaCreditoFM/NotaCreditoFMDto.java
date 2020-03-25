
package com.davivienda.utilidades.ws.cliente.notaCreditoFM;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for notaCreditoFMDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="notaCreditoFMDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://notacreditofminterface.procesadortransacciones.davivienda.com/}parametrosDeTransaccionBaseDTO">
 *       &lt;sequence>
 *         &lt;element name="concepto" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="credito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaAplicacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="talon" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="valorEfectivo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notaCreditoFMDto", propOrder = {
    "concepto",
    "credito",
    "fechaAplicacion",
    "talon",
    "valorEfectivo"
})
public class NotaCreditoFMDto
    extends ParametrosDeTransaccionBaseDTO
{

    protected Short concepto;
    protected String credito;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaAplicacion;
    protected Integer talon;
    protected BigDecimal valorEfectivo;

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
     * Gets the value of the credito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCredito() {
        return credito;
    }

    /**
     * Sets the value of the credito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCredito(String value) {
        this.credito = value;
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

}
