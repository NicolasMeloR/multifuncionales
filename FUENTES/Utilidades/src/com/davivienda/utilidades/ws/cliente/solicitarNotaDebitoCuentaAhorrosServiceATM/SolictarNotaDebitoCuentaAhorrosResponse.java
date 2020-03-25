
package com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaAhorrosServiceATM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for solictarNotaDebitoCuentaAhorrosResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solictarNotaDebitoCuentaAhorrosResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://solictarnotadebitocuentaahorrosserviceinteface.servicios.procesadortransacciones.davivienda.com/}respuestaNotaDebitoCuentaAhorrosDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solictarNotaDebitoCuentaAhorrosResponse", propOrder = {
    "_return"
})
public class SolictarNotaDebitoCuentaAhorrosResponse {

    @XmlElement(name = "return")
    protected RespuestaNotaDebitoCuentaAhorrosDto _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaNotaDebitoCuentaAhorrosDto }
     *     
     */
    public RespuestaNotaDebitoCuentaAhorrosDto getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaNotaDebitoCuentaAhorrosDto }
     *     
     */
    public void setReturn(RespuestaNotaDebitoCuentaAhorrosDto value) {
        this._return = value;
    }

}
