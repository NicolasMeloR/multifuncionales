
package com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaCorrienteServiceATM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for solictarNotaDebitoCuentaCorriente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solictarNotaDebitoCuentaCorriente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dto" type="{http://solictarnotadebitocuentacorrienteinterface.servicios.procesadortransacciones.davivienda.com/}solicitudNotaDebitoCuentaCorrienteDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solictarNotaDebitoCuentaCorriente", propOrder = {
    "dto"
})
public class SolictarNotaDebitoCuentaCorriente {

    protected SolicitudNotaDebitoCuentaCorrienteDto dto;

    /**
     * Gets the value of the dto property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudNotaDebitoCuentaCorrienteDto }
     *     
     */
    public SolicitudNotaDebitoCuentaCorrienteDto getDto() {
        return dto;
    }

    /**
     * Sets the value of the dto property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudNotaDebitoCuentaCorrienteDto }
     *     
     */
    public void setDto(SolicitudNotaDebitoCuentaCorrienteDto value) {
        this.dto = value;
    }

}
