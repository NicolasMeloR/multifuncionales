
package com.davivienda.utilidades.ws.cliente.nDebitoCosto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DataHeader" type="{http://www.davivienda.com/xml/NotaDebitoCosto}DataHeaderType"/>
 *         &lt;element name="Data" type="{http://www.davivienda.com/xml/NotaDebitoCosto}DataType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestType", propOrder = {
    "dataHeader",
    "data"
})
public class RequestType {

    @XmlElement(name = "DataHeader", required = true)
    protected DataHeaderType dataHeader;
    @XmlElement(name = "Data", required = true)
    protected DataType data;

    /**
     * Gets the value of the dataHeader property.
     * 
     * @return
     *     possible object is
     *     {@link DataHeaderType }
     *     
     */
    public DataHeaderType getDataHeader() {
        return dataHeader;
    }

    /**
     * Sets the value of the dataHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHeaderType }
     *     
     */
    public void setDataHeader(DataHeaderType value) {
        this.dataHeader = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link DataType }
     *     
     */
    public DataType getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataType }
     *     
     */
    public void setData(DataType value) {
        this.data = value;
    }

}
