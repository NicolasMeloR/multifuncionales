
package com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaAhorros;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for solicitarNotaDebitoCuentaAhorrosResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solicitarNotaDebitoCuentaAhorrosResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="result" type="{http://servicios.davivienda.com/solicitarNotaDebitoCuentaAhorrosServiceTypes}RespuestaNotaDebitoCuentaAhorrosDto"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitarNotaDebitoCuentaAhorrosResponse_", propOrder = {
    "result"
})
public class SolicitarNotaDebitoCuentaAhorrosResponse {

    @XmlElement(required = true, nillable = true)
    protected RespuestaNotaDebitoCuentaAhorrosDto result;

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaNotaDebitoCuentaAhorrosDto }
     *     
     */
    public RespuestaNotaDebitoCuentaAhorrosDto getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaNotaDebitoCuentaAhorrosDto }
     *     
     */
    public void setResult(RespuestaNotaDebitoCuentaAhorrosDto value) {
        this.result = value;
    }

}
