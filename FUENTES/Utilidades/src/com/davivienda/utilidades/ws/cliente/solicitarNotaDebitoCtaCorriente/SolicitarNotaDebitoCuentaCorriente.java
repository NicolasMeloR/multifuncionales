
package com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaCorriente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for solicitarNotaDebitoCuentaCorriente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solicitarNotaDebitoCuentaCorriente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SolicitudNotaDebitoCuentaCorrienteDto_1" type="{http://servicios.davivienda.com/solicitarNotaDebitoCuentaCorrienteTypes}SolicitudNotaDebitoCuentaCorrienteDto"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitarNotaDebitoCuentaCorriente_", propOrder = {
    "solicitudNotaDebitoCuentaCorrienteDto1"
})
public class SolicitarNotaDebitoCuentaCorriente {

    @XmlElement(name = "SolicitudNotaDebitoCuentaCorrienteDto_1", required = true, nillable = true)
    protected SolicitudNotaDebitoCuentaCorrienteDto solicitudNotaDebitoCuentaCorrienteDto1;

    /**
     * Gets the value of the solicitudNotaDebitoCuentaCorrienteDto1 property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudNotaDebitoCuentaCorrienteDto }
     *     
     */
    public SolicitudNotaDebitoCuentaCorrienteDto getSolicitudNotaDebitoCuentaCorrienteDto1() {
        return solicitudNotaDebitoCuentaCorrienteDto1;
    }

    /**
     * Sets the value of the solicitudNotaDebitoCuentaCorrienteDto1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudNotaDebitoCuentaCorrienteDto }
     *     
     */
    public void setSolicitudNotaDebitoCuentaCorrienteDto1(SolicitudNotaDebitoCuentaCorrienteDto value) {
        this.solicitudNotaDebitoCuentaCorrienteDto1 = value;
    }

}
