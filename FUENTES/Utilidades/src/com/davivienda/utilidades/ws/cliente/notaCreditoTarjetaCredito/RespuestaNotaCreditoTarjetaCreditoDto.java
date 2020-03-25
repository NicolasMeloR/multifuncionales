
package com.davivienda.utilidades.ws.cliente.notaCreditoTarjetaCredito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaNotaCreditoTarjetaCreditoDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaNotaCreditoTarjetaCreditoDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://notacreditotarjetacreditointerface.procesadortransacciones.davivienda.com/}respuestaDeTransaccionBaseDTO">
 *       &lt;sequence>
 *         &lt;element name="indicadorC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indicadorP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oficina" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="talon" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="valorNotaCredito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaNotaCreditoTarjetaCreditoDto", propOrder = {
    "indicadorC",
    "indicadorP",
    "oficina",
    "talon",
    "valorNotaCredito"
})
public class RespuestaNotaCreditoTarjetaCreditoDto
    extends RespuestaDeTransaccionBaseDTO
{

    protected String indicadorC;
    protected String indicadorP;
    protected Short oficina;
    protected Integer talon;
    protected String valorNotaCredito;

    /**
     * Gets the value of the indicadorC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndicadorC() {
        return indicadorC;
    }

    /**
     * Sets the value of the indicadorC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndicadorC(String value) {
        this.indicadorC = value;
    }

    /**
     * Gets the value of the indicadorP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndicadorP() {
        return indicadorP;
    }

    /**
     * Sets the value of the indicadorP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndicadorP(String value) {
        this.indicadorP = value;
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
     * Gets the value of the valorNotaCredito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValorNotaCredito() {
        return valorNotaCredito;
    }

    /**
     * Sets the value of the valorNotaCredito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValorNotaCredito(String value) {
        this.valorNotaCredito = value;
    }

}
