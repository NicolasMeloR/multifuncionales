
package com.davivienda.utilidades.ws.cliente.notaCredito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataHeaderResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataHeaderResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nombreOperacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="caracterAceptacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ultimoMensaje" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="codMsgRespuesta" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="msgRespuesta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataHeaderResponseType", propOrder = {
    "nombreOperacion",
    "total",
    "caracterAceptacion",
    "ultimoMensaje",
    "codMsgRespuesta",
    "msgRespuesta"
})
public class DataHeaderResponseType {

    @XmlElement(required = true)
    protected String nombreOperacion;
    protected int total;
    @XmlElement(required = true)
    protected String caracterAceptacion;
    protected short ultimoMensaje;
    protected short codMsgRespuesta;
    protected String msgRespuesta;

    /**
     * Gets the value of the nombreOperacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreOperacion() {
        return nombreOperacion;
    }

    /**
     * Sets the value of the nombreOperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreOperacion(String value) {
        this.nombreOperacion = value;
    }

    /**
     * Gets the value of the total property.
     * 
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     */
    public void setTotal(int value) {
        this.total = value;
    }

    /**
     * Gets the value of the caracterAceptacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaracterAceptacion() {
        return caracterAceptacion;
    }

    /**
     * Sets the value of the caracterAceptacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaracterAceptacion(String value) {
        this.caracterAceptacion = value;
    }

    /**
     * Gets the value of the ultimoMensaje property.
     * 
     */
    public short getUltimoMensaje() {
        return ultimoMensaje;
    }

    /**
     * Sets the value of the ultimoMensaje property.
     * 
     */
    public void setUltimoMensaje(short value) {
        this.ultimoMensaje = value;
    }

    /**
     * Gets the value of the codMsgRespuesta property.
     * 
     */
    public short getCodMsgRespuesta() {
        return codMsgRespuesta;
    }

    /**
     * Sets the value of the codMsgRespuesta property.
     * 
     */
    public void setCodMsgRespuesta(short value) {
        this.codMsgRespuesta = value;
    }

    /**
     * Gets the value of the msgRespuesta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgRespuesta() {
        return msgRespuesta;
    }

    /**
     * Sets the value of the msgRespuesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgRespuesta(String value) {
        this.msgRespuesta = value;
    }

}
