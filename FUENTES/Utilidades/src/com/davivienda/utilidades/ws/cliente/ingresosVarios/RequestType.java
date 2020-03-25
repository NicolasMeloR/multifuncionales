
package com.davivienda.utilidades.ws.cliente.ingresosVarios;

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
 *         &lt;element name="DataHeader" type="{http://www.davivienda.com/xml/IngresosVarios}DataHeaderRequestType"/>
 *         &lt;element name="Data" type="{http://www.davivienda.com/xml/IngresosVarios}DataRequestType"/>
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
    protected DataHeaderRequestType dataHeader;
    @XmlElement(name = "Data", required = true)
    protected DataRequestType data;

    /**
     * Gets the value of the dataHeader property.
     * 
     * @return
     *     possible object is
     *     {@link DataHeaderRequestType }
     *     
     */
    public DataHeaderRequestType getDataHeader() {
        return dataHeader;
    }

    /**
     * Sets the value of the dataHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHeaderRequestType }
     *     
     */
    public void setDataHeader(DataHeaderRequestType value) {
        this.dataHeader = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link DataRequestType }
     *     
     */
    public DataRequestType getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataRequestType }
     *     
     */
    public void setData(DataRequestType value) {
        this.data = value;
    }

}
