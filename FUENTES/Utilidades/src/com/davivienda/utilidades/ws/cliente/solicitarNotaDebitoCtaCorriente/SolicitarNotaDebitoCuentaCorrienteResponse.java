
package com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaCorriente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for solicitarNotaDebitoCuentaCorrienteResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solicitarNotaDebitoCuentaCorrienteResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="result" type="{http://servicios.davivienda.com/solicitarNotaDebitoCuentaCorrienteTypes}RespuestaNotaDebitoCuentaCorrienteDto"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitarNotaDebitoCuentaCorrienteResponse_", propOrder = {
    "result"
})
public class SolicitarNotaDebitoCuentaCorrienteResponse {

    @XmlElement(required = true, nillable = true)
    protected RespuestaNotaDebitoCuentaCorrienteDto result;

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaNotaDebitoCuentaCorrienteDto }
     *     
     */
    public RespuestaNotaDebitoCuentaCorrienteDto getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaNotaDebitoCuentaCorrienteDto }
     *     
     */
    public void setResult(RespuestaNotaDebitoCuentaCorrienteDto value) {
        this.result = value;
    }

}
