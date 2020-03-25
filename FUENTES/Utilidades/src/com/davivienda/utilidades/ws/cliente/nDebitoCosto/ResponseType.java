
package com.davivienda.utilidades.ws.cliente.nDebitoCosto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DataHeader" type="{http://www.davivienda.com/xml/NotaDebitoCosto}DataHeaderResponseType"/>
 *         &lt;element name="Data" type="{http://www.davivienda.com/xml/NotaDebitoCosto}DataResponseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseType", propOrder = {
    "dataHeader",
    "data"
})
public class ResponseType {

    @XmlElement(name = "DataHeader", required = true)
    protected DataHeaderResponseType dataHeader;
    @XmlElement(name = "Data")
    protected DataResponseType data;

    /**
     * Gets the value of the dataHeader property.
     * 
     * @return
     *     possible object is
     *     {@link DataHeaderResponseType }
     *     
     */
    public DataHeaderResponseType getDataHeader() {
        return dataHeader;
    }

    /**
     * Sets the value of the dataHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHeaderResponseType }
     *     
     */
    public void setDataHeader(DataHeaderResponseType value) {
        this.dataHeader = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link DataResponseType }
     *     
     */
    public DataResponseType getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataResponseType }
     *     
     */
    public void setData(DataResponseType value) {
        this.data = value;
    }

}
