
package com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaAhorrosServiceATM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for solictarNotaDebitoCuentaAhorros complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solictarNotaDebitoCuentaAhorros">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dto" type="{http://solictarnotadebitocuentaahorrosserviceinteface.servicios.procesadortransacciones.davivienda.com/}solicitudNotaDebitoCuentaAhorrosDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solictarNotaDebitoCuentaAhorros", propOrder = {
    "dto"
})
public class SolictarNotaDebitoCuentaAhorros {

    protected SolicitudNotaDebitoCuentaAhorrosDto dto;

    /**
     * Gets the value of the dto property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudNotaDebitoCuentaAhorrosDto }
     *     
     */
    public SolicitudNotaDebitoCuentaAhorrosDto getDto() {
        return dto;
    }

    /**
     * Sets the value of the dto property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudNotaDebitoCuentaAhorrosDto }
     *     
     */
    public void setDto(SolicitudNotaDebitoCuentaAhorrosDto value) {
        this.dto = value;
    }

}
