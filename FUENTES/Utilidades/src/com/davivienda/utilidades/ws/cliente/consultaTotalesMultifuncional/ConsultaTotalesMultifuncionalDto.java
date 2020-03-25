
package com.davivienda.utilidades.ws.cliente.consultaTotalesMultifuncional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consultaTotalesMultifuncionalDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consultaTotalesMultifuncionalDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ConsultaTotalesMultifuncional.servicios.procesadortransacciones.davivienda.com/}parametrosDeTransaccionBaseDTO">
 *       &lt;sequence>
 *         &lt;element name="indicadorTotales" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="tipoConsulta" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaTotalesMultifuncionalDto", propOrder = {
    "indicadorTotales",
    "tipoConsulta"
})
public class ConsultaTotalesMultifuncionalDto
    extends ParametrosDeTransaccionBaseDTO
{

    protected Short indicadorTotales;
    protected Short tipoConsulta;

    /**
     * Gets the value of the indicadorTotales property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getIndicadorTotales() {
        return indicadorTotales;
    }

    /**
     * Sets the value of the indicadorTotales property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setIndicadorTotales(Short value) {
        this.indicadorTotales = value;
    }

    /**
     * Gets the value of the tipoConsulta property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getTipoConsulta() {
        return tipoConsulta;
    }

    /**
     * Sets the value of the tipoConsulta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setTipoConsulta(Short value) {
        this.tipoConsulta = value;
    }

}
