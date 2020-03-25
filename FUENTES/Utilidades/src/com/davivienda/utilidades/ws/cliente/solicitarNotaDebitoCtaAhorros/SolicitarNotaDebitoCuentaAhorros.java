
package com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaAhorros;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for solicitarNotaDebitoCuentaAhorros complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solicitarNotaDebitoCuentaAhorros">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SolicitudNotaDebitoCuentaAhorrosDto_1" type="{http://servicios.davivienda.com/solicitarNotaDebitoCuentaAhorrosServiceTypes}SolicitudNotaDebitoCuentaAhorrosDto"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitarNotaDebitoCuentaAhorros_", propOrder = {
    "solicitudNotaDebitoCuentaAhorrosDto1"
})
public class SolicitarNotaDebitoCuentaAhorros {

    @XmlElement(name = "SolicitudNotaDebitoCuentaAhorrosDto_1", required = true, nillable = true)
    protected SolicitudNotaDebitoCuentaAhorrosDto solicitudNotaDebitoCuentaAhorrosDto1;

    /**
     * Gets the value of the solicitudNotaDebitoCuentaAhorrosDto1 property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudNotaDebitoCuentaAhorrosDto }
     *     
     */
    public SolicitudNotaDebitoCuentaAhorrosDto getSolicitudNotaDebitoCuentaAhorrosDto1() {
        return solicitudNotaDebitoCuentaAhorrosDto1;
    }

    /**
     * Sets the value of the solicitudNotaDebitoCuentaAhorrosDto1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudNotaDebitoCuentaAhorrosDto }
     *     
     */
    public void setSolicitudNotaDebitoCuentaAhorrosDto1(SolicitudNotaDebitoCuentaAhorrosDto value) {
        this.solicitudNotaDebitoCuentaAhorrosDto1 = value;
    }

}
