
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorros;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServicioException complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServicioException">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codMRespuesta" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="mensaje" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServicioException", propOrder = {
    "codMRespuesta",
    "mensaje",
    "total"
})
public class ServicioException {

    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short codMRespuesta;
    @XmlElement(required = true, nillable = true)
    protected String mensaje;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer total;

    /**
     * Gets the value of the codMRespuesta property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCodMRespuesta() {
        return codMRespuesta;
    }

    /**
     * Sets the value of the codMRespuesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCodMRespuesta(Short value) {
        this.codMRespuesta = value;
    }

    /**
     * Gets the value of the mensaje property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Sets the value of the mensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensaje(String value) {
        this.mensaje = value;
    }

    /**
     * Gets the value of the total property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotal(Integer value) {
        this.total = value;
    }

}
