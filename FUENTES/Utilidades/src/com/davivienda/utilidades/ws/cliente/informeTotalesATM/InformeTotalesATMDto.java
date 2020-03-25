
package com.davivienda.utilidades.ws.cliente.informeTotalesATM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for informeTotalesATMDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="informeTotalesATMDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://InformeTotalesATM.servicios.impl.procesadortransacciones.davivienda.com/}parametrosDeTransaccionBaseDTO">
 *       &lt;sequence>
 *         &lt;element name="estacionAtm" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "informeTotalesATMDto", propOrder = {
    "estacionAtm"
})
public class InformeTotalesATMDto
    extends ParametrosDeTransaccionBaseDTO
{

    protected Short estacionAtm;

    /**
     * Gets the value of the estacionAtm property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getEstacionAtm() {
        return estacionAtm;
    }

    /**
     * Sets the value of the estacionAtm property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setEstacionAtm(Short value) {
        this.estacionAtm = value;
    }

}
