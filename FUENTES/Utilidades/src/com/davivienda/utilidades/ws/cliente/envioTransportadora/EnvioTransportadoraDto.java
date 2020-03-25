
package com.davivienda.utilidades.ws.cliente.envioTransportadora;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for envioTransportadoraDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="envioTransportadoraDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enviotransportadorainterface.servicios.procesadortransacciones.davivienda.com/}parametrosDeTransaccionBaseDTO">
 *       &lt;sequence>
 *         &lt;element name="cantidadRegistros" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="codigoTransportadora" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="indicadorTransaccion" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="registrosRepetitivos" type="{http://enviotransportadorainterface.servicios.procesadortransacciones.davivienda.com/}repetitivoDisminucionProvisionDto" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "envioTransportadoraDto", propOrder = {
    "cantidadRegistros",
    "codigoTransportadora",
    "indicadorTransaccion",
    "registrosRepetitivos",
    "talon",
    "valorEfectivo"
})
public class EnvioTransportadoraDto
    extends ParametrosDeTransaccionBaseDTO
{

    protected Short cantidadRegistros;
    protected Long codigoTransportadora;
    protected Short indicadorTransaccion;
    @XmlElement(nillable = true)
    protected List<RepetitivoDisminucionProvisionDto> registrosRepetitivos;
    protected Integer talon;
    protected BigDecimal valorEfectivo;

    /**
     * Gets the value of the cantidadRegistros property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCantidadRegistros() {
        return cantidadRegistros;
    }

    /**
     * Sets the value of the cantidadRegistros property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCantidadRegistros(Short value) {
        this.cantidadRegistros = value;
    }

    /**
     * Gets the value of the codigoTransportadora property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoTransportadora() {
        return codigoTransportadora;
    }

    /**
     * Sets the value of the codigoTransportadora property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoTransportadora(Long value) {
        this.codigoTransportadora = value;
    }

    /**
     * Gets the value of the indicadorTransaccion property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getIndicadorTransaccion() {
        return indicadorTransaccion;
    }

    /**
     * Sets the value of the indicadorTransaccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setIndicadorTransaccion(Short value) {
        this.indicadorTransaccion = value;
    }

    /**
     * Gets the value of the registrosRepetitivos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the registrosRepetitivos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegistrosRepetitivos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RepetitivoDisminucionProvisionDto }
     * 
     * 
     */
    public List<RepetitivoDisminucionProvisionDto> getRegistrosRepetitivos() {
        if (registrosRepetitivos == null) {
            registrosRepetitivos = new ArrayList<RepetitivoDisminucionProvisionDto>();
        }
        return this.registrosRepetitivos;
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
